@echo off

set ArgumentOne=%1
set action=%ArgumentOne:"=%

SET str=%5
set sst=%str:~1,-1%

if "%action%" == "create" (
            .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "useradd -d /export/home/%sst% -m %5 1>/dev/null 2>tmp2; cat tmp2" 
)

if "%action%" == "password" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sed -e 's/%5:\*LK\*/%5:9EmcTtiw8ONmY/' /etc/shadow > /etc/shadow.1; mv /etc/shadow.1 /etc/shadow" 
)



if "%action%" == "login" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "/usr/ucb/whoami"  
)


if "%action%" == "delete" (
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rm -f -R /export/home/%sst%"
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "userdel %5"  

)

if "%action%" == "group" (
            .\Scripts\plink.exe  -batch %2 -l %3 -pw %4 "useradd -d /export/home/%sst% -m %5 1>/dev/null 2>tmp2; cat tmp2 | grep -v '64 blocks'"
			.\Scripts\plink.exe  -batch %2 -l %3 -pw %4 "sed -e 's/%5:\*LK\*/%5:9EmcTtiw8ONmY/' /etc/shadow > /etc/shadow.1; mv /etc/shadow.1 /etc/shadow"
		        .\Scripts\plink.exe  -batch %2 -l %3 -pw %4 "groupadd %7"
		        .\Scripts\plink.exe  -batch %2 -l %3 -pw %4 "usermod -G %7 %5"
)

if "%action%" == "deletegrp" (
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rm -f -R /export/home/%sst%"
        .\Scripts\plink.exe  -batch %2 -l %3 -pw %4 "userdel %5"
		.\Scripts\plink.exe  -batch %2 -l %3 -pw %4 "groupdel %6"
)
	


EXIT %ERRORLEVEL%                       