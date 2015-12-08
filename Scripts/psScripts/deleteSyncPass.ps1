#Deletes a synchronized password.
param(
	$tpamAddress,$keyFile,$apiUser,
	$SyncPassName 
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

# Execute the operation on TPAM.
$result = $clnt.deleteSyncPass($SyncPassName)

# Check the outcome of the operation.
"DeleteSyncPass: rc = " + $result.returnCode + ", message =" + $result.message
$clnt.disconnect()