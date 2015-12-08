Dim strWebsite
Dim aRequest
strWebsite = WScript.Arguments.Item(0)
strURI= "http://" & strWebsite & "/"
Err.Clear
On Error Resume Next
Set aRequest = CreateObject ("Microsoft.XMLHTTP")
aRequest.Open "GET",strURI,False
aRequest.Send
Wscript.Echo aRequest.responseText
If aRequest.responseText <>"" then
    Wscript.echo "Site answered"
End If

if  Err.Number <> 0  then   ' An exception occurred
   ' Check if access denied error occured
   If Err.Number = "-2147024891" Then
      Wscript.Echo "Access denied"
   Else	  
	Wscript.Echo "Exception:" & vbCrLf &_
        "    Error number: " & Err.Number
   End If	
End If