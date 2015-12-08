if "%1" == "create" (
	.\Scripts\psexec.exe  \\%2 -u %3 -p %4 net user %5 %6 /Add
	.\Scripts\psexec.exe \\%2 -u %3 -p %4 net group "Domain Admins" /ADD %5 2>nul
)

	
if "%1" == "password" (
        .\Scripts\psexec.exe  \\%2 -u %3 -p %4 net user %5 %6
)


if "%1" == "login" (
        .\Scripts\psexec.exe  \\%2 -u %3 -p %4 "dir > nul"
)

if "%1" == "delete" (
	.\Scripts\psexec.exe \\%2 -u %3 -p %4 net user %5 /Delete
)

if "%1" == "getlogin" (
	cscript /NoLogo .\Scripts\GetVar.js GeneratedPwd
	for /f "tokens=1,2 delims= " %%i in (GeneratedPwd) do .\Scripts\psexec.exe  \\%2 -u "%3" -p %%i "dir > nul"
	del GeneratedPwd
)

                       
EXIT %errorlevel%