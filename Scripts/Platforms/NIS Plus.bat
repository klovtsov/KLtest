if "%1" == "create" (
            		.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "useradd -u 18989 -d /export/home/%5 -m %5 1>/dev/null" 
			.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sed -e 's/%5:\*LK\*/%5:smhEsBv2Y8W7M/' /etc/shadow > /etc/shadow.1; mv /etc/shadow.1 /etc/shadow" 
			.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "/usr/lib/nis/nisaddent -mvf /etc/passwd passwd" 
			.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "/usr/lib/nis/nisaddent -mvf /etc/shadow shadow" 			
			.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "/usr/bin/nisaddcred -p 18989 -P %5.%6. -l qwerty LOCAL" 			
			.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "/usr/bin/nisaddcred -p unix.18989@%6 -P %5.%6. -l qwerty DES" 
 			)

	
if "%1" == "password" (
			.\Scripts\Platforms\Nis\nis.js %2 %3 %4 %5 %6
)





if "%1" == "corrupt" (

		if %6 == "1" (.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sed -e 's/ssh/sshcurrupt/g' %5 > %5.1; mv %5.1 %5" 
		)  ELSE ( .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sed -e 's/sshcurrupt/ssh/g' %5 > %5.1; mv %5.1 %5" 
			)
)


if "%1" == "delete" (
        	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "userdel %5" 
		.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "nisaddcred -r %5.%6 %6" 
		.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "nistbladm -r '[name=%5],passwd.org_dir.%6'" 
)


EXIT %ERRORLEVEL%                       