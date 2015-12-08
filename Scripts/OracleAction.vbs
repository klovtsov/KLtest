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
sConn="provider=OraOLEDB.Oracle;Data Source =(DESCRIPTION=(CID=GTU_APP)(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST="+sServer+")(PORT="+port+")))(CONNECT_DATA=(SID="+servname+")));"
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
'CalcErr(Err)
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
strSQL = "CREATE TABLE "+dbname+"."+tablename+" (UserID NUMERIC NOT NULL, UserName VARCHAR2(64), FirstName VARCHAR2(32), LastName VARCHAR2(32), Phone VARCHAR2(32), EMail VARCHAR2(32), Mobile VARCHAR2(32), Description VARCHAR2(256)) " 
'WScript.Echo strSQL
'WScript.Echo Err.Description
thecommand.CommandText = strSQL
thecommand.CommandType = cnstCommand
thecommand.ActiveConnection = oConn
thecommand.Execute
oConn.Close
'CalcErr(Err)
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
strSQL = "CREATE TABLE "+dbname+"."+tablename+" (ComputerID NUMERIC NOT NULL, ComputerName VARCHAR2(64), NetworkAddress VARCHAR2(32), Email VARCHAR2(32), Description VARCHAR2(256)) "
'WScript.Echo strSQL
'WScript.Echo Err.Description
thecommand.CommandText = strSQL
thecommand.CommandType = cnstCommand
thecommand.ActiveConnection = oConn
thecommand.Execute
oConn.Close
'CalcErr(Err)
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
strSQL = "INSERT INTO "+dbname+"."+tablename+ " (UserID, UserName, FirstName, LastName, Phone, EMail, Mobile, Description) VALUES ("+myuserid+", "+"'"+myusername+"'"+", "+"'"+myfirstname+"'"+ ", "+"'"+mylastname+"'"+", "+"'"+myphone+"'"+", "+"'"+myemail+"'"+ ", " +"'"+mymobile+"'"+", " +"'"+mydescription+"'"+ " ) "
'WScript.Echo strSQL
'WScript.Echo Err.Description
thecommand.CommandText = strSQL
thecommand.CommandType = cnstCommand
thecommand.ActiveConnection = oConn
thecommand.Execute
oConn.Close
'CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function InsertUsers()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(4)
saPwd = WScript.Arguments.Item(5)
dbname = WScript.Arguments.Item(6)
tablename = WScript.Arguments.Item(7)
myusername = WScript.Arguments.Item(8)
myfirstname = WScript.Arguments.Item(9)
mylastname = WScript.Arguments.Item(10)
myphone = WScript.Arguments.Item(11)
myemail = WScript.Arguments.Item(12)
mymobile = WScript.Arguments.Item(13)
mydescription = WScript.Arguments.Item(14)
mycount = WScript.Arguments.Item(15)
oConn.Open sConn, Admin, saPwd    'connect to SQL by sa credentials
'WScript.Echo Err.Description
Set theCommand = CreateObject("ADODB.Command")
const cnstCommand = 1 'Command type - 1 is for standard query

For i=1 to mycount

strSQL = "INSERT INTO "+dbname+"."+tablename+ " (UserID, UserName, FirstName, LastName, Phone, EMail, Mobile, Description) VALUES ("& i &", " & "'" & myusername & i & "'" & ", " & "'" & myfirstname & i & "'" & ", " & "'" & mylastname & i & "'" & ", " & "'" & myphone & "'" & ", " & "'" & myemail & "'" & ", " & "'" & mymobile & "'" & ", " & "'" & mydescription & i & "'" & " ) "

'WScript.Echo strSQL
'WScript.Echo Err.Description
thecommand.CommandText = strSQL
thecommand.CommandType = cnstCommand
thecommand.ActiveConnection = oConn
thecommand.Execute

Next

oConn.Close
'CalcErr(Err)
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
strSQL = "INSERT INTO "+dbname+"."+tablename+ " (ComputerID, ComputerName, NetworkAddress, Email, Description) VALUES ("+mycomputerid+", "+"'"+mycomputername+"'"+", "+"'"+mynetworkaddress+"'"+", "+"'"+myemail+"'"+", " +"'"+mydescription+"'"+ ") "
'WScript.Echo strSQL
'WScript.Echo Err.Description
thecommand.CommandText = strSQL
thecommand.CommandType = cnstCommand
thecommand.ActiveConnection = oConn
thecommand.Execute
oConn.Close
'CalcErr(Err)
End Function

''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function CreateUser()
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
'WScript.Echo strSQL
'WScript.Echo Err.Description
thecommand.CommandText = strSQL
thecommand.CommandType = cnstCommand
thecommand.ActiveConnection = oConn
thecommand.Execute
strSQL = "grant select any table to "+username
thecommand.CommandText = strSQL
thecommand.Execute
strSQL = "grant create session to "+username
thecommand.CommandText = strSQL
thecommand.Execute
strSQL = "grant connect to "+username
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
const cnstCommand = 1 'Command type - 1 is for standard query
strSQL = "drop user "+Username+""
thecommand.CommandText = strSQL
thecommand.CommandType = cnstCommand
thecommand.ActiveConnection = oConn
thecommand.Execute
oConn.Close
'CalcErr(Err)
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
strSQL = "drop table "+dbname+"."+tablename+""
thecommand.CommandText = strSQL
thecommand.CommandType = cnstCommand
thecommand.ActiveConnection = oConn
thecommand.Execute
oConn.Close
'CalcErr(Err)
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
'CalcErr(Err)
End Function
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
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
