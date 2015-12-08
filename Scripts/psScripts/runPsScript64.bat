@echo off
set scriptfile=.\Scripts\psScripts\%1.ps1
set scriptargs=
shift
:loop1
if "%~1"=="" goto after_loop
set scriptargs=%scriptargs% %1
shift
goto loop1

:after_loop
%SystemRoot%\syswow64\WindowsPowerShell\v1.0\powershell.exe -NoLogo -NonInteractive -NoProfile -ExecutionPolicy RemoteSigned -File %scriptfile% %scriptargs%