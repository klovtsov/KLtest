#Unlocks a currently locked user account.
param(
	$tpamAddress,$keyFile,$apiUser,
	$userName 
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

#endregion
# Execute the operation on TPAM.
$result = $clnt.unlockUser($userName)
# Check the outcome of the operation.
"unlockUser: rc = " + $result.returnCode + ", message =" + $result.message
$clnt.disconnect()