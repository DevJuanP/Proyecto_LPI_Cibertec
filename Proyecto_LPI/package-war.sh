#!/bin/bash

# Colores
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo -e "${GREEN}=== Empaquetando WAR ===${NC}"

# Verificar que existan las clases compiladas
if [ ! -d "build/classes" ]; then
    echo -e "${RED}Error: No se encuentran las clases compiladas${NC}"
    echo -e "${YELLOW}Ejecuta primero: bash compile.sh${NC}"
    exit 1
fi

# Crear estructura WAR
echo -e "${YELLOW}Creando estructura del WAR...${NC}"
mkdir -p build/war/WEB-INF/classes
mkdir -p build/war/WEB-INF/lib

# Copiar clases compiladas
echo -e "${YELLOW}Copiando clases compiladas...${NC}"
cp -r build/classes/* build/war/WEB-INF/classes/

# Copiar librerías (excepto servlet-api que la provee Tomcat)
echo -e "${YELLOW}Copiando librerías...${NC}"
cp lib/jbcrypt-0.4.jar build/war/WEB-INF/lib/
cp lib/mysql-connector-j-8.2.0.jar build/war/WEB-INF/lib/

# Copiar archivos web (JSP, HTML, CSS, JS)
echo -e "${YELLOW}Copiando archivos web...${NC}"
if [ -d "src/main/webapp" ]; then
    cp -r src/main/webapp/* build/war/
fi

# Crear web.xml básico si no existe
if [ ! -f "build/war/WEB-INF/web.xml" ]; then
    echo -e "${YELLOW}Creando web.xml...${NC}"
    cat > build/war/WEB-INF/web.xml << 'EOF'
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
         https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
         version="6.0">
    
    <display-name>BibliotecaVirtual</display-name>
    <description>Sistema de Gestión Bibliotecaria</description>
    
    <!-- Welcome file list -->
    <welcome-file-list>
        <welcome-file>index.jsp</welcome-file>
        <welcome-file>index.html</welcome-file>
    </welcome-file-list>
    
    <!-- Session timeout (en minutos) -->
    <session-config>
        <session-timeout>30</session-timeout>
    </session-config>
</web-app>
EOF
fi

# Crear archivo WAR
echo -e "${YELLOW}Creando archivo WAR...${NC}"
cd build/war
jar -cvf ../BibliotecaVirtual.war *
cd ../..

# Verificar
if [ -f "build/BibliotecaVirtual.war" ]; then
    echo -e "${GREEN}✓ WAR creado exitosamente${NC}"
    echo ""
    echo -e "${GREEN}Archivo: build/BibliotecaVirtual.war${NC}"
    echo -e "${YELLOW}Tamaño: $(du -h build/BibliotecaVirtual.war | cut -f1)${NC}"
    echo ""
    echo -e "${GREEN}Para desplegar:${NC}"
    echo -e "  1. Copia el WAR a tu servidor Tomcat: sudo cp build/BibliotecaVirtual.war /var/lib/tomcat10/webapps/"
    echo -e "  2. Reinicia Tomcat"
    echo -e "  3. Accede a: http://localhost:8080/BibliotecaVirtual/"

    # Desplegar a tomcat10
    cp build/BibliotecaVirtual.war /var/lib/tomcat10/webapps/
else
    echo -e "${RED}Error creando el WAR${NC}"
    exit 1
fi
