SET fit=C:\TPAM\fitnesse.jar
SET TPAMsuite=FrontPage.AutomatedTesting.PlatformsTesting
SET addr=vasily.abuziarov@quest.com

java -jar %fit% -c "%TPAMsuite%?Suite"
java -jar %fit% -c "%TPAMsuite%?pageHistory&resultDate=latest">report.html

Mailer.exe spb8232 report.html relayemea.prod.quest.corp %addr% "TPAM autotest results."
