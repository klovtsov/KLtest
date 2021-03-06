@echo off
set ArgumentOne=%1
set action=%ArgumentOne:"=%

if "%action%" == "create" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "/usr/lbin/tsconvert -r" 
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "useradd -m %5 1> /dev/null" 
	cscript .\Scripts\Platforms\"HP-UX Telnet.js" passwd %2 %3 %4 %5 %6
)

	
if "%action%" == "password" (
	cscript .\Scripts\Platforms\"HP-UX Telnet.js" passwd %2 %3 %4 %5 %6
)



if "%action%" == "login" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "whoami"  
)


if "%action%" == "delete" (
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "userdel -r %5 2>/dev/null"  
)


if "%action%" == "getlogin" (
	cscript /NoLogo .\Scripts\GetVar.js GeneratedPwd
	for /f "tokens=1,2 delims= " %%i in (GeneratedPwd) do .\Scripts\plink.exe -batch %2 -l %3 -pw %%i "ls > /dev/null" 
	del GeneratedPwd
)

EXIT %ERRORLEVEL%                       