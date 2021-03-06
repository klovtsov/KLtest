param(
	$tpamAddress,$keyFile,$apiUser,
	$systemName
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"
# Execute the operation on TPAM.
$res = $clnt.deleteSystem($systemName)
# Check the outcome of the operation.
"addSystem: rc = " + $res.returnCode + ", message =" + $res.message
$clnt.disconnect()