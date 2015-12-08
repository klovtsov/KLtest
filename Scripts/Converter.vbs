SuiteName = WScript.Arguments.Item(0)
TestCaseName = WScript.Arguments.Item(1)
NumberOfSuccess = WScript.Arguments.Item(2)
NumberOfFailures = WScript.Arguments.Item(3)
ReportPath = WScript.Arguments.Item(4)
'LogData = WScript.Arguments.Item(5)


If NumberOfFailures >= 1 Then
	result = "failed"
	status = "status_failed"
Else
	result = "passed"
	status = "status_passed"
End If

NumberOfSuccessD = CInt(NumberOfSuccess)
NumberOfFailuresD = CInt(NumberOfFailures)
numTestTotal = NumberOfSuccessD + NumberOfFailuresD
numTestTotal=CStr(numTestTotal)

If NumberOfFailuresD >= 1 Then
	NumberOfFailuresTests = "1"
	NumberOfSuccessTests = "0"
Else
	NumberOfFailuresTests = "0"
	NumberOfSuccessTests = "1"
End If


Set objFS = CreateObject("Scripting.FileSystemObject")

While objFS.FileExists(ReportPath)
   Ext = ".html"
   Ext2 ="2.html"
   ReportPath = Replace(ReportPath,Ext,Ext2)
Wend

Set objNewFile = objFS.CreateTextFile(ReportPath)
objNewFile.WriteLine "<html>"
objNewFile.WriteLine "<head><style type='text/css'>"
objNewFile.WriteLine "body, table {"
objNewFile.WriteLine "    font-family: Verdana, Arial, sans-serif;"
objNewFile.WriteLine "    font-size: 12;"
objNewFile.WriteLine "}"
objNewFile.WriteLine "table {"
objNewFile.WriteLine "    border-collapse: collapse;"
objNewFile.WriteLine "    border: 1px solid #ccc;"
objNewFile.WriteLine "}"
objNewFile.WriteLine "th, td {"
objNewFile.WriteLine "    padding-left: 0.3em;"
objNewFile.WriteLine "    padding-right: 0.3em;"
objNewFile.WriteLine "}"
objNewFile.WriteLine "a {"
objNewFile.WriteLine "    text-decoration: none;"
objNewFile.WriteLine "}"
objNewFile.WriteLine ".title {"
objNewFile.WriteLine "    font-style: italic;"
objNewFile.WriteLine "}"
objNewFile.WriteLine ".selected {"
objNewFile.WriteLine "    background-color: #ffffcc;"
objNewFile.WriteLine "}"
objNewFile.WriteLine ".status_done {"
objNewFile.WriteLine "    background-color: #eeffee;"
objNewFile.WriteLine "}"
objNewFile.WriteLine ".status_passed {"
objNewFile.WriteLine "    background-color: #ccffcc;"
objNewFile.WriteLine "}"
objNewFile.WriteLine ".status_failed {"
objNewFile.WriteLine "    background-color: #ffcccc;"
objNewFile.WriteLine "}"
objNewFile.WriteLine ".breakpoint {"
objNewFile.WriteLine "    background-color: #cccccc;"
objNewFile.WriteLine "    border: 1px solid black;"
objNewFile.WriteLine "}"
objNewFile.WriteLine "</style><title>" + SuiteName + " suite results</title></head>"
objNewFile.WriteLine "<body>"
objNewFile.WriteLine "<h1>" + SuiteName + " suite results </h1>"
objNewFile.WriteLine "<table>"
objNewFile.WriteLine "<tr>"
objNewFile.WriteLine "<td>result:</td>"
objNewFile.WriteLine "<td>" + result + "</td>"
objNewFile.WriteLine "</tr>"
objNewFile.WriteLine "<tr>"
objNewFile.WriteLine "<td>totalTime:</td>"
objNewFile.WriteLine "<td>n/a</td>"
objNewFile.WriteLine "</tr>"
objNewFile.WriteLine "<tr>"
objNewFile.WriteLine "<td>numTestTotal:</td>"
objNewFile.WriteLine "<td>1</td>"
objNewFile.WriteLine "</tr>"
objNewFile.WriteLine "<tr>"
objNewFile.WriteLine "<td>numTestPasses:</td>"
objNewFile.WriteLine "<td>" + NumberOfSuccessTests + "</td>"
objNewFile.WriteLine "</tr>"
objNewFile.WriteLine "<tr>"
objNewFile.WriteLine "<td>numTestFailures:</td>"
objNewFile.WriteLine "<td>" + NumberOfFailuresTests + "</td>"
objNewFile.WriteLine "</tr>"
objNewFile.WriteLine "<tr>"
objNewFile.WriteLine "<td>numCommandPasses:</td>"
objNewFile.WriteLine "<td>" + NumberOfSuccess + "</td>"
objNewFile.WriteLine "</tr>"
objNewFile.WriteLine "<tr>"
objNewFile.WriteLine "<td>numCommandFailures:</td>"
objNewFile.WriteLine "<td>" + NumberOfFailures + "</td>"
objNewFile.WriteLine "</tr>"
objNewFile.WriteLine "<tr>"
objNewFile.WriteLine "<td>numCommandErrors:</td>"
objNewFile.WriteLine "<td>n/a</td>"
objNewFile.WriteLine "</tr>"
objNewFile.WriteLine "<tr>"
objNewFile.WriteLine "<td>Selenium Version:</td>"
objNewFile.WriteLine "<td>2.0</td>"
objNewFile.WriteLine "</tr>"
objNewFile.WriteLine "<tr>"
objNewFile.WriteLine "<td>Selenium Revision:</td>"
objNewFile.WriteLine "<td>a1</td>"
objNewFile.WriteLine "</tr>"
objNewFile.WriteLine "<tr>"

objNewFile.WriteLine "<td>"
objNewFile.WriteLine "<table id=""suiteTable"" class=""selenium"" border=""1"" cellpadding=""1"" cellspacing=""1""><tbody>"
objNewFile.WriteLine "<tr class=""title "+status+"""><td><b>"+SuiteName+" Suite</b></td></tr>"
objNewFile.WriteLine "<tr class=""  "+status+"""><td>"+TestCaseName+"</td></tr>"
objNewFile.WriteLine "</tbody></table>"
objNewFile.WriteLine "</td>"
objNewFile.WriteLine "<td>&nbsp;</td>"
objNewFile.WriteLine "</tr>"
objNewFile.WriteLine "</table><table><tr>"
objNewFile.WriteLine "<td><a name=""testresult0"">"+ TestCaseName +".html</a><br/><div>"
objNewFile.WriteLine "<table border=""1"" cellpadding=""1"" cellspacing=""1"">"

objNewFile.WriteLine "<thead>"
objNewFile.WriteLine "<tr class=""title "+status+"""><td rowspan=""1"" colspan=""3"">"+TestCaseName+"</td></tr>"
objNewFile.WriteLine "</thead><tbody>"

'objNewFile.WriteLine "<tr class=""  status_done"" style=""cursor: pointer;"">"
'objNewFile.WriteLine "<td>LogData</td>"
'objNewFile.WriteLine "<td>" + LogData + "</td>"
'objNewFile.WriteLine "<td></td>"
'objNewFile.WriteLine "</tr>"

'objNewFile.WriteLine "<td><a name=""testresult0"">LogData: " + LogData + "</a><br/><div>"
'objNewFile.WriteLine "<table border=""1"" cellpadding=""1"" cellspacing=""1"">"
'objNewFile.WriteLine "<thead>"
objNewFile.WriteLine "</tbody></table>"
objNewFile.WriteLine "</div></td>"
objNewFile.WriteLine "</body></html>"
objNewFile.Close
 
