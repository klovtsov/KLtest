@echo off

set CacheUserPassword=%3
set CacheUserPassword=%CacheUserPassword:"=%


.\Scripts\psexec.exe -e -i \\\\%1 -u %2 -p %CacheUserPassword% whoami

echo Return Code %errorlevel%

rem Old & Tied
rem echo echo aaa555 >1.bat
rem .\Scripts\psexec.exe -e -i \\%1 -u %2 -p %CacheUserPassword% -c -f 1.bat
rem del 1.bat
