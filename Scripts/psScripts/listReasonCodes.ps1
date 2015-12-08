#Will list any active reason codes and their description that have been defined in TPAM.
param(
	$tpamAddress,$keyFile,$apiUser
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
    $codes = @()
	$res = $clnt.listReasonCodes([ref]$codes)
	if ($res.entries) {
	    $res.entries |ft -AutoSize -Property name,description
	} else {
	    'listReasonCodes: ' + $res.message
	}
	$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error occured: $errDescrption"
}