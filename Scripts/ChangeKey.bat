@echo off
set /p instr=<%1
set str=%instr:A=D%
echo.%str% > %1