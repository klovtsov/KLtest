@echo off

if %6 == "Nix" ( goto :start
)

SET str=%6
set sst=%str:~1,-1%
set sstc=c:\%sst%

SET strr=%7
set sstt=%strr:~1,-1%
set sstcc=c:\%sstt%

net use \\%2\c$ /USER:%3 %4
:start
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
)

if "%1" == "check" if %5 == "Win" ( .\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 cmd /c "findstr /r testsuccessfully %sstc%testpsmlog^; del %sstc%testpsmlog"
)
if "%1" == "check" if %5 == "Nix" ( .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "find /home/%6 -name successfully -print -exec rm -f {} \;"
)

if "%1" == "delete" if %6 == "Win" ( .\Scripts\psexec.exe -e -d -h \\%2 -u %3 -p %4 net user %5 /scriptpath:""
        net use \\%2\c$ /USER:%3 %4        
rem    RD /S /Q \\%2\c$\netlogon
		DEL /F /Q \\%2\c$\%sstt%testpsmlog
        DEL /F /Q \\%2\c$\netlogon\%sstt%logonscript.bat
	    net use \\%2\c$ /D
)

if "%1" == "delete" if %6 == "Nix" ( echo "fake"
)

EXIT %errorlevel%


