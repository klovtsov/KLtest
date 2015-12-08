@echo off
set ArgumentOne=%1
set action=%ArgumentOne:"=%

if "%action%" == "create" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "useradd -m %5" 
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "mkdir /home/%5/.ssh" 
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "mkdir /home/%5/%6" 

)

	


if "%action%" == "delete" (
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "userdel %5 2>/dev/null" 
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rm -rf /home/%5/"
)



EXIT %ERRORLEVEL%                       