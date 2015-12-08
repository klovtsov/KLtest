dim Act, sServer, connUser, sContainer, connUserPwd
dim Results, sNewUserName, sNewUserPasswd
Const ADS_USE_SSL = 2
Const ADS_PROPERTY_APPEND=3
Results ="..\Results"

Act = WScript.Arguments.Item(0)
sServer= WScript.Arguments.Item(1)
sContainer = WScript.Arguments.Item(2)
connUser = WScript.Arguments.Item(3)
connUserPwd = WScript.Arguments.Item(4)

                                       
dim oDSO, oOU, oShell
Set oDSO = GetObject("LDAP:")
Set oShell = CreateObject("WScript.Shell")
Set fso = CreateObject("Scripting.FileSystemObject")

Select Case Act
Case "createuser"
	CreateUser()
Case "createcomputer"
	CreateComputer()
Case "createou"
	CreateOU()
Case "deleteuser"
	DeleteUser()
Case "deletecomputer"
	DeleteComputer()
Case "deleteou"
	DeleteOU()
End Select

''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function CreateUser()
	On Error Resume Next
	'Err.Clear
	sNewUserName = WScript.Arguments.Item(5)    'New user name
	sNewUserPasswd = WScript.Arguments.Item(6)      'Password for user which will be created
	Set oOU = oDSO.OpenDSObject("LDAP://" & sServer & "/" & sContainer,connUser,connUserPwd,ADS_USE_SSL)
	Set oUSR = oOU.Create("inetOrgPerson", "uid=" & sNewUserName)
	oUSR.put "uid", sNewUserName
	oUSR.put "givenName", "Ivan"
	oUSR.put "sn", "Ivanov"
	oUSR.put "cn", sNewUserName
	oUSR.put "description", "some words from AD"
	oUSR.put "uidNumber", "20009"                   'Check if it is max in domain
	oUSR.put "gidNumber", "100"
	oUSR.put "homeDirectory", "/home/" & sNewUserName 
	oUSR.put "loginShell", "/bin/bash"
	oUSR.put "mail", sNewUserName & "@tpam.spb.qsft"
	oUSR.PutEx ADS_PROPERTY_APPEND, "objectClass", Array("top","posixAccount","inetOrgPerson")
	oUSR.put "userpassword", sNewUserPasswd
	oUSR.SetInfo
	'CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function DeleteUser()
	On Error Resume Next
	Err.Clear
	UserName = WScript.Arguments.Item(5)
    Set oOU = oDSO.OpenDSObject("LDAP://" & sServer & "/" & sContainer,connUser,connUserPwd,ADS_USE_SSL)
	oOU.Delete "inetOrgPerson", "uid=" & UserName
	'CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function CreateComputer()
	On Error Resume Next
	'Err.Clear
	sNewComputerName = WScript.Arguments.Item(5)    'New computer name
	Set oOU = oDSO.OpenDSObject("LDAP://" & sServer & "/" & sContainer,connUser,connUserPwd,ADS_USE_SSL)
	Set oCMP = oOU.Create("computer", "cn=" & sNewComputerName)
	oCMP.put "cn", sNewComputerName
	oCMP.put "description", "Super Computer from AD"
	oCMP.PutEx ADS_PROPERTY_APPEND, "objectClass", Array("computer")
	oCMP.SetInfo
	'CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function DeleteComputer()
	On Error Resume Next
	Err.Clear
	ComputerName = WScript.Arguments.Item(5)
    Set oOU = oDSO.OpenDSObject("LDAP://" & sServer & "/" & sContainer,connUser,connUserPwd,ADS_USE_SSL)
	oOU.Delete "computer", "cn=" & ComputerName
	'CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function CreateOU()
	On Error Resume Next
	Err.Clear
	Set oDomain = oDSO.OpenDSObject("LDAP://" & sServer,connUser,connUserPwd,ADS_USE_SSL)
  	Set oOU = oDomain.Create("organizationalUnit", sContainer)
	oOU.SetInfo
	'CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function DeleteOU()
	On Error Resume Next
	Err.Clear
	Set oDomain = oDSO.OpenDSObject("LDAP://" & sServer,connUser,connUserPwd,ADS_USE_SSL)
  	oDomain.Delete "organizationalUnit", sContainer
	'CalcErr(Err)
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


'TC = "Account_" & Act & "_NDS" 
'Conv = ".\Scripts\Converter.vbs " & Chr(34) & "TPAM_Automation" & Chr(34) & " " & Chr(34) & TC & Chr(34) & " " & Su & " " & Fa & " " & Chr(34) & ".\Results\" & TC & "_report.html" & Chr(34)
'objShell.Run(Conv)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''