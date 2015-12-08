@echo off
net use \\%1\%2$ /USER:%3 %4
set lpath=%5
set rpath=%lpath::=$%
set rpath=%rpath:"=%
set fpath="\\%1\%rpath%"
find  /c /i %6 %fpath%

echo ExitCode %errorlevel%

net use \\%1\%2$ /D
