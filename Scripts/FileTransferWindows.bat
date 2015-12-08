rem  @echo off
if "%1" == "create" (
        net use \\%2\c$ /USER:%3 %4
        IF EXIST \\%2\c$\%5 (
		    .\Scripts\psexec.exe -d -e -h \\%2 -u %3 -p %4 net share %~5 /delete
			RMDIR /S /Q \\%2\c$\%5
		)
	MD \\%2\c$\%5
	echo TestFile > \\%2\c$\%5\%6
    net use \\%2\c$ /D
	.\Scripts\psexec.exe -d -e -h \\%2 -u %3 -p %4 net share %~5=C:\%~5 /grant:Administrators,FULL /UNLIMITED
)

if "%1" == "delete" (
	.\Scripts\psexec.exe -d -e -h \\%2 -u %3 -p %4 net share %~5 /delete
    net use \\%2\c$ /USER:%3 %4        
    RMDIR /S /Q \\%2\c$\%5
    net use \\%2\c$ /D
) 
if "%1" == "check" (
    net use \\%2\c$ /USER:%3 %4
    IF EXIST \\%2\c$\%5\%6 (
       echo FileExists
    ) ELSE (
       echo FileDoesNotExist
    )
    net use \\%2\c$ /D
)                     



