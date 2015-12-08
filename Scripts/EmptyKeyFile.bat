@echo off
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "cat /dev/null > ~/.ssh/%~4"
EXIT %errorlevel%