#List the subscribers of a specific synchronized password.
param(
	$tpamAddress,$keyFile,$apiUser,$syncPassName,
	[string[]]$propsToList = @('system','account','acctAutoFl','passwordStatus','networkAddress')
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
    $subscrbrs = @()
	$res = $clnt.listSyncPwdSubscribers($syncPassName,[ref]$subscrbrs)
	if($propsToList.count -eq 1){
        $propsToList=$propsToList[0].Split(',')
    }
	$res.entries |ft -AutoSize -Property $propsToList
	$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error occured: $errDescrption"
}