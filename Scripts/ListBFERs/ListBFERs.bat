set TPAMPath=C:\TPAM

set PathToWebDriver=%TPAMPath%\Scripts\Webdriver

set SuiteBferList=suite_bfer_list.txt
set EdmzBferList=edmz_bfer_list.txt
set Results=results.txt

rem set StarTeamCmd="C:\Program Files\Borland\StarTeam Cross-Platform Client 2009\stcmd.exe"

rem set ServerString="svc-spb-osqabuilder:q`w1e2r3osqa@spbsrcw02:49201/TPAM/2.5/"

set EmailAddr=alexander.buyanov@quest.com,igor.baranov@quest.com,konstantin.lovtsov@quest.com,yuri.kuniver@quest.com

rem set EmailAddr=yuri.kuniver@quest.com

set EmailSender=C:\YK\StarTeamNotifications\blat.exe


rem %StarTeamCmd% co -p %ServerString% -is -q -x -o -fp %TPAMPath%


powershell .\findTaggedTests.ps1 %TPAMPath% %SuiteBferList%


call groovy -cp %PathToWebDriver%\selenium-server-standalone-2.32.0.jar CheckBFERs.groovy %SuiteBferList% > %EdmzBferList%

PING 1.1.1.1 -n 1 -w 5000 >NUL
                              
copy %SuiteBferList%+%EdmzBferList% %Results%

PING 1.1.1.1 -n 1 -w 5000 >NUL


%EmailSender% %Results% -server relayemea.prod.quest.corp -to %EmailAddr% -f tpamresults@tpam.com -subject "Suite & BugTracker: BFERs list"

PING 1.1.1.1 -n 1 -w 5000 >NUL
                              
del %SuiteBferList% %EdmzBferList% %Results%