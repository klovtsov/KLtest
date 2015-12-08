

		.\Scripts\psexec.exe -e -d -h \\%1 -u %2 -p %3 cmd /c (for /f "tokens=1,2,3" %%i in ^('query.exe session'^) do ^(if "%%i"==%4 logoff.exe %%j^) ^)
		PING 1.1.1.1 -n 1 -w 10000 >NUL
		.\Scripts\psexec.exe -e -d -h \\%1 -u %2 -p %3 cmd /c (for /d %%x in ^(c:\Users\%4* ^) do rd /s /q "%%x" ^)

