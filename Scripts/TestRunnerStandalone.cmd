if [%1]==[] (
	echo Usage: %0 config_file_name  - config file must be stored in TPAMScriptDir
	goto eof
)

setlocal enabledelayedexpansion

set TPAMPath=C:\TPAM

set SEED=%RANDOM%

set LogDir=%TPAMPath%\Logs
set LogFile=%LogDir%\testrunner.log

set ScriptFile=testrunner_standalone.js
set TPAMScriptDir=%TPAMPath%\Scripts
set ConfigFile=%TPAMScriptDir%\%1 

if not exist %LogDir% (

   mkdir %LogDir%	

)


for /l %%n in (4,-1,1) do (

	set /a newnum=%%n+1

	if exist %LogFile%.%%n (

   		move %LogFile%.%%n %LogFile%.!newnum!
	)

)

if exist %LogFile% (

   move %LogFile% %LogFile%.1

)



cscript %TPAMScriptDir%\%ScriptFile% %ConfigFile% >%LogFile%


set ConfigFileFailedTests=%TPAMScriptDir%\testrunner.config.failed_tests

if exist %ConfigFileFailedTests% (

	cscript %TPAMScriptDir%\%ScriptFile% %ConfigFileFailedTests% >>%LogFile%
	del %ConfigFileFailedTests%

)

:eof

