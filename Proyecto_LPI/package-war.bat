@echo off
setlocal enabledelayedexpansion

echo === Compilando Proyecto BibliotecaVirtual ===
echo.

REM Verificar que existan las dependencias
if not exist "lib" (
    echo ERROR: No se encuentra el directorio 'lib/'
    echo Ejecuta primero: download-dependencies.bat
    exit /b 1
)

REM Verificar dependencias requeridas
set "MISSING_DEPS=0"

if not exist "lib\jakarta.servlet-api-6.0.0.jar" (
    echo ERROR: Falta lib\jakarta.servlet-api-6.0.0.jar
    set "MISSING_DEPS=1"
)

if not exist "lib\jbcrypt-0.4.jar" (
    echo ERROR: Falta lib\jbcrypt-0.4.jar
    set "MISSING_DEPS=1"
)

if not exist "lib\mysql-connector-j-8.2.0.jar" (
    echo ERROR: Falta lib\mysql-connector-j-8.2.0.jar
    set "MISSING_DEPS=1"
)

if "%MISSING_DEPS%"=="1" (
    echo.
    echo Ejecuta: download-dependencies.bat
    exit /b 1
)

REM Construir classpath (usar ; en Windows en lugar de :)
set "CLASSPATH=lib\jakarta.servlet-api-6.0.0.jar;lib\jbcrypt-0.4.jar;lib\mysql-connector-j-8.2.0.jar"

REM Limpiar compilacion anterior
echo Limpiando compilacion anterior...
if exist "build\classes" rmdir /s /q build\classes
if exist "build\war" rmdir /s /q build\war
if exist "build\*.war" del /q build\*.war
if not exist "build\classes" mkdir build\classes

REM Compilar en el orden correcto
echo.
echo Compilando modelos...
javac -encoding UTF-8 -cp "%CLASSPATH%" -d build/classes src/main/java/model/*.java
if errorlevel 1 (
    echo ERROR compilando modelos
    exit /b 1
)

echo Compilando utilidades...
javac -encoding UTF-8 -cp "%CLASSPATH%;build/classes" -d build/classes src/main/java/util/*.java
if errorlevel 1 (
    echo ERROR compilando utilidades
    exit /b 1
)

echo Compilando conexion a BD...
javac -encoding UTF-8 -cp "%CLASSPATH%;build/classes" -d build/classes src/main/java/connection/*.java
if errorlevel 1 (
    echo ERROR compilando conexion
    exit /b 1
)

echo Compilando repositorios...
javac -encoding UTF-8 -cp "%CLASSPATH%;build/classes" -d build/classes src/main/java/repository/*.java
if errorlevel 1 (
    echo ERROR compilando repositorios
    exit /b 1
)

echo Compilando DTOs...
javac -encoding UTF-8 -cp "%CLASSPATH%;build/classes" -d build/classes src/main/java/dto/**/*.java
if errorlevel 1 (
    echo ERROR compilando DTOs
    exit /b 1
)

echo Compilando servicios...
javac -encoding UTF-8 -cp "%CLASSPATH%;build/classes" -d build/classes src/main/java/service/*.java
if errorlevel 1 (
    echo ERROR compilando servicios
    exit /b 1
)

echo Compilando core...
javac -encoding UTF-8 -cp "%CLASSPATH%;build/classes" -d build/classes src/main/java/core/*.java
if errorlevel 1 (
    echo ERROR compilando core
    exit /b 1
)

echo Compilando filter...
javac -encoding UTF-8 -cp "%CLASSPATH%;build/classes" -d build/classes src/main/java/filter/*.java
if errorlevel 1 (
    echo ERROR compilando filter
    exit /b 1
)

echo Compilando controladores...
javac -encoding UTF-8 -cp "%CLASSPATH%;build/classes" -d build/classes src/main/java/controller/*.java
if errorlevel 1 (
    echo ERROR compilando controladores
    exit /b 1
)

echo.
echo === Compilacion exitosa ===
echo.
echo Clases compiladas en: build\classes\
echo.
echo Para crear el WAR, ejecuta: package-war.bat

endlocal
