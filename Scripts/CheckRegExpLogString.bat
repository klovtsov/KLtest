@echo off

.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "cat %4 | egrep -e '%~5' | wc -l"

