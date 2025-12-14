#!/bin/bash

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}=== Compilando Proyecto BibliotecaVirtual ===${NC}"

# Verificar que existan las dependencias
if [ ! -d "lib" ]; then
    echo -e "${RED}Error: No se encuentra el directorio 'lib/'${NC}"
    echo -e "${YELLOW}Ejecuta primero: bash download-dependencies.sh${NC}"
    exit 1
fi

# Verificar dependencias
REQUIRED_JARS=(
    "lib/jakarta.servlet-api-6.0.0.jar"
    "lib/jbcrypt-0.4.jar"
    "lib/mysql-connector-j-8.2.0.jar"
)

for jar in "${REQUIRED_JARS[@]}"; do
    if [ ! -f "$jar" ]; then
        echo -e "${RED}Error: Falta la dependencia $jar${NC}"
        echo -e "${YELLOW}Ejecuta: bash download-dependencies.sh${NC}"
        exit 1
    fi
done

# Construir classpath
CLASSPATH="lib/jakarta.servlet-api-6.0.0.jar:lib/jbcrypt-0.4.jar:lib/mysql-connector-j-8.2.0.jar"

# Limpiar compilación anterior
echo -e "${YELLOW}Limpiando compilación anterior...${NC}"
rm -rf build/classes/*
rm -rf build/war
rm -f build/*.war
mkdir -p build/classes

# Compilar en el orden correcto
echo -e "${YELLOW}Compilando modelos...${NC}"
javac -encoding UTF-8 -cp "$CLASSPATH" -d build/classes src/main/java/model/*.java
if [ $? -ne 0 ]; then
    echo -e "${RED}Error compilando modelos${NC}"
    exit 1
fi

echo -e "${YELLOW}Compilando utilidades...${NC}"
javac -encoding UTF-8 -cp "$CLASSPATH:build/classes" -d build/classes src/main/java/util/*.java
if [ $? -ne 0 ]; then
    echo -e "${RED}Error compilando utilidades${NC}"
    exit 1
fi

echo -e "${YELLOW}Compilando conexión a BD...${NC}"
javac -encoding UTF-8 -cp "$CLASSPATH:build/classes" -d build/classes src/main/java/connection/*.java
if [ $? -ne 0 ]; then
    echo -e "${RED}Error compilando conexión${NC}"
    exit 1
fi

echo -e "${YELLOW}Compilando repositorios...${NC}"
javac -encoding UTF-8 -cp "$CLASSPATH:build/classes" -d build/classes src/main/java/repository/*.java
if [ $? -ne 0 ]; then
    echo -e "${RED}Error compilando repositorios${NC}"
    exit 1
fi

echo -e "${YELLOW}Compilando servicios...${NC}"
javac -encoding UTF-8 -cp "$CLASSPATH:build/classes" -d build/classes src/main/java/service/*.java
if [ $? -ne 0 ]; then
    echo -e "${RED}Error compilando servicios${NC}"
    exit 1
fi

echo -e "${YELLOW}Compilando core...${NC}"
javac -encoding UTF-8 -cp "$CLASSPATH:build/classes" -d build/classes src/main/java/core/*.java
if [ $? -ne 0 ]; then
    echo -e "${RED}Error compilando core${NC}"
    exit 1
fi

echo -e "${YELLOW}Compilando filter...${NC}"
javac -encoding UTF-8 -cp "$CLASSPATH:build/classes" -d build/classes src/main/java/filter/*.java
if [ $? -ne 0 ]; then
    echo -e "${RED}Error compilando filter${NC}"
    exit 1
fi

echo -e "${YELLOW}Compilando controladores...${NC}"
javac -encoding UTF-8 -cp "$CLASSPATH:build/classes" -d build/classes src/main/java/controller/*.java
if [ $? -ne 0 ]; then
    echo -e "${RED}Error compilando controladores${NC}"
    exit 1
fi

echo -e "${GREEN}✓ Compilación exitosa${NC}"
echo ""
echo -e "${YELLOW}Clases compiladas en: build/classes/${NC}"
echo ""
echo -e "${GREEN}Para crear el WAR, ejecuta: bash package-war.sh${NC}"
