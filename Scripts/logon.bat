@echo off

if %6 == "Nix" ( goto :start
)

if %6 == "fbsd" ( goto :start
)

if %6 == "Aix" ( goto :start
)

if %6 == "Sol" ( goto :start
)

if %6 == "hpux" ( goto :start
)


SET str=%6
set sst=%str:~1,-1%
set sstc=c:\%sst%
set sstcwad=c:\psmtmp\%sst%

SET strr=%7
set sstt=%strr:~1,-1%
set sstcc=c:\%sstt%
set sstccwad=c:\psmtmp\%sstt%


net use \\%2\c$ /USER:%3 %4
:start
if "%1" == "create" if %6 == "Winad" (MD \\%2\c$\psmtmp
.\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 cmd /c "Echo echo testsuccessfully ^> %sstccwad%testpsmlog > \\%2\netlogon\%sstt%logonscript.bat"
.\Scripts\psexec.exe -e -d -h \\%2 -u %3 -p %4 net user %5 /scriptpath:%sstt%logonscript.bat 2>nul
net use \\%2\c$ /D
)

if "%1" == "create" if %6 == "Win" if exist \\%2\c$\netlogon (.\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 cmd /c "Echo echo testsuccessfully ^> %sstcc%testpsmlog > c:\netlogon\%sstt%logonscript.bat"
.\Scripts\psexec.exe -e -d -h \\%2 -u %3 -p %4 net user %5 /scriptpath:%sstt%logonscript.bat
net use \\%2\c$ /D
)

if "%1" == "create" if %6 == "Win" if not exist \\%2\c$\netlogon (MD \\%2\c$\netlogon
.\Scripts\psexec.exe -e -d -h \\%2 -u %3 -p %4 net share netlogon=c:\netlogon
.\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 cmd /c "Echo echo testsuccessfully ^> %sstcc%testpsmlog > c:\netlogon\%sstt%logonscript.bat"
.\Scripts\psexec.exe -e -d -h \\%2 -u %3 -p %4 net user %5 /scriptpath:%sstt%logonscript.bat
net use \\%2\c$ /D
)

if "%1" == "create" if %6 == "Nix" ( .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "echo 'touch /home/%5/successfully' | cat - /home/%5/.bashrc > temp && mv -f temp /home/%5/.bashrc" 
									 .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "cat /home/%5/.bashrc | awk 'NR == 1'"
)

if "%1" == "create" if %6 == "Aix" ( .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "echo 'touch /home/%5/successfully' | cat - /home/%5/.profile > temp && mv -f temp /home/%5/.profile" 
									 .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "cat /home/%5/.profile | awk 'NR == 1'"
)

if "%1" == "create" if %6 == "QAS" (.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "mkdir /home/%5"  
									.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "chown %5 /home/%5"  
									.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "echo 'touch /home/%5/successfully' > /home/%5/.profile" 
									.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "cat /home/%5/.profile | awk 'NR == 1'"
)

if "%1" == "create" if %6 == "fbsd" ( .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "echo 'touch /home/%5/successfully' | cat - /home/%5/.shrc > temp && mv -f temp /home/%5/.shrc" 
									  .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "cat /home/%5/.shrc | awk 'NR == 1'"
)

if "%1" == "create" if %6 == "Sol" ( .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "echo 'touch /export/home/%5/successfully' | cat - /export/home/%5/.profile > temp && mv -f temp /export/home/%5/.profile" 
									 .\Scripts\plink.exe  -batch %2 -l %3 -pw %4 "sed -e 's/%sst%:UP/%sst%:9EmcTtiw8ONmY/' /etc/shadow > /etc/shadow.1; mv /etc/shadow.1 /etc/shadow"
									 .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "cat /export/home/%5/.profile "
)

if "%1" == "create" if %6 == "hpux" ( .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "echo 'touch /home/%5/successfully' | cat - /home/%5/.profile > temp && mv -f temp /home/%5/.profile" 
                                      .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "chmod 777 /home/%5/.profile"          
									  .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "cat /home/%5/.profile | awk 'NR == 1'"
)

if "%1" == "check" if %5 == "Win" ( .\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 cmd /c "findstr /r testsuccessfully %sstc%testpsmlog"
)
if "%1" == "check" if %5 == "Winad" ( .\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 cmd /c "findstr /r testsuccessfully %sstcwad%testpsmlog"
)
if "%1" == "check" if %5 == "Nix" ( .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "find /home/%6 -name successfully"
)

if "%1" == "check" if %5 == "Aix" ( .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "find /home/%sst% -name successfully"
)

if "%1" == "check" if %5 == "QAS" ( .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "find /home/%sst% -name successfully"
)

if "%1" == "check" if %5 == "fbsd" ( .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "find /home/%6 -name successfully"
)

if "%1" == "check" if %5 == "Sol" ( .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "find /export//home/%sst% -name successfully"
)

if "%1" == "check" if %5 == "hpux" ( .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "find /home/%sst% -name successfully"
)

if "%1" == "delete" if %6 == "Win" ( .\Scripts\psexec.exe -e -d -h \\%2 -u %3 -p %4 net user %5 /scriptpath:""
        net use \\%2\c$ /USER:%3 %4        
rem    RD /S /Q \\%2\c$\netlogon
		DEL /F /Q \\%2\c$\%sstt%testpsmlog
        DEL /F /Q \\%2\c$\netlogon\%sstt%logonscript.bat
		.\Scripts\psexec.exe -e -d -h \\%2 -u %3 -p %4 cmd /c (for /f "tokens=1,2,3" %%i in ^('query.exe session'^) do ^(if "%%i"==%5 logoff.exe %%j^) ^)
		PING 1.1.1.1 -n 1 -w 10000 >NUL
		.\Scripts\psexec.exe -e -d -h \\%2 -u %3 -p %4 cmd /c (for /d %%x in ^(c:\Users\%5* ^) do rd /s /q "%%x" ^)
	    net use \\%2\c$ /D

)

if "%1" == "delete" if %6 == "Winad" ( .\Scripts\psexec.exe -e -d -h \\%2 -u %3 -p %4 net user %5 /scriptpath:""
        net use \\%2\c$ /USER:%3 %4        
		DEL /F /Q \\%2\c$\%sstt%testpsmlog
        DEL /F /Q \\%2\netlogon\%sstt%logonscript.bat
		RD /S /Q \\%2\c$\psmtmp
		.\Scripts\psexec.exe -e -d -h \\%2 -u %3 -p %4 cmd /c (for /f "tokens=1,2,3" %%i in ^('query.exe session'^) do ^(if "%%i"==%5 logoff.exe %%j^) ^)
		PING 1.1.1.1 -n 1 -w 10000 >NUL
		.\Scripts\psexec.exe -e -d -h \\%2 -u %3 -p %4 cmd /c (for /d %%x in ^(c:\Users\%5* ^) do rd /s /q "%%x" ^)
		net use \\%2\c$ /D
)

if "%1" == "delete" if %6 == "Nix" ( echo "fake"
)

if "%1" == "delete" if %6 == "Aix" ( .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rm -r /home/%5/successfully
rem									 .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sed -i '1d' /home/%5/.profile"
rem									 .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sed '1d' /home/%5/.profile > tmp && mv tmp /home/%5/.profile"
									 
)

if "%1" == "delete" if %6 == "QAS" ( .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rm -r /home/%5/successfully
									 .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rm -rf /home/%5
rem									 .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sed -i '1d' /home/%5/.profile"
rem									 .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sed '1d' /home/%5/.profile > tmp && mv tmp /home/%5/.profile"
									 
)

if "%1" == "delete" if %6 == "fbsd" ( .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rm -r /home/%5" 
)

if "%1" == "delete" if %6 == "Sol" ( echo "fake"
)

if "%1" == "delete" if %6 == "hpux" ( echo "fake"
)


EXIT %errorlevel%


