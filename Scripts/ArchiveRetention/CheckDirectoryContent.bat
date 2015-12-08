@echo off
                                                
.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "ls %4"

EXIT %errorlevel%
