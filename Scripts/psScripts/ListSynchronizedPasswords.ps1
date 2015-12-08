#Lists all synchronized passwords configured in TPAM.
param(
	$tpamAddress,$keyFile,$apiUser,
	[string[]]$propsToList = @('syncPassName','subscribers','scheduledChanges')
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
    $pwds = @()
	$res = $clnt.listSynchronizedPasswords([ref]$pwds)
	if($propsToList.count -eq 1){
        $propsToList=$propsToList[0].Split(',')
    }
	$res.entries|ft -AutoSize -Property $propsToList
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error occured: $errDescrption"
}
Finally{
    $clnt.disconnect()
}