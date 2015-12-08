@echo off
set ArgumentOne=%1
set action=%ArgumentOne:"=%

if "%action%" == "create" (
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "mkuser %5" 
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "echo %5:%6 | chpasswd" 
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "pwdadm -f ADMIN %5" 

)
	
if "%action%" == "password" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "echo %5:%6 | chpasswd" 
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "pwdadm -f ADMIN %5" 
)


if "%action%" == "login" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "whoami" 
)


if "%action%" == "delete" (
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rmuser %5" 
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rm -rf /home/%5"
)


if "%action%" == "getlogin" (
	cscript /NoLogo .\Scripts\GetVar.js GeneratedPwd
	for /f "tokens=1,2 delims= " %%i in (GeneratedPwd) do .\Scripts\plink.exe -batch %2 -l %3 -pw %%i "ls > /dev/null" 2>resultAIX.txt
	del GeneratedPwd
)

if "%action%" == "group" (
rem        .\Scripts\plink.exe %2 -l %3 -pw %4 "mkuser %5"
rem	.\Scripts\plink.exe %2 -l %3 -pw %4 "echo %5:%6 | chpasswd"
rem        .\Scripts\plink.exe %2 -l %3 -pw %4 "pwdadm -f ADMIN %5"
		        .\Scripts\plink.exe %2 -l %3 -pw %4 "mkgroup %6"
		        .\Scripts\plink.exe %2 -l %3 -pw %4 "chgrpmem -m + %5 %6"
)

if "%action%" == "deletegrp" (
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rmuser %5" 
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rm -rf /home/%5"
		.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rmgroup %6"
)

EXIT %ERRORLEVEL%                       