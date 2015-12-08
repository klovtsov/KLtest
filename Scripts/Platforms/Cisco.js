// How to run

// rem cscript /NoLogo .\Cisco.js create 10.30.44.180 yk ykykyk enablePassword yk8 yk8yk8yk8
// rem cscript /NoLogo .\Cisco.js password 10.30.44.180 yk ykykyk enablePassword yk8 yk88888
// rem cscript /NoLogo .\Cisco.js login 10.30.44.180 yk8 yk88888 enablePassword
// rem cscript /NoLogo .\Cisco.js delete 10.30.44.180 yk ykykyk enablePassword yk8
// echo %ERRORLEVEL%




/////////////// Get command line parameters ///////////////

var Args = WScript.Arguments;

var Command, Host, RunningUser, RunningUserPassword, EnablePassword, NewUser, NewUserPassword   // cmdline parameters 0 through 6
var Parameters = "Host,RunningUser,RunningUserPassword,EnablePassword,NewUser,NewUserPassword";



Command = Args(0);
var LogFile = ".\\Scripts\\Platforms\\Cisco\\" + Command + "_session.log";
Host = Args(1);
RunningUser = Args(2);
RunningUserPassword = Args(3);
EnablePassword = Args(4);


if (!(Command == "login"))
{
	NewUser = Args(5);
}


if ((Command == "create") || (Command == "password"))
{
	NewUserPassword = Args(6);
}


/////////////// Prepare scenario for telnet session  /////////////// 


// Read script template into array

var ScriptTemplate = ".\\Scripts\\Platforms\\Cisco\\Cisco_" + Command + ".template"; 

var mfso = new ActiveXObject("Scripting.FileSystemObject");           
var infile = mfso.OpenTextFile(ScriptTemplate);
var ScriptTemplateArray = new Array();
var arrayIndex = 0;

while (!infile.AtEndOfStream)
{
	instring = infile.ReadLine()
	ScriptTemplateArray[arrayIndex] = instring;
	arrayIndex++;
}
infile.Close();


// Replacement stuff


var ParametersArray = Parameters.split(",");
var ParameterInTemplate, ParameterInScenario, ParameterTmp;

var ParametersArrayLength=ParametersArray.length;

for(var i=0; i<ParametersArrayLength; i++)
{

	ParameterInTemplate = "%" + ParametersArray[i] + "%";

	eval ("ParameterTmp=\"" + ParametersArray[i] + "\"");
	ParameterInScenario=eval(ParameterTmp);

	for (var j=0;j<arrayIndex;j++)
	{
		if (ScriptTemplateArray[j].match(ParameterInTemplate))
		{
			// WScript.Echo ("Yes, matched!" + ScriptTemplateArray[j] + ParameterInTemplate + ParameterInScenario);
			ScriptTemplateArray[j]=ScriptTemplateArray[j].replace(ParameterInTemplate, ParameterInScenario)
		}
	}
}

/////////////// Create telnet scenario /////////////// 

var ScriptScenario = ".\\Scripts\\Platforms\\Cisco\\Cisco_" + Command + ".script"; 
var outfile = mfso.OpenTextFile(ScriptScenario,2,true);

for (var k=0;k<arrayIndex;k++) outfile.WriteLine(ScriptTemplateArray[k]);

outfile.Close();


///////////////  Run telnet session /////////////// 

var mshell = new ActiveXObject("WScript.Shell");
var RunStr = ".\\Scripts\\tst10.exe /r:" + ScriptScenario + " /o:" + LogFile;
mshell.run(RunStr,1,true);


///////////////  Analyze log and exit with Return Code /////////////// 

var logfso = new ActiveXObject("Scripting.FileSystemObject");           
var logfile = logfso.OpenTextFile(LogFile);
var LogContent = logfile.ReadAll();

if (LogContent.match("Enter configuration command") && !LogContent.match("ERROR"))
{
	// WScript.Echo ("Good!");
	WScript.Quit (0);
} else {
	// WScript.Echo ("Fail!");
	WScript.Quit (1);
}



