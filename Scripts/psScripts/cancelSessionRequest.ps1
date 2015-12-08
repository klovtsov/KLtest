#Allows session requests to be cancelled
param(
    $tpamAddress,$keyFile,$apiUser,
	$RequestID,$Comment
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

$result = $clnt.cancelSessionRequest($RequestID,$Comment)
# Check the outcome of the operation.
"approve: rc = " + $result.returnCode + ", message =" + $result.message
$clnt.disconnect()