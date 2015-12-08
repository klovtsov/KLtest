@echo off
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "tail -n 15 /var/log/messages|egrep -c 'Accepted publickey for %4'"