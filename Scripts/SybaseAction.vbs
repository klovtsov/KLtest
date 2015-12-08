dim Act,sServer, sConn, oConn,oRS,Results,strSQL
Results ="..\Results"

Act = WScript.Arguments.Item(0)
sServer= WScript.Arguments.Item(1)
port = WScript.Arguments.Item(2)
Catalog = WScript.Arguments.Item(3)
sConn="Provider = ASEOLEDB ; Data Source = "+ sServer +":"+ port +" ; Initial Catalog = "+ Catalog +";CharSet=iso_1;"
Set objShell = CreateObject("WScript.Shell")
Set fso = CreateObject("Scripting.FileSystemObject")
Set oConn = CreateObject("ADODB.Connection")

Select Case Act

Case "createdb"
	CreateDB()
Case "createusertable"
	CreateUserTable()
Case "createcomputertable"
	CreateComputerTable()
Case "createuser"
	CreateUser()
Case "insertuser"
	InsertUser()
Case "insertcomputer"
	InsertComputer()
Case "deleteuser"
	DeleteUser()
Case "deletetable"
	DeleteTable()
Case "deletedb"
	DeleteDB()
Case "insertusers"
	InsertUsers()

End Select

''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function CreateUser()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(4)
sapwd = WScript.Arguments.Item(5)
username = WScript.Arguments.Item(6)
passwd = WScript.Arguments.Item(7)
dbname = WScript.Arguments.Item(8)
oConn.Open sConn, Admin, sapwd
Set theCommand = CreateObject("ADODB.Command")
strSQL = "sp_addlogin "+ username+"," + chr(34) + passwd + chr(34) + " "
'WScript.Echo strSQL
thecommand.CommandText = strSQL
thecommand.ActiveConnection = oConn
thecommand.Execute
WScript.Echo Err.Description
strSQL = "sp_adduser "+ username
thecommand.CommandText = strSQL
thecommand.Execute
strSQL = "USE " + dbname+ ";  grant select on " + usertablename + " to public; grant select on " + computertablename + " to public"  
thecommand.CommandText = strSQL
thecommand.Execute
oConn.Close
'CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function DeleteUser()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(4)
saPwd = WScript.Arguments.Item(5)
username = WScript.Arguments.Item(6)
oConn.Open sConn, Admin, saPwd    'connect to SQL by sa credentials
Set theCommand = CreateObject("ADODB.Command")
strSQL = "sp_dropuser "+ username
thecommand.CommandText = strSQL
thecommand.ActiveConnection = oConn
thecommand.Execute
strSQL = "sp_droplogin "+ username
thecommand.CommandText = strSQL
thecommand.ActiveConnection = oConn
thecommand.Execute
oConn.Close
'CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function CreateDB()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(4)
saPwd = WScript.Arguments.Item(5)
dbname = WScript.Arguments.Item(6)
oConn.Open sConn, Admin, saPwd    'connect to SQL by sa credentials
'WScript.Echo Err.Description
Set theCommand = CreateObject("ADODB.Command")
const cnstCommand = 1 'Command type - 1 is for standard query
strSQL = "CREATE DATABASE "+dbname
'WScript.Echo strSQL
'WScript.Echo Err.Description
thecommand.CommandText = strSQL
thecommand.CommandType = cnstCommand
thecommand.ActiveConnection = oConn
thecommand.Execute
oConn.Close
CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function CreateUserTable()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(4)
saPwd = WScript.Arguments.Item(5)
dbname = WScript.Arguments.Item(6)
tablename = WScript.Arguments.Item(7)
oConn.Open sConn, Admin, saPwd    'connect to SQL by sa credentials
'WScript.Echo Err.Description
Set theCommand = CreateObject("ADODB.Command")
const cnstCommand = 1 'Command type - 1 is for standard query
strSQL = "CREATE TABLE "+dbname+".dbo."+tablename+" (UserID INT NOT NULL, UserName NVARCHAR(64), FirstName NVARCHAR(32), LastName NVARCHAR(32), Phone NVARCHAR(32), EMail NVARCHAR(32), Mobile NVARCHAR(32), Description NVARCHAR(256)) " 
'WScript.Echo strSQL
'WScript.Echo Err.Description
thecommand.CommandText = strSQL
thecommand.CommandType = cnstCommand
thecommand.ActiveConnection = oConn
thecommand.Execute
oConn.Close
CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function CreateComputerTable()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(4)
saPwd = WScript.Arguments.Item(5)
dbname = WScript.Arguments.Item(6)
tablename = WScript.Arguments.Item(7)
oConn.Open sConn, Admin, saPwd    'connect to SQL by sa credentials
'WScript.Echo Err.Description
Set theCommand = CreateObject("ADODB.Command")
const cnstCommand = 1 'Command type - 1 is for standard query
strSQL = "CREATE TABLE "+dbname+".dbo."+tablename+" (ComputerID INT NOT NULL, ComputerName NVARCHAR(64), NetworkAddress NVARCHAR(32), Email NVARCHAR(32), Description NVARCHAR(256)) "
'WScript.Echo strSQL
'WScript.Echo Err.Description
thecommand.CommandText = strSQL
thecommand.CommandType = cnstCommand
thecommand.ActiveConnection = oConn
thecommand.Execute
oConn.Close
CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function InsertUser()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(4)
saPwd = WScript.Arguments.Item(5)
dbname = WScript.Arguments.Item(6)
tablename = WScript.Arguments.Item(7)
myuserid = WScript.Arguments.Item(8)
myusername = WScript.Arguments.Item(9)
myfirstname = WScript.Arguments.Item(10)
mylastname = WScript.Arguments.Item(11)
myphone = WScript.Arguments.Item(12)
myemail = WScript.Arguments.Item(13)
mymobile = WScript.Arguments.Item(14)
mydescription = WScript.Arguments.Item(15)
oConn.Open sConn, Admin, saPwd    'connect to SQL by sa credentials
'WScript.Echo Err.Description
Set theCommand = CreateObject("ADODB.Command")
const cnstCommand = 1 'Command type - 1 is for standard query
strSQL = "INSERT INTO "+dbname+".dbo."+tablename+ " (UserID, UserName, FirstName, LastName, Phone, EMail, Mobile, Description) VALUES ("+myuserid+", "+"'"+myusername+"'"+", "+"'"+myfirstname+"'"+ ", "+"'"+mylastname+"'"+", "+"'"+myphone+"'"+", "+"'"+myemail+"'"+ ", " +"'"+mymobile+"'"+", " +"'"+mydescription+"'"+ " ) "
'WScript.Echo strSQL
'WScript.Echo Err.Description
thecommand.CommandText = strSQL
thecommand.CommandType = cnstCommand
thecommand.ActiveConnection = oConn
thecommand.Execute
oConn.Close
CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''

Function InsertComputer()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(4)
saPwd = WScript.Arguments.Item(5)
dbname = WScript.Arguments.Item(6)
tablename = WScript.Arguments.Item(7)
mycomputerid = WScript.Arguments.Item(8)
mycomputername = WScript.Arguments.Item(9)
mynetworkaddress = WScript.Arguments.Item(10)
myemail = WScript.Arguments.Item(11)
mydescription = WScript.Arguments.Item(12)
oConn.Open sConn, Admin, saPwd    'connect to SQL by sa credentials
'WScript.Echo Err.Description
Set theCommand = CreateObject("ADODB.Command")
const cnstCommand = 1 'Command type - 1 is for standard query
strSQL = "INSERT INTO "+dbname+".dbo."+tablename+ " (ComputerID, ComputerName, NetworkAddress, Email, Description) VALUES ("+mycomputerid+", "+"'"+mycomputername+"'"+", "+"'"+mynetworkaddress+"'"+", "+"'"+myemail+"'"+", " +"'"+mydescription+"'"+ ") "
'WScript.Echo strSQL
'WScript.Echo Err.Description
thecommand.CommandText = strSQL
thecommand.CommandType = cnstCommand
thecommand.ActiveConnection = oConn
thecommand.Execute
oConn.Close
CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function DeleteTable()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(4)
saPwd = WScript.Arguments.Item(5)
dbname = WScript.Arguments.Item(6)
tablename = WScript.Arguments.Item(7)
oConn.Open sConn, Admin, saPwd    'connect to SQL by sa credentials
Set theCommand = CreateObject("ADODB.Command")
const cnstCommand = 1 'Command type - 1 is for standard query
strSQL = "drop table "+dbname+".dbo."+tablename+""
thecommand.CommandText = strSQL
thecommand.CommandType = cnstCommand
thecommand.ActiveConnection = oConn
thecommand.Execute
oConn.Close
CalcErr(Err)
End Function

'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function DeleteDB()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(4)
saPwd = WScript.Arguments.Item(5)
dbname = WScript.Arguments.Item(6)
oConn.Open sConn, Admin, saPwd    'connect to SQL by sa credentials
Set theCommand = CreateObject("ADODB.Command")
const cnstCommand = 1 'Command type - 1 is for standard query
strSQL = "drop database "+dbname+""
thecommand.CommandText = strSQL
thecommand.CommandType = cnstCommand
thecommand.ActiveConnection = oConn
thecommand.Execute
oConn.Close
CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''

''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function CalcErr(Err)

'Set MyFile = fso.OpenTextFile("tpam.log",8,true)
'passwd = MyFile.WriteLine(Date() & " " &  Time() & " " & WScript.ScriptName & "," & Act & "... " & Err.Description)
'WScript.Echo(Date() & " " &  Time() & " " & WScript.ScriptName & "," & Act & "... " & Err.Description)
'MyFile.Close

Fa = 0
Su = 0
if Err.Number <> 0 Then
     Fa = 1
	WScript.Quit(Err.Number)
'    WScript.Echo Err.Description
Else
    Su = 1
End If
'TC = "Account_" & Act & "_Sybase" 
'Conv = ".\Scripts\Converter.vbs " & Chr(34) & "TPAM_Automation" & Chr(34) & " " & Chr(34) & TC & Chr(34) & " " & Su & " " & Fa & " " & Chr(34) & ".\Results\" & TC & "_report.html" & Chr(34)'
'objShell.Run(Conv)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''

