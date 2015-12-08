
if "%1" == "list" (
task%1 /s %2 /u %3 /p %4 /FI "ImageName eq %5" > cliout.tmp
                                                )
if "%1" == "kill" (
task%1 /s %2 /u %3 /p %4 /FI "ImageName eq %5" > cliout.tmp
						)