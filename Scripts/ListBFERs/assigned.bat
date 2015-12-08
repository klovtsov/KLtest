set TPAMPath=C:\TPAM

set PathToWebDriver=%TPAMPath%\Scripts\Webdriver

set Results=results.txt

set EmailAddr=yuri.kuniver@quest.com

set EmailSender=C:\YK\StarTeamNotifications\blat.exe

call groovy -cp %PathToWebDriver%\selenium-server-standalone-2.32.0.jar assigned.groovy > %Results%

PING 1.1.1.1 -n 1 -w 5000 >NUL

%EmailSender% %Results% -server relayemea.prod.quest.corp -to %EmailAddr% -f tpamresults@tpam.com -subject "Assigned BFERs"

PING 1.1.1.1 -n 1 -w 5000 >NUL

del %Results%

