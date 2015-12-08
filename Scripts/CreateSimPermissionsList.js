var strDir = WScript.Arguments(0).replace(/\\/g,"\\\\");
var strFile = WScript.Arguments(1);
var strPath = strDir + strFile;
var strPrefix = WScript.Arguments(2);
var strSystemName = WScript.Arguments(3);
var strUserName = WScript.Arguments(4);
var strPolicyName = WScript.Arguments(5);
var intQuantity = WScript.Arguments(6);


//WScript.Echo("Path: " + strPath);


var oFSO = new ActiveXObject("Scripting.FileSystemObject");

if (oFSO.FileExists(strPath))
{
oFSO.DeleteFile(strPath);
}



oFile = oFSO.OpenTextFile(strPath,2,true);

var strHeaders = "Update Action" + "\t" + "System Name" + "\t" + "Account Name" + "\t" + "File Name" + "\t" + "Collection Name" + "\t" + "User Name" + "\t" + "Group Name" + "\t" + "Access Policy";
//WScript.Echo("Line: " + strHeaders);
oFile.WriteLine(strHeaders);

for (i=1;i<=intQuantity;i++) {

var strLine = "";
var strLine = "A";
strLine += "\t";
strLine += strSystemName;
strLine += "\t";
strLine += "\t";
strLine += "\t";
strLine += "\t";
strLine += strPrefix + strUserName + i.toString();
strLine += "\t";
strLine += "\t";
strLine += strPolicyName;


//WScript.Echo("Line: " + strLine);
oFile.WriteLine(strLine);

}

oFile.Close();






