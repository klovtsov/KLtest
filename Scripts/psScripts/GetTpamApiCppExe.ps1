param(
    [String]$version="ParApi-Cpp_.NET-2.5.914.zip",
    $comment, $EmailAddr,
	[string]$builderMachine='10.30.39.5', #A computer with Visual Studio
	$builderUser='pritchet\yasek',
	$builderUserPwd='*7utinaa',
	[string]$vsProjectPath='C:\TpamBuilds\tpamapicpp' #Folder with VS project
)

function Expand-ZIPFile($file, $destination) {
    $shell = new-object -com shell.application
    $zip = $shell.NameSpace($file)
    foreach($item in $zip.items()) {
        $shell.Namespace($destination).copyhere($item)
    }
}

function Send-Result {
	$emailFrom = "tpamresults@tpam.com" 
    $subject="TpamApiCpp.exe build result"
    $message = (Get-Content $log) -join "`n" 
    $smtpserver="relayemea.prod.quest.corp"
    $smtp=new-object Net.Mail.SmtpClient($smtpServer) 
    $smtp.Send($emailFrom, $EmailAddr, $subject, $message)
}

#Removes temporal folders.
function Delete-TempFolder {
	param(
		[string]$folderName
	)
    if (Test-Path $folderName) {
       Remove-Item $folderName -Recurse -Force -Confirm:$false -ErrorAction Stop
    }
}

#change path here on appropriate
$TPAM = 'C:\TPAM\' #'C:\Work\TPAM\Automation\TPAM2.5\' #
$ScriptsFolder = $TPAM + "Scripts\"
$log = $TPAM + "Scripts\Temp\buildCppAPIlog.txt"
$path = "\\morflsw01.prod.quest.corp\RD-TPAM-DEV-BUILD\latest\api\"
$fullpathtoshare = $path + $version
$TPAMcppTempFolder = $TPAM + "Scripts\Temp\CppAPI\"
$fullpath = $TPAMcppTempFolder + $version
#

try {
    $dt =Get-Date
	"Creating a new tpamapiccp.exe $dt" > $log
    Delete-TempFolder $TPAMcppTempFolder
	#Region Extract an API msi
    New-Item -ItemType Directory -Force -Path $TPAMcppTempFolder -ErrorAction Stop | Out-Null
    "Extracting MSI msi..." >> $log
    Copy-Item $fullpathtoshare $TPAMcppTempFolder -Force -PassThru -ErrorAction Stop | Out-Null
    Expand-ZIPFile -file $fullpath -destination $TPAMcppTempFolder
    $msi = "PAR API Library Setup.msi"
    $version -match '\d+\.\d+\.\d+' | out-null
    $dv = $Matches[0]
    $fullpathtomsi = $TPAMcppTempFolder + "Cpp_.NET\$dv\$msi"

    if (Test-Path $fullpathtomsi) {
        "MSI has been successfully extracted!" >> $log
    } else {
        Throw "ERROR: MSI extraction failed!"   
    }
    #endregion
#*****************************

#Region Build machine connection credentials
$PWord = ConvertTo-SecureString -String $builderUserPwd -AsPlainText -Force
$builderCreds = New-Object -TypeName System.Management.Automation.PSCredential -ArgumentList $builderUser, $PWord

"Build C++ executable..." >> $log

#copy msi to builder system
$driveLetter = 'J'
$builder_temp = 'cppApiBuilding'
$full_path_to_buiuder_temp = $driveLetter+':\'+$builder_temp

$batName='buildTpamApi.bat'
$bat = @"
@echo off
set workFolder=%~dp0
set projPath=%~1
set log=%workFolder%\build.log
set msiPath=%workFolder%PAR API Library Setup.msi
set wrapperPth="C:\Program Files\Quest Software\PAR API Library\libMD\ParApiWrapper.dll"
set releaseFld=%projPath%\release

echo Installation ParApi > %log%
msiexec /i "%msiPath%" /quiet
PING 1.1.1.1 -n 1 -w 5000 >NUL

echo A new tpamapicpp.exe build has been started. >> %log%
echo Release folder is %releaseFld% >> %log%

IF EXIST %releaseFld% (
    RMDIR /S /Q %releaseFld%
    echo Old release folder was deleted. >> %log%
)
echo. >> %log%
call "C:\Program Files\Microsoft Visual Studio 8\VC\vcvarsall.bat" x86
VCBUILD %projPath%\bbb.sln >> %log%

echo. >> %log%
COPY %releaseFld%\tpamapicpp.exe %workFolder% /Y >> %log%

echo. >> %log%
echo Uninstall ParApi >> %log%
msiexec /x "%msiPath%" /quiet

EXIT %errorlevel%
"@
    
	#Error in Powershell 2.0 (https://connect.microsoft.com/feedback/ViewFeedback.aspx?FeedbackID=334084&SiteID=99)
    #New-PSDrive -Name $driveLetter -PSProvider FileSystem -Root ("\\$builderMachine\c$") -Credential $builderCreds |Out-Null
    #workaround
    $netw = new-object -ComObject WScript.Network
    $netw.MapNetworkDrive($driveLetter+":", "\\$builderMachine\c$", $false, $builderUser, $builderUserPwd)
    #Some cleanup
    Delete-TempFolder $full_path_to_buiuder_temp
	
	New-Item -ItemType Directory -Force -Path $full_path_to_buiuder_temp -ErrorAction Stop|Out-Null
    Copy-Item $fullpathtomsi $full_path_to_buiuder_temp -ErrorAction Stop |Out-Null
	
	#create bat file on remote machine
    $bat |Out-File "$full_path_to_buiuder_temp\$batName" -Encoding ASCII
    
	#execute bat file on remote machine (Powershell Remoting feature is not available)
    $expr = "$TPAM\Scripts\psexec.exe \\$builderMachine -u $builderUser -p $builderUserPwd cmd /c c:\$builder_temp\$batName $vsProjectPath"
    Invoke-Expression $expr -ErrorAction Stop |Out-Null
	
	#Copy compiled file
    $rsltFile = "$full_path_to_buiuder_temp\tpamapicpp.exe"
    $logFile = "$full_path_to_buiuder_temp\build.log"
	if (Test-Path $rsltFile) {
        "`n Building C++ executable was successfull `n" >> $log
		(Get-Content $logFile) -join "`n" >> $log
        Copy-Item $rsltFile $ScriptsFolder -ErrorAction Stop
    }else{
        "`n Building C++ executable was unsuccessfull `n" >> $log
		(Get-Content $logFile) -join "`n" >> $log
		Throw "Can't find a result c++ file"
    }
	#endregion

	#Region Check-in to StarTeam
	$OS = Get-WmiObject Win32_OperatingSystem 
	 If ($OS.OSArchitecture -eq '64-bit')
	    {$ProgramFiles = "Program Files (x86)"}
	  else {
	    $ProgramFiles = "Program Files"
	}
	                         
	$stcmd = "C:\$ProgramFiles\Borland\StarTeam Cross-Platform Client 2009\stcmd.exe"
	$ServerString = "svc-spb-osqabuilder:q``w1e2r3osqa@spbsrcw02:49201/TPAM/2.5/Scripts"
	$stcmdargs = " ci -p $ServerString -o -fp $ScriptsFolder -r `"$comment`" `"tpamapicpp.exe`"  "

	$psi = New-object System.Diagnostics.ProcessStartInfo 
    $psi.CreateNoWindow = $true 
	$psi.UseShellExecute = $false 
	$psi.RedirectStandardOutput = $true 
	$psi.RedirectStandardError = $true 
	$psi.FileName = $stcmd 
	$psi.Arguments = @($stcmdargs) 
	$process = New-Object System.Diagnostics.Process 
	$process.StartInfo = $psi 
	[void]$process.Start()
	$output = $process.StandardOutput.ReadToEnd() 
	$output += $process.StandardError.ReadToEnd()
	$process.WaitForExit() 
	"`n Check-in File `n" >> $log
	$output >> $log
    
	Delete-TempFolder $TPAMcppTempFolder
	#endregion
}catch {
    $ErrorMessage = $_.Exception.Message
    "Script execution has ran into problems. Error message is: $ErrorMessage" >> $log
} finally {
    $drv=$driveLetter+":"
	if(Test-Path $drv){#Remove-PSDrive -Name "$driveLetter"
        $netw.RemoveNetworkDrive($drv,$true,$true)
	}
    Send-Result
}
