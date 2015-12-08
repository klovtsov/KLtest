var strDir = WScript.Arguments(0).replace(/\\/g,"\\\\");
var strFile = WScript.Arguments(1);
var strPath = strDir + strFile;
//WScript.Echo("Path: " + strPath);
var bRet = -1;

var oFSO = new ActiveXObject("Scripting.FileSystemObject");

if (oFSO.FileExists(strPath))
{
oFSO.DeleteFile(strPath);
bRet = 0;
}
//WScript.Echo(bRet);
WScript.Quit(bRet);
