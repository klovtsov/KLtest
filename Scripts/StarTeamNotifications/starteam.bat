@echo off
setlocal
set file="mail.txt"

java -jar starteamnotifications.jar -s spbsrcw02:49201 -u svc-spb-osqabuilder:q`w1e2r3osqa -p TPAM -v 2.5 -r -age 1 > %file%

FOR /F "usebackq" %%A IN ('%file%') DO set size=%%~zA

if %size% GTR 0 (

	perl quotes.pl >> %file%

	blat.exe mail.txt -server relayemea.prod.quest.corp -to alexander.buyanov@quest.com,igor.baranov@quest.com,konstantin.lovtsov@quest.com,yuri.kuniver@quest.com -f tpamresults@tpam.com -subject "TPAM Activity"


) 









