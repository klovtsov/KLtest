rem @echo off 

if "%1" == "create" (
	.\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 net user %5 %6 /Add
        PING 1.1.1.1 -n 1 -w 15000 >NUL
	.\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 net group "Domain Admins" /ADD %5 
) else call .\Scripts\Platforms\eSPCW.bat %1 %2 %3 %4 %5 %6


EXIT %ERRORLEVEL%                       