FOR /F "tokens=1,2,3,4 delims=," %%G IN (retention.config) DO ..\\plink.exe -batch %%G -l %%H -pw %%I "find %%J -mtime +7 | xargs rm -Rf"
