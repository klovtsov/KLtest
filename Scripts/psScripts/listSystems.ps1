param(
	$tpamAddress,$keyFile,$apiUser,
	[string]$systemName,
	[string]$networkAddress,
	[string]$collectionName,
	[string]$platform,
	[string]$autoFlag,
	[string]$PasswordChangeProfile,
	[string]$PasswordCheckProfile,
	[string]$SystemCustom1,[string]$SystemCustom2,[string]$SystemCustom3,
	[string]$SystemCustom4,[string]$SystemCustom5,[string]$SystemCustom6,
	[string[]]$propsToList = @('systemName','platformName','networkAddress')
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
	#region Set filtering options
	$filter = New-Object eDMZ.ParApi.SystemFilter
	if ($systemName) {
		$filter.systemName = $systemName
	}
	if ($networkAddress) {
		$filter.networkAddress = $networkAddress
	}
	if ($collectionName) {
		$filter.collectionName = $collectionName
	}
	if ($platform) {
		$filter.platform = $platform
	}
	if ($autoFlag) {
		$filter.sysAutoFl = $autoFlag
	}
	if($PasswordChangeProfile){
	   $filter.passwordChangeProfile = $PasswordChangeProfile
	}
	if($PasswordCheckProfile){
	   $filter.passwordCheckProfile = $PasswordCheckProfile
	}
	if($SystemCustom1){
	    $filter.systemCustom1 = $SystemCustom1 
	}
	if($SystemCustom2){
	    $filter.systemCustom2 = $SystemCustom2 
	}
	if($SystemCustom3){
	    $filter.systemCustom3 = $SystemCustom3 
	}
	if($SystemCustom4){
	    $filter.systemCustom4 = $SystemCustom4 
	}
	if($SystemCustom5){
	    $filter.systemCustom5 = $SystemCustom5 
	}
	if($SystemCustom6){
	    $filter.systemCustom6 = $SystemCustom6 
	}
	
	#endregion
	$systems = @()
	$res = $clnt.listSystems($filter,[ref]$systems)
	# Script can be called from command line. In this case only arrayy is passed as string
    if($propsToList.count -eq 1){
       $propsToList=$propsToList[0].Split(',')
    }
	$res.entries|ft -AutoSize -Property $propsToList
	$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error arises: $errDescrption"
}