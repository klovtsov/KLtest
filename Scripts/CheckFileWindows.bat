@echo off
 
if "%1" == "check" (
    net use \\%2\c$ /USER:%3 %4
    IF EXIST \\%2\c$%~p5%6 (
       FIND %7 \\%2\c$%~p5%~6
    ) ELSE (
       echo FileDoesNotExist
    )
    net use \\%2\c$ /D
)                   


if "%1" == "delete" (
    net use \\%2\c$ /USER:%3 %4
    IF EXIST \\%2\c$%~p5%6 (
	del /F \\%2\c$%~p5%6
       echo FileDeleted
    ) ELSE (
       echo FileDoesNotExist
    )
    net use \\%2\c$ /D
)                     



