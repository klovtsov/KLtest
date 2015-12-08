var Res = ".\\Results";
var Scripts = ".\\Scripts";
var Results = Res + "\\Suite_Results.html";
var SetFile = ".\\Settings.ini";
var TFolder = ".\\Tests";
var SFolder = TFolder + "\\Suite";
var TCFolder = TFolder +  "\\TestCases";
var SFile = SFolder + "\\" + Suite;
var ResSuite = Res + "\\" + Suite;
var Temp = ".\\Temp";
var Reporter = "Reporter.vbs";

try {
var mfso = new ActiveXObject("Scripting.FileSystemObject");           
var mshell = new ActiveXObject("WScript.Shell");
var xml = new ActiveXObject("Msxml2.DOMDocument.3.0");
var Args = WScript.Arguments;
if (Args.length < 3) throw new Error ("Following arguments should be passed to the script:\n1. Path to a suite file.\n2. Platform name.\n3. Browser type.");

var Suite =  Args(0);
var SP = Args(1);
var Browser = Args(2);
var CmdFile = Suite.replace(".ini",".cmd");

var ProxyHost,ProxyPort,ProxyUser,ProxyPwd,Req,ReqPwd,ReqEMail,ReqEMailPwd;
ProxyHost = ProxyPort = ProxyUser = ProxyPwd = Req = ReqPwd = ReqEMail = ReqEMailPwd = "";
var TPAMHost,Platforms,Approver,ApproverPwd,MainAcc,MainAccPwd;
TPAMHost = Platforms = Approver = ApproverPwd = MainAcc = MainAccPwd = "";
var SystemName,SystemAddress,FunctAcct,FunctAcctPwd;
SystemName = SystemAddress = FunctAcct = FunctAcctPwd = "";

CleanResults();
ReadCommon();

//Platforms multiplicator
var SPArr = SP.split(",");
var FinalFiles = new Array();
for (var s in SPArr)
{
	SystemPlatform = SPArr[s];
	if (!GetPlatform()) throw new Error("Platform \"" +  SystemPlatform + "\" was not found in \"Settings.ini\" file");
	var NewTemp = Temp + "\\" + SystemPlatform + "_" + Browser;
	NewTemp = NewTemp.replace(/\s/g,"_");
	FinalFiles[FinalFiles.length] = NewTemp + "\\" + CmdFile.replace(".cmd","_" + SystemPlatform + "_" + Browser + ".cmd").replace(/\s/g,"_");
	ReadSuite(FinalFiles[FinalFiles.length-1],NewTemp);
}



//Create resulting batch all suites
var CF = mfso.OpenTextFile(CmdFile,2,true);

var FFAll="del /Q " + Res + "\\*.*\n";
for (var f in FinalFiles)
{
	FFAll += "call " + FinalFiles[f] + "\n";	
}
	

//Reporter script command line
var RepStr = "cscript " + Scripts + "\\" + Reporter + " " + Res + " " + ".\\" + "Report.html";
FFAll+=RepStr;	

CF.Write(FFAll);
CF.Close();



WScript.Echo("Configuration complete.\nYou can execute batch file(s)");

}
catch(e)
{
	WScript.Echo(e.message);
}

function CleanResults()
{
	if (!mfso.FolderExists(Temp)) mfso.CreateFolder(Temp);
	if (!mfso.FolderExists(Res)) mfso.CreateFolder(Res);

	var RF = mfso.GetFolder(Res);
	var rfs = new Enumerator(RF.files);

	for (;!rfs.atEnd();rfs.moveNext()) 
	{
		mfso.DeleteFile(rfs.item());
	}	

}

//Get platform parameters from the Settings.ini
function GetPlatform()
{
	//Get platform parameters
	var found = false;
	var SF = mfso.OpenTextFile(SetFile);
	while (!SF.AtEndOfStream)
	{
		var line = SF.ReadLine();
		if (line.indexOf(";") == 0) continue;
		if (line.indexOf("[" + SystemPlatform + "]") == 0)
		{
			found = true;
			line = "";
			while(line.indexOf("[") == -1 && !SF.AtEndOfStream)
			{
				line = SF.ReadLine();
				GetNV(line);
			}
		}
		
	}
	SF.Close();
	return found;
}


//Resolve parameters from the folder tree
function ParseSubs(path)
{
	var folder = mfso.GetFolder(path);
	var fs = new Enumerator(folder.SubFolders);
	var fc = new Enumerator(folder.files);
	for (;!fs.atEnd();fs.moveNext()) ParseSubs(fs.item());
	for (;!fc.atEnd();fc.moveNext()) RetreiveArray(fc.item());
}

//Resolve parameters from the line
function RetreiveArray(InFileName)
{
	var infile = mfso.OpenTextFile(InFileName);
	var outstring = new Array();
	var arrayIndex = 0;
	while (!infile.AtEndOfStream)
	{
		instring = infile.ReadLine()
		var instring = ExtractLine(instring);
		outstring[arrayIndex] = instring;
		arrayIndex++;
	}
	infile.Close();
	outfile = mfso.OpenTextFile(InFileName,2,true);
	for (var i=0;i<arrayIndex;i++) outfile.WriteLine(outstring[i]);
	outfile.Close();
}

//Resolve parameter from the line
function ExtractLine(instring)
{
	var mCurr = 0;
	var xxx = 0;
	while (xxx == 0)
	{
		mInd = instring.indexOf("%",mCurr);
		if (mInd == -1) return instring; //No more parameters in the string since mCurr index.
		mIndNext = instring.indexOf("%",mInd + 1); //Get next "%" symbol
		if (mIndNext == -1) return instring;  //No next symbol, so it might be just "%" sign
		mExpanded = ""; 
		var CurStr = instring.substring(mInd,mIndNext + 1).replace(/\%/g,"");
//		WScript.Echo("Extracting: " + CurStr);
		try	
		{
			mExpanded = eval(CurStr);
		}
		catch (e)
		{
		}
		if (mExpanded != null && mExpanded != "" && mExpanded.indexOf("%") != 0)
		{
//			WScript.Echo(CurStr + "=" + mExpanded);
			instring = instring.substring(0,mInd) + mExpanded + instring.substring(mIndNext+1);
		}
		else
		{
			//WScript.Echo("Couldn't resolve: \"" + CurStr  + "\"");
		}
		mCurr++;
	}
	return instring;
}

//Create javascript variable from the string
function GetNV(line)
{
	var name = line.substring(0,line.indexOf("="));
	var val = line.substring(line.indexOf("=") + 1);
	try
	{
		eval(name + "=\"" +  val.replace("\\","\\\\") + "\"");
		
//		WScript.Echo(name + "=" + eval(name));
	}
	catch(e)
	{
	}

}

//Read common setting from Settings.ini
function ReadCommon()
{
	var SF = mfso.OpenTextFile(SetFile);
	while (!SF.AtEndOfStream)
	{
		var sline = SF.ReadLine();
		if (sline.indexOf("CommonSettings") != -1)
		{
			sline = SF.ReadLine();
			while (sline.indexOf("[") == -1)
			{
				GetNV(sline);
				sline = SF.ReadLine();
			}
	
		}
	}
	SF.Close();
	//WScript.Echo("CommonSettings have been read");
}


//Read & Parse suite file
function ReadSuite(FinalBatch,CurTemp)
{
	var TCExt = ".html";
	var ExExt = ".js";
	if (!mfso.FolderExists(CurTemp)) mfso.CreateFolder(CurTemp);
	var SuiF = mfso.openTextFile(Suite);
	var ResData = "";
	while (!SuiF.AtEndOfStream)
	{
		var line = SuiF.ReadLine();
		if (line.indexOf(";") == 0 || line.length == 0) continue;
		if (line.indexOf(TCExt) == -1)
		{
			//Suite step is run script
//			WScript.Echo(line);
			var extr_line = "call " + Scripts + "\\" + ExtractLine(line);
			var SEi = extr_line.indexOf(ExExt) + ExExt.length;
			var extr1 =  extr_line.substring(0,SEi);
			var extr2 =  extr_line.substring(SEi);
			
			
			ResData += extr1 + " \"" +  SystemPlatform + "\"" + extr2 + "\n";
			
		}
		else
		{
			//Suite step is selenium testcase
		
			// Get suite parameters
//			WScript.Echo(line);
			var SuitePlat = SystemPlatform.replace(/\s/g,"_");
			var Sel = ExtractLine(line);
			var SelOpt = Sel.substring(Sel.indexOf(TCExt) + TCExt.length + 1);
			var TCPart = Sel.substring(0,Sel.indexOf(TCExt) + TCExt.length);
			var TC = TFolder + "\\" + TCPart;
			var SelUrl = GetHref(TC);
			var TCFile = TC.substring(TC.lastIndexOf("\\") + 1);
			var TargetTC = TCFile.replace(".html","_" + SuitePlat + ".html");
			var Target = CurTemp + "\\" +TargetTC;
			mfso.CopyFile(TC,Target);
			RetreiveArray(Target);
			

			//Creating Temp Suite
			var NewSuiteFile =  CurTemp + "\\" + TCFile.replace(TCExt,"_Suite_" + SuitePlat + "_" + Browser + TCExt);
			var NewSuiteRes =  Res + "\\" + TCFile.replace(TCExt, "_Suite_" + SuitePlat +  "_" + Browser + "_Results.html");
			var SuitePart1 = "<html><head> <title>Test Suite</title></head><body><table><tbody><tr><td><b>Test Suite</b></td></tr>";
			var AddStr = "\n<tr><td><a href=\"" + TargetTC + "\">" + TCFile.replace(TCExt,"")  + "</a></td></tr>";
			var SuitePart2 = "</tbody></table></body></html>";
			var ResSF = mfso.OpenTextFile(NewSuiteFile,2,true);
			ResSF.Write(SuitePart1 + AddStr + SuitePart2);
			ResSF.Close();


			//Creating arguments for Selenium RC
			var Proxy = " -Dhttp.proxyHost=" + ProxyHost + " -Dhttp.proxyPort=" + ProxyPort + " -Dhttp.proxyUser=" + ProxyUser + " -Dhttp.proxyPassword=" + ProxyPwd;
			var Jar = "java -jar .\\Scripts\\selenium-server-standalone-2.5.0.jar -userExtensions " + Scripts + "\\user-extensions.js"; 
			var AvPr = " -avoidproxy";
			var TrustCert = " -trustAllSSLCertificates";
			var BrowserLauncher,Options;


			switch (Browser)
			{
				case "firefox":
					BrowserLauncher = "*" + Browser;
					Options = TrustCert;
					break;
				case "ie":
					BrowserLauncher = "iexploreproxy";
					Options = TrustCert + AvPr;
					break;
			}


			
			if (SelUrl.indexOf(TPAMHost) == -1)
			{
				//Selenium testcase goes outside, we need proxy
				Options = Proxy + Options;
			}


			//Constructing URL
			var URL = SelUrl;
			if (SelOpt.length != 0)
			{
				//We've got account settings in suite call
				var AccArr = SelOpt.split(" ");
				URL = URL.replace(TPAMHost,AccArr[0] + ":" + AccArr[1] + "@" + TPAMHost);
			}

			var OthOpt  = " -htmlsuite \"" + BrowserLauncher + "\" \"" + URL + "\" \"" + NewSuiteFile+ "\" \"" +NewSuiteRes + "\"";
			var SelCmd = Jar + Options +  OthOpt;
			ResData+=SelCmd + "\n";
		}
		
	}


	//Create resulting batch for the suite
	var CF = mfso.OpenTextFile(FinalBatch,2,true);
	CF.Write(ResData);
	CF.Close();

		

}

//Get URL from selenium testcase file
function GetHref(TC)
{
	var URL = "";
	var flag = "href=\"";
	var TCF = mfso.OpenTextFile(TC);
	while (!TCF.AtEndOfStream)
	{
		var tline = TCF.ReadLine();
		var indF = tline.indexOf(flag);
		if (indF != -1)
		{
			URL = tline.substring(indF + flag.length,tline.indexOf("\"",indF + flag.length));
			URL = ExtractLine(URL);
			break;
		}
	}
	TCF.Close();
	return URL;
}



