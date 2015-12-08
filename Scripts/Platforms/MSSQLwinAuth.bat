if "%1" == "create" (
        net use \\%2\c$ /USER:%3 %4
        IF EXIST \\%2\c$\test GOTO TESTDIREXIST
	MD \\%2\c$\test
	:TESTDIREXIST
	COPY .\scripts\MSSQLwinAuth.vbs \\%2\c$\test /Y
    net use \\%2\c$ /D
	.\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 cscript.exe c:\test\MSSQLwinAuth.vbs //nologo //B //T:60 create localhost\%5 %6 %7
)
if "%1" == "password" (
    .\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 cscript.exe c:\test\MSSQLwinAuth.vbs //nologo //B //T:60 password localhost\%5 %6 %7
)
if "%1" == "delete" (
	.\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 cscript.exe c:\test\MSSQLwinAuth.vbs //nologo //B //T:60 delete localhost\%5 %6
    net use \\%2\c$ /USER:%3 %4        
    RMDIR /S /Q \\%2\c$\test
    net use \\%2\c$ /D
)
                       
EXIT %errorlevel%