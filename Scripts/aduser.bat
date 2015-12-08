
if "%1" == "create" (
	.\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 dsadd user "cn=%~5,%~6" -samid %5 -upn %5@%7 -fn Ivan -ln Ivanov -display %5 -pwd grain41R -email %5@tpam.spb.qsft -desc "some words from AD" -pwdneverexpires yes -disabled no  

)
if "%1" == "delete" (
	.\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 dsrm -noprompt "cn=%~5,%~6"   
)

exit 0