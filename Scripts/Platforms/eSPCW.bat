SET LogFile=tpam.log
if "%1" == "create" (
	.\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 net user %5 %6 /Add
        PING 1.1.1.1 -n 1 -w 15000 >NUL 
	.\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 net localgroup "Administrators" /ADD %5 
	goto res
)
	
if "%1" == "password" (
        .\Scripts\psexec.exe -e  -i -h \\%2 -u %3 -p %4 net user %5 %6 
		goto res
)

if "%1" == "login" (
        .\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 whoami 
	goto res
)

if "%1" == "delete" (
	.\Scripts\psexec.exe -e  -i -h \\%2 -u %3 -p %4 net user %5 /Delete 
	goto res
)

if "%1" == "getlogin" (
	cscript /NoLogo .\Scripts\GetVar.js GeneratedPwd
	for /f "tokens=1,2 delims= " %%i in (GeneratedPwd) do .\Scripts\psexec.exe -e -h \\%2 -u "%3" -p %%i whoami
	del GeneratedPwd
	goto res
)

if "%1" == "corrupt" (
		if %6 == "1" (
rem			.\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 cmd /c copy %5 %5.old  >> %LogFile% 2>&1
			.\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4  -c .\Scripts\ChangeKey.bat %5 
			goto res
		)  ELSE ( 
rem		    .\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 cmd /c del /Q /F %5 
rem			.\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 cmd /c  ren %5.old authorized_keys 
             .\Scripts\psexec.exe -e -i -h \\%2 -u %3 -p %4 cmd /c copy %5.res %5  
			goto res
                ) 
)
:res                       
