@echo off

set CLASSPATH=%cd%\lib\*

cd Scripts\GroovyJavaAPI

groovy %* 2>stderr.txt

