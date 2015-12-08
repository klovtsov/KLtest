var strMessage = WScript.Arguments(0);
var strLog = WScript.Arguments(1).replace(/\\/g,"\\\\");
//WScript.Echo("Trap log: " + strLog);
var bRet = -1;

try {

var oShell = new ActiveXObject("WScript.Shell");

var oFSO = new ActiveXObject("Scripting.FileSystemObject");

var oFile = oFSO.OpentextFile(strLog);

while(!oFile.AtEndOfStream) {

var strLine = oFile.ReadLine();
if (strLine.indexOf(strMessage) != -1) {
	bRet = 0;
	}
}

oFile.Close();

}
catch(e)
{
                                               WScript.Echo("Something wrong with output file" + e.message);
}

//WScript.Echo(bRet);
WScript.Quit(bRet);
