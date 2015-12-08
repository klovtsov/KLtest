@echo off
.\Scripts\pscp.exe -l %2 -pw %3 %~5id_dsa.pub %1:/tmp
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "mkdir -p /home/%~4/.ssh ; chown -R %4 /home/%~4"
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "cat /tmp/id_dsa.pub >> /home/%~4/.ssh/%~6"
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "chown -R %4 /home/%~4"
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "rm -f /tmp/id_dsa.pub"
del %~5id_dsa.pub	
EXIT %errorlevel%