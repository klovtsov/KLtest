@echo off
if "%1" == "create" (
    .\Scripts\plink.exe -batch %2 -l %3 -pw %4 "mkdir %~5; echo Hello world! > '%~5/%~6'"   
)
if "%1" == "delete" (
	.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "rm -R %~5"
)
if "%1" == "check" (
    .\Scripts\plink.exe -batch %2 -l %3 -pw %4 if [ -e '%~5/%~6' ]; then echo FileExists; rm -R '%~5/%~6'; else echo FileNotExist; fi
)                     