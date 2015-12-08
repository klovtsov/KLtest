@echo off
.\Scripts\pscp.exe -l %2 -pw %3 %~4%~6 %1:/tmp
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "mkdir -p ~/.ssh "
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "cat /tmp/%~6 >> ~/.ssh/%~5"
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "chown -R %2 ~"
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "rm -f /tmp/%~6"
del %~4%~6	
EXIT %errorlevel%