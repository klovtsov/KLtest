if "%1" == "create" (
	.\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 net user %5 %6 /Add
	.\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 net group "Domain Admins" /ADD %5 2>nul
)ELSE (
	call .\Scripts\Platforms\Windows.bat %1 %2 %3 %4 %5 %6
)
                      
EXIT %errorlevel%