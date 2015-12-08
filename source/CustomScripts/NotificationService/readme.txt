Two Java projects:

1. Handling the server's certificate

java InstallCert 10.30.44.191:9443 (or running from Eclipse) - adding the server's certificate to the KeyStore with your trusted certificates
As result, <ProjectDir>jssecacerts will be created. You need to copy it to "C:\Program Files (x86)\Java\jre6\lib\security\jssecacerts" (jre path can be changed) 

2. Working with notification service

CallNotificationService.java - source
notificationservice.jar - jar 

Three arguments: TPAM address, certificate file and its password.

Example: java -jar notificationservice.jar 10.30.44.191 system1.p12 Q1w2e3r4

run.bat - to initiate ping from N system simultaneously

Three arguments: TPAM address, a number of the first system, a number of the last system.

Example:  run.bat 10.30.44.191 1 3
As result, ping for system1, system2, system3 will be initiated. For each system, resultN.log will be created.
