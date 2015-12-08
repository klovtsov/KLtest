// ======================================================================================================================================
// TestRunner Config Optional Parameters. If you need to change these values, please, add corresponding lines to your testrunner.config.
// Overridden by corresponding parameters in TestRunner Config if they are specified.

var RunFailedTestsOnceMore = "N";    // By default, we don't run failed tests once more. Can be overridden in testrunner.config.
var GetLatestTestSources = "Y";		 // By default, we do get latest test sources from version control system (currently, StarTeam). 

var AnalyzeErrorLogEnabled = "Y";	              // By default, we analyze Error Logs to see if they contains patterns we are looking for.
var AnalyzeErrorLogPatternFile = "patterns.txt";  // By default, patterns we are looking for in Error Logs are located in this file.

// var UseNewSikuli = "N";		// Obsoleted: By default, we do NOT use Sikuli 1.1.0 (<suite>/lib/sikulixapi.jar) and rely on old one (sikuli-script.jar) 

var UseFitWebDriver = "N";		// By default, we do NOT use FitWebDriver and WebDriver-based Fitnesse fixtures


// ======================================================================================================================================


var EmailAddr = "yuri.kuniver@quest.com";  // Overridden by the corresponding parameter in TestRunner Config
var Prefix = "yk5";                        // Overridden by the corresponding parameter in TestRunner Config


var mfso = new ActiveXObject("Scripting.FileSystemObject");           
var mshell = new ActiveXObject("WScript.Shell");
var Dispatcher = "tpamdisp101";
var DispatcherCredentials = new Array("","");
// var AutoUser = "Administrator";
// var AutoUserPwd = "Q1w2e3r4t5";
var Slim_Port=9000;
var Selenium_Port=9100;
var Fit_Port=9200;
var ATPath = "FrontPage.AutomatedTesting";
var TPAMPath = "C:\\TPAM";
var PSExecPath = TPAMPath + "\\Scripts\\";
var NetworkTPAMPath = TPAMPath.replace(":","$");
var ResultsFolder = "\\FitNesseRoot\\files\\testResults";
var NetworkResultsFolder = "";
var FitPath = " -jar " + TPAMPath + "\\fitnesse.jar -c ";
var JavaCmd = "cmd /c \"java -Xmx1000m";
var SuiteName;
var StarTeamLog = TPAMPath + "\\starteam.log";
var FitSuite = "?suite";
var FitTest = "?test";
var stcmd = "C:\\Program Files\\Borland\\StarTeam Cross-Platform Client 2009\\stcmd.exe";


var ResultsStorage = "tpamhub";
var ResultsStorageUser = "Admin2";
var ResultsStoragePasswd = "Q1w2e3r4t5";
var ReportFile = TPAMPath + "\\report.html";

var ConfigArray = [];

var PpmArray = [];
var PpmRegexp = /^\w+:\w+/;
var PpmPlatforms = [];
var PpmSuite;

var NonPpmArray = [];
var NonPpmRegexp = /^[\w\.]+,[\w\.]+/;
var Scenario, ScenarioType;

var ConfigRegexp = /^\w+=".+"/;

var PlatformsRegexp = /PlatformsTesting/;
var DpaRegexp = /DpaScenarios/
var IeRegexp = /InternetExplorerScenarios/;
var SshRegexp = /SshPlatformsTests/;

var PlatformsResult, DpaResult, IeResult, SshResult;
var NeedPPMUsers = 0;

var RegexpResultPpm, RegexpResultNonPpm, RegexpResultConfig 
var VarName, VarValue;

var i=0, j=0;
var ConfigString;
var TestsWereKilled = false;

var LocalScreenshotFolder = TPAMPath + "\\Screenshots";
var ScreenshotTaken = "No";


// ------------------------------------------------------------------------------- //
// Reading Configuration from config file


WScript.Echo("\n\n------------ Reading configuration ---------------------\n\n"); 


var args = WScript.Arguments;

if (args.length != 1) {
	
	WScript.Echo("Config File required as a cmd line parameter");
	WScript.Quit(0);
}

var ConfigFile = WScript.Arguments(0);

WScript.Echo("ConfigFile: " + ConfigFile);


var fso = WScript.CreateObject("Scripting.FileSystemObject");

if (fso.FileExists(ConfigFile))
{
    var txtStream = fso.OpenTextFile(ConfigFile);  
    
	while (!txtStream.atEndOfStream) {
		
		ConfigStringDirty = txtStream.ReadLine();
		ConfigString = ConfigStringDirty.replace(/\s+$/, '');

		RegexpResultPpm = PpmRegexp.exec(ConfigString);
		RegexpResultNonPpm = NonPpmRegexp.exec(ConfigString);
		RegexpResultConfig = ConfigRegexp.exec(ConfigString);

		if (RegexpResultPpm) { 
			
			WScript.Echo("PPM Scenarios matched: " + ConfigString);
			PpmArray[i] = ConfigString;
			i++;

			PlatformsResult = PlatformsRegexp.exec(ConfigString);   // is it UI Tests (PlatformsTesting) ?
			DpaResult = DpaRegexp.exec(ConfigString);				// is it DPA/UI Tests (DpaScenarios) ?
			IeResult = IeRegexp.exec(ConfigString);				    // is it InternetExplorer/UI Tests (InternetExplorerScenarios) ?
			SshResult = SshRegexp.exec(ConfigString);				// is it Ssh Platforms Tests (SshPlatformsTests) ?

			if (PlatformsResult || DpaResult || IeResult || SshResult) {
				NeedPPMUsers = 1;
			}	
		

		} else if (RegexpResultNonPpm) { 
			
			WScript.Echo("NonPPM Scenarios matched: " + ConfigString);
			NonPpmArray[j] = ConfigString;
			j++;

		} else if (RegexpResultConfig) {

			ConfigArray = ConfigString.split("=");
			VarName = ConfigArray[0];
			VarValue = ConfigArray[1].replace(/"/g,"");	
			this[VarName] = VarValue;
			WScript.Echo("Configuration variable defined: " + VarName + "=" + VarValue);
		} else {
	        WScript.Echo("Configuration String not matched: " + ConfigString);
		}
    }	
}

txtStream.Close();


WScript.Echo("\n\n------------ End of Configuration ---------------------\n\n");



// ------------------------------------------------------------------------------------------------------- //
// Do we need to get latest test sources from Version Control System?


if (GetLatestTestSources == "Y") {

	WScript.Echo("\n\nChecking out the latest test sources from GitHub... \n\n"); 

	var gitLog = "GitLog.txt";

	var gitCommand = "cmd /c git fetch" + " > " + gitLog;
	mshell.Run(gitCommand,7,true);

	var gitCommand = "cmd /c git reset --hard origin/master" + " >> " + gitLog;
	mshell.Run(gitCommand,7,true);


} else {

	WScript.Echo("\n\nAccording to TestRunner.config, we don't need to check out the latest test sources from GitHub... \n\n"); 

}



// ------------------------------------------------------------------------------------------------------- //
// Do we need to change groovy_runner.bat to use another WebDriver in groovy scripts?


var keyPath = "HKEY_LOCAL_MACHINE\\SOFTWARE\\Mozilla\\Mozilla Firefox\\";
var FirefoxVerCurrent = mshell.RegRead(keyPath);

var FirefoxVerOld = "28.0";
var WebDriverOld = "2.41.0";
var WebDriverNew = "2.43.1";
var WebDriverVer;

if (FirefoxVerCurrent == FirefoxVerOld) {  // If we have a new Firefox, we are going to use a new WebDriver
	WebDriverVer = WebDriverOld;
} else {
	WebDriverVer = WebDriverNew;
}

var WebDriverVerCmd = "cmd /c " + TPAMPath +"\\Scripts\\sfk.exe filter " + TPAMPath + "\\Scripts\\WebDriver\\groovy_runner.bat " + 
		"-rep \"/WebDriver=*/WebDriver=" + WebDriverVer + "/\" -write -yes";

WScript.Echo("Replacing WebDriver Version: " + WebDriverVerCmd + "\n\n");
mshell.Run(WebDriverVerCmd,1,true);



// ------------------------------------------------------------------------------------------------------- //
// Do we need to use new Sikuli in Groovy scripts? If we do, we need to change groovy_runner.bat
// Obsoleted: now in Groovy scripts, we rely on new Sikuli only.


// if (UseNewSikuli == "Y") {
// 
// 	var WebDriverSikuliCmd = "cmd /c " + TPAMPath +"\\Scripts\\sfk.exe filter " + TPAMPath + "\\Scripts\\WebDriver\\groovy_runner.bat " + 
// 			"-rep \"/..\\..\\lib\\sikuli-script/sikulixapi/\" -write -yes";
// 
// 	WScript.Echo("Replacing old Sikuli jar with new one: " + WebDriverSikuliCmd + "\n\n");
// 	mshell.Run(WebDriverSikuliCmd,1,true);
// 
// }


// ------------------------------------------------------------------------------------------------------- //
// Do we need to use new FitWebDriver and WebDriver-based Fitnesse fixtures? If we do, we need to change the path to the tests. 


if (UseFitWebDriver == "Y") {

	ATPath = "FrontPage.AutoTestsWebDrv";
	WScript.Echo("We are going to use FitWebDriver: changing path to the tests: " + ATPath + "\n\n");

}



// --------------------------------------------------------------------------------------------------//
// Getting computer name 

var WshNetwork = WScript.CreateObject("WScript.Network");
Dispatcher = WshNetwork.ComputerName;
WScript.Echo("Computer Name: " + Dispatcher);

var TargetScreenshotFolder = "\\\\" + ResultsStorage + "\\" + NetworkTPAMPath + "\\Screenshots\\" + Dispatcher;


// --------------------------------------------------------------------------------------------------//
// We need to kill all java, firefox and iexlore processes before running Fitnesse tests; 

WScript.Echo("\n\nWe are gonna kill all firefox, iexplore, and java processes before running Fitnesse tests... \n\n");
KillThemAll();



// --------------------------------------------------------------------------------------------------//
// Do we need PPM users to be created via UI (in case of PlatformsTesting, DpaScenarios, or InternetExplorerScenarios) ?

	
if (NeedPPMUsers == 1) {
	WScript.Echo ("We need to create Approvers/Requestors in UI. A little later ...");
} else {
	WScript.Echo ("We are NOT going to do anything with Approvers/Requestors in UI ...");
}

WScript.Echo ("\n");



// --------------------------------------------------------------------------------------------------//
//					Change Fitnesse page with configuration parameters


WScript.Echo("\n\n------------ Some changes in Fitnesse files ---------------------\n\n");


var FitnesseFile = TPAMPath + "\\FitNesseRoot\\FrontPage\\content.txt";

var FitnesseFileNew = FitnesseFile + ".new";



WScript.Echo("File to parse: " + FitnesseFile + "\n");

WScript.Echo("Updated file: " + FitnesseFileNew + "\n");


var TPAMHostRegexp = /!define TPAMHost {[\d\.]+}/; 
var PrefixRegexp = /!define prefix {[\w]+}/;
var DispatcherAddressRegexp = /!define DispatcherAddress {[\d\.]+}/;

var yfso = WScript.CreateObject("Scripting.FileSystemObject");

var txtStreamOutput = yfso.OpenTextFile(FitnesseFileNew,2,true);  

if (yfso.FileExists(FitnesseFile))
{
    var txtStream = yfso.OpenTextFile(FitnesseFile);  
    
	while (!txtStream.atEndOfStream) {
		
		FitnesseString = txtStream.ReadLine();

		RegexpResultTPAMHost = TPAMHostRegexp.exec(FitnesseString);
		RegexpResultPrefix = PrefixRegexp.exec(FitnesseString);
		RegexpResultDispatcherAddress = DispatcherAddressRegexp.exec(FitnesseString);

		if (RegexpResultTPAMHost) { 
			txtStreamOutput.WriteLine("!define TPAMHost {" + TPAMHost + "}");
		} else if (RegexpResultPrefix) { 
			txtStreamOutput.WriteLine("!define prefix {" + Prefix + "}");
		} else if (RegexpResultDispatcherAddress) {
			txtStreamOutput.WriteLine("!define DispatcherAddress {" + DispatcherAddress + "}");
		} else {
			txtStreamOutput.WriteLine(FitnesseString);
		}
    }	
}

txtStream.Close();
txtStreamOutput.Close();

yfso3 = new ActiveXObject("Scripting.FileSystemObject");
yfso3.CopyFile(FitnesseFileNew, FitnesseFile);
yfso3.DeleteFile(FitnesseFileNew);



//-------------------------------------------------------------------------------------------------------//



//Purge Fitnesse History and delete old TPAM log

WScript.Echo("\n\nPurge Fitnesse History and delete old TPAM log ...\n\n");

var PurgeHistory = JavaCmd + FitPath + "FrontPage?purgeHistory&days=0";
WScript.Echo("Purge command: " + PurgeHistory);
mshell.Run(PurgeHistory,1,true);

try
{

	yfso4 = new ActiveXObject("Scripting.FileSystemObject");

	var ErrorLogs = TPAMPath + "\\FitNesseRoot\\ErrorLogs"; 
	yfso4.DeleteFolder(ErrorLogs);

	var TestResults = TPAMPath + "\\FitNesseRoot\\files\\testResults"; 
	yfso4.DeleteFolder(TestResults);

	var TPAMLog = TPAMPath +"\\tpam.log";
	if (yfso4.FileExists(TPAMLog)) {
		yfso4.DeleteFile(TPAMLog);
	}	

	if (yfso4.FolderExists(LocalScreenshotFolder)) {
		yfso4.DeleteFolder(LocalScreenshotFolder);
	}	

}
catch(e)
{
			WScript.Echo("Something wrong in deleting History or Logs or Screenshots: " + e);
}



//-------------------------------------------------------------------------------------------------------//

/*  This piece of code is obsolete. We had two folders (Images and ImagesNew) to keep pictures for both WinXP/2003 and Win7. Now, we moved to Win7.

// Copying Sikuli Images from ImagesNew to Images

WScript.Echo("\n\nCopying Sikuli Images from ImagesNew to Images...\n");

try
{

	var ImagesNew = TPAMPath + "\\Scripts\\ImagesNew\\*.*";
	var ImagesOld = TPAMPath + "\\Scripts\\Images\\";

	WScript.Echo("Source: " + ImagesNew + "; Target: " + ImagesOld);

	ifso4 = new ActiveXObject("Scripting.FileSystemObject");
	ifso4.CopyFile(ImagesNew,ImagesOld);

}
	
catch(e)
{
			WScript.Echo("Something wrong in copying images" + e);
}

*/


//-------------------------------------------------------------------------------------------------------//

/*
// Creating PPM Password Profiles 

WScript.Echo("\nCreating PPM Password Profiles...\n");
var CreateProfiles = JavaCmd + " -DseleniumPort=" + Selenium_Port + FitPath + ATPath + ".PpmPasswordProfiles.CreateProfiles?test";
mshell.Run(CreateProfiles,7,true);
*/

//-------------------------------------------------------------------------------------------------------//


// Run PPM Scenarios


WScript.Echo("\n\n------------ Start PPM Scenarios ---------------------\n\n");


if (NeedPPMUsers == 1) {
	//Create PPM request and approver
	WScript.Echo("Creating approver and requestor...\n");
	var CreateUsers = JavaCmd + " -DseleniumPort=" + Selenium_Port + FitPath + ATPath + ".PpmUsers.CreateUsers?test";
	mshell.Run(CreateUsers,7,true);
}





for (var i=0; i<PpmArray.length; i++) {

	ConfigArray = PpmArray[i].split(":");

	PpmSuite = ConfigArray[0];
	WScript.Echo ("\nPpmSuite: " + PpmSuite);
	
	PpmPlatforms = ConfigArray[1].split(",");
	for (var j=0; j<PpmPlatforms.length; j++) {
		WScript.Echo(PpmPlatforms[j]);
	}

	WScript.Echo("\n ");

	ScenarioType = "?test";

	for (var j = 0; j < PpmPlatforms.length; j++)
	{


			var TCPath = ATPath + "." + PpmSuite + "." + eval("PpmPlatforms" + "[j]");
			var CmdLine = JavaCmd + " -DSLIM_PORT=" + Slim_Port + " -DseleniumPort=" + Selenium_Port + FitPath + TCPath +  ScenarioType + " -p " + Fit_Port + "\"";
			// var testCase = PSExecPath + "psexec  \\\\"+ Dispatcher + " -i 1 -w \"" + TPAMPath + "\" -u " + AutoUser + " -p " + AutoUserPwd + " " + CmdLine; 
			var testCase = CmdLine;  

			WScript.Echo(GetMyDateTime() + ": " + testCase);

			mshell.Run(testCase,7,false);
			
			WScript.Sleep(20000);
			Slim_Port++;
			Selenium_Port++;
			Fit_Port++;
	}
	Wait();
	WScript.Sleep(20000);
}


if (NeedPPMUsers == 1) {
	//Remove approver\requestor
	WScript.Echo("\n==========================\nRemoving approver and requestor...\n");
	var RemoveUsers = JavaCmd + " -DseleniumPort=" + Selenium_Port + FitPath + ATPath + ".PpmUsers.RemoveUsers?test";
	mshell.Run(RemoveUsers,7,true);
}

WScript.Echo("\n\n------------ End PPM Scenarios ---------------------\n\n");





//-------------------------------------------------------------------------------------------------------//



WScript.Echo("\n\n------------ Start Non-PPM Scenarios ---------------------\n\n");

// Run NonPPM Scenarios


for (var i=0; i<NonPpmArray.length; i++) {

	// Config string format examples (Name,Type<,TimeLimit>): 

	// a) FileTransfer,suite b) FileTransfer.FreeBsd,test 
	// c) FileTransfer,suite,120 d) FileTransfer.FreeBsd,test,20

	ConfigArray = NonPpmArray[i].split(",");

	Scenario = ConfigArray[0];
	ScenarioType = ConfigArray[1];
	ScenarioLimit = (2 == (ConfigArray.length - 1)) ? ConfigArray[2] : 0;  // If Time Limit for a given suite (test) is specified
 

	WScript.Echo("===========================");

	InfoString = "Scenario: " + Scenario + " ScenarioType: " + ScenarioType;

	if (ScenarioLimit != 0 ) InfoString = InfoString + " ScenarioLimit: " + ScenarioLimit;
	
	WScript.Echo(InfoString);

	WScript.Echo("===========================");

	var TCPath = ATPath + "." + Scenario + "?";
	var CmdLine = JavaCmd + " -DSLIM_PORT=" + Slim_Port + " -DseleniumPort=" + Selenium_Port + FitPath + TCPath +  ScenarioType + " -p " + Fit_Port + "\"";

	// var testCase = PSExecPath + "psexec  \\\\"+ Dispatcher + " -i 1 -w \"" + TPAMPath + "\" -u " + AutoUser + " -p " + AutoUserPwd + " " + CmdLine; 

	var testCase = CmdLine;  

	WScript.Echo(GetMyDateTime() + ": " + testCase);
	mshell.Run(testCase,7,false);
                
	WScript.Sleep(20000);
	Slim_Port++;
	Selenium_Port++;
	Fit_Port++;

	(0 == ScenarioLimit) ? Wait() : Wait(ScenarioLimit);  // If Time Limit for a given suite (test) is specified

	WScript.Sleep(20000);

}


WScript.Echo("\n\n------------ End Non-PPM Scenarios ---------------------\n\n");


//-------------------------------------------------------------------------------------------------------//

/*
//	Removing PPM Password Profiles 

WScript.Echo("\n==========================\n\nRemoving PPM Password Profiles...\n");
var RemoveProfiles = JavaCmd + " -DseleniumPort=" + Selenium_Port + FitPath + ATPath + ".PpmPasswordProfiles.DeleteProfiles?test";
mshell.Run(RemoveProfiles,7,true);
*/

//-------------------------------------------------------------------------------------------------------//



// Work done, prepare results

var FitResultsFolder = TPAMPath + "\\FitNesseRoot\\files\\testResults";

WScript.Echo("\n\n-------- Tuning Fitnesse File Results in " + FitResultsFolder + " (recursively) --------------\n");

try
{ 
	TuneFitnesseResults(FitResultsFolder);  // recursion
}	
catch(e)
{
	WScript.Echo("Something wrong with tuning Fitnesse Result Files: " + e);
}


WScript.Echo("\n------------- End of Tuning Fitnesse File Results --------------\n");


MergeLogsAndResults();

PrepareAndEmailReportWithResults();



//-------------------------------------------------------------------------------------------------------//
// Do we need to run failed tests once more?


if (RunFailedTestsOnceMore == "Y") {     // if this variable is present in ConfigFile (an argument for this script)
										 // Well, if there are any failed tests, we are going to run them once more...

	var FailedTestsConfig = PSExecPath + "testrunner.config.failed_tests";

	WScript.Echo("\n\nIf there are any failed tests, we are going to run them once more.\n\n");

	PrepareTestRunnerConfigForFailedTests();

}


WScript.Echo("===========================\nFinished\n==============================\n");



//----------------------------------  Functions --------------------------------------------------------//

function Wait(Limit)
{

	var gWbemLocator = new ActiveXObject("WbemScripting.SWbemLocator");
	var TestNotFinished = true;
	var Step = 0;

	// Wait for the tests to be finished
	
	WScript.Echo("Waiting: ");

	while (TestNotFinished)
	{

		var foundBrowser = false;
		var foundCmd = false;

		Step++;
		WScript.StdOut.Write(Step + " ");

		var objWMIService = gWbemLocator.ConnectServer(Dispatcher,"root\\cimv2",DispatcherCredentials[0],DispatcherCredentials[1]);
		var colItems = objWMIService.ExecQuery("SELECT * FROM Win32_Process");
		var enumItems = new Enumerator(colItems);
	
		for (; !enumItems.atEnd(); enumItems.moveNext()) 
		{
			var ProcessName = enumItems.item().Name;
			if (ProcessName == "java.exe") foundCmd = true;
			if (ProcessName == "firefox.exe" || ProcessName == "iexplore.exe") foundBrowser = true; // not used, but left it there
		}
		
		
		if (!foundCmd) TestNotFinished = false;    // Tests are finished, going out.
		
		// Tests took too long. Probably, we have hung processes, so we are going to kill the tests.	
		if (Limit != "undefined" && Step > Limit) {
			TestNotFinished = false; 
			WScript.StdOut.Write(" Tests run too long, killing them...");
			TestsWereKilled = true;
		}
		
		WScript.Sleep(60000);
	}

	WScript.Echo("\n\n"); 

	// Killing tests due to Limit exceeding (if they are still running). Also, taking care of orphaned browser processes, if any
	KillThemAll();

}


function KillThemAll()
{

	var gWbemLocator = new ActiveXObject("WbemScripting.SWbemLocator");
	var objWMIService = gWbemLocator.ConnectServer(Dispatcher,"root\\cimv2",DispatcherCredentials[0],DispatcherCredentials[1]);
	var colItems = objWMIService.ExecQuery("SELECT * FROM Win32_Process");
	var enumItems = new Enumerator(colItems);
		

	for (; !enumItems.atEnd(); enumItems.moveNext()) 
	{
		var ProcessName = enumItems.item().Name;  
		if (ProcessName == "firefox.exe" || ProcessName == "iexplore.exe" || ProcessName == "chrome.exe" || ProcessName == "java.exe") 
			enumItems.item().Terminate();
	}

}



function PrepareAndEmailReportWithResults()
{

	// Create HTML 
	
	var CreateReport = JavaCmd + FitPath + "FrontPage?testHistory>" + ReportFile;
	WScript.Echo("Report command: " + CreateReport);
	mshell.Run(CreateReport,1,true);

	var ErrorLogs = "cmd /c echo ^<br^>^<br^>Logs are available at \\\\" + ResultsStorage + "\\c$\\TPAM\\FitNesseRoot\\ErrorLogs with creds " +
									ResultsStorageUser + " - " + ResultsStoragePasswd +	">>" + ReportFile;

	WScript.Echo("ErrorLogs command: " + ErrorLogs);
	mshell.Run(ErrorLogs,1,true);

	if (ScreenshotTaken == "Yes") {

		var ScreenshotCmd = "cmd /c echo ^<br^>^<br^>Screenshots are available at " + TargetScreenshotFolder + " with creds " +
										ResultsStorageUser + " - " + ResultsStoragePasswd +	">>" + ReportFile;

		WScript.Echo("Screenshots command: " + ScreenshotCmd);
		mshell.Run(ScreenshotCmd,1,true);


	}
		

	// Patch HTML

	WScript.Echo("Patching HTML Report file (URLs switched to TPAMResults machine)...");

	var mfso5 = new ActiveXObject("Scripting.FileSystemObject");           

	var ReportFileHandle = mfso5.OpenTextFile(ReportFile);
	var ReportFileContents = ReportFileHandle.ReadAll();
	ReportFileHandle.Close();

	var OldHref = /href=\"/g;
	var NewHref = "href=\"http://" + ResultsStorage + "/";

	var ReportFileContentsUpdated = ReportFileContents.replace(OldHref,NewHref);

	ReportFileHandle = mfso5.OpenTextFile(ReportFile,2,true);
	ReportFileHandle.Write(ReportFileContentsUpdated);
	ReportFileHandle.Close();


	// Some clean-ups

	var cleanupCmd = "cmd /c " + TPAMPath +"\\Scripts\\sfk.exe filt " + ReportFile + 
			" -cut \"FitNesse (v20110104) Started...\" to \"Content-Type: text/html; charset=utf-8\" -write -yes";
	WScript.Echo("CleanUp: " + cleanupCmd);
	mshell.Run(cleanupCmd,1,true);


	cleanupCmd = "cmd /c " + TPAMPath +"\\Scripts\\sfk.exe filt " + ReportFile + 
			" -cut \"<form action\" to \"</form>\" -write -yes";
	WScript.Echo("CleanUp: " + cleanupCmd);
	mshell.Run(cleanupCmd,1,true);

	var reportStringsToCut = [];
	reportStringsToCut[0] = "Command Complete"; 
	reportStringsToCut[1] = "Test History for FrontPage"; 
	
	for (var p=0; p<reportStringsToCut.length; p++) {

		cleanupCmd = "cmd /c " + TPAMPath +"\\Scripts\\sfk.exe replace " + ReportFile + " \"/" + reportStringsToCut[p] + "//\" -yes";
		WScript.Echo("CleanUp: " + cleanupCmd);
		mshell.Run(cleanupCmd,1,true);

	}
	
	// Do we need to verify if Error Logs contain patterns we are looking for?

	var ErrorLogLocal = TPAMPath + "\\FitNesseRoot\\ErrorLogs";
	var PatternFileFullPath = TPAMPath + "\\Scripts\\" + AnalyzeErrorLogPatternFile;
	var patternCmd = "cmd /c " + TPAMPath +"\\Scripts\\sfk.exe find -dir " + ErrorLogLocal + " -file content.txt -pat ";
	
	WScript.Echo("Pattern File: " + PatternFileFullPath);

	var pcmd;


	if ((AnalyzeErrorLogEnabled == "Y") && fso.FileExists(PatternFileFullPath))
	{
		WScript.Echo("Verifying if Error Logs contain patterns we are looking for...");

		var EchoCommand = "cmd /c echo ^<br^>^<br^> >>" + ReportFile;
		mshell.Run(EchoCommand,1,true);
		
		var patternStream = fso.OpenTextFile(PatternFileFullPath);  
		
		while (!patternStream.atEndOfStream) {
			
			Pattern = patternStream.ReadLine();
			WScript.Echo("Pattern: " + Pattern);
			pcmd = patternCmd + "\"" + Pattern + "\" " + ">>" + ReportFile;
			WScript.Echo("Cmd: " + pcmd);
			mshell.Run(pcmd,7,true);
		}	

		patternStream.Close();
	}

	
	// Were there tests that took too much time, so we had to kill them?

	if (TestsWereKilled) {

		var KilledTestsInfo = "cmd /c echo ^<br^>Attention: some tests took too much time, so we had to kill them. " +
		"Please, take a look at the test runner log attached." + ">>" + ReportFile;

		mshell.Run(KilledTestsInfo,1,true);
	}


	//Send Email

	WScript.Echo("Waiting for 10 sec (10000 ms) before sending e-mail notification...");	
	WScript.Sleep(10000);
	

	WScript.Echo("Sending Email with fitnesse report and logs attached...");

	var EmailSubject = Dispatcher + ": TPAM Autotest results";

	var ExternalLog = TPAMPath + "\\logs\\testrunner.log";
	var EmailLog = TPAMPath + "\\logs\\testrunner_email.log";
	mfso5.CopyFile (ExternalLog, EmailLog);

	var AttachedFiles = "\"" + TPAMPath + "\\tpam.log\"," + "\"" + EmailLog + "\"";

	var Mailer = "cmd /c " + TPAMPath + "\\Scripts\\blat.exe " + "\"" + ReportFile + "\"" + " -server relayemea.prod.quest.corp -to " + EmailAddr + " -f tpamresults@tpam.com -subject \"" + EmailSubject + "\"" + " -html -attacht " + AttachedFiles; 
	WScript.Echo("EMail command: " + Mailer);
	mshell.Run(Mailer,7,true);
}




function GetMyDateTime()
{
  var d,hour,min,day,mon,s="";
  
  d = new Date();
  
  min = d.getMinutes();
  hour = d.getHours();
  day = d.getDate();
  mon = d.getMonth() + 1;
  
  s = day + "." + mon + " " + hour + "." + min;
  return(s);
}



function TuneFitnesseResults(dir)
{

// If all tests are green, but there are exceptions, ignore them to mark test green in TestHistory. 
// For that, 20130401183449_36_0_0_1.xml => 20130401183449_36_0_0_0.xml
// Where 36 is the whole number of tests, the first zero stands for wrong tests, the next zero stands for ignored tests 
// and the last number (1) stands for the number of exceptions

  var fso, f, fc, fpath;
  
  var currentFile, currentFileName, currentFileNameStart, newFileName;
  var exceptionNumber, ignoreNumber;

  var FileNameRegexp = /(.+\d+)_0_(\d+)_(\d+)\.xml$/;
  var FileNameRegexpResult;

  fso = new ActiveXObject("Scripting.FileSystemObject");
  fso2 = new ActiveXObject("Scripting.FileSystemObject");


	  f = fso.GetFolder(dir);
	  fpath = f.Path;
	  fpath = fpath.replace(/\\/g,"\\\\");
	  // WScript.Echo("Folder: " + fpath);	
	  fc = new Enumerator(f.Files);


	  for (; !fc.atEnd(); fc.moveNext())
	  {
		currentFile = fc.item();
		currentFileName = fpath + "\\\\" + currentFile.Name;
				
		WScript.Echo("FileName: " + currentFileName); 
		
		FileNameRegexpResult = FileNameRegexp.exec(currentFileName);

		if (FileNameRegexpResult) 
		{
			currentFileNameStart = FileNameRegexpResult[1];
			ignoreNumber = FileNameRegexpResult[2];
			exceptionNumber = FileNameRegexpResult[3];

			if (exceptionNumber != 0) 
			{
				WScript.Echo("Fitnesse Result File with zero wrong tests: " + currentFileName + 
				  " ... and it looks like this file has exceptions: " + exceptionNumber);
		
				newFileName = currentFileNameStart + "_0_" + ignoreNumber + "_0.xml";
				WScript.Echo("New Name for Fitnesse Result File: " + newFileName);
				currentFile.Move(newFileName);
			}
		}
		
	  }

	  var esub = new Enumerator( f.SubFolders );
	   
	  // Loop through sub folder list and scan through a recursive call to this function
	  for(; !esub.atEnd(); esub.moveNext() )
	  {
	    var fsub = fso.GetFolder( esub.item() );
	    TuneFitnesseResults( fsub );
	  }
}



function MergeLogsAndResults()
{

	WScript.Echo("Copying Fitnesse ErrorLogs and testResults to ResultsStorage machine...");

	try
	{
		// Net use ResultsStorage

		var NetUseResultsStorage = "net use \\\\" + ResultsStorage + " " + ResultsStoragePasswd + " /user:" + ResultsStorage + "\\" + ResultsStorageUser;
		WScript.Echo("Net use: " + NetUseResultsStorage);
		mshell.Run(NetUseResultsStorage,7,true);


		// Copy Logs

		var LogsFolder = "\\FitNesseRoot\\ErrorLogs";
		var TargetLogsFolder = "\\\\" + ResultsStorage + "\\" + NetworkTPAMPath + LogsFolder;
		var SourceLogsFolder = TPAMPath + LogsFolder;
		WScript.Echo("Copy (Source): " + SourceLogsFolder);
		WScript.Echo("Copy (Target): " + TargetLogsFolder);
		mfso.CopyFolder(SourceLogsFolder,TargetLogsFolder);

		// Copy TestResults

		var ResultsFolder = "\\FitNesseRoot\\files\\testResults"; 
		var TargetResultsFolder = "\\\\" + ResultsStorage + "\\" + NetworkTPAMPath + ResultsFolder;
		var SourceResultsFolder = TPAMPath + ResultsFolder;
		WScript.Echo("Copy (Source): " + SourceResultsFolder);
		WScript.Echo("Copy (Target): " + TargetResultsFolder);
		mfso.CopyFolder(SourceResultsFolder,TargetResultsFolder);

		// Copy Screenshots
			
		if (mfso.FolderExists(LocalScreenshotFolder)) {

			ScreenshotTaken = "Yes";
			WScript.Echo("Hmm, there are screenshots! Folder for screenshots: " + TargetScreenshotFolder);

			if (!mfso.FolderExists(TargetScreenshotFolder)) {
				var createScreenshotFolder = "cmd /c " + TPAMPath +"\\Scripts\\sfk.exe mkdir " + TargetScreenshotFolder;
				WScript.Echo("CreateScreenshotFolder: " + createScreenshotFolder);
				mshell.Run(createScreenshotFolder,1,true);
			}
				
			mfso.CopyFolder(LocalScreenshotFolder,TargetScreenshotFolder);
		}	
		
	}	
	
	catch(e)
	{
				WScript.Echo("Something wrong with merging logs and results: " + e);
	}

}


function PrepareTestRunnerConfigForFailedTests()
{

	var FailedTests = [];
	var k = 0;

	var PpmTestResultDir, NonPpmTestResultDir, fso, f, fc, fpath, fsub, fsubName, esub, folderName;
	
	var FileNameRegexp = /(.+\d+)_0_(\d+)_(\d+)\.xml$/;
	var FileNameRegexpResult;

	var flagFailed = 0;

	
	fso = new ActiveXObject("Scripting.FileSystemObject");

	// Analyzing PPM test results

	WScript.Echo("PPM tests:\n");

	for (var i=0; i<PpmArray.length; i++) {
		
		ConfigArray = PpmArray[i].split(":");

		PpmSuite = ConfigArray[0];
		
		PpmPlatforms = ConfigArray[1].split(",");

		for (var j=0; j<PpmPlatforms.length; j++) {

			PpmTestResultDir = FitResultsFolder + "\\" + ATPath + "." + PpmSuite + "." + PpmPlatforms[j];
			WScript.Echo(PpmTestResultDir);

			try
			{
				f = fso.GetFolder(PpmTestResultDir);
				
				fc = new Enumerator(f.Files);

				testFile = fc.item();
				testFileName = testFile.Name;
						
				WScript.Echo(testFileName);

				FileNameRegexpResult = FileNameRegexp.exec(testFileName);

				if (!FileNameRegexpResult) 
				{
					FailedTests[k] = PpmSuite + ":" + PpmPlatforms[j];
					k++;
					WScript.Echo("Failed Test matched: " + PpmSuite + ":" + PpmPlatforms[j]);
				}
			}
			catch(e)
			{
					WScript.Echo("PPM test: a directory is missing. Does the test exists? " + e);
			}

		}
	}

	// Analyzing NonPPM test results


	WScript.Echo("\n\nNonPPM tests:\n");

	for (var i=0; i<NonPpmArray.length; i++) {

		ConfigArray = NonPpmArray[i].split(",");

		Scenario = ConfigArray[0];
		ScenarioType = ConfigArray[1];
		

		// Depending on scenario type (suite or test), we analyze different folders.

		if (ScenarioType == "test") {

			NonPpmTestResultDir = FitResultsFolder + "\\" + ATPath + "." + Scenario;
			WScript.Echo(NonPpmTestResultDir);
		
			try
			{
				f = fso.GetFolder(NonPpmTestResultDir);
				
				fc = new Enumerator(f.Files);

				testFile = fc.item();
				testFileName = testFile.Name;
						
				WScript.Echo(testFileName);

				FileNameRegexpResult = FileNameRegexp.exec(testFileName);

				if (!FileNameRegexpResult) 
				{
					FailedTests[k] = NonPpmArray[i];
					k++;
					WScript.Echo("Failed Test matched: " + NonPpmArray[i]);
				}
			}
			catch(e)
			{
					WScript.Echo("Non-PPM test: a directory is missing. Does the test exists? " + e);
			}
	

		} else if (ScenarioType == "suite") {

			WScript.Echo(FitResultsFolder);

			try
			{

				f = fso.GetFolder(FitResultsFolder);

				esub = new Enumerator( f.SubFolders );

				// Loop through sub folder list
				
				for(; !esub.atEnd(); esub.moveNext() )
				{
					fsub = fso.GetFolder( esub.item() );
					fsubname = fsub.Name;
					//WScript.Echo("Subfolder: " + fsubname);

					if ((fsubname.match(Scenario)) &&									// A possible candidate
						(!fsubname.match(ATPath + "." + Scenario + "$")) &&				// But not root folder for a given suite
						(!fsubname.match("SuiteSetUp")) && (!fsubname.match("SuiteTearDown")) ) {  // And not a special folder

						WScript.Echo("Subfolder matched: " + fsubname);

						fc = new Enumerator(fsub.Files);

						testFile = fc.item();
						testFileName = testFile.Name;
							
						WScript.Echo(testFileName);

						FileNameRegexpResult = FileNameRegexp.exec(testFileName);

						if (!FileNameRegexpResult) 
						{
							WScript.Echo("Failed Test matched: " + fsubname);
							FailedTests[k] = fsubname.split(ATPath+ ".").pop() + ",test";
							k++;
						}
					} // if 
				} // for
				
			} // try
			catch(e)
			{
					WScript.Echo("Non-PPM sulte: a directory is missing. Does the test exists? " + e);
			}

		}            // Scenario Type = "suite"


	}    // for (var i=0; i<NonPpmArray.length; i++) {
	
	
	// Preparaing Test Runner Config for Failed Tests 

	if (FailedTests.length > 0 ) {

		WScript.Echo("Preparing Test config for failed tests: " + FailedTestsConfig + "\n\n");
	
		try {

			var txtStreamOutput = fso.OpenTextFile(FailedTestsConfig,2,true);
			
			txtStreamOutput.WriteLine("DispatcherAddress=" + "\"" + DispatcherAddress + "\"");
			txtStreamOutput.WriteLine("TPAMHost=" + "\"" + TPAMHost + "\"");
			txtStreamOutput.WriteLine("Prefix=" + "\"" + Prefix + "\"");
			txtStreamOutput.WriteLine("EmailAddr=" + "\"" + EmailAddr + "\"");
			
			// if (UseNewSikuli == "Y") {
			//	txtStreamOutput.WriteLine("UseNewSikuli="  + "\"" + "Y" + "\"");
			// }

			if (UseFitWebDriver == "Y") {
				txtStreamOutput.WriteLine("UseFitWebDriver="  + "\"" + "Y" + "\"");
			}
	
			txtStreamOutput.WriteLine("RunFailedTestsOnceMore="  + "\"" + "N" + "\"" + "\n\n");

			for (var i=0; i<FailedTests.length; i++) {
				txtStreamOutput.WriteLine(FailedTests[i]);
			}
			
			txtStreamOutput.Close();

		}
		catch(e)
		{
				WScript.Echo("Something wrong in creating config for failed tests" + e);
		}

	}

}




//plink all users
/*
ATPath = "FrontPage";
SuiteName = "DeployProject";
PlatformsArray0 = new Array ("PlinkConfiguration","PlinkConfiguration","PlinkConfiguration","PlinkConfiguration","PlinkConfiguration");
TestType = "?suite";
Run();

//Prepare TPAM
var PrepareTpam = JavaCmd + " -DseleniumPort=" + Selenium_Port + FitPath + "FrontPage.DeployProject.PrepareTpam.TestCase?test";
mshell.Run(PrepareTpam,7,true);

*/


