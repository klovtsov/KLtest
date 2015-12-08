@echo off
SET TempFile=PerlOut.tmp
if "%1" == "Standard" (
    perl .\Scripts\sshkey2.pl %2 %3 %4 %5 %6 > %TempFile% 
)
if "%1" == "System" (
    perl .\Scripts\sshkey3.pl %2 %3 %4 %5 %6 > %TempFile% 
)
if "%1" == "Account" (
	perl .\Scripts\sshkey1.pl %2 %3 %4 %5 %6 %7 > %TempFile%
)