@echo off

.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "netstat -t -n | egrep '%~4' | egrep -c ESTABLISHED"

