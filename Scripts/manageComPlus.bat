if "%1" == "create" (
        net use \\%2\c$ /USER:%3 %4
        IF EXIST \\%2\c$\test GOTO TESTDIREXIST
	MD \\%2\c$\test
	:TESTDIREXIST
    COPY .\scripts\eSPCWtestexes\MyServer.dll \\%2\c$\test /Y
	COPY .\scripts\eSPCWtestexes\ComObjectsManager.vbs \\%2\c$\test /Y
    .\Scripts\eSPCWtestexes\ntrights.exe +r SeBatchLogonRight -u %5 -m \\%2
    net use \\%2\c$ /D
	.\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 cscript.exe c:\test\ComObjectsManager.vbs  //nologo //B //T:60 create QstTestApp %5 %6 c:\test\MyServer.dll
)
	
if "%1" == "start" (
        .\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 cscript.exe c:\test\ComObjectsManager.vbs //nologo //B //T:60 start QstTestApp
)
if "%1" == "stop" (
        .\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 cscript.exe c:\test\ComObjectsManager.vbs //nologo //B //T:60 stop QstTestApp
)
if "%1" == "delete" (
	.\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 cscript.exe c:\test\ComObjectsManager.vbs //nologo //B //T:60 delete QstTestApp
        net use \\%2\c$ /USER:%3 %4        
        RMDIR /S /Q \\%2\c$\test
        .\Scripts\eSPCWtestexes\ntrights.exe -r SeBatchLogonRight -u %5 -m \\%2
        net use \\%2\c$ /D
)
                       
EXIT %errorlevel%