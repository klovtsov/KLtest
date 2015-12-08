var mfso = new ActiveXObject("Scripting.FileSystemObject");
var Args = WScript.Arguments;
var Var =  Args(0);
var Res = ".\\Results";
var Val = "";
var folder = mfso.GetFolder(Res);
var fc = new Enumerator(folder.files);
   
var BFiles = new Array();
var BDates = new Array();
var BDateSort = new Array();
var BCount=0;


for (;!fc.atEnd();fc.moveNext()) 
{
	var F = fc.item();
	
	var ff = mfso.OpenTextFile(F);
	while (!ff.AtEndOfStream)
	{
		var line = ff.ReadLine();
		if (line.indexOf("${" + Var + "}") != -1)		
		{
			line = ff.ReadLine();
			BFiles[BCount] = line.replace("<td>","").replace("</td>","").replace("\t","");
			BDates[BCount] = F.DateLastModified;
			BDateSort[BCount] = F.DateLastModified;
			BCount++;
			break;
		}

	}
	ff.Close();
}

BDateSort.sort(sortNumber);
Oldest = "" + BDateSort[BDateSort.length-1];
for (var i=0;i<BDates.length;i++)
{              
	var Orig = "" + BDates[i];

	if (Orig == Oldest) 
	{
		Val = BFiles[i];
		break;
	}
}
//WScript.Echo(Val);
var Res = mfso.OpenTextFile(Var,2,true);
Res.WriteLine(Val);
Res.Close();


function sortNumber(a,b)
{
return a - b;
}


