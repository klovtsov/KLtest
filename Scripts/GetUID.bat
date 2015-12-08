@echo off

if %5 == "win" (
wmic /node:"%2" /user:%3 /password:%4 path win32_useraccount where name="%1" get sid | findstr "S-"
)

if "%1" == "nix" (
.\Scripts\plink.exe -batch %2 -l %3 -pw %4 "id %5 | sed 's/^uid=//;s/(.*$//'" 
)

