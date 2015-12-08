@echo off
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "mkdir -p ~/.ssh"
.\Scripts\pscp.exe -l %2 -pw %3 %5 %1:/Users/%~2/.ssh
EXIT %errorlevel%
