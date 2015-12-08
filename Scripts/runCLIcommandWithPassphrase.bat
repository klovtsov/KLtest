@echo off
rem %1 - Passphrase
rem %2 - UserName
rem %3 - TPAM host address
rem %4 - CLI command
.\Scripts\plink.exe -pw %1 -i .\Scripts\keys\%2.ppk -C %2@%3 %4