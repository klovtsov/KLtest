rem @echo off
if "%1" == "create" ( 
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "racadm config -g cfgUserAdmin -o cfgUserAdminUserName -i %5 %7" 
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "racadm config -g cfgUserAdmin -o cfgUserAdminPassword -i %5 %6" 
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "racadm config -g cfgUserAdmin -i %5 -o cfgUserAdminPrivilege 0x00000001" 
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "racadm config -g cfgUserAdmin -i %5 -o cfgUserAdminIpmiLanPrivilege 15" 
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "racadm config -g cfgUserAdmin -i %5 -o cfgUserAdminSolEnable 0" 
        .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "racadm config -g cfgUserAdmin -i %5 -o cfgUserAdminEnable 1" 
)
	
if "%1" == "password" (
	    .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "racadm config -g cfgUserAdmin -o cfgUserAdminPassword -i %5 %6" 
)

if "%1" == "login" (
 .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "help" 
)

if "%1" == "delete" (
    .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "racadm config -g cfgUserAdmin -o cfgUserAdminUserName -i %5 """ """" 
)

if "%1" == "getlogin" (
	cscript /NoLogo .\Scripts\GetVar.js GeneratedPwd
	for /f "tokens=1,2 delims= " %%i in (GeneratedPwd) do .\Scripts\plink.exe -batch %2 -l %3 -pw %%i "help" 2>resultDRAC.txt
	del GeneratedPwd
)

EXIT %ERRORLEVEL%