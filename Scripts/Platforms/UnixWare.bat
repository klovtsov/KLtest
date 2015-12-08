@echo off

if "%1" == "create" (
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "useradd -m %5" 
.\scripts\plink %2 -l %3 -pw %4 "echo '#!/usr/bin/expect -f \n spawn passwd [lindex $argv 0] \n set password [lindex $argv 1] \n expect \"password:\" \n send \"$password\\r\" \n expect \"password:\" \n send \"$password\\r\" \n expect eof' | cat > /home/expectscript"
.\scripts\plink %2 -l %3 -pw %4 "chmod +x /home/expectscript"
.\scripts\plink %2 -l %3 -pw %4 "/home/expectscript %5 %6"

)

	
if "%1" == "password" (
	.\scripts\plink %2 -l %3 -pw %4 "/home/expectscript %5 %6" 
)



if "%1" == "login" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "/usr/ucb/whoami" 
)


if "%1" == "delete" (
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "userdel -r %5" 
		.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rm -f /home/expectscript" 
)


