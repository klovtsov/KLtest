@echo off
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "mkdir /home/%~4/.ssh"
.\Scripts\pscp.exe -l %2 -pw %3 %5 %1:/home/%~4/.ssh
if "%~6" == "SecSSH" (
    .\Scripts\plink.exe -batch %1 -l %2 -pw %3 "ssh-keygen -i -f /home/%~4/.ssh/id_dsa.export > /home/%~4/.ssh/id_dsa.pub"
)
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "chown -R %~4 /home/%~4"	
EXIT %errorlevel%