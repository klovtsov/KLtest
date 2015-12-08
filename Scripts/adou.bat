
if "%1" == "create" (
	.\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 dsadd ou "ou=%~5,%~6"   

)
if "%1" == "delete" (
	.\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 dsrm -noprompt "ou=%~5,%~6"   
)

exit 0