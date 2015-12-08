param(
	$projectdir = 'c:\tpam',
	[string]$fileToWrite
)
$encoding = 'ASCII'
$initDir = $projectdir + '\FitNesseRoot\FrontPage\AutomatedTesting'

function ql ([String[]]$in){
   $ofs=','
   "ListBFERs: $in"	
}

function Write-ToResultFile {
    $input |Out-File -FilePath $fileToWrite -Append -Encoding $encoding
}

function Write-Separation{
    for ($i = 0; $i -lt $3; $i++) {
		''|Write-ToResultFile
	}
	'**************************************************************'|Write-ToResultFile
}

#region Normal tests without tags
<#
"Pages with Normal status" |Out-File -FilePath $fileToWrite -Encoding $encoding
#List of pages which are known normal pages (containers, scenario libraries and etc.)
$knownNormals = "ScenarioLibrary","SetUp","TearDown","TestSpecific","SuiteTearDown",
    "SuiteSetUp","CliScenarios","PsmSmokeTest","ReportActivity","WebDriverSample"

$pages = dir -Recurse -Filter properties.xml -Path $initDir |
   where{((Select-Xml -Path $_.FullName -XPath '//Normal'|Measure-Object).count -gt 0) -and
   ($knownNormals -notcontains $_.Directory.Name)}
 $num = $pages.count
 "Total: $num files" | Write-ToResultFile
 foreach ($page in $pages){
    $page.DirectoryName.Substring($initDir.Length).replace('\','|')| Write-ToResultFile
	}
#>	
#endregion	

#region Tagged Tests
$a = dir -Recurse -Filter properties.xml -Path $initDir |
 Select-Xml -XPath '//Suites'|where{$_.Node.InnerText -ne ''}
 $normals = @()
 $normalList = @()
 $workaronds = @()
 $workarondsList = @()
 
foreach ($test in $a) {
	$propFile = Get-Item $test.Path
	$dirName = $propFile.DirectoryName
	$testName = $dirName.Substring($initDir.Length+1).replace('\','|')
	
	$res = New-Object -TypeName System.Management.Automation.PSObject
	$res.PSObject.TypeNames.Insert(0,'TaggedTest')
	$res `
		| Add-Member -Name Tag -MemberType NoteProperty -Value $test.Node.InnerText -PassThru `
		| Add-Member -Name TestName -MemberType NoteProperty -Value $testName -PassThru |Out-Null	
	
	if ((Select-Xml $propFile -XPath '//Test|//Suite'|Measure-Object).count) {
	    if ((Select-Xml $propFile -XPath '//Help').Node.InnerText -eq 'wrk') {
		   $workaronds += $res
		   $workarondsList += $res.Tag
		}
	}
	if ((Select-Xml $propFile -XPath '//Normal'|Measure-Object).count) {
	    $normals += $res
        $normalList  += $res.Tag
	}
}
#endregion

#Write results to file
if (Test-Path $fileToWrite) {
	Remove-Item $fileToWrite
}
"Tests with Normal Page Type & Bug Number in Tag field:" |Write-ToResultFile
if ($normals.Count -gt 0) {
	$normals |ft -AutoSize |Write-ToResultFile
    (ql $normalList) |Write-ToResultFile
}else{
    'No tests with Normal Page Type & Bug Number in Tag field were found.'|Write-ToResultFile
}

Write-Separation

"Tests with Workarounds (wrk in Help field and Bug Number in Tag field):" |Write-ToResultFile
if ($workaronds.Count -gt 0) {
	$workaronds |ft -AutoSize |Write-ToResultFile
    (ql $workarondsList) | Write-ToResultFile
} else {
    'No Tests with Workarounds (wrk in Help field and Bug Number in Tag field) were found'|Write-ToResultFile
}

Write-Separation
''|Write-ToResultFile