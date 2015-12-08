@echo off
rem Arguments: 1- key file, 2 - result file, 3 - TPAM address, 4 - TPAM DPA address
rem 5-Managed system address, 6 - functAcct, 7- functAcctPwd, 8 - keyPath

rem Get key from id_dsa.pub file
set /p key=<%1
rem create authorized_keys file
echo command=^"edmzclient.exe^",from=^"%~3,%~4^",no-port-forwarding %key% > %2
rem Copy result file to target system
set str=%~8
set thost=%~5
set dl=%str:~0,1%
net use \\%thost%\%dl%$ /USER:%6 %7
set rst=%str:~2%
COPY %2 "\\%thost%\%dl%$%rst%" /Y
rem make a reseved copy
COPY %2 "\\%thost%\%dl%$%rst%.res" /Y
net use \\%thost%\%dl%$ /D
del /F %2