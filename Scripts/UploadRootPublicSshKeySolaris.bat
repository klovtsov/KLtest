@echo off
.\Scripts\pscp.exe -l %2 -pw %3 %~4id_dsa.pub %1:/tmp
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "mkdir -p .ssh "
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "cat /tmp/id_dsa.pub >> .ssh/%~5"
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "rm -f /tmp/id_dsa.pub"
del %~4id_dsa.pub	
EXIT %errorlevel%