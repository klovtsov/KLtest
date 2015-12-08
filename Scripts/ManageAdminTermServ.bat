@echo off
if "%1" == "create" (
	.\scripts\eSPCWtestexes\cmdkey.exe /generic:%2 /user:%3 /pass:%4 > nul
	start mstsc /v:%2 /w:800 /h:600 /admin
)
if "%1" == "remove" (
         .\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 rwinsta 2    
)
echo EXIT %errorlevel%