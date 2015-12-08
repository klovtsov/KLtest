
	if "%1" == "disable"  (

	.\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 "net user %5 /ACTIVE:NO"  > klout.txt  2>&1
	type klout.txt
rem	del /f klout.txt	
	)

	if "%1" == "enable"  (
	
	.\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 "net user %5 /ACTIVE:YES"  > klout.txt  2>&1	
	type klout.txt
rem	del /f klout.txt	
	)

	if "%1" == "status"  (

        .\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 "net user %5"   > klout.txt   2>&1
	type klout.txt | find "Account active"
rem	del /f klout.txt		
	)
	
EXIT %errorlevel%

