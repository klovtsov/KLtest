rem @echo off
if "%1" == "create" (
    .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sudo touch '%~5/%~6'"   
)
if "%1" == "delete" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "sudo rm -f %~5/*txt"
)
if "%1" == "check" (
    .\Scripts\plink.exe -batch %2 -l %3 -pw %4 if [ -e '%~5/%~6' ]; then echo FileExists; sudo rm -f '%~5/%~6'; else echo FileNotExist; fi
)                     