set Host=%1
set Host=%Host:"=%

set User=%2
set User=%User:"=%

set Password=%3
set Password=%Password:"=%

set Path=%4
set Path=%Path:"=%

set HowOld=%5
set HowOld=%HowOld:"=%


Scripts\plink.exe -batch %Host% -l %User% -pw %Password% "find %Path% -mtime %HowOld%"


