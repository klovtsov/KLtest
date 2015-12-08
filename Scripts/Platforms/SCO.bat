SET RESF=resultSCO.txt
if "%1" == "create" (
.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "useradd -m %5" 
.\scripts\plink %2 -l %3 -pw %4 "echo 'spawn passwd [lindex $argv 0]' > expectAns"
.\scripts\plink %2 -l %3 -pw %4 "echo 'expect \"Enter choice ^(default is 1^):\^"' ^>^> expectAns" 
.\scripts\plink %2 -l %3 -pw %4 "echo 'send \"\\r^\"' >> expectAns"
.\scripts\plink %2 -l %3 -pw %4 "echo 'expect \"New password:\"'>> expectAns" 
.\scripts\plink %2 -l %3 -pw %4 "echo 'send \"[lindex $argv 1]\\r\"' >> expectAns"
.\scripts\plink %2 -l %3 -pw %4 "echo 'expect \"Re-enter password:\"' >> expectAns"
.\scripts\plink %2 -l %3 -pw %4 "echo 'send \"[lindex $argv 1]\\r\"' >> expectAns"
.\scripts\plink %2 -l %3 -pw %4 "echo 'expect eof' >> expectAns"
.\scripts\plink %2 -l %3 -pw %4 "/usr/gnu/bin/expect -f expectAns %5 %6" 
.\scripts\plink %2 -l %3 -pw %4 "rm expectAns"
)
if "%1" == "password" (
.\scripts\plink %2 -l %3 -pw %4 "echo 'spawn passwd [lindex $argv 0]' > expectAns"
.\scripts\plink %2 -l %3 -pw %4 "echo 'expect \"Enter choice ^(default is 1^):\^"' ^>^> expectAns" 
.\scripts\plink %2 -l %3 -pw %4 "echo 'send \"\\r^\"' >> expectAns"
.\scripts\plink %2 -l %3 -pw %4 "echo 'expect \"New password:\"'>> expectAns" 
.\scripts\plink %2 -l %3 -pw %4 "echo 'send \"[lindex $argv 1]\\r\"' >> expectAns"
.\scripts\plink %2 -l %3 -pw %4 "echo 'expect \"Re-enter password:\"' >> expectAns"
.\scripts\plink %2 -l %3 -pw %4 "echo 'send \"[lindex $argv 1]\\r\"' >> expectAns"
.\scripts\plink %2 -l %3 -pw %4 "echo 'expect eof' >> expectAns"
.\scripts\plink %2 -l %3 -pw %4 "/usr/gnu/bin/expect -f expectAns %5 %6" 
.\scripts\plink %2 -l %3 -pw %4 "rm expectAns"
)
if "%1" == "login" (
.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "ls > /dev/null" 
)
if "%1" == "delete" (
.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "userdel %5" 
.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rm -fR /u/%5" 
)
if "%1" == "getlogin" (
	cscript /NoLogo .\Scripts\GetVar.js GeneratedPwd
	for /f "tokens=1,2 delims= " %%i in (GeneratedPwd) do .\Scripts\plink.exe -batch %2 -l %3 -pw %%i "ls > /dev/null" 
	del GeneratedPwd
)

SET Result=0
FOR /F %%i IN (%RESF%) DO set /a Result=Result+1
SET SUC=0
SET FAIL=0

IF %Result%  == 0 (
	SET SUC=1
)

IF NOT %Result% == 0 (
	SET FAIL=1
)
SET TC=Account_%1_SCO
rem cscript .\Scripts\Converter.vbs "TPAM_Automation" "%TC%" %SUC% %FAIL% ".\Results\%TC%_report.html" %RESF%
