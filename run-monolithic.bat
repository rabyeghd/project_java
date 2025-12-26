@echo off
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-25.0.1.8-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

echo ========================================
echo Nettoyage et Compilation (Structure Standard src)...
echo ========================================

if exist "bin" rd /s /q bin
mkdir bin

javac -cp "src;lib/*" -d bin src/dao/*.java src/models/*.java src/util/*.java src/app/TrainingCenterProject.java
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERREUR] La compilation a echoue.
    pause
    exit /b 1
)

echo.
echo [OK] Compilation terminee avec succes.
echo.
echo Execution du projet...
echo.

java -cp "bin;lib/*" app.TrainingCenterProject

echo.
echo ========================================
pause
