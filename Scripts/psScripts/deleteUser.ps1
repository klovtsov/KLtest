#Permanently deletes the named user account.
param(
	$tpamAddress,$keyFile,$apiUser,
	$userName 
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

#endregion
# Execute the operation on TPAM.
$result = $clnt.deleteUser($userName)
# Check the outcome of the operation.
"deleteUser: rc = " + $result.returnCode + ", message =" + $result.message
$clnt.disconnect()