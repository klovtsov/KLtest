@echo off

if "%1" == "create" (
	.\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 net user %5 %6 /Add 
	PING 1.1.1.1 -n 1 -w 15000 >NUL
		if "%~3" NEQ "Administrateur" (
		.\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 net localgroup "Administrators" /ADD %5 2>nul
		rem .\Scripts\psexec.exe \\%2 -u %3 -p %4 net group "Domain Admins" /ADD %5 2>nul
		)
		if "%~3" == "Administrateur" (
		.\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 net localgroup "Administrateurs" /ADD %5 2>nul 
		)
)

	
if "%1" == "password" (
        .\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 net user %5 %6 
)


if "%1" == "login" (
        .\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 whoami 
)

if "%1" == "delete" (
	.\Scripts\psexec.exe -e  -i -h \\%2 -u %3 -p %4 net user %5 /Delete 
)

if "%1" == "getlogin" (
	cscript /NoLogo .\Scripts\GetVar.js GeneratedPwd
	for /f "tokens=1,2 delims= " %%i in (GeneratedPwd) do .\Scripts\psexec.exe -e -h \\%2 -u "%3" -p %%i whoami
	del GeneratedPwd
)

if "%1" == "group" (
	.\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 net user %5 %6 /Add
	PING 1.1.1.1 -n 1 -w 15000 >NUL
	.\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 net localgroup "Administrators" /ADD %5 2>nul
		.\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 net localgroup %7 /Add 
			.\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 net localgroup "%7" /ADD %5 
	rem .\Scripts\psexec.exe \\%2 -u %3 -p %4 net group "Domain Admins" /ADD %5 2>nul
)

	
if "%1" == "deletegrp" (
        .\Scripts\psexec.exe -e  -i -h \\%2 -u %3 -p %4 cmd /c (net user %5 /Delete ^& net localgroup %6 /Delete ^)
rem		.\Scripts\psexec.exe -e  -i -h \\%2 -u %3 -p %4 net localgroup %6 /Delete 
)

EXIT %ERRORLEVEL%                       
