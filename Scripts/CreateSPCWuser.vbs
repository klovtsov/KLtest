'VBScript to create setting for special user.
'Arguments: 1,2 -credentials,which are used to connect to remote machine, 3 - ip address of remote machine
'   4 - local path of xml file, 5 - name of the user, which setting wiil be created

'Check argument number
If WScript.Arguments.Length <> 5 Then
   Wscript.Echo "Wrong number of arguments"
   WScript.Quit(1)
End If
'Set variables
FunctAcnt = WScript.Arguments.Item(0)
FunctAcntPwd = WScript.Arguments.Item(1)
SystemAddress = WScript.Arguments.Item(2)
SettingFilePath = WScript.Arguments.Item(3)
UserName = WScript.Arguments.Item(4)

'Connect to remote computer and map a network drive
'Try to define free drive letter
  strAlpha = "EFGJKLMNOPQRSTUVWXYZ" 'assume one of this letters is free  
  Set objFSO = CreateObject("Scripting.FileSystemObject")
  Set objNetwork = CreateObject("WScript.Network")
  Set colLocalDrives = objFSO.Drives 
  Set CheckDrive = objNetwork.EnumNetworkDrives()
 
On Error Resume Next
Err.Clear
 For intCount = 1 To Len(strAlpha)
    DriveExists = False
    'Take one letter from strAlpha
	strDriveLetter = Mid(strAlpha,intCount,1)
	'Compare letter with existing drives
	For Each objDrive in colLocalDrives
       If objDrive.DriveLetter = strDriveLetter Then
	       DriveExists = True
	       Exit For
		End If
    Next
    'If letter is not found in local drives network drives will be checked
    If Not DriveExists Then
		strDriveLetter = strDriveLetter & ":"
		For intDrive = 0 To CheckDrive.Count - 1 Step 2 
			If CheckDrive.Item(intDrive) = strDriveLetter Then
				DriveExists = True 
				Exit For
			End If
		Next
	End If
	'Checking message
	'Wscript.Echo strDriveLetter & " exists: " & DriveExists
	'If letter is free map network drive and exit loop, else next letter will be checked
	If Not DriveExists Then
	    'Construct share name
		arrPath = Split(SettingFilePath,":")
		strDrLttr = arrPath(0)
		strShare = "\\" & SystemAddress & "\" & strDrLttr & "$"
		strNewPath = strDriveLetter & arrPath(1)
	    'Wscript.Echo "Share: " & strShare
		'Wscript.Echo "New path: " & strNewPath
		'Map network drive
	    objNetwork.MapNetworkDrive strDriveLetter, strShare, False, FunctAcnt, FunctAcntPwd
		'Wscript.Echo "Network drive will be mapped to: "& strDriveLetter
		Exit For
	End If
Next  
if  Err.Number <> 0  then
    Wscript.Echo "Error: " & Err.Description
	WScript.Quit(Err.Number)
End if

On Error Resume Next
'Open XML file
Set xmlDoc = CreateObject("Microsoft.XMLDOM") 'create parser object
xmlDoc.Async = "False"
xmlDoc.Load(strNewPath)
If (xmlDoc.parseError.errorCode <> 0) Then
	Dim myErr
	Set myErr = xmlDoc.parseError
	Wscript.Echo "You have error " & myErr.reason
Else
    Set root = xmlDoc.documentElement
	'User setting will be copied from default user
	Set NodeList = xmlDoc.getElementsByTagName("account")
	AccountExists = False
	For i=0 To (NodeList.length - 1)
	    Set AccElmnt = NodeList.Item(i)
		Set accname = AccElmnt.getElementsByTagName("name").Item(0)
		If accname.Text = "DEFAULT" Then
			AccountExists = True
			'Copy setting to new account
			Set newNode = AccElmnt.cloneNode(true)
			Set NewAccname = newNode.getElementsByTagName("name").Item(0)
			NewAccname.Text = UserName
			root.AppendChild(newNode)
		End If
	Next
	'Save xml document
	xmlDoc.save(strNewPath)
	If Not AccountExists Then
		Wscript.Echo "DEFAULT Account was not found"
	End If
End If
'Pause script execution
WScript.sleep 2000
'Close xml file
Set xmlDoc = Nothing
'unmap network drive
objNetwork.RemoveNetworkDrive strDriveLetter

if  Err.Number <> 0  then
    Wscript.Echo "Error: " & Err.Description
	WScript.Quit(Err.Number)
End if