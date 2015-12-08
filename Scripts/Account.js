var PLFolder  = ".\\Scripts\\Platforms";
var mshell = new ActiveXObject("WScript.Shell");
var mfso = new ActiveXObject("Scripting.FileSystemObject");

var Args = WScript.Arguments;
var AllArgs = Args(1) + " " + Args(2) + " " ;
for (var a  = 3; a < Args.length; a++)
{

	AllArgs += "\"" + Args(a) + "\"" + " ";
}
//AllArgs = AllArgs.replace(/\\/g,"\\\\");
//WScript.Echo(AllArgs);
var Platform = Args(0);

if (Platform.toLowerCase().indexOf("windows") != -1) Platform="Windows";
if (Platform.toLowerCase().indexOf("sql") != -1) Platform="SQL";
if (Platform.toLowerCase().indexOf("macosx") != -1) Platform="MacOSX";
if (Platform.toLowerCase().indexOf("oracle") != -1) Platform="Oracle";
if (Platform.toLowerCase().indexOf("sybase") != -1) Platform="Sybase";
if (Platform.toLowerCase().indexOf("ibm") != -1) Platform="IBMHMC";
if (Platform.toLowerCase().indexOf("freebsd") != -1) Platform="FreeBSD";

var getFolder = mfso.GetFolder(PLFolder);
var rfs = new Enumerator(getFolder.files);
for (;!rfs.atEnd();rfs.moveNext()) 
{
	var fn = rfs.item().Name;
	if (fn.indexOf(Platform) != -1)
	{
//		var RunStr = "cmd /k " + PLFolder + "\\" + rfs.item().Name + " " + AllArgs;
		var RunStr = PLFolder + "\\" + rfs.item().Name + " " + AllArgs;
//		WScript.Echo("Running: " + RunStr);
		mshell.run(RunStr,1,true);
		break;
		
	}
}		

