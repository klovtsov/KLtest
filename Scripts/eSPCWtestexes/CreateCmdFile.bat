@echo off
set drive=%3
set drive=%drive:"=%
if "%1" == "create" (
        echo \\%2\%drive%$
        net use \\%2\%drive%$ /USER:%4 %5
        IF EXIST \\%2\%drive%$\test GOTO TESTDIREXIST
	MD \\%2\%drive%$\test
	:TESTDIREXIST
        echo @echo off > \\%2\%drive%$\test\%6.bat
        if "%7" neq "" (
            echo @ping localhost -w 1000 -n %7 ^> nul >> \\%2\%drive%$\test\%6.bat
        )
        echo echo TestBefore ^> %drive%:\test\%6.log >> \\%2\%drive%$\test\%6.bat
        net use \\%2\%drive%$ /D
)
	
if "%1" == "delete" (
	net use \\%2\%drive%$ /USER:%4 %5        
        RMDIR /S /Q \\%2\%drive%$\test
        net use \\%2\%drive%$ /D
)
                       
EXIT %errorlevel%