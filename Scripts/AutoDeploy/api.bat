rem %1 - version e.g. 2.5.914
rem %2 - comment or dispatchers list
rem %3 - email

rem Example: api.bat 2.5.914 mycomment yuri.kuniver@quest.com "tpamdisp202,tpamdisp203"


powershell ..\psscripts\GetJavaAPI.ps1 -version "TPAM-API-Java-%1.zip" -dispatchers "%4" -email '%3'

powershell ..\psscripts\GetCppNetAPI.ps1 -version "ParApi-Cpp_.NET-%1.zip" -comment '%2' -email '%3'

powershell ..\psscripts\GetTpamApiCppExe.ps1 -version "ParApi-Cpp_.NET-%1.zip" -comment '%2' -email '%3'

powershell ..\psscripts\GetPerlAPI.ps1 -version "ParApi-Perl-%1.zip" -dispatchers "%4" -email '%3'
