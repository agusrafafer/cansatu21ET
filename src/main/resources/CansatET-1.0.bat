@echo off
rem title CansatET
rem nircmd.exe win hide ititle "CansatET"
set jverExp=19
if defined %JAVA_HOME% goto javaNoInstalado

for /f tokens^=2-5^ delims^=.-_^" %%j in ('java -fullversion 2^>^&1') do set "jver=%%j%%k"

echo %jverExp%
echo %jver%
if %jverExp% gtr %jver% goto versionJavaMal

if not exist CansatET-1.0.jar goto jarNoExiste

java -jar CansatET-1.0.jar
goto:eof

:javaNoInstalado
echo MSGBOX "Error: Java no esta instalado en el equipo"  >> %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
goto:eof

:versionJavaMal
echo MSGBOX "Error: La version de Java debe ser 9 o superior. Version recomendada: https://www.azul.com/downloads/?version=java-13-mts&os=linux&package=jdk"  >> %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
goto:eof

:jarNoExiste
echo MSGBOX "Error: No existe el archivo ejecutable" >> %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
goto:eof