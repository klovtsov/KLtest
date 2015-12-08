if "%1" == "create" (
    net use \\%2\c$ /USER:%3 %4
    IF EXIST \\%2\c$\test GOTO TESTDIREXIST
	MD \\%2\c$\test
	:TESTDIREXIST
    COPY .\scripts\eSPCWtestexes\InstallUtil.exe \\%2\c$\test /Y
	COPY .\scripts\eSPCWtestexes\TestServ.exe \\%2\c$\test /Y
	.\Scripts\eSPCWtestexes\ntrights.exe +r SeServiceLogonRight -u %5 -m \\%2
        net use \\%2\c$ /D
	.\Scripts\psexec.exe -e -i -s -h \\%2 -u %3 -p %4 c:\test\installutil.exe c:\test\TestServ.exe
	PING 1.1.1.1 -n 1 -w 5000 >NUL
    IF "%~7" == "." (
	 .\Scripts\psexec.exe -e -i -s -h \\%2 -u %3 -p %4 sc config QstTestService obj= %~7\%~5 password= %6
	) ELSE 	(
		.\Scripts\psexec.exe -e -i -s -h \\%2 -u %3 -p %4 sc config QstTestService obj= %5 password= %6
	)
)
	
if "%1" == "start" (
        .\Scripts\eSPCWtestexes\psservice.exe \\%2 -u %3 -p %4 start QstTestService
)
if "%1" == "stop" (
        .\Scripts\eSPCWtestexes\psservice.exe \\%2 -u %3 -p %4 stop QstTestService
)
if "%1" == "delete" (
	.\Scripts\psexec.exe -e -i -s -h \\%2 -u %3 -p %4 c:\test\installutil.exe /u c:\test\TestServ.exe
        net use \\%2\c$ /USER:%3 %4        
        RMDIR /S /Q \\%2\c$\test
        .\Scripts\eSPCWtestexes\ntrights.exe -r SeServiceLogonRight -u %5 -m \\%2
		net use \\%2\c$ /D
)                     
EXIT %errorlevel%