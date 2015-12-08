@echo off

set ArgumentOne=%1
set action=%ArgumentOne:"=%
set tempfile=macosx_uid.txt

if "%action%" == "create" (
	
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "dscl . -list /Users UniqueID | awk '{print ++$2}' | sort -ug | tail -1" > %tempfile%
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sudo dscl . -create /Users/%5" 
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sudo dscl . -create /Users/%5 UserShell /bin/bash" 
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sudo dscl . -create /Users/%5 RealName "%5 Tst. User"" 
	
	for /f "tokens=1,2 delims= " %%k in (%tempfile%) do (
        	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 sudo dscl . -create /Users/%5 UniqueID %%k
	)
                	
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sudo dscl . -create /Users/%5 PrimaryGroupID 80" 
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sudo dscl . -create /Users/%5 NFSHomeDirectory /Local/Users/%5" 
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sudo dscl . -passwd /Users/%5 %6" 
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sudo mkdir -p /Local/Users/%5 >/dev/null"
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sudo chown -R %5 /Local/Users/%5"
	
	del %tempfile% 
)

	
if "%action%" == "password" (
	
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sudo dscl . -passwd /Users/%5 %6" 
)



if "%action%" == "login" (
	
	.\Scripts\plink.exe -batch -ssh %2 -l %3 -pw %4 "whoami" 
)


if "%action%" == "delete" (
	
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sudo dscl . -delete /Users/%5" 
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sudo rm -d -r /Local/Users/%5 >/dev/null"
)


EXIT %ERRORLEVEL%                       
