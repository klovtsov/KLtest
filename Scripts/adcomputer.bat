if "%1" == "create" (
	.\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 dsadd computer "cn=%~5,%~6" -samid %7 -desc "Super Computer from AD"  

)
if "%1" == "delete" (
	.\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 dsrm -noprompt "cn=%~5,%~6"   
)

exit 0

