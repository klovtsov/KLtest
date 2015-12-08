@echo off
set ArgumentOne=%1
set action=%ArgumentOne:"=%


if "%action%" == "create" (
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "useradd %5 -s /bin/ash" 
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sed -e 's/\(%5:x:[0-9]*\):[0-9]*\(.*\)/\1:0\2/' /etc/passwd > /etc/passwd.1; mv /etc/passwd.1 /etc/passwd" 


)

	
if "%action%" == "password" (
rem	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sed -e 's/%5:!/%5:NoMatter/' /etc/shadow > /etc/shadow.1; mv /etc/shadow.1 /etc/shadow" 
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sed -e 's/\(%5.*\)\/sbin\/nologin/\1\/bin\/ash/' /etc/passwd > /etc/passwd.1; mv /etc/passwd.1 /etc/passwd" 
)



if "%action%" == "login" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "whoami"  
)


if "%action%" == "delete" (
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "userdel %5"  
 	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rm -rf /home/%5/"  
)

EXIT %ERRORLEVEL%                       