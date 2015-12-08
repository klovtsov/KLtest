@echo off & setlocal enableextensions

  for /f "tokens=2,*" %%i in ('systeminfo^|findstr /b "Time Zone:"') do (

    set tz_=%%j)

  echo "%tz_%"

 endlocal & goto :EOF

