'define arguments
actn = WScript.Arguments.Item(0)
appname = WScript.Arguments.Item(1)

Select Case actn

Case "create"
    AppUser = WScript.Arguments.Item(2)
	AppUserPwd = WScript.Arguments.Item(3)
	ServerPath = WScript.Arguments.Item(4)
	Call Create(appname,AppUser,AppUserPwd,ServerPath)
Case "start"
	Call StartApp(appname)
Case "stop"
	Call StopApp(appname)
Case "delete"
	Call Delete(appname)
End Select

wscript.Quit 0

'create new com+ application
Sub Create(appname,appuser,appuserpwd,component)
    On Error Resume Next
    Err.Clear
    
	AppID = "{DEADBEEF-BADD-BADD-BADD-2BE2DEF4BEDD}"
	Dim cat, apps, app
	Set cat = CreateObject("COMAdmin.COMAdminCatalog")
	Set apps = cat.GetCollection("Applications")
	Set app = apps.Add()
    
	' ID is an arbitrary GUID, that you can create using uuidgen
	app.Value("ID") = AppID
	app.Value("Name") = appname
	app.Value("Description") = "Test application for eSPCW testing"
	app.Value("Activation") = 1' COMAdmin.COMAdminActivationOptions.COMAdminActivationLocal
	app.Value("ApplicationAccessChecksEnabled") = 1 'COMAdmin.COMAdminAccessChecksLevelOptions.COMAdminAccessChecksApplicationComponentLevel
	app.Value("Identity") = appuser
	app.Value("Password") = appuserpwd
	app.Value("RunForever") = True
	Call apps.SaveChanges
    'install component
	cat.InstallComponent appname, component, "", ""
	'Add Role to application
	RoleName = "Admins"
	Call apps.Populate
	Set roles = apps.GetCollection("Roles",AppID)
	Set role = roles.Add()
	   role.Value("Name") = RoleName
	Call   roles.SaveChanges
	'Assign a user account to the role
	Set users = roles.GetCollection("UsersInRole",role.Key)
	Set user = users.Add()
	   user.Value("User") = appuser
	Call users.SaveChanges
	
	if  Err.Number <> 0  then
	   Wscript.echo "Exception:" & vbCrLf &_
        "    Error number: " & Err.Number & vbCrLf &_
        "    Error description: " & Err.Description & vbCrLf
	End if
End Sub

Sub Delete(appname)
    On Error Resume Next
    Err.Clear

    Dim cat, apps, app
	Set cat = CreateObject("COMAdmin.COMAdminCatalog")
	Set apps = cat.GetCollection("Applications")
	Call apps.Populate
	for i=apps.Count-1 to 0 step -1
	   if apps.Item(i).Name = appname Then
	      apps.Remove(i)
		  apps.SaveChanges()
	   End if
	Next
	
	if  Err.Number <> 0  then
	   wscript.echo "Exception:" & vbCrLf &_
        "    Error number: " & Err.Number & vbCrLf &_
        "    Error description: " & Err.Description & vbCrLf
	End if
End Sub

Sub StartApp(appname)
    On Error Resume Next
    Err.Clear
	
    Dim cat, apps, app
	Set cat = CreateObject("COMAdmin.COMAdminCatalog")
	Set apps = cat.GetCollection("Applications")
	Call apps.Populate
    cat.StartApplication(appname)
	if  Err.Number <> 0  then
	   wscript.Quit Err.Number
	Else
	    wscript.echo "Application " & appname & " started" 
	End if
End Sub

Sub StopApp(appname)
    On Error Resume Next
    Err.Clear
    Dim cat, apps, app
	Set cat = CreateObject("COMAdmin.COMAdminCatalog")
	Set apps = cat.GetCollection("Applications")
	Call apps.Populate
    cat.ShutdownApplication(appname)
	if  Err.Number <> 0  then
	   wscript.echo "Exception:" & vbCrLf &_
        "    Error number: " & Err.Number & vbCrLf &_
        "    Error description: " & Err.Description & vbCrLf
	Else
	    wscript.echo "Application " & appname & " stopped"
	End if
End Sub