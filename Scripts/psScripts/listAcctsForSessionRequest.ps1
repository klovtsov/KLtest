# Provides a list of accounts that the user can submit a session request for.
param(
	$tpamAddress,$keyFile,$apiUser,
	$systemName,$accountName,[Int32]$mostRecent,
	$accountCustom1,$accountCustom2,$accountCustom3,$accountCustom4,$accountCustom5,
	$accountCustom6,$systemCustom1,$systemCustom2,$systemCustom3,$systemCustom4,
	$systemCustom5,$systemCustom6,[Int32]$maxRows = 25
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"
try {
	#region Set filtering options
	$filter = New-Object eDMZ.ParApi.AcctForSessionRequestFilter
		if ($accountName) {
			$filter.accountName = $accountName
		}
		if ($systemName) {
			$filter.systemName = $systemName
		}
		if ($mostRecent) {
			$filter.mostRecent = $mostRecent
		}
		
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
	    $filter.maxRows = $maxRows
	#endregion
	$psmAccounts = @()
	$res = $clnt.listAcctsForSessionRequest($filter,[ref]$psmAccounts)
	if ($res.entries) {
	    $res.entries | Select -Property systemName,accountName,minApprovers,details
	} else {
	    'listRequestDetails: ' + $res.message
	}
	$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error arises: $errDescrption"
}