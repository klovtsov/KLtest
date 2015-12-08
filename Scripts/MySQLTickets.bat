SET TempFile=TempFile.txt
del %TempFile%

	
if "%1" == "createdb" (
    echo CREATE DATABASE %~5^; > %TempFile%
    .\Scripts\mysql.exe --host=%2 --port=3306 --user=%3 --password=%4 < %TempFile%
	 del %TempFile%                      
)



if "%1" == "createtable" (
    echo CREATE TABLE %~5.%~6 (TicketID INT NOT NULL AUTO_INCREMENT PRIMARY KEY^, TicketNumber VARCHAR(32^)^, RequestReason VARCHAR(32^)^, RequestorName VARCHAR(32^)^, FirstName VARCHAR(32^)^, LastName VARCHAR(32^)^, Computer VARCHAR(32^)^, Account VARCHAR(32^)^)^;  > %TempFile%
    .\Scripts\mysql.exe --host=%2 --port=3306 --user=%3 --password=%4 < %TempFile% 
  del %TempFile%                 
)



if "%1" == "dropdb" (
    echo DROP DATABASE %~5^; > %TempFile% 
    .\Scripts\mysql.exe --host=%2 --port=3306 --user=%3 --password=%4 < %TempFile% 
  del %TempFile%                       
)

if "%1" == "insertticket" (
    echo INSERT INTO %~5.%~6 (TicketNumber^, RequestReason^, RequestorName^, FirstName^, LastName^, Computer^, Account^) VALUES ('%~7^'^, 'Nado^'^, 'JSmith^'^, 'John^'^, 'Smith^'^, '%~8^'^, '%~9^'^)^; > %TempFile% 
    .\Scripts\mysql.exe --host=%2 --port=3306 --user=%3 --password=%4 < %TempFile% 
  del %TempFile%                       
)



EXIT %ERRORLEVEL%                       