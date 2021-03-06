    #Soft deletes the system account.
param(
	$tpamAddress,$keyFile,$apiUser,
	$systemName,$accountName 
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

#endregion
# Execute the operation on TPAM.
$result = $clnt.deleteAccount($systemName,$accountName)
# Check the outcome of the operation.
"deleteAccount: rc = " + $result.returnCode + ", message =" + $result.message
$clnt.disconnect()