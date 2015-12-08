@echo off
set drive=%3
set drive=%drive:"=%
if "%1" == "create" (
        net use %6 \\%2\%drive%$ /USER:%4 %5
)	
if "%1" == "delete" (
        net use %2 /D
)
if "%1" == "check" (
    IF EXIST %2\installEdmzClient.log (
       echo File is available
    ) ELSE (
       echo File is unavailable
    )
)                     
EXIT %errorlevel%