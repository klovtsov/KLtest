@echo off

set JAVA_OPTS="-Xmx1024M"

rem We need these variables for smooth software upgrade on the dispatchers
set OldFirefox=19.0.2
set OldWebDriver=2.32.0
set NewFirefox=28.0
set NewWebDriver=2.41.0
set FirefoxVersion=firefox.version

reg query "HKEY_LOCAL_MACHINE\SOFTWARE\Mozilla\Mozilla Firefox" > %FirefoxVersion%

for /f "tokens=3" %%i in ('type %FirefoxVersion%^|findstr /r "CurrentVersion"') do (
	set ffver=%%i
)

if %ffver% == %OldFirefox% (
	set WebDriverVersion=%OldWebDriver%
) else (
	set WebDriverVersion=%NewWebDriver%
)

set groovyCP="selenium-server-standalone-%WebDriverVersion%.jar;..\..\lib\sikuli-script.jar"

del %FirefoxVersion%

cd Scripts\WebDriver

groovy -cp %groovyCP% %*

