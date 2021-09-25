#!/bin/sh
HayJava=$(java -version 2>&1 | grep "Runtime Environment" | wc -l)
ArchJar=./CansatET-1.0.jar
JavaVer=$(java -version 2>&1 | sed -n ';s/.* version "\(.*\)\.\(.*\)\..*".*/\1\2/p;')
if [ $HayJava = 1 ]; then
	if [ "$JavaVer" -gt "9" ]; then
	    if [ -f "$ArchJar" ]; then
		java -jar ./CansatET-1.0.jar
	    else
		echo "Error: No existe el archivo ejecutable"
		xmessage -center "Error: No existe el archivo ejecutable" -buttons Ok:0
	    fi
	else 
		echo 'Error: La version de Java debe ser 9 o superior. Version recomendada: https://www.azul.com/downloads/?version=java-13-mts&os=linux&package=jdk' 
		xmessage -center "Error: La version de Java debe ser 9 o superior. Version recomendada: https://www.azul.com/downloads/?version=java-13-mts&os=linux&package=jdk" -buttons Ok:0
	fi
else 
    echo 'Error: Java no esta instalado en el equipo' 
    xmessage -center "Error: Java no esta instalado en el equipo" -buttons Ok:0
fi

