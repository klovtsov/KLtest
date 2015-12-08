@echo off
set currpwdfile=iiscurrentpwds.txt
set lastknownpwdfile=iislastknownpwds.txt
if "%1" == "create" (
	.\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 cscript.exe c:\inetpub\AdminScripts\adsutil.vbs //nologo //B set w3svc/%~7/anonymoususername %5
	.\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 cscript.exe c:\inetpub\AdminScripts\adsutil.vbs //nologo //B set w3svc/%~7/anonymoususerpass %6
	.\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 cscript.exe c:\inetpub\AdminScripts\adsutil.vbs //nologo //B set w3svc/%~7/ROOT/anonymoususername %5
    .\Scripts\psexec.exe -e -h \\%2 -u %3 -p %4 cscript.exe c:\inetpub\AdminScripts\adsutil.vbs //nologo //B set w3svc/%~7/ROOT/anonymoususerpass %6
	net use \\%2\c$ /USER:%3 %4
	   cscript.exe //nologo .\Scripts\eSPCWtestexes\IISPwdChecker.vbs "\\%2\c$\%8" %5 %lastknownpwdfile%
	net use \\%2\c$ /D
)
if "%1" == "check" (
    net use \\%2\c$ /USER:%3 %4 >nul
	    cscript.exe //nologo .\Scripts\eSPCWtestexes\IISPwdChecker.vbs "\\%2\c$\%6" %5 %currpwdfile%
	net use \\%2\c$ /D >nul
	fc %currpwdfile% %lastknownpwdfile%|find /i "no differences">nul
	if errorlevel 1 (echo different) ELSE (echo same)
	copy %currpwdfile% %lastknownpwdfile% >NUL
	del %currpwdfile% >NUL
)
