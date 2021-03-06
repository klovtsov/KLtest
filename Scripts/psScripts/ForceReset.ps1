#Forces a password change for the specified system account.
param(
    $tpamAddress,$keyFile,$apiUser,
    $systemName,
	$accountName
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

# Execute the operation on TPAM.
 $result = $clnt.forceReset($systemName,$accountName)
# Check the outcome of the operation.
"forceReset: rc = " + $result.returnCode + ", message =" + $result.message
$clnt.disconnect()