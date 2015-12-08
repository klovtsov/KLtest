@echo off
set ArgumentOne=%1
set action=%ArgumentOne:"=%

if "%action%" == "create" (
    .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "useradd -m %5" 
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "echo %5:%6 | chpasswd" 

)

	
if "%action%" == "password" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "echo %5:%6 | chpasswd" 
)



if "%action%" == "login" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "whoami" 
)


if "%action%" == "delete" (
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "userdel %5 2>/dev/null" 
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rm -rf /home/%5/"
)

if "%action%" == "group" (
rem        .\Scripts\plink.exe %2 -l %3 -pw %4 "useradd -m %5"
rem	.\Scripts\plink.exe %2 -l %3 -pw %4 "echo %5:%6 | chpasswd"
		        .\Scripts\plink.exe %2 -l %3 -pw %4 "groupadd %6"
		        .\Scripts\plink.exe %2 -l %3 -pw %4 "usermod -G %6 %5"
)

if "%action%" == "deletegrp" (
        .\Scripts\plink.exe %2 -l %3 -pw %4 "userdel %5 2>/dev/null"
	.\Scripts\plink.exe %2 -l %3 -pw %4 "rm -rf /home/%5/"
		.\Scripts\plink.exe %2 -l %3 -pw %4 "groupdel %6"
)
