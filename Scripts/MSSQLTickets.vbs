dim Act,sServer, sConn, oConn,oRS,Results,strSQL
Results ="..\Results"

Act = WScript.Arguments.Item(0)
sServer= WScript.Arguments.Item(1)
sConn="provider=sqloledb;data source=" & sServer & ";initial catalog=master"
Set objShell = CreateObject("WScript.Shell")
Set fso = CreateObject("Scripting.FileSystemObject")
Set oConn = CreateObject("ADODB.Connection")


Select Case Act

Case "createdb"
	CreateDB()
Case "createtable"
	CreateTable()
Case "insertticket"
	InsertTicket()
Case "dropdb"
	DropDB()
End Select
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Function CreateDB()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(2)
saPwd = WScript.Arguments.Item(3)
dbname = WScript.Arguments.Item(4)
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
Function CreateTable()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(2)
saPwd = WScript.Arguments.Item(3)
dbname = WScript.Arguments.Item(4)
tablename = WScript.Arguments.Item(5)
oConn.Open sConn, Admin, saPwd    'connect to SQL by sa credentials
'WScript.Echo Err.Description
Set theCommand = CreateObject("ADODB.Command")
const cnstCommand = 1 'Command type - 1 is for standard query
strSQL = "CREATE TABLE "+dbname+".dbo."+tablename+" (TicketID INT NOT NULL, TicketNumber NVARCHAR(32), RequestReason NVARCHAR(32), RequestorName NVARCHAR(32), FirstName NVARCHAR(32), LastName NVARCHAR(32), Computer NVARCHAR(32), Account NVARCHAR(32)) " 
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
Function InsertTicket()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(2)
saPwd = WScript.Arguments.Item(3)
dbname = WScript.Arguments.Item(4)
tablename = WScript.Arguments.Item(5)
myticketid = WScript.Arguments.Item(6)
myticketnumber = WScript.Arguments.Item(7)
myrequestreason = WScript.Arguments.Item(8)
myrequestorname = WScript.Arguments.Item(9)
myfirstname = WScript.Arguments.Item(10)
mylastname = WScript.Arguments.Item(11)
mycomputer = WScript.Arguments.Item(12)
myaccount = WScript.Arguments.Item(13)
oConn.Open sConn, Admin, saPwd    'connect to SQL by sa credentials
'WScript.Echo Err.Description
Set theCommand = CreateObject("ADODB.Command")
const cnstCommand = 1 'Command type - 1 is for standard query
strSQL = "INSERT INTO "+dbname+".dbo."+tablename+ " (TicketID, TicketNumber, RequestReason, RequestorName, FirstName, LastName, Computer, Account) VALUES ("+myticketid+", "+"'"+myticketnumber+"'"+", "+"'"+myrequestreason+"'"+", "+"'"+myrequestorname+"'"+","+"'"+myfirstname+"'"+", "+"'"+mylastname+"'"+ ", "+"'"+mycomputer+"'"+ ", " +"'"+myaccount+"'"+ " ) "
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
Function DropDB()
On Error Resume Next
Err.Clear
Admin = WScript.Arguments.Item(2)
saPwd = WScript.Arguments.Item(3)
dbname = WScript.Arguments.Item(4)
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
