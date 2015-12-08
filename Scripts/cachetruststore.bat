set ParRootCert=%1
set Truststore=%2
set Trustpasswd=%3

set Trustpasswd=%Trustpasswd:"=%

echo y|cmd /c keytool -importcert -trustcacerts -file %ParRootCert% -keystore %Truststore% -storepass %Trustpasswd%
