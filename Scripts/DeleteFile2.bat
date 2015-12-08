@echo off
set /a exitcode=0
del /F /Q %1%2
for /f "usebackq" %%i in (`dir /b %~1`) do (if %%i == %2 (set /a exitcode=1))
rem echo %exitcode% 
exit %exitcode% 
