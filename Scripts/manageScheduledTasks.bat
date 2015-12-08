@echo on
if "%1" == "create" (
    net use \\%2\c$ /USER:%3 %4
	.\Scripts\eSPCWtestexes\ntrights.exe +r SeBatchLogonRight -u %6 -m \\%2
	net use \\%2\c$ /D
	.\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 SCHTASKS /Create /RU %6 /RP %7 /SC DAILY /TN %5 /TR notepad /F >nul
)	
if "%1" == "run" (
    SCHTASKS /Run /S %2 /U %3 /P %4 /TN %5
)
if "%1" == "stop" (
    SCHTASKS /End /S %2 /U %3 /P %4 /TN %5
)
if "%1" == "delete" (
	SCHTASKS /delete /S %2 /U %3 /P %4 /TN %5 /F
	net use \\%2\c$ /USER:%3 %4
	.\Scripts\eSPCWtestexes\ntrights.exe -r SeBatchLogonRight -u %6 -m \\%2
	net use \\%2\c$ /D
)
if "%1" == "list" (
    schtasks /query /S %2 /U %3 /P %4 /FO table /NH > %temp%\STtemp.txt
    findstr /r "%5.*%6" %temp%\STtemp.txt
)
echo EXIT %errorlevel%