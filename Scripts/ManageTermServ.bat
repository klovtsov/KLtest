@echo off
if "%1" == "create" (
	.\scripts\eSPCWtestexes\cmdkey.exe /generic:%2 /user:%3 /pass:%4 > nul
	start mstsc /v:%2 /w:800 /h:600
    goto res
)

if "%1" == "check" (
     .\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4  -cf .\Scripts\eSPCWtestexes\startqwinsta.bat %5 %6 >nul
     if "%6" == "Disc" (
         .\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 rwinsta 1    
     )
)
:res
echo EXIT %errorlevel%