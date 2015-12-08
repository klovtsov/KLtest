#Performs a forced reset on a user’s password.
param(
	$tpamAddress,$keyFile,$apiUser,
	$userName,$password 
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

#endregion
# Execute the operation on TPAM.
$result = $clnt.changeUserPassword($userName,$password)
# Check the outcome of the operation.
"changeUserPassword: rc = " + $result.returnCode + ", message =" + $result.message
$clnt.disconnect()