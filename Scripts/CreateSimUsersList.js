var strDir = WScript.Arguments(0).replace(/\\/g,"\\\\");
var strFile = WScript.Arguments(1);
var strPath = strDir + strFile;
var strPrefix = WScript.Arguments(2);
var strUserName = WScript.Arguments(3);
var strUserType = WScript.Arguments(4);
var strUserInterface = WScript.Arguments(5);
var strInitialPassword = WScript.Arguments(6);
var intQuantity = WScript.Arguments(7);


//WScript.Echo("Path: " + strPath);


var oFSO = new ActiveXObject("Scripting.FileSystemObject");

if (oFSO.FileExists(strPath))
{
oFSO.DeleteFile(strPath);
}



oFile = oFSO.OpenTextFile(strPath,2,true);

var strHeaders = "UserName" + '\t' + "LastName" + '\t' + "FirstName" + '\t' + "UserType" + '\t' + "UserInterface" + '\t' + "Password";
//WScript.Echo("Line: " + strHeaders);
oFile.WriteLine(strHeaders);

for (i=1;i<=intQuantity;i++) {

var strLine = "";
var strLine = strPrefix + strUserName + i.toString();
strLine += "\t";
strLine += "L";
strLine += "\t";
strLine += "K";
strLine += "\t";
strLine += strUserType;
strLine += "\t";
strLine += strUserInterface;
strLine += "\t";
strLine += strInitialPassword;

//WScript.Echo("Line: " + strLine);
oFile.WriteLine(strLine);

}

oFile.Close();






