# Lists all accounts that can be PSM enabled.
param(
	$tpamAddress,$keyFile,$apiUser,
	$systemName,$accountName,$accountAutoFlag = 'ALL',$accountPSMFlag = 'ALL',
	$accountCustom1,$accountCustom2,$accountCustom3,$accountCustom4,$accountCustom5,
	$accountCustom6,$collectionName,$AccountLockFlag = 'ALL',$DualControlFlag = 'ALL',
	$networkAddress,$platform,$systemAutoFlag = 'ALL',$systemCustom1,$systemCustom2,
	$systemCustom3,$systemCustom4,$systemCustom5,$systemCustom6,$systemPSMFlag = 'ALL',
	$sortType = 'Ascending',$sort = 'SYSTEM_NAME',[Int32]$maxRows = 25,
	[string[]]$propsToList = @('systemName','accountName','enableFlag','fileTransType')
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
	#region Set filtering options
	$filter = New-Object eDMZ.ParApi.PsmAccountFilter
		if ($accountName) {
			$filter.accountName = $accountName
		}
		if ($systemName) {
			$filter.systemName = $systemName
		}
		$filter.accountAutoFlag = $accountAutoFlag
		$filter.accountPsmFlag = $accountPSMFlag
		$filter.accountLockFlag = $AccountLockFlag
		$filter.dualControlFlag = $DualControlFlag
		
		$filter.systemAutoFlag = $systemAutoFlag
		$filter.systemPsmFlag = $systemPSMFlag
		
		if ($accountCustom1) {
			$filter.accountCustom1 = $accountCustom1
		}
		
		if ($accountCustom2) {
			$filter.accountCustom2 = $accountCustom2
		}
		
		if ($accountCustom3) {
			$filter.accountCustom3 = $accountCustom3
		}
		
		if ($accountCustom4) {
			$filter.accountCustom4 = $accountCustom4
		}
		
		if ($accountCustom5) {
			$filter.accountCustom5 = $accountCustom5
		}
		
		if ($accountCustom6) {
			$filter.accountCustom6 = $accountCustom6
		}
		
		if ($collectionName) {
			$filter.collectionName = $collectionName
		}
		
		if ($networkAddress) {
			$filter.networkAddress = $networkAddress
		}
		
		if ($platform) {
			$filter.platform = $platform
		}
		
		if ($systemCustom1) {
			$filter.systemCustom1 = $systemCustom1
		}
		if ($systemCustom2) {
			$filter.systemCustom2 = $systemCustom2
		}
		if ($systemCustom3) {
			$filter.systemCustom3 = $systemCustom3
		}
		if ($systemCustom4) {
			$filter.systemCustom4 = $systemCustom4
		}
		if ($systemCustom5) {
			$filter.systemCustom5 = $systemCustom5
		}
		if ($systemCustom6) {
			$filter.systemCustom6 = $systemCustom6
		}
		
		$filter.sortType = $sortType
		$filter.sort = $sort
	    $filter.maxRows = $maxRows
	#endregion
	$psmAccounts = @()
	$res = $clnt.listPsmAccounts($filter,[ref]$psmAccounts)
	if($propsToList.count -eq 1){
       $propsToList=$propsToList[0].Split(',')
    }
	if ($res.entries) {
	    $res.entries|ft -AutoSize -Property $propsToList
	} else {
	    'listRequestDetails: ' + $res.message
	}
	$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error arises: $errDescrption"
}