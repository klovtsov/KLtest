@echo off
net use \\%1\%2$ /USER:%3 %4
IF EXIST \\%1\%2$\test\%5 (
   echo File exists
) ELSE (
   echo File does not exist
)
net use \\%1\%2$ /D