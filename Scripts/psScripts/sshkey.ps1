param(
    $projectPath = 'c:\Work\TPAM\Automation\TPAM2.5',
	$tpamAddress ='10.30.44.206',
	$keyFile = 'ab10test_user1_api',
	$apiUser = 'ab10test_user1_api'
)

try {
	$fullKeyPath = $projectPath + '\Scripts\keys\' + $keyFile
	[Reflection.Assembly]::LoadFile($projectPath + '\Scripts\ParApiWrapper.dll')|Out-Null
	$clnt = New-Object -TypeName eDMZ.ParApi.ApiClientWrapper -ArgumentList ($tpamAddress,$fullKeyPath,$apiUser)
	$clnt.connect()
	$clnt.setCommandTimeout(70)
	$sshKeyParameters = New-Object eDMZ.ParApi.SshKeyParms
	$sshKeyParameters.standardKey = 'id_dsa'
	$sshKeyParameters.keyFormat = 'OPENSSH'
	$clnt.sshKey($sshKeyParameters)
	$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error arises: $errDescrption"
}