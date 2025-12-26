@echo off
setlocal enabledelayedexpansion

echo === Empaquetando WAR ===
echo.

REM *** CONFIGURACION: Ajusta esta ruta segun tu instalacion de Tomcat 11 ***
REM Rutas comunes:
REM   - C:\Program Files\Apache Software Foundation\Tomcat 11.0\webapps
REM   - C:\apache-tomcat-11.0.2\webapps
REM   - C:\tomcat11\webapps
set "TOMCAT_WEBAPPS=C:\Program Files\Apache Software Foundation\Tomcat 11.0\webapps"

REM Verificar que existan las clases compiladas
if not exist "build\classes" (
    echo ERROR: No se encuentran las clases compiladas
    echo Ejecuta primero: compile.bat
    exit /b 1
)

REM Crear estructura WAR
echo Creando estructura del WAR...
if not exist "build\war\WEB-INF\classes" mkdir build\war\WEB-INF\classes
if not exist "build\war\WEB-INF\lib" mkdir build\war\WEB-INF\lib

REM Copiar clases compiladas
echo Copiando clases compiladas...
xcopy /E /I /Y build\classes\* build\war\WEB-INF\classes\ > nul
if errorlevel 1 (
    echo ERROR copiando clases
    exit /b 1
)

REM Copiar librerias (excepto servlet-api que la provee Tomcat)
echo Copiando librerias...
copy /Y lib\jbcrypt-0.4.jar build\war\WEB-INF\lib\ > nul
copy /Y lib\mysql-connector-j-8.2.0.jar build\war\WEB-INF\lib\ > nul
if errorlevel 1 (
    echo ERROR copiando librerias
    exit /b 1
)

REM Copiar archivos web (JSP, HTML, CSS, JS)
echo Copiando archivos web...
if exist "src\main\webapp" (
    xcopy /E /I /Y src\main\webapp\* build\war\ > nul
)

REM Crear web.xml basico si no existe
if not exist "build\war\WEB-INF\web.xml" (
    echo Creando web.xml...
    (
        echo ^<?xml version="1.0" encoding="UTF-8"?^>
        echo ^<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
        echo          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        echo          xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
        echo          https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
        echo          version="6.0"^>
        echo.
        echo     ^<display-name^>BibliotecaVirtual^</display-name^>
        echo     ^<description^>Sistema de Gestion Bibliotecaria^</description^>
        echo.
        echo     ^<!-- Welcome file list --^>
        echo     ^<welcome-file-list^>
        echo         ^<welcome-file^>index.jsp^</welcome-file^>
        echo         ^<welcome-file^>index.html^</welcome-file^>
        echo     ^</welcome-file-list^>
        echo.
        echo     ^<!-- Session timeout ^(en minutos^) --^>
        echo     ^<session-config^>
        echo         ^<session-timeout^>30^</session-timeout^>
        echo     ^</session-config^>
        echo ^</web-app^>
    ) > build\war\WEB-INF\web.xml
)

REM Crear archivo WAR
echo Creando archivo WAR...
cd build\war
jar -cvf ..\BibliotecaVirtual.war * > nul
cd ..\..

REM Verificar
if exist "build\BibliotecaVirtual.war" (
    echo.
    echo === WAR creado exitosamente ===
    echo.
    echo Archivo: build\BibliotecaVirtual.war
    for %%I in (build\BibliotecaVirtual.war) do echo Tamano: %%~zI bytes
    echo.

    REM Intentar desplegar a Tomcat 11
    if exist "%TOMCAT_WEBAPPS%" (
        echo Desplegando a Tomcat 11...
        copy /Y build\BibliotecaVirtual.war "%TOMCAT_WEBAPPS%\" > nul
        if errorlevel 1 (
            echo.
            echo ADVERTENCIA: No se pudo copiar al directorio de Tomcat.
            echo Puede que necesites ejecutar este script como Administrador.
            echo.
            echo Copia manual: copy build\BibliotecaVirtual.war "%TOMCAT_WEBAPPS%\"
        ) else (
            echo.
            echo === Desplegado exitosamente ===
            echo.
            echo Reinicia Tomcat 11 y accede a:
            echo http://localhost:8080/BibliotecaVirtual/
        )
    ) else (
        echo.
        echo NOTA: No se encontro Tomcat en: %TOMCAT_WEBAPPS%
        echo.
        echo Ajusta la variable TOMCAT_WEBAPPS en este script o copia manualmente:
        echo   copy build\BibliotecaVirtual.war "C:\ruta\a\tomcat11\webapps\"
        echo.
        echo Luego reinicia Tomcat 11 y accede a:
        echo   http://localhost:8080/BibliotecaVirtual/
    )
) else (
    echo ERROR creando el WAR
    exit /b 1
)

endlocal
