#Forces a password change for the specified manual management account.
param(
    $tpamAddress,$keyFile,$apiUser,
    [string]$PasswordID,
	$Status
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

# Execute the operation on TPAM.
 $result = $clnt.manualPasswordReset($PasswordID,$Status)
# Check the outcome of the operation.
"forceReset: rc = " + $result.returnCode + ", message =" + $result.message
$clnt.disconnect()