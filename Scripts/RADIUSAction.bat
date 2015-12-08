@echo off


if "%1" == "create" (
	.\Scripts\plink.exe %2 -l %3 -pw %4 "cp -fp %7 %7.old"
	.\Scripts\plink.exe %2 -l %3 -pw %4 "/bin/echo '%5 Cleartext-Password := \"%6\"' >> %7" 
rem	.\Scripts\plink.exe %2 -l %3 -pw %4 "chgrp radiusd %7" 

)
if "%1" == "config" (
	.\Scripts\plink.exe %2 -l %3 -pw %4 "cp -fp %5 %5.old"
        .\Scripts\plink.exe %2 -l %3 -pw %4 "/bin/echo 'client %6 {' >> %5"
	.\Scripts\plink.exe %2 -l %3 -pw %4 "/bin/echo '	secret	 =  %7' >> %5"
	.\Scripts\plink.exe %2 -l %3 -pw %4 "/bin/echo '	require_message_authenticator = no' >> %5"	 
	.\Scripts\plink.exe %2 -l %3 -pw %4 "/bin/echo '}' >> %5"
	.\Scripts\plink.exe %2 -l %3 -pw %4 "%8 restart" 
)
if "%1" == "noconfig" (
	.\Scripts\plink.exe %2 -l %3 -pw %4 "mv -f %5.old %5"
	.\Scripts\plink.exe %2 -l %3 -pw %4 "mv -f %6.old %6"
	.\Scripts\plink.exe %2 -l %3 -pw %4 "%7 restart" 
)



	
