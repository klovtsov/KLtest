@echo off
set ArgumentOne=%1
set action=%ArgumentOne:"=%

if "%action%" == "create" (
        .\Scripts\plink.exe -telnet %2 <.\Scripts\Platforms\AS400\create.txt >.\Scripts\Platforms\AS400\out.txt  
	FIND /C "Special authorities granted" .\Scripts\Platforms\AS400\out.txt 
	DEL /Q /F .\Scripts\Platforms\AS400\out.txt
)

	
if "%action%" == "password" (
	.\Scripts\sfk filter .\Scripts\Platforms\AS400 -rep "/MANACCTPWDNEWPLACEHOLDER/%~3/" -write -yes
        .\Scripts\plink.exe -telnet %2 <.\Scripts\Platforms\AS400\password.txt >.\Scripts\Platforms\AS400\out.txt  
	FIND /C "changed" .\Scripts\Platforms\AS400\out.txt 
	DEL /Q /F .\Scripts\Platforms\AS400\out.txt
)



if "%action%" == "login" (
	.\Scripts\sfk filter .\Scripts\Platforms\AS400 -rep "/NEWPASSWORDPLACEHOLDER/%~3/" -write -yes
        .\Scripts\plink.exe -telnet %2 <.\Scripts\Platforms\AS400\login.txt >.\Scripts\Platforms\AS400\out.txt 
	FIND /C "Set initial menu" .\Scripts\Platforms\AS400\out.txt 
	DEL /Q /F .\Scripts\Platforms\AS400\out.txt
)


if "%action%" == "delete" (
       .\Scripts\plink.exe -telnet %2 <.\Scripts\Platforms\AS400\delete.txt >.\Scripts\Platforms\AS400\out.txt   
	FIND /C "deleted" .\Scripts\Platforms\AS400\out.txt 
	DEL /Q /F .\Scripts\Platforms\AS400\out.txt
)



EXIT %ERRORLEVEL%                       