#Lists all defined system accounts.
param(
	$tpamAddress,$keyFile,$apiUser,
	[string]$systemName,$AccountName,$NetworkAddress,$collectionName,$Platform,
	$SystemAutoFlag = 'ALL',$AccountAutoFlag = 'ALL',$DualControlFlag,$Sort = 'SystemName',
	[int]$MaxRows = 25,
	$PasswordChangeProfile,$PasswordCheckProfile,$DisabledSchedules = 'ALL',
	$SystemCustom1,$SystemCustom2,$SystemCustom3,$SystemCustom4,$SystemCustom5,$SystemCustom6,
	$AccountCustom1,$AccountCustom2,$AccountCustom3,$AccountCustom4,$AccountCustom5,$AccountCustom6,
	[string[]]$propsToList = @('accountName','systemName','PasswordChangeProfile','PasswordCheckProfile')
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"
try {	
	#region Set filtering options
	$filter = New-Object eDMZ.ParApi.AccountFilter
	if ($systemName) {
		$filter.systemName = $systemName
	}
	if ($AccountName) {
	    $filter.accountName = $AccountName
	}
	if ($NetworkAddress) {
		$filter.networkAddress = $NetworkAddress
	}
	if ($collectionName) {
		$filter.collectionName = $collectionName
	}
	if ($Platform) {
		$filter.platform = $Platform
	}
	if ($PasswordChangeProfile) {
		$filter.passwordChangeProfile = $PasswordChangeProfile
	}
	if ($PasswordCheckProfile) {
		$filter.passwordCheckProfile = $PasswordCheckProfile
	}
	$filter.disabledSchedules = $DisabledSchedules
	$filter.sysAutoFl = $SystemAutoFlag
	$filter.acctAutoFl = $AccountAutoFlag
	if ($DualControlFlag) {
		$filter.dualControlRequiredFlag = $DualControlFlag
	}
	$sorting = @{"SystemName"='SYSTEM_NAME';"AccountName"='ACCOUNT_NAME';"NextChangeDate"='NEXT_CHANGE_DT'}
	$filter.sort = $sorting[$Sort]
	$filter.maxRows = $MaxRows
	#System custom fields
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
	#Account custom fields
	if($AccountCustom1){
	    $filter.accountCustom1 = $AccountCustom1
	}
	if($AccountCustom2){
	    $filter.accountCustom2 = $AccountCustom2
	}
	if($AccountCustom3){
	    $filter.accountCustom3 = $AccountCustom3
	}
	if($AccountCustom4){
	    $filter.accountCustom4 = $AccountCustom4
	}
	if($AccountCustom5){
	    $filter.accountCustom5 = $AccountCustom5
	}
	if($AccountCustom6){
	    $filter.accountCustom6 = $AccountCustom6
	}
	
	#endregion
	$accounts = @()
	$res = $clnt.listAccounts($filter,[ref]$accounts)
    # Script can be called from command line. In this case only arrayy is passed as string
    if($propsToList.count -eq 1){
       $propsToList=$propsToList[0].Split(',')
    }
	$res.entries|ft -AutoSize -Property $propsToList
	$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error occured: $errDescrption"
}