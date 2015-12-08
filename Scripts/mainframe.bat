@echo off
set ArgumentOne=%1
set action=%ArgumentOne:"=%

	
if "%action%" == "password" (
        .\Scripts\TST10.exe /r:".\Scripts\Platforms\Mainframe\password.txt" /o:".\Scripts\Platforms\Mainframe\out.txt"    
)                                                                          

if "%action%" == "login" (
	.\Scripts\sfk filter .\Scripts\Platforms\Mainframe -rep "/NEWPASSWORDPLACEHOLDER/%~2/" -write -yes
        .\Scripts\TST10.exe /r:.\Scripts\Platforms\Mainframe\login.txt /o:.\Scripts\Platforms\Mainframe\out.txt
	FIND /C "READY" .\Scripts\Platforms\Mainframe\out.txt
)




EXIT %ERRORLEVEL%                       
