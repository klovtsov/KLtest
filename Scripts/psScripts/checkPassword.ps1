#Initiates a password test for the specified system account.
param(
    $tpamAddress,$keyFile,$apiUser,
    $systemName,
	$accountName
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

# Execute the operation on TPAM.
 $result = $clnt.checkPassword($systemName,$accountName)
# Check the outcome of the operation.
"checkPassword: rc = " + $result.returnCode + ", message =" + $result.message
$clnt.disconnect()