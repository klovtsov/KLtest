# Allows session requests to be approved
param(
    $tpamAddress,$keyFile,$apiUser,
	$RequestID,$Comment
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

$result = $clnt.approveSessionRequest($RequestID,$Comment)
# Check the outcome of the operation.
"approveSessionRequest: rc = " + $result.returnCode + ", message =" + $result.message
$clnt.disconnect()