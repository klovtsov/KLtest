dim Act,sServer, sConn, oConn,oRS,Results,strSQL

Act = WScript.Arguments.Item(0)
sServer= WScript.Arguments.Item(1)
sConn="provider=sqloledb.1;Integrated Security = SSPI;data source=" & sServer & ";initial catalog=master"
Set objShell = CreateObject("WScript.Shell")
Set oConn = CreateObject("ADODB.Connection")

Select Case Act

Case "create"
	Create()
Case "password"
	Password()
Case "delete"
	Delete()
End Select
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function Create()
    On Error Resume Next
    Err.Clear
    username = WScript.Arguments.Item(2)
    passwd = WScript.Arguments.Item(3)
    oConn.Open sConn    'connect to SQL
    strSQL="exec sp_addlogin @loginame='" & username & "',@passwd='" & passwd & "'"
    oConn.execute strSQL
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function Password()
    On Error Resume Next
    Err.Clear
    username = WScript.Arguments.Item(2)
    passwordNew = WScript.Arguments.Item(3)
    oConn.Open sConn    'connect to SQL 
    strSQL="ALTER LOGIN " & username &  " WITH PASSWORD = '" & passwordNew &  "'"
    oConn.execute strSQL
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function Delete()
    On Error Resume Next
    Err.Clear
    username = WScript.Arguments.Item(2)
    oConn.Open sConn    'connect to SQL
    'sql command creation
    strSQL="DROP LOGIN " & Chr(34) & username & Chr(34)
    oConn.execute strSQL 
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
WScript.Quit(Err.Number)