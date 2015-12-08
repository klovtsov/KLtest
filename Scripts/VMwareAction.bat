if "%~5" == "start" (

.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "VM_NUMBER=`vim-cmd vmsvc/getallvms | grep %4 | cut -c 1`;vim-cmd vmsvc/power.on $VM_NUMBER"

)

if "%~5" == "stop" (

.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "VM_NUMBER=`vim-cmd vmsvc/getallvms | grep %4 | cut -c 1`;vim-cmd vmsvc/power.off $VM_NUMBER"

)

if "%~5" == "reboot" (

.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "VM_NUMBER=`vim-cmd vmsvc/getallvms | grep %4 | cut -c 1`;vim-cmd vmsvc/power.reset $VM_NUMBER"

)



EXIT %ERRORLEVEL%                       