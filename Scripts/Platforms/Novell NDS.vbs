dim Act, sServer, sConn, sContext, connUser, connUserPwd
dim sConnUser, Results
Results ="..\Results"

Act = WScript.Arguments.Item(0)
sServer= WScript.Arguments.Item(1)
sContext = WScript.Arguments.Item(2)
connUser = WScript.Arguments.Item(3)
connUserPwd = WScript.Arguments.Item(4)

sConn="LDAP://" & sServer & ":389/" & sContext
sConnUser = "cn=" & connUser & "," & sContext

Set Dsobj = GetObject("LDAP:")
Set objShell = CreateObject("WScript.Shell")
Set fso = CreateObject("Scripting.FileSystemObject")

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
	username = WScript.Arguments.Item(5)    'New user name
	passwd = WScript.Arguments.Item(6)      'Password for user which will be created
	Set treeobj = Dsobj.OpenDSObject(sConn,sConnUser, connUserPwd, 32)
	sUserCN = "cn=" & username
	Set user = treeobj.Create("user", sUserCN)
	user.sn = username
	user.objectClass = "user"
	user.put "userpassword", passwd
	user.SetInfo
	CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function Delete()
	On Error Resume Next
	Err.Clear
	username = WScript.Arguments.Item(5)
    Set treeObj = Dsobj.OpenDSObject(sConn,sConnUser, connUserPwd, 32)
	treeObj.Delete "inetOrgPerson", "cn=" & username
	CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function Password()
On Error Resume Next
Err.Clear
username = WScript.Arguments.Item(5)
passwordNew = WScript.Arguments.Item(6)
sConn = sConn & "/cn=" & username
Set objUser = Dsobj.OpenDSObject(sConn,sConnUser, connUserPwd, 32)
    objUser.put "userpassword", passwordNew
	objUser.SetInfo
CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function Login()
	On Error Resume Next
	Err.Clear
	Set treeobj = Dsobj.OpenDSObject(sConn,sConnUser, connUserPwd, 32)
	CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function GetLogin()
	On Error Resume Next
	Err.Clear
	FileName = "GeneratedPwd"
	strcommandline = ".\Scripts\GetVar.js " & FileName
	Set fso = CreateObject("Scripting.FileSystemObject")
	Return = objShell.Run(strcommandline, 1, true)
	Set MyFile = fso.OpenTextFile(FileName)
	passwd = MyFile.ReadLine
	MyFile.Close
	fso.DeleteFile(FileName)
	Set treeobj = Dsobj.OpenDSObject(sConn,sConnUser, passwd, 32)
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
TC = "Account_" & Act & "_NDS" 
'Conv = ".\Scripts\Converter.vbs " & Chr(34) & "TPAM_Automation" & Chr(34) & " " & Chr(34) & TC & Chr(34) & " " & Su & " " & Fa & " " & Chr(34) & ".\Results\" & TC & "_report.html" & Chr(34)
'objShell.Run(Conv)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''