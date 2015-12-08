dim Act,sServer, sConn, oConn,oRS,Results,strSQL
Results ="..\Results"


Dim A
A = ""
Set objArgs = WScript.Arguments
For Each strArg in objArgs
    A = A + " " + strArg
Next 
'WScript.Echo A

Act = WScript.Arguments.Item(0)
sServer= WScript.Arguments.Item(1)
port = WScript.Arguments.Item(2)
servname = WScript.Arguments.Item(3)
sConn="provider=OraOLEDB.Oracle;Data Source =(DESCRIPTION=(CID=GTU_APP)(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST="+sServer+")(PORT="+port+")))(CONNECT_DATA=(SERVICE_NAME="+servname+")));"
Set objShell = CreateObject("WScript.Shell")
Set fso = CreateObject("Scripting.FileSystemObject")
Set oConn = CreateObject("ADODB.Connection")

Select Case Act

Case "create"
	Create()
Case "password"
	Password()
Case "login"
	Login()
Case "getlogin"
	GetLogin()
Case "delete"
	Delete()
End Select

''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function Create()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(4)
saPwd = WScript.Arguments.Item(5)
username = WScript.Arguments.Item(6)
passwd = WScript.Arguments.Item(7)
oConn.Open sConn, Admin, saPwd    'connect to SQL by sa credentials
'WScript.Echo Err.Description
Set theCommand = CreateObject("ADODB.Command")
const cnstCommand = 1 'Command type - 1 is for standard query
strSQL = "CREATE USER "+username+" IDENTIFIED BY "+ chr(34) + passwd + chr(34) + " "
'WScript.Echo Err.Description
thecommand.CommandText = strSQL
thecommand.CommandType = cnstCommand
thecommand.ActiveConnection = oConn
thecommand.Execute
strSQL = "grant create session to "+username
thecommand.CommandText = strSQL
thecommand.Execute
oConn.Close
CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function Delete()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(4)
saPwd = WScript.Arguments.Item(5)
username = WScript.Arguments.Item(6)
oConn.Open sConn, Admin, saPwd    'connect to SQL by sa credentials
Set theCommand = CreateObject("ADODB.Command")
const cnstCommand = 1 'Command type - 1 is for standard query
strSQL = "drop user "+Username+""
thecommand.CommandText = strSQL
thecommand.CommandType = cnstCommand
thecommand.ActiveConnection = oConn
thecommand.Execute
oConn.Close
CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function Password()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(4)
saPwd = WScript.Arguments.Item(5)
username = WScript.Arguments.Item(6)
passwordNew = WScript.Arguments.Item(7)
oConn.Open sConn, Admin, saPwd    'connect to SQL by sa credentials
Set theCommand = CreateObject("ADODB.Command")
const cnstCommand = 1 'Command type - 1 is for standard query
strSQL = "ALTER USER "+username+" IDENTIFIED BY "+ chr(34) + passwordNew + chr(34) +" "
thecommand.CommandText = strSQL
thecommand.CommandType = cnstCommand
thecommand.ActiveConnection = oConn
thecommand.Execute
oConn.Close
CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function Login()
On Error Resume Next
Err.Clear
username = WScript.Arguments.Item(4)
passwd = WScript.Arguments.Item(5)
oConn.Open sConn, username, passwd
oConn.Close
CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function GetLogin()
On Error Resume Next
Err.Clear
FileName = "GeneratedPwd"
strcommandline = ".\Scripts\GetVar.js " & FileName
Return = objShell.Run(strcommandline, 1, true)
Set MyFile = fso.OpenTextFile(FileName)
passwd = MyFile.ReadLine
MyFile.Close
fso.DeleteFile(FileName)
username = WScript.Arguments.Item(4)
oConn.Open sConn, username, passwd
oConn.Close
CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function CalcErr(Err)

'Set MyFile = fso.OpenTextFile("tpam.log",8,true)
'passwd = MyFile.WriteLine(Date() & " " &  Time() & " " & WScript.ScriptName & "," & Act & "... " & Err.Description)
WScript.Echo(Date() & " " &  Time() & " " & WScript.ScriptName & "," & Act & "... " & Err.Description)
'MyFile.Close

Fa = 0
Su = 0
if Err.Number <> 0 Then
     Fa = 1
'     WScript.Echo Err.Description
     WScript.Quit(Err.Number)
Else
    Su = 1
End If
TC = "Account_" & Act & "_Oracle" 
'Conv = ".\Scripts\Converter.vbs " & Chr(34) & "TPAM_Automation" & Chr(34) & " " & Chr(34) & TC & Chr(34) & " " & Su & " " & Fa & " " & Chr(34) & ".\Results\" & TC & "_report.html" & Chr(34)
'objShell.Run(Conv)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
