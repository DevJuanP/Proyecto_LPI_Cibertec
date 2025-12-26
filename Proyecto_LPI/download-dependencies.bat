@echo off
setlocal enabledelayedexpansion

echo === Descargando Dependencias ===
echo.

REM Crear directorio para librerias
if not exist "lib" mkdir lib

echo Descargando dependencias...

REM Jakarta Servlet API 6.0.0
echo - Jakarta Servlet API...
curl -L -o lib/jakarta.servlet-api-6.0.0.jar "https://repo1.maven.org/maven2/jakarta/servlet/jakarta.servlet-api/6.0.0/jakarta.servlet-api-6.0.0.jar"
if errorlevel 1 (
    echo ERROR: Fallo la descarga de Jakarta Servlet API
    exit /b 1
)

REM Jakarta JSTL API 3.0.0
echo - Jakarta JSTL API...
curl -L -o lib/jakarta.servlet.jsp.jstl-api-3.0.0.jar "https://repo1.maven.org/maven2/jakarta/servlet/jsp/jstl/jakarta.servlet.jsp.jstl-api/3.0.0/jakarta.servlet.jsp.jstl-api-3.0.0.jar"
if errorlevel 1 (
    echo ERROR: Fallo la descarga de Jakarta JSTL API
    exit /b 1
)

REM Jakarta JSTL Implementation 3.0.1
echo - Jakarta JSTL Implementation...
curl -L -o lib/jakarta.servlet.jsp.jstl-3.0.1.jar "https://repo1.maven.org/maven2/org/glassfish/web/jakarta.servlet.jsp.jstl/3.0.1/jakarta.servlet.jsp.jstl-3.0.1.jar"
if errorlevel 1 (
    echo ERROR: Fallo la descarga de Jakarta JSTL Implementation
    exit /b 1
)

REM jBCrypt 0.4
echo - jBCrypt...
curl -L -o lib/jbcrypt-0.4.jar "https://repo1.maven.org/maven2/org/mindrot/jbcrypt/0.4/jbcrypt-0.4.jar"
if errorlevel 1 (
    echo ERROR: Fallo la descarga de jBCrypt
    exit /b 1
)

REM MySQL Connector/J 8.2.0
echo - MySQL Connector/J...
curl -L -o lib/mysql-connector-j-8.2.0.jar "https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.2.0/mysql-connector-j-8.2.0.jar"
if errorlevel 1 (
    echo ERROR: Fallo la descarga de MySQL Connector
    exit /b 1
)

echo.
echo === Dependencias descargadas en el directorio 'lib/' ===
dir lib
echo.
echo Listo! Ahora puedes compilar usando: compile.bat

endlocal
