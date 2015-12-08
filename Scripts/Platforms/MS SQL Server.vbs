dim Act,sServer, sConn, oConn,oRS,Results,strSQL
Results ="..\Results"

Act = WScript.Arguments.Item(0)
sServer= WScript.Arguments.Item(1)
sConn="provider=sqloledb;data source=" & sServer & ";initial catalog=master"
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
Case "addsrvrolemember"
	Addsrvrolemember()
Case "dropsrvrolemember"
	Dropsrvrolemember()
End Select

''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function Create()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(2)
saPwd = WScript.Arguments.Item(3)
username = WScript.Arguments.Item(4)
passwd = WScript.Arguments.Item(5)
WScript.Echo saPwd
oConn.Open sConn, Admin, saPwd    'connect to SQL by sa credentials
strSQL="exec sp_addlogin @loginame='" & username & "',@passwd='" & passwd & "'"
oConn.execute strSQL 
CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function Delete()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(2)
saPwd = WScript.Arguments.Item(3)
username = WScript.Arguments.Item(4)
'ExpectedError = "Login failed for user '" & username &"'."
oConn.Open sConn, Admin, saPwd    'connect to SQL by sa credentials
'sql command creation
strSQL="DROP LOGIN " & Chr(34) & username & Chr(34)
oConn.execute strSQL 
CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function Password()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(2)
saPwd = WScript.Arguments.Item(3)
username = WScript.Arguments.Item(4)
passwordNew = WScript.Arguments.Item(5)
oConn.Open sConn, Admin, saPwd    'connect to SQL by sa credentials
strSQL="ALTER LOGIN " & username &  " WITH PASSWORD = '" & passwordNew &  "'"
oConn.execute strSQL 
CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function Login()
On Error Resume Next
Err.Clear
username = WScript.Arguments.Item(2)
passwd = WScript.Arguments.Item(3)
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
username = WScript.Arguments.Item(2)
oConn.Open sConn, username, passwd
oConn.Close
CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function Addsrvrolemember()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(2)
saPwd = WScript.Arguments.Item(3)
username = WScript.Arguments.Item(4)
WScript.Echo saPwd
oConn.Open sConn, Admin, saPwd    'connect to SQL by sa credentials
strSQL="exec sp_addsrvrolemember '" & username & "', 'dbcreator'"
oConn.execute strSQL 
CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function Dropsrvrolemember()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(2)
saPwd = WScript.Arguments.Item(3)
username = WScript.Arguments.Item(4)
WScript.Echo saPwd
oConn.Open sConn, Admin, saPwd    'connect to SQL by sa credentials
strSQL="exec sp_dropsrvrolemember '" & username & "', 'dbcreator'"
oConn.execute strSQL 
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
     WScript.Quit(Err.Number)
Else
    Su = 1
End If

'TC = "Account_" & Act & "_SQL" 
'Conv = ".\Scripts\Converter.vbs " & Chr(34) & "TPAM_Automation" & Chr(34) & " " & Chr(34) & TC & Chr(34) & " " & Su & " " & Fa & " " & Chr(34) & ".\Results\" & TC & "_report.html" & Chr(34)
'objShell.Run(Conv)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
