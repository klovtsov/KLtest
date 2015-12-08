@echo off
set MAILUSER=%~4
for /f "tokens=1,2 delims=@" %%i in ("%MAILUSER%") do set MAILUSER=%%i
set MAILPATH=%~5
set MAILPATH=%MAILPATH::=$%
set /a exitcode=1
.\Scripts\psexec.exe -d  \\%1 -u %~2 -p %~3 winpop add %~4 /createuser %6
PING 1.1.1.1 -n 1 -w 5000 >NUL
net use \\%1 /u:%2 %3
for /f "usebackq" %%i in (`dir /b \\%1\%MAILPATH%`) do (if %%i == P3_%MAILUSER%.mbx (set /a exitcode=0))
echo %MAILUSER%
echo %MAILPATH%
rem echo %exitcode% 
exit %exitcode% 


