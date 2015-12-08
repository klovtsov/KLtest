$prefixes = 'anonymous','customProfile','BridJExtractedLibraries','Userprofile'
$tmpfolder = 'C:\Users\Admin2\AppData\Local\Temp'
foreach ($prefix in $prefixes) {
    $a = $prefix + '*'
	gci $tmpfolder|?{$_.Name -like $a -and $_.CreationTime -lt (Get-Date).AddDays(-1)}|Remove-Item -Recurse -Force -Confirm:$false
}
