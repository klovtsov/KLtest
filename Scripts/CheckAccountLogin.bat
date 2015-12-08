@echo off
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "tail -n 12 /var/log/secure | egrep -c 'Accepted (password|publickey) for %4'"