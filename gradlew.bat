@if "%DEBUG%"=="" @echo off
setlocal enabledelayedexpansion

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

set JAVA_EXE="java.exe"
set WRAPPER_JAR=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

"%JAVA_EXE%" -Dorg.gradle.appname=%APP_BASE_NAME% -classpath "%WRAPPER_JAR%" org.gradle.wrapper.GradleWrapperMain %*
if errorlevel 1 exit /b 1
