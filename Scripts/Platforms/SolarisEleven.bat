@echo on
set ArgumentOne=%1
set action=%ArgumentOne:"=%
set expectScript=/tmp/expectScript

if "%action%" == "create" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sudo /usr/sbin/useradd -d /export/home/%~5 -m %~5 1>/dev/null 2>/tmp/tmp2; cat /tmp/tmp2" 

	.\Scripts\plink %2 -l %3 -pw %4 "echo '#!/usr/bin/expect -f \n spawn sudo passwd [lindex $argv 0] \n set password [lindex $argv 1] \n expect \"assword:\" \n send \"$password\\r\" \n expect \"assword:\" \n send \"$password\\r\" \n expect eof' | cat > %expectScript%"
	.\Scripts\plink %2 -l %3 -pw %4 "chmod +x %expectScript%"
rem .\Scripts\plink %2 -l %3 -pw %4 "%expectScript% %5 %6"

)

if "%action%" == "password" (
	.\Scripts\plink %2 -l %3 -pw %4 "%expectScript% %5 %6" 
)



if "%action%" == "login" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "whoami"  
)


if "%action%" == "delete" (
	rem .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sudo rm -f -R /export/home/%5"
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sudo /usr/sbin/userdel -r %5"  
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rm -f %expectScript%" 

)

if "%action%" == "group" (
	.\Scripts\plink.exe  -batch %2 -l %3 -pw %4 "sudo useradd -d /export/home/%5 -m %5 1>/dev/null 2>tmp2; cat tmp2 | grep -v '64 blocks'"
rem	.\Scripts\plink.exe  -batch %2 -l %3 -pw %4 "sudo sed -e 's/%5:\*LK\*/%5:9EmcTtiw8ONmY/' /etc/shadow > /etc/shadow.1; mv /etc/shadow.1 /etc/shadow"
	.\Scripts\plink.exe  -batch %2 -l %3 -pw %4 "sudo groupadd %7"
	.\Scripts\plink.exe  -batch %2 -l %3 -pw %4 "sudo usermod -G %7 %5"
)

if "%action%" == "deletegrp" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sudo rm -f -R /export/home/%5"
	.\Scripts\plink.exe  -batch %2 -l %3 -pw %4 "sudo userdel %5"
	.\Scripts\plink.exe  -batch %2 -l %3 -pw %4 "sudo groupdel %6"
)
	


EXIT %ERRORLEVEL%                       
