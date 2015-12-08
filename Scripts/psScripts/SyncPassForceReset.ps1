#Forces the reset of a synchronized password.
param(
    $tpamAddress,$keyFile,$apiUser,
    $SyncPassName,$NewPassword
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

# Execute the operation on TPAM.
if ($NewPassword) {
	$result = $clnt.syncPassForceReset($syncPassName,$NewPassword)
} else {
    $result = $clnt.syncPassForceReset($syncPassName)
}
 
# Check the outcome of the operation.
"SyncPwdForceReset: rc = " + $result.returnCode + ", message =" + $result.message
$clnt.disconnect()