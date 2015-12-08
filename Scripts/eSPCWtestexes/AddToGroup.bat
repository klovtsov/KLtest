.\Scripts\psexec.exe -e -i -h \\%1 -u %2 -p %3 net localgroup %5 /ADD %4 >nul
EXIT %errorlevel%