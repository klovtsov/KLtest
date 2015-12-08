@echo off

set Log=cacheperl.log

set RunDir=/tmp/tpamcacheserverremoteaccess
set ScriptName=cacheserverremoteaccess.sh

set Wsdl=%4
set Wsdl=%Wsdl:"=%

set CacheUserCertificate=%5
set CacheUserCertificate=%CacheUserCertificate:"=%

set CacheUserPassword=%6
set CacheUserPassword=%CacheUserPassword:"=%

set SystemName=%7
set SystemName=%SystemName:"=%

set AccountName=%8
set AccountName=%AccountName:"=%



scripts\plink.exe %1 -l %2 -pw %3 "mkdir %RunDir%"

scripts\pscp.exe -l %2 -pw %3 scripts\%ScriptName%.gz %1:%RunDir% 1>&2 2>%Log%
scripts\pscp.exe -l %2 -pw %3 scripts\keys\%Wsdl% %1:%RunDir%  1>&2 2>%Log%
scripts\pscp.exe -l %2 -pw %3 scripts\keys\%CacheUserCertificate% %1:%RunDir% 1>&2 2>%Log%

scripts\plink.exe %1 -l %2 -pw %3 "gunzip %RunDir%/%ScriptName%.gz"
scripts\plink.exe %1 -l %2 -pw %3 "chmod 755 %RunDir%/%ScriptName%"
scripts\plink.exe %1 -l %2 -pw %3 "%RunDir%/%ScriptName% %Wsdl% %CacheUserCertificate% %CacheUserPassword% %SystemName% %AccountName%"


scripts\plink.exe %1 -l %2 -pw %3 "rm -rf %RunDir%"
