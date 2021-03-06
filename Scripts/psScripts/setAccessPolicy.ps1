param(
    $tpamAddress,$keyFile,$apiUser,
	$AccessPolicyName,
	$Action = 'ADD',
	$AccountName,
	$CollectionName,
	$FileName,
	$GroupName,
	$SystemName,
	$UserName
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
	$params = New-Object -TypeName eDMZ.ParApi.SetAccessPolicyParms
	if ($CollectionName) {
		$params.collectionName = $CollectionName
	} else {
	    $params.systemName = $systemName
		if ($AccountName) {
			$params.accountName = $accountName
		}
		if ($FileName) {
			$params.fileName = $FileName
		}
	}
	if ($UserName) {
		$params.userName = $UserName
	}elseif ($GroupName) {
	    $params.groupName =$GroupName
	}
	else {
	    throw 'Either user or group must be specified'
	}
	$result = $clnt.setAccessPolicy($AccessPolicyName,$Action,$params)
	# Check the outcome of the operation.
	"setAccessPolicy: rc = " + $result.returnCode + ", message =" + $result.message
	$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error arises: $errDescrption"
}