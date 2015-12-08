// How to run

// rem cscript /NoLogo .\hpilo.js create 10.30.44.180 yk ykykyk yk8 yk8yk8yk8
// rem cscript /NoLogo .\hpilo.js password 10.30.44.180 yk ykykyk yk8 yk88888
// rem cscript /NoLogo .\hpilo.js login 10.30.44.180 yk8 yk88888
// rem cscript /NoLogo .\hpilo.js delete 10.30.44.180 yk ykykyk yk8
// echo %ERRORLEVEL%




/////////////// Get command line parameters ///////////////

var Args = WScript.Arguments;

var Command, Host, RunningUser, RunningUserPassword, NewUser, NewUserPassword   // cmdline parameters 0 through 5
var Parameters = "Host,RunningUser,RunningUserPassword,NewUser,NewUserPassword";
var LogFile = "Scripts/Platforms/nis/telnet_session.log";

Host = Args(0);
RunningUser = Args(1);
RunningUserPassword = Args(2);
NewUser = Args(3);
NewUserPassword = Args(4)


/////////////// Prepare scenario for telnet session  /////////////// 


// Read script template into array

var ScriptTemplate = "Scripts/Platforms/nis/pw2.template"; 

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

	// eval ("ParameterTmp=\"" + ParametersArray[i] + "\"");
	// ParameterInScenario=eval(ParameterTmp);

	ParameterInScenario=eval(ParametersArray[i]);

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

var ScriptScenario = "Scripts/Platforms/nis/pw2.script"; 
var outfile = mfso.OpenTextFile(ScriptScenario,2,true);

for (var k=0;k<arrayIndex;k++) outfile.WriteLine(ScriptTemplateArray[k]);

outfile.Close();


///////////////  Run telnet session /////////////// 

var mshell = new ActiveXObject("WScript.Shell");
var RunStr = "Scripts/tst10.exe /r:" + ScriptScenario + " /o:" + LogFile;
mshell.run(RunStr,1,true);


///////////////  Analyze log and exit with Return Code /////////////// 

try
{
	var logfso = new ActiveXObject("Scripting.FileSystemObject");           
	var logfile = logfso.OpenTextFile(LogFile);
	var LogContent = logfile.ReadAll();



	if (LogContent.match("successful"))
	{
	// WScript.Echo ("Good!");
	
	//outfile2 = mfso.OpenTextFile("nisplus.txt",2,true);
	//outfile2.WriteLine("error");
	//outfile2.Close();
		WScript.Quit (1);
	} else {
	// WScript.Echo ("Fail!");
		outfile2 = mfso.OpenTextFile("nisplus.txt",2,true);
		outfile2.WriteLine("error");
		outfile2.Close();
		WScript.Quit (0);
	}
}

catch(e)
{
	WScript.Quit (0);
}


