var strDir = WScript.Arguments(0).replace(/\\/g,"\\\\");
var strFile = WScript.Arguments(1);
var strPath = strDir + strFile;
var strPrefix = WScript.Arguments(2);
var strSystemName = WScript.Arguments(3);
var strAccountName = WScript.Arguments(4);
var strInitialPassword = WScript.Arguments(5);
var intQuantity = WScript.Arguments(6);


//WScript.Echo("Path: " + strPath);


var oFSO = new ActiveXObject("Scripting.FileSystemObject");

if (oFSO.FileExists(strPath))
{
oFSO.DeleteFile(strPath);
}



oFile = oFSO.OpenTextFile(strPath,2,true);

var strHeaders = "System Name" + "\t" + "Account Name" + "\t" + "Current Password" + "\t" + "Auto-Password Mgmt" + "\t" + "Required Approvers";
//WScript.Echo("Line: " + strHeaders);
oFile.WriteLine(strHeaders);

for (i=1;i<=intQuantity;i++) {

var strLine = "";
var strLine = strSystemName;
strLine += "\t";
strLine += strPrefix + strAccountName + i.toString();
strLine += "\t";
strLine += strInitialPassword;
strLine += "\t";
strLine += "Y";
strLine += "\t";
strLine += "0";



//WScript.Echo("Line: " + strLine);
oFile.WriteLine(strLine);

}

oFile.Close();






