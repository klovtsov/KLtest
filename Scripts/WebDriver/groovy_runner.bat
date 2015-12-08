@echo off

set JAVA_OPTS="-Xmx1024M"

set WebDriver=2.41.0

set groovyCP="selenium-server-standalone-%WebDriver%.jar;sikulixapi.jar;..\..\lib\mail.jar"

cd Scripts\WebDriver

groovy -cp %groovyCP% %*

