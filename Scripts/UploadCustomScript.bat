@echo off
.\Scripts\pscp.exe -l %2 -pw %3 .\Scripts\%~4.gz %~1:%~5
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "gunzip %~5/%~4.gz"
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "chmod +x %~5/%~4"
EXIT %errorlevel%
