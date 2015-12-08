@echo off

if "%1" == "create" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "echo %6 | pw add user %5 -h 0 -m /home/%5" 

)

	
if "%1" == "password" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "echo %6 | pw mod user %5 -h 0" 
)



if "%1" == "login" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "whoami" 
)


if "%1" == "delete" (
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rmuser -y %5" 
)

if "%1" == "group" (
rem        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "echo %6 | pw add user %5 -h 0 -m /home/%5"
		.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "pw groupadd %6"
		.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "pw groupmod %6 -M %5"
)

if "%1" == "deletegrp" (
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rmuser -y  %5" 
		.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "pw group del %6"
)



EXIT %ERRORLEVEL%                       