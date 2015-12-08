WScript.Interactive = False

FunctAcnt = WScript.Arguments.Item(0)
FunctAcntPwd = WScript.Arguments.Item(1)
SystemAddress = WScript.Arguments.Item(2)
action = WScript.Arguments.Item(3)
 
Set objSWbemLocator = CreateObject("WbemScripting.SWbemLocator")
Set objSWbemServices = objSWbemLocator.ConnectServer(SystemAddress, _
    "root\cimv2", _
     FunctAcnt, _
     FunctAcntPwd, _
     "MS_409")

if action = "clear" then

Set colLogFiles = objSWbemServices.ExecQuery _
    ("Select * from Win32_NTEventLogFile " _
    & "Where LogFileName='System'")
For Each objLogfile in colLogFiles
    objLogFile.ClearEventLog()
Next
End If

if action = "check" then 

Set colLoggedEvents = objSWbemServices.ExecQuery _
    ("Select * from Win32_NTLogEvent " _
        & "Where Logfile = 'System'")
For Each objEvent in colLoggedEvents
 If objEvent.Message = "The QstTestService service entered the running state." then
 rem wscript.echo("ok")
WScript.Quit(0)
 End If
Next
WScript.Quit(1)
end if
 rem wscript.echo("no")

