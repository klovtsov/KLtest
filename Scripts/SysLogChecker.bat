@Echo off

rem %1 - Host, %2 - HostUser, %3 - HostUserPwd, 
rem %4 - Date, %5 - TpamUser, %6 - Operation,
rem %7 - ObjectName (or ObjectType)

.\Scripts\plink.exe -batch %1 -l %2 -pw %3 "cat /var/log/messages | grep %4 | grep %5 | grep %6 | grep %7" 
