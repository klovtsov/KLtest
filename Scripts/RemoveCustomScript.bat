@echo off
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "rm -f %~5/%~4"
EXIT %errorlevel%
