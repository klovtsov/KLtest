SET TempFile=TempFile.txt
del %TempFile%

if "%1" == "create" (
    echo CREATE USER %6@'%%' IDENTIFIED BY %7^; > %TempFile%
    .\Scripts\mysql.exe --host=%2 --port=%5 --user=%3 --password=%4 < %TempFile%     
    del %TempFile%
)

	
if "%1" == "password" (
    echo SET PASSWORD FOR %6@'%%' = PASSWORD^(%7^)^; > %TempFile%
    .\Scripts\mysql.exe --host=%2 --port=%5 --user=%3 --password=%4 < %TempFile% 
)



if "%1" == "login" (
    echo exit > %TempFile%
    .\Scripts\mysql.exe --host=%2 --port=%4 --user=%3 --password=%5 < %TempFile% 
)


if "%1" == "delete" (
    echo DROP USER %6@'%%'^; > %TempFile% 
    .\Scripts\mysql.exe --host=%2 --port=%5 --user=%3 --password=%4 < %TempFile% 
)




EXIT %ERRORLEVEL%                       