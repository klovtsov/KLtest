if "%1" == "remove" (
    .\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 cmd /c  del %5 
	goto res
)

if "%1" == "restore" (
    .\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 cmd /c copy %5.res %5  
	goto res 
)

if "%1" == "removehost" (
    net use \\%2\c$ /USER:%3 %4
    IF EXIST \\%2\c$\test GOTO TESTDIREXIST
	MD \\%2\c$\test
	:TESTDIREXIST
	COPY .\scripts\eSPCWtestexes\RemIpFromKey.vbs \\%2\c$\test /Y
    net use \\%2\c$ /D
    .\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 cscript.exe c:\test\RemIpFromKey.vbs %5 %6 
)
:res