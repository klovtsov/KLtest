ProjDir = WScript.Arguments.Item(0) ' Project root folter
BrowserDownloadDir = WScript.Arguments.Item(1)
TPAMHostAddress = WScript.Arguments.Item(2)

TPAMFullHostAddress = "https://" + TPAMHostAddress

BrowserDownloadDirRep = Replace(BrowserDownloadDir, "\", "\\")

'MsgBox BrowserDownloadDirRep

ffpf = ProjDir + "\ff4s_profile\"
'TPAMffprofilefolder = ProjDir + "\ff4s_profile\"

Set WshShell = CreateObject("WScript.Shell")
strTEMP = WshShell.ExpandEnvironmentStrings("%TEMP%")

Set fso = CreateObject("Scripting.FileSystemObject")

If FSO.FolderExists(ProjDir) Then
Else
    MsgBox "The folder " & ProjDir & " doesn't exists!", 16
End If

Set MainFolder = fso.GetFolder(strTEMP)
For Each fldr In MainFolder.SubFolders
    If fldr.DateLastModified > LastDate Or IsNull(LastDate) Then
        LastFolder = fldr.Name
        LastDate = fldr.DateLastModified
    End If
Next

cpf = strTEMP + "\" + LastFolder + "\"
'MsgBox cpf


cert8db = cpf + "cert8.db"
cert_overridetxt = cpf + "cert_override.txt"

FSO.CopyFile cert8db, ffpf
FSO.CopyFile cert_overridetxt, ffpf

Set objFSO = CreateObject("Scripting.FileSystemObject")

'modify Prefs.js

Prefs = ffpf + "\Prefs.js"

Do While NOT objFSO.FileExists(Prefs)
Loop

Set tf = objFSO.OpenTextFile(Prefs, 8, True)


tf.WriteBlankLines(3)
'tf.WriteLine("user_pref(" + Chr(34) + "dom.disable_open_during_load" + Chr(34) + ", false);") 

tf.WriteLine("user_pref(" + Chr(34) + "browser.download.dir" + Chr(34) + ","+ Chr(34) + BrowserDownloadDirRep+ Chr(34) + ");") 

tf.WriteLine("user_pref(" + Chr(34) + "browser.download.folderList" + Chr(34) + ",2);") 

tf.WriteLine("user_pref(" + Chr(34) + "browser.download.manager.closeWhenDonet" + Chr(34) + ",true);") 

tf.WriteLine("user_pref(" + Chr(34) + "extensions.newAddons" + Chr(34) + ",false);") 

tf.WriteLine("user_pref(" + Chr(34) + "extensions.update.notifyUser" + Chr(34) + ",false);") 

tf.WriteLine("user_pref(" + Chr(34) + "network.auth.force-generic-ntlm" + Chr(34) + ",true);") 

tf.WriteLine("user_pref(" + Chr(34) + "network.automatic-ntlm-auth.trusted-uris" + Chr(34) + ","+ Chr(34) + TPAMFullHostAddress + Chr(34) + ");") 

tf.WriteLine("user_pref(" + Chr(34) + "network.cookie.prefsMigrated" + Chr(34) + ",true);") 

tf.WriteLine("user_pref(" + Chr(34) + "network.negotiate-auth.trusted-uris" + Chr(34) + ","+ Chr(34) + TPAMFullHostAddress + Chr(34) + ");") 

tf.WriteLine("user_pref(" + Chr(34) + "browser.shell.checkDefaultBrowser" + Chr(34) + ",false);")

tf.WriteLine("user_pref(" + Chr(34) + "browser.download.useDownloadDir" + Chr(34) + ",true);") 

tf.WriteLine("user_pref(" + Chr(34) + "browser.download.manager.useWindow" + Chr(34) + ",false);") 

tf.WriteLine("user_pref(" + Chr(34) + "browser.download.manager.showWhenStarting" + Chr(34) + ",false);") 

tf.WriteLine("user_pref(" + Chr(34) + "browser.download.useDownloadDir" + Chr(34) + ",true);") 

tf.WriteLine("user_pref(" + Chr(34) + "browser.helperApps.neverAsk.saveToDisk" + Chr(34) + ","+ Chr(34) + "application/octet-stream" + Chr(34) + ");") 
  
  tf.WriteBlankLines(3)
  
  tf.Close
