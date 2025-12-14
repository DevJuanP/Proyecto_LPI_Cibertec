#!/bin/bash

# Crear directorio para librerías
mkdir -p lib

echo "Descargando dependencias..."

# Jakarta Servlet API 6.0.0
echo "- Jakarta Servlet API..."
curl -L -o lib/jakarta.servlet-api-6.0.0.jar \
  "https://repo1.maven.org/maven2/jakarta/servlet/jakarta.servlet-api/6.0.0/jakarta.servlet-api-6.0.0.jar"

# jBCrypt 0.4
echo "- jBCrypt..."
curl -L -o lib/jbcrypt-0.4.jar \
  "https://repo1.maven.org/maven2/org/mindrot/jbcrypt/0.4/jbcrypt-0.4.jar"

# MySQL Connector/J 8.2.0
echo "- MySQL Connector/J..."
curl -L -o lib/mysql-connector-j-8.2.0.jar \
  "https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.2.0/mysql-connector-j-8.2.0.jar"

echo ""
echo "Dependencias descargadas en el directorio 'lib/':"
ls -lh lib/

echo ""
echo "¡Listo! Ahora puedes compilar usando el script compile.sh"
