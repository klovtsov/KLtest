SettingFilePath = WScript.Arguments.Item(0)
UserName = Replace(WScript.Arguments.Item(1),"\","\\")
outFile = WScript.Arguments.Item(2)
xPathFilter = "//anonymousAuthentication[@userName='" & UserName & "']"  

On Error Resume Next
'Open XML file
Set xmlDoc = CreateObject("Microsoft.XMLDOM") 'create parser object
xmlDoc.Async = "False"
xmlDoc.Load(SettingFilePath)
If (xmlDoc.parseError.errorCode <> 0) Then
	Dim myErr
	Set myErr = xmlDoc.parseError
	Wscript.Echo "You have an error " & myErr.reason
	Wscipt.Quit(1)
Else
	Set NodeList = xmlDoc.selectNodes(xPathFilter)
	Set outputFile = CreateObject("Scripting.FileSystemObject").OpenTextFile(outFile,2,true)
	'Wscript.Echo NodeList.length
	For i=0 To (NodeList.length - 1)
	    outputFile.Write NodeList.Item(i).getAttribute("password")
	Next
	outputFile.Close
	Set outputFile = Nothing
End If
'Close xml file
Set xmlDoc = Nothing