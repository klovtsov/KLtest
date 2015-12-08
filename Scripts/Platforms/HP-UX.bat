@echo off
set ArgumentOne=%1
set action=%ArgumentOne:"=%

if "%action%" == "create" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "/usr/sbin/pwunconv 1>/dev/null; /usr/lbin/tsconvert 1>/dev/null; /usr/lbin/modprpw -v %3; /usr/lbin/modprpw -m umaxlntr=10 %3" 
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "useradd -m %5 1> /dev/null; /usr/lbin/modprpw -v %5; /usr/lbin/modprpw -m umaxlntr=10 %5" 
	cscript .\Scripts\Platforms\"HP-UX Telnet.js" passwd_trusted %2 %3 %4 %5 %6
)

	
if "%action%" == "password" (
	cscript .\Scripts\Platforms\"HP-UX Telnet.js" passwd_trusted %2 %3 %4 %5 %6

)



if "%action%" == "login" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "whoami" 
)


if "%action%" == "delete" (
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "userdel -r %5 2>/dev/null" 
)

if "%action%" == "group" (
 		        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "groupadd %6"
		        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "usermod -G %6 %5"
)

if "%action%" == "deletegrp" (
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "userdel -r %5 2>/dev/null"
		.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "groupdel %6"
)


EXIT %ERRORLEVEL%                       