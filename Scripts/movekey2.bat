rem Moves key to *unix platforms 
rem Arguments: PlatformAddress, functAccount, functAccountPwd, keyFile, destinationFolder (/export/home as example)
.\Scripts\pscp.exe -l %2 -pw %3 %4 %1:%5