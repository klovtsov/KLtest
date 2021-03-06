param(
    [String]$version,
    $comment, $EmailAddr
)

function Expand-ZIPFile($file, $destination) {
    $shell = new-object -com shell.application
    $zip = $shell.NameSpace($file)
    foreach($item in $zip.items()) {
        $shell.Namespace($destination).copyhere($item)
    }
}

#change path here on appropriate
$TPAM = "C:\TPAM\"
$log = $TPAM + "Scripts\Temp\CppAPIlog.txt"
#$comment = "testrun"
$path = "\\morflsw01.prod.quest.corp\RD-TPAM-DEV-BUILD\latest\api\"
#$version = "ParApi-Cpp_.NET-2.5.914.zip"
$fullpathtoshare = $path + $version


$TPAMcppTempFolder = $TPAM + "Scripts\Temp\CppAPI\"
$fullpath = $TPAMcppTempFolder + $version

New-Item -ItemType Directory -Force -Path $TPAMcppTempFolder | Out-Null
"`n Copy File `n" > $log
Copy-Item $fullpathtoshare $TPAMcppTempFolder -Force -PassThru >> $log | Out-Null
Expand-ZIPFile -file $fullpath -destination $TPAMcppTempFolder


#****************************
$msi = "PAR API Library Setup.msi"
$version -match '\d+\.\d+\.\d+' | out-null
$dv = $Matches[0]
$fullpathtomsi = $TPAMcppTempFolder + "Cpp_.NET\$dv\$msi"
msiexec /a "$fullpathtomsi" TARGETDIR="$TPAMcppTempFolder" /q | out-null
#*****************************
$ParApiWrapper = $TPAMcppTempFolder + "libMD\ParApiWrapper.dll"
$ScriptsFolder = $TPAM + "Scripts\"

#copy file to Scripts folder

"`n Copy File `n" >> $log
Copy-Item $ParApiWrapper $ScriptsFolder -PassThru >> $log


$OS = Get-WmiObject Win32_OperatingSystem 
 If ($OS.OSArchitecture -eq '64-bit')
    {$ProgramFiles = "Program Files (x86)"}
  else {
    $ProgramFiles = "Program Files"
}
                         
$stcmd = "C:\$ProgramFiles\Borland\StarTeam Cross-Platform Client 2009\stcmd.exe"
$ServerString = "svc-spb-osqabuilder:q``w1e2r3osqa@spbsrcw02:49201/TPAM/2.5/Scripts"
$stcmdargs = " ci -p $ServerString -o -fp $ScriptsFolder -r `"$comment`" `"ParApiWrapper.dll`"  "

$psi = New-object System.Diagnostics.ProcessStartInfo 
$psi.CreateNoWindow = $true 
$psi.UseShellExecute = $false 
$psi.RedirectStandardOutput = $true 
$psi.RedirectStandardError = $true 
$psi.FileName = $stcmd 
$psi.Arguments = @($stcmdargs) 
$process = New-Object System.Diagnostics.Process 
$process.StartInfo = $psi 
if (Test-Path $ParApiWrapper ) {
	[void]$process.Start()
	$output = $process.StandardOutput.ReadToEnd() 
	$output += $process.StandardError.ReadToEnd()
	$process.WaitForExit() 
	"`n Check-in Files `n" >> $log
}
$output >> $log


gci $TPAMcppTempFolder | Remove-Item -Recurse -Force -Confirm:$false 
if (Test-Path $TPAMcppTempFolder* ) {
    "Temporary files are not deleted successfully!" >> $log
} else {
    "Temporary files are deleted successfully." >> $log
}

#send mail
$emailFrom = "tpamresults@tpam.com" 
$subject="CPP/.Net API upload result"
$message = (Get-Content $log) -join "`n" 
$smtpserver="relayemea.prod.quest.corp"
$smtp=new-object Net.Mail.SmtpClient($smtpServer) 
$smtp.Send($emailFrom, $EmailAddr, $subject, $message)