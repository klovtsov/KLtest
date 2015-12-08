@echo off
set ArgumentOne=%1
set action=%ArgumentOne:"=%

if "%action%" == "create" (
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "mkhmcusr -u %5 -a hmcviewer --passwd %6"  
)

	
if "%action%" == "password" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "chhmcusr -u %5 -t passwd -v %6" 
)



if "%action%" == "login" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "whoami"  
)


if "%action%" == "delete" (
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rmhmcusr -u %5"  
)


if "%action%" == "getlogin" (
	cscript /NoLogo .\Scripts\GetVar.js GeneratedPwd
	for /f "tokens=1,2 delims= " %%i in (GeneratedPwd) do .\Scripts\plink.exe -batch %2 -l %3 -pw %%i "ls" 2>resultHMC.txt
	del GeneratedPwd
)

EXIT %ERRORLEVEL%                       