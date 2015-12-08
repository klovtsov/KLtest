SET TempFile=TempFile.txt
del %TempFile%


if "%1" == "createuser" (
    echo CREATE USER %6@'%%' IDENTIFIED BY %7^; > %TempFile%
    .\Scripts\mysql.exe --host=%2 --port=%5 --user=%3 --password=%4 < %TempFile%     
    del %TempFile%

)

if "%1" == "grantselect" (
    echo GRANT SELECT ON *.* TO %~7@'%%'^; > %TempFile%
    .\Scripts\mysql.exe --host=%2 --port=%5 --user=%3 --password=%4 < %TempFile%     
    del %TempFile%

)

	
if "%1" == "createdb" (
    echo CREATE DATABASE %~6^; > %TempFile%
    .\Scripts\mysql.exe --host=%2 --port=%5 --user=%3 --password=%4 < %TempFile%
	 del %TempFile%                      
)



if "%1" == "createusertable" (
    echo CREATE TABLE %~6.%~7 (UserID INT NOT NULL AUTO_INCREMENT PRIMARY KEY^, UserName VARCHAR(64^)^, FirstName VARCHAR(32^)^, LastName VARCHAR(32^)^, Phone VARCHAR(32^)^, EMail VARCHAR(32^)^, Mobile VARCHAR(32^)^, Description VARCHAR(256^)^)^;  > %TempFile%
    .\Scripts\mysql.exe --host=%2 --port=%5 --user=%3 --password=%4 < %TempFile% 
  del %TempFile%                 
)

if "%1" == "createcomputertable" (
     echo CREATE TABLE %~6.%~7 (ComputerID INT NOT NULL AUTO_INCREMENT PRIMARY KEY^, ComputerName VARCHAR(64^)^, NetworkAddress VARCHAR(32^)^, Email VARCHAR(32^)^, Description VARCHAR(256^)^)^;  > %TempFile%
    .\Scripts\mysql.exe --host=%2 --port=%5 --user=%3 --password=%4 < %TempFile% 
  del %TempFile%                       
)


if "%1" == "deleteuser" (
    echo DROP USER %~6@'%%'^; > %TempFile% 
    .\Scripts\mysql.exe --host=%2 --port=%5 --user=%3 --password=%4 < %TempFile% 
  del %TempFile%                       
)

if "%1" == "deletedb" (
    echo DROP DATABASE %~6^; > %TempFile% 
    .\Scripts\mysql.exe --host=%2 --port=%5 --user=%3 --password=%4 < %TempFile% 
  del %TempFile%                       
)

if "%1" == "insertuser" (
    echo INSERT INTO %~6.%~7 (UserName^, FirstName^, LastName^, Phone^, EMail^, Mobile^, Description^) VALUES ('%~8^'^, '%~9^'^, '%~9ov^'^, '111-111-111^'^, '%~8^@tpam.spb.qsft^'^, '222-222-222^'^, 'some words from AD^'^)^; > %TempFile% 
    .\Scripts\mysql.exe --host=%2 --port=%5 --user=%3 --password=%4 < %TempFile% 
  del %TempFile%                       
)

if "%1" == "insertcomputer" (
    echo INSERT INTO %~6.%~7 (ComputerName^, NetworkAddress^, Email^, Description^) VALUES ('%~8^'^, '%~9^'^, '%~8^@tpam.spb.qsft^'^, 'Super Computer from AD^'^)^; > %TempFile% 
    .\Scripts\mysql.exe --host=%2 --port=%5 --user=%3 --password=%4 < %TempFile% 
  del %TempFile%                       
)

if "%1" == "dropuser" (
    echo DELETE FROM %~6.%~7 WHERE UserName=%~8^; > %TempFile% 
    .\Scripts\mysql.exe --host=%2 --port=%5 --user=%3 --password=%4 < %TempFile% 
 del %TempFile%                       
)

if "%1" == "dropcomputer" (
    echo DELETE FROM %~6.%~7 WHERE ComputerName=%~8^; > %TempFile% 
    .\Scripts\mysql.exe --host=%2 --port=%5 --user=%3 --password=%4 < %TempFile% 
 del %TempFile%                       
)



EXIT %ERRORLEVEL%                       