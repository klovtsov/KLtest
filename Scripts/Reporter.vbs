Dim fso, folder, files, NewsFile,ResPath
On Error resume Next
ResPath = WScript.Arguments.Item(0)          ' Path to the result files       
SummFilePath = WScript.Arguments.Item(1)     ' File where total report will be created
  Set fso = CreateObject("Scripting.FileSystemObject")
  If ResPath = "" Then
      msgbox "A folder with result files is not set."
      wscript.Quit
  End If
  If SummFilePath = "" Then
      msgbox "A summary report file is not set."
      wscript.Quit
  End If
  ''Delete summary file 
If fso.FileExists(SummFilePath) Then
   fso.DeleteFile SummFilePath
End If
'Get an array which contains sorted files list (files are sorted by last modification time)
files = GetFilesFromFolder(ResPath)
FilesCount = Ubound(files)
If Err.Number <> 0 Then
   msgbox "Check script arguments. Perhaps they are referring to non-existent paths."
   Err.Clear
   wscript.Quit
End If
 '''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
 
 '!!!Calculate Summary Values!!!
tcSize = 0
IsPass = "passed"
TotalTime = 0
TotalTest = 0
TotalPass = 0 
TotalFailed = 0
CmdPass = 0
CmdFail = 0
CmdErr = 0
Dim testTableLogs()
Dim testTableStatus()
Dim testTableName()
Dim testTableLink()

'Creating Summary file
Set SumObjIE = CreateObject("InternetExplorer.Application") 
    SumObjIE.Visible = "False"
    SumObjIE.Navigate files(0)                     'Mean the first file as a template
	Set Document = SumObjIE.Document

'Copy head from first file to result file
Style = Document.getElementsByTagName("STYLE").item(0).outerHTML
If Err.Number <> 0 Then
	ErrMsg = "The file " & files(0) & "has a wrong structure. Style element was not found."
	msgbox ErrMsg
	wscript.Quit
End If
Set SummFile = fso.CreateTextFile(SummFilePath, True)
SummFile.WriteLine("<html>")
SummFile.WriteLine("<HEAD>")
SummFile.Write Style
Title = Document.getElementsByTagName("Title").item(0).outerHTML
SummFile.Write title
SummFile.WriteLine("</HEAD>")
Call SumObjIE.Quit
SummFile.WriteLine("<body>")
SummFile.WriteLine("<h1>Test suite results </h1>")

For i = 0 to FilesCount-1
	'Change statistics in the united file
	Stat = GetFileStat(files(i))
	If Stat(0) = "failed" Then
	   IsPass = "failed"
	End If
	TotalTime = TotalTime + Stat(1)
	TotalTest = TotalTest + Stat(2)
	TotalPass = TotalPass + Stat(3)
	TotalFailed = TotalFailed + Stat(4)
	CmdPass = CmdPass + Stat(5)
	CmdFail = CmdFail + Stat(6)
	CmdErr = CmdErr + Stat(7)
	'Create an array which contains tcases log
	TotTestInFile = Int(Stat(2))
	for n=0 to TotTestInFile - 1
		a = GetTCTable(files(i),n)
	    'Replace test result number to total
	    Set objRegExp = CreateObject("VBScript.RegExp")
		objRegExp.Pattern = "testresult" & n
		newName = "testresult" & tcSize
		Res = objRegExp.Replace(a(0), newName)
		'Add to array
		ReDim Preserve testTableLink(tcSize)
		   testTableLink(tcSize) = newName
		ReDim Preserve testTableLogs(tcSize)
			testTableLogs(tcSize) = Res
		ReDim Preserve testTableStatus(tcSize)
			testTableStatus(tcSize) = a(1)
		ReDim Preserve testTableName(tcSize)
			testTableName(tcSize) = a(2)
		tcSize = tcSize + 1
	Next
Next

Set SumObjIE = CreateObject("InternetExplorer.Application") 
	SumObjIE.Visible = "False"
    SumObjIE.Navigate files(0)
    Set Document = SumObjIE.Document
'Modify total html
 Set resTbl = Document.getElementsByTagName("table").item(0)            'Find first table in the document
  resTbl.getElementsByTagName("td").item(1).innerText = IsPass       'Is passed
  resTbl.getElementsByTagName("td").item(3).innerText = TotalTime    'Total time
  resTbl.getElementsByTagName("td").item(5).innerText = TotalTest    'Total tests
  resTbl.getElementsByTagName("td").item(7).innerText = TotalPass    'Total passed
  resTbl.getElementsByTagName("td").item(9).innerText = TotalFailed  'Total failed
  resTbl.getElementsByTagName("td").item(11).innerText = CmdPass     'Command passed
  resTbl.getElementsByTagName("td").item(13).innerText = CmdFail     'Command Failures
  resTbl.getElementsByTagName("td").item(15).innerText = CmdErr      'Command Errors
 
'Remove child nodes from sample file
childList = Document.getElementsByTagName("table").item(2).ChildNodes.Item(0).ChildNodes.length
For chi = 0 to childList-1
    Set ChildNode = Document.getElementsByTagName("table").item(2).ChildNodes.Item(0).ChildNodes.Item(chi)
    ChildNode.removeNode(True)
Next

Set SummSubTblTbody = resTbl.getElementsByTagName("td").item(20).ChildNodes.Item(0).ChildNodes.Item(0)   'tbody
childList = SummSubTblTbody.ChildNodes.length
For chi = 1 to childList-1
    Set ChildNode = SummSubTblTbody.ChildNodes.Item(chi)
    ChildNode.removeNode(True)
Next

'Add Test cases tables to result file
For i =0 to tcSize-1
   'Add values to the summary table
   Set SummSubTbl = resTbl.getElementsByTagName("td").item(20).ChildNodes.Item(0)
   Set newSubTblElem = Document.createElement("tr")
   Call newSubTblElem.setAttribute("class",testTableStatus(i))
   SummSubTbl.ChildNodes.Item(0).appendChild(newSubTblElem)
   Set newSubTblElemCell = Document.createElement("td")
   newSubTblElem.appendChild(newSubTblElemCell)
   Set newSubTblElemCellVal = Document.createElement("a")
   Call newSubTblElemCellVal.setAttribute("href","#" & testTableLink(i))
   newSubTblElemCellVal.innerHtml = testTableName(i)
   newSubTblElemCell.appendChild(newSubTblElemCellVal)
   'Add table for testcase log   
    Set newElem = Document.createElement("tr")
    newElem.innerHtml = testTableLogs(i) 
    Document.getElementsByTagName("table").item(2).ChildNodes.Item(0).appendChild(newElem)
Next

If IsPass = "failed" Then
   SuiteColorStatus = "title status_failed"
Else
   SuiteColorStatus = "title status_passed"
End If
Call SummSubTblTbody.ChildNodes.Item(0).setAttribute("className",SuiteColorStatus)
'Write results
 'Copy changed Table to result file
strResTbl = resTbl.outerHTML
str = Document.getElementsByTagName("table").item(2).outerHTML       'Write tests log
SummFile.Write strResTbl                                             'Write summary table
SummFile.Write str

'Close all files and send message
SummFile.WriteLine("</body>")
SummFile.WriteLine("</html>")
SumObjIE.Quit()
Call LaunchFF(SummFilePath)

Function GetFileStat(filename)  'Gets stat for a specified file 
  Dim Ans(8)
  On Error Resume Next
  Set objIE = CreateObject("InternetExplorer.Application") 'create DOM object
      objIE.Visible = "False"
      objIE.Navigate filename
      Set Document = objIE.Document
  Set resTbl = Document.body.getElementsByTagName("table").item(0)     'Find first table in the document
      Ans(0) = resTbl.getElementsByTagName("td").item(1).innerText    'Is passed
	  TotTime = resTbl.getElementsByTagName("td").item(3).innerText    'Total time
	  Ans(1) = CheckValues(TotTime,"TotalTime")
	  TotTest = resTbl.getElementsByTagName("td").item(5).innerText
	  Ans(2) =  CheckValues(TotTest,"TotalTest")                        'Total tests
	  PasTest = resTbl.getElementsByTagName("td").item(7).innerText
	  Ans(3) =  CheckValues(PasTest,"TestPassed")                       'Passed tests
	  FldTest = resTbl.getElementsByTagName("td").item(9).innerText      'Failed tests
	  Ans(4) = CheckValues(FldTest,"TestFailed")
	  cmdp = resTbl.getElementsByTagName("td").item(11).innerText     'Command passed
	  Ans(5) = CheckValues(cmdp,"CommandPassed")
	  cmdf = resTbl.getElementsByTagName("td").item(13).innerText
	  Ans(6) =  CheckValues(cmdf,"CommandFailed")                                      'Command Failures
	  cmde = resTbl.getElementsByTagName("td").item(15).innerText      'Command Errors 
	  Ans(7) = CheckValues(cmde,"CommandErrors")
  If Err.Number <> 0 Then
     ErrMsg = "It's unable to get statistics from the file: " & filename & "." & vbCrLf _
    	 & "Maybe file does not contain the necessary data."
	msgbox ErrMsg
	Ans(0) = "failed"
	For k=1 to 7
	    Ans(k) = 0
	Next
  End If
  Err.Clear  
  GetFileStat = Ans
  Call objIE.Quit
End Function

Function GetTCTable(filename,number)
    Dim Ans(3) '0 - html code of test log, 1 - test status(failed or passed), 2 - name of test 
    On Error Resume Next
    Set CurrObjIE = CreateObject("InternetExplorer.Application") 'create DOM object
		CurrObjIE.Visible = "False"
		CurrObjIE.Navigate filename
		Set Document = CurrObjIE.Document
	name = "testresult" & number
	Set TcTable = Document.getElementsByName(name).item(0).ParentNode
	str = TcTable.OuterHTML
	Ans(0)  = str
	'Check result number	       
	Set tHead = TcTable.ChildNodes.Item(2).ChildNodes.Item(0).ChildNodes.Item(0).ChildNodes.Item(0)
	Stat =tHead.getAttribute("className")
	Set objRegExp = CreateObject("VBScript.RegExp")
	oPattern= "(status_passed|status_failed)"
	objRegExp.Pattern = oPattern
	Set objMatches = objRegExp.Execute(Stat)
	Set objMatch = objMatches.Item(0)
    Set objSubmatches = objMatch.Submatches
    Ans(1) = objSubmatches.Item(0)
	'get test name
	Set objRegExp1 = CreateObject("VBScript.RegExp")
	oPattern1 = "name=.*(testresult[0-9]*).*>(.*)\.html</a>"
    objRegExp1.IgnoreCase = True
	objRegExp1.Pattern = oPattern1
   Set objMatches1 = objRegExp1.Execute(str)
   Set objMatch1 = objMatches1.Item(0)
   Set objSubmatches1 = objMatch1.Submatches
   Ans(2) = objSubmatches1.Item(1)          
	'Ans(2) = tHead.ChildNodes.Item(0).innerHTML
	If Err.Number <> 0 Then
	    ErrMsg = "When reading a file: " & filename & " an error has occurred" & vbCrLf _
            & Err.Description		
		msgbox ErrMsg
	End If
	GetTCTable = Ans
	Call CurrObjIE.Quit
End Function

Sub LaunchFF(filepath)
    Dim objShell, ffloc, PA, PF
    'Check computer architecture
	Set oShell = CreateObject( "WScript.Shell" )
    PA=oShell.ExpandEnvironmentStrings("%PROCESSOR_ARCHITECTURE%")
	If PA = "AMD64" Then
	    PF=oShell.ExpandEnvironmentStrings("%ProgramFiles(x86)%")
	Else
	    PF=oShell.ExpandEnvironmentStrings("%ProgramFiles%")
	End If
	'Define FF location
    ffloc = PF & "\Mozilla Firefox\firefox.exe"
    'run firefox
	cmd = """" & ffloc & """" & " -new-window " & filepath
    oShell.Run(cmd)
    Set oShell = Nothing
End Sub

Function CheckValues(str,ParName)
    If IsNumeric(str) Then
	    CheckValues = str
    Else
	   msgstr = "The " & ParName & " is not numerical. Value 0 will be set."
	   'Call msgbox(msgstr,0,"Warning")
	   CheckValues = "0"
	End If
End Function

Function GetFilesFromFolder(FolderName)
	Dim Ans()
	'This function find all files in folder (and subfolders) and returns
	'list of filenames where files are sorted by DateLastModified
	Const adVarChar = 200                     ' tells the script that we want these new fields to have the variant data type.
	Const MaxCharacters = 255                 ' tells the script that the field can contain a maximum of 255 characters.
	Set DataList = CreateObject("ADOR.Recordset")
	DataList.Fields.Append "FileName", adVarChar, MaxCharacters
	DataList.Fields.Append "FileDate", adVarChar, MaxCharacters
	DataList.Open
	'Fill our dataset by file properties
	set fso = CreateObject("Scripting.fileSystemObject") 
	set fold = fso.getFolder(FolderName)
	FoldSize = fold.files.count
	Redim Ans(FoldSize)
	for each file in fold.files
		DataList.AddNew
		DataList("FileName") = File.Path
		DataList("FileDate") = file.DateLastModified
		DataList.Update
	Next
	DataList.Sort = "FileDate"
	DataList.MoveFirst
	i = 0
	Do Until DataList.EOF
		Ans(i) = DataList.Fields.Item("FileName")
	DataList.MoveNext
	i=i+1
	Loop
	GetFilesFromFolder = Ans
End Function