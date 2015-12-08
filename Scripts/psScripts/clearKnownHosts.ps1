# Clear Known Hosts Entry for a given system
param(
    $tpamAddress,$keyFile,$apiUser,
    $SystemName
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
	$result = $clnt.clearKnownHosts($SystemName)
	"clearKnownHosts: rc = " + $result.returnCode + ", message =" + $result.message
	$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error occured: $errDescrption"
}
