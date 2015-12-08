<%
' This page is made to emulate a Web Service-based Ticket System for PAR
'
' request.querystring applies to HTTP GET invocations
' request.form applied to HTTP POST invocations
'
' initThings is a subroutine at the bottom of the file that can be used to
' create a list of "valid" ticket information in a VB Dictionary object.
' The Dictionary object (TSDict) is then queried for a ticket number to
' output data.
'
dim TSDict, errTextArr, tNum, uName, doError, doErrorVal

errTextArr = Array( "Authentication or other unknown error", _
                    "Ticket Number not found", _
                    "Ticket Number not in recognizable format")

if ( request.querystring("tNum") <> "" ) then
   tNum = request.querystring("tNum")
elseif ( request.form("tNum") <> "" ) then
   tNum = request.form("tNum")
else
   tNum = "unspecified"
end if

if ( request.querystring("uName") <> "" ) then
   uName = request.querystring("uName")
elseif ( request.form("uName") <> "" ) then
   uName = request.form("uName")
else
   uName = "unspecified"
end if

if ( request.querystring("fmt") <> "" ) then
   fmt = request.querystring("fmt")
elseif ( request.form("fmt") <> "" ) then
   fmt = request.form("fmt")
else
   fmt = "1"
end if

if ( request.querystring("static") <> "" ) then
   staticText = request.querystring("static")
elseif ( request.form("static") <> "" ) then
   staticText = request.form("static")
else
   staticText = "UNSPECIFIED"
end if

initThings

' by setting the doError parameter to any non-empty value you can force an
' error during validation.  Your choice as to what error you force.
if ( request.querystring("doError") <> "" ) then
   doError = true
   doErrorVal = request.querystring("doError")
elseif ( request.form("doError") <> "" ) then
   doError = true
   doErrorVal = request.form("doError")
else
   doError = false
end if

' If the ticket number exists in the dictionary, get it for output
' otherwise make the page return an error.
if ( TSDict.Exists(tNum) ) then
   set oTS = TSDict(tNum)
else
   doError = true
   doErrorVal = 1
end if

'encoding="iso-8859-1"? 
response.write "<?xml version=""1.0"" encoding=""iso-8859-1"" ?>" & vbcrlf
response.ContentType = "text/xml; charset utf-8"
if ( doError ) then
   if ( isNumeric(doErrorVal) and cLng(doErrorVal) <= uBound(errTextArr) ) then
      errText = errTextArr(cLng(doErrorVal))
   else
      errText = errTextArr(0)
   end if
%>
<ErrorDetailsObject xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
   <ErrorNumber><%=errText%></ErrorNumber>
   <ErrorText>An Error Occurred</ErrorText>
   <ErrorDescription><%=doErrorVal%></ErrorDescription>
</ErrorDetailsObject>
   <%
elseif (fmt = "2" ) then
%>
<VCResponse success="true">
<record id="08702703" type="CHANGE">
<APPROVAL_STATUS>APPROVED</APPROVAL_STATUS>
<TYPE_OF_RECORD>C</TYPE_OF_RECORD>
<INSTALL_DATE>11/08/10</INSTALL_DATE>
</record></VCResponse>
<%
else
%>
<TicketDetailsObject xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
   <CallRef><%=Server.HTMLEncode(tNum)%></CallRef> 
   <SoeId><%=Server.HTMLEncode(oTS.UserName)%></SoeId>
   <AffectedSoeId><%=Server.HTMLEncode(oTS.UserName)%></AffectedSoeId>
   <ProductName><%=oTS.ProdName%></ProductName>
   <Severity><%=oTS.Severity%></Severity>
   <Status><%=oTS.Status%></Status>
   <DateLogged><%=oTS.OpenDate%></DateLogged>
   <Description><%=oTS.Description%></Description>
   <OwnerGroupName>Virtual Incident Development</OwnerGroupName>
   <TicketType><%=oTS.TicketType%></TicketType>
   <AssignmentDetails>
      <GroupName>Virtual Incident Development</GroupName> 
      <GroupID>3489</GroupID> 
      <SoeId /> 
      <DateAssigned>8/10/2010 12:51:03 PM</DateAssigned>
      <Comment>Ticket Assigned</Comment> 
   </AssignmentDetails>
   <RevisionDetails>
      <SoeID><%=Server.HTMLEncode(oTS.UserName)%></SoeID>
      <UserUpdate>Ticket Created</UserUpdate>
      <DateUpdate>8/10/2010 12:51:03 PM</DateUpdate>
   </RevisionDetails>
   <RevisionDetails>
      <SoeID><%=Server.HTMLEncode(oTS.UserName)%></SoeID>
      <UserUpdate>Ticket Created</UserUpdate>
      <DateUpdate>8/10/2010 12:51:03 PM</DateUpdate>
   </RevisionDetails>
</TicketDetailsObject>
<%
end if

'
' crude, but hopefully simple to set up TicketSystem "database" for Web
' Services testing
'
class Ticket
   ' nothing fancy, just some "columns" of data, no get or set properties
   ' just public data members
   public TicketNumber, UserName, Description, ProdName
   public Severity, Status, OpenDate, TicketType

   ' quick and simple way to initialize all the data member in one go
   public sub init(aTNum,aUName,aDesc,aProd,aSev,aStatus,aODt,aTType)
      TicketNumber = aTNum
      UserName = aUName
      Description = aDesc
      ProdName = aProd
      Severity = aSev
      Status = aStatus
      OpenDate = aODt
      TicketType = aTType
   end sub
end class

sub initThings()
   dim C
   set TSDict = CreateObject("Scripting.Dictionary")

   ' follow the bouncing pattern:
   ' 1) create a new instance of the Ticket Class
   ' 2) call the init sub to set all the data values
   ' 3) add the class to the dictionary using the ticket number as the index
   ' 4) repeat as necessary
   set C = new Ticket
   C.init 666, _
   "foo", _
   "StaticText: " & staticText & "&lt;WBR&gt;Can you please support testing of the new UK Retail CD node LDNGCBTRX. We attempt to send a test file xxtest t&lt;WBR&gt;o the production SFS node file system under the location '#d01>buk_prod_exec>xfer_area' . Please remove the te&lt;WBR&gt;st file following a successful test transmission. Thank you. &lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;", _
   "Virtual Tech", _
   3, _
   "Open", _
   "12/1/2010 13:14:15", _
   4
   TSDict.Add "666", C

   set C = new Ticket
   C.init 33004768, _
   "gm37410", _
   "Can &#128;&#129;&#240;&#241; you please support testing of the new UK Retail CD node LDNGCBTRX. We attempt to send a test file xxtest t&lt;WBR&gt;o the production SFS node file system under the location &#145;#d01>buk_prod_exec>xfer_area&#146; . Please remove the te&lt;WBR&gt;st file following a successful test transmission. Thank you. &lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;&lt;WBR&gt;", _
   "10.Technology Request (Move Add Change not in Mark", _
   3, _
   "Work in Progress", _
   "10/10/2011 12:01:38 PM", _
   7
   TSDict.Add "33004768", C

   set C = new Ticket
   C.init 33006666, _
   "gm37410", _
   "Can &#128;&#129;&#240;&#241; you &#129; &#130; &#131; &#132; &#133; &#134; &#135; &#136; &#137; &#138; &#139; &#140; &#141; &#142; &#143; &#144; &#145; &#146; &#147; &#148; &#149; &#150; &#151; &#152; &#153; &#154; &#155; &#156; &#157; &#158; &#159; &#160; &#161; &#162; &#163; &#164; &#165; &#166; &#167; &#168; &#169; &#170; &#171; &#172; &#173; &#174; &#175; &#176; &#177; &#178; &#179; &#180; &#181; &#182; &#183; &#184; &#185; &#186; &#187; &#188; &#189; &#190; &#191;", _
   "10.Technology Request (Move Add Change not in Mark", _
   3, _
   "Work in Progress", _
   "10/10/2011 12:01:38 PM", _
   7
   TSDict.Add "33006666", C

   set C = new Ticket
   C.init 33007777, _
   "gm37410", _
   "Can &#128;&#129;&#240;&#241; you &#129; &#130; &#131; &#132; &#133; &#134; &#135; &#136; &#137; &#138; &#139; &#140; &#141; &#142; &#143; &#144; &#145; &#146; &#147; &#148; &#149; &#150; &#151; &#152; &#153; &#154; &#155; &#156; &#157; &#158; &#159; &#160; &#161; &#162; &#163; &#164; &#165; &#166; &#167; &#168; &#169; &#170; &#171; &#172; &#173; &#174; &#175; &#176; &#177; &#178; &#179; &#180; &#181; &#182; &#183; &#184; &#185; &#186; &#187; &#188; &#189; &#190; &#191; &#192; &#193; &#194; &#195; &#196; &#197; &#198; &#199; &#200; &#201; &#202; &#203; &#204; &#205; &#206; &#207; &#208; &#209; &#210; &#211; &#212; &#213; &#214; &#215; &#216; &#217; &#218; &#219; &#220; &#221; &#222; &#223; &#224; &#225; &#226; &#227; &#228; &#229; &#230; &#231; &#232; &#233; &#234; &#235; &#236; &#237; &#238; &#239; &#240; &#241; &#242; &#243; &#244; &#245; &#246; &#247; &#248; &#249; &#250; &#251; &#252; &#253; &#254; &#255;", _
   "10.Technology Request (Move Add Change not in Mark", _
   3, _
   "Work in Progress", _
   "10/10/2011 12:01:38 PM", _
   7
   TSDict.Add "33007777", C

   set C = new Ticket
   C.init 123,"User1","Description of a Ticket","ProductA",4,"Assigned","12/1/2010 01:14:15 PM",5
   TSDict.Add "123", C

   set C = new Ticket
   C.init 12345,"User1","Description of a Ticket","Windows",3,"Assigned","12/1/2010 01:14:15 PM",5
   TSDict.Add "12345", C

   set C = new Ticket
   C.init 22222,"User1","Description of a Ticket","Linux",3,"Assigned","12/1/2010 01:14:15 PM",5
   TSDict.Add "22222", C

   set C = new Ticket
   C.init 77777,"User1","Description of a Ticket","MyProduct",4,"Assigned","12/1/2010 01:14:15 PM",5
   TSDict.Add "77777", C

end sub
%>


