' Copy firefox profile folder to Project root folder "c:\TPAM\ff4s_profile"
ProjDir = WScript.Arguments.Item(0) ' Project root folter
Set objFSO = CreateObject("Scripting.FileSystemObject")
Set WshShell = CreateObject("WScript.Shell")
strAppDATA = WshShell.ExpandEnvironmentStrings("%APPDATA%")
ffpf = ProjDir + "\ff4s_profile"
objFSO.CreateFolder ffpf
pFolder = strAppDATA + "\Mozilla\Firefox\Profiles\"
Set Folder = objFSO.GetFolder(pFolder)
Set SubFolder = Folder.SubFolders
For Each f1 in SubFolder
      s = f1.name 
      Next
strSource = pFolder + s
strDestination = ffpf
r = WshShell.Run("%COMSPEC% /c COPY /v " & Chr(34) + strSource  + Chr(34)  & " " & strDestination ,0 ,TRUE)
Prefs = ffpf + "\Prefs.js"
sessionstorejs = ffpf + "\sessionstore.js"
objFSO.DeleteFile  sessionstorejs, 1
Set tf = objFSO.OpenTextFile(Prefs, 8, True)
tf.WriteBlankLines(1)
tf.WriteLine("user_pref(" + Chr(34) + "dom.disable_open_during_load" + Chr(34) + ", false);") 
tf.WriteBlankLines(1)
tf.Close
