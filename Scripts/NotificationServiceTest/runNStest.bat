set TPAM=%1

set StartUser=%~2
set EndUser=%~3

set Dir=%~4
set System=%~5
set CertPwd=%~6

set CallService=start /B java -jar %Dir%\notificationservice.jar %TPAM%

FOR /L %%N IN (%StartUser%,1,%EndUser%) DO (%CallService% %Dir%\%System%%%N.p12 %CertPwd% & PING 1.1.1.1 -n 1 -w 5000 >NUL)

rem > %Dir%\result%%N.log

rem EXIT %ERRORLEVEL%
