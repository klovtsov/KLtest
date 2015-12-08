var strDir = WScript.Arguments(0).replace(/\\/g,"\\\\");
var strBaseFile = WScript.Arguments(1) + ".base";
var strFile = WScript.Arguments(1);
var strBasePath = strDir + strBaseFile;
var strPath = strDir + strFile;
var strPrefix = WScript.Arguments(2);

//WScript.Echo("Path: " + strPath);


var oFSO = new ActiveXObject("Scripting.FileSystemObject");

if (oFSO.FileExists(strPath))
{
oFSO.DeleteFile(strPath);
}


oBaseFile = oFSO.OpenTextFile(strBasePath);

oFile = oFSO.OpenTextFile(strPath,2,true);

var strHeaders = oBaseFile.ReadLine();
oFile.WriteLine(strHeaders);

while(!oBaseFile.AtEndOfStream) {

var strLine = strPrefix + oBaseFile.ReadLine();
oFile.WriteLine(strLine);

}

oFile.Close();
oBaseFile.Close();





