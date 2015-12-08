@echo off
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "cd %~4; unzip `ls -1 -t -r *.zip | tail -1`"
.\Scripts\pscp.exe -l %2 -pw %3 %~1:%~4/*.csv %~5
.\Scripts\pscp.exe -l %2 -pw %3 %~1:%~4/*.htm %~5
.\Scripts\pscp.exe -l %2 -pw %3 %~1:%~4/*.xml %~5
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "rm -f %~4/*"
EXIT %errorlevel%
