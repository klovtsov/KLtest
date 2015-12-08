<#
    .SYNOPSIS
	    Lists specific details about session requests.
	.SYNTAX
	    listSessionRequestDetails.ps1 [-status ALL|PENDING|ACTIVE|CURRENT|OPEN] 
		[-requestorName <String>] [-systemName <String>] [-accountName <String>]
		[-startDate 'YYYY/MM/DD'] [-endDate 'YYYY/MM/DD'] [-MaxRows <int32>]
    .EXAMPLE
        listSessionRequestDetails.ps1 -systemName dc1 -accountName testAcc -startDate '2013/11/03'
  #>
param(
	$tpamAddress,$keyFile,$apiUser,
	$status = 'OPEN',$requestorName,$accountName,$systemName,$startDate,$endDate,
	[Int32]$maxRows = 25,
	[string[]]$propsToList = @('requestID','requestStatus','submittedDt','expiresDt')
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
	#region Set filtering options
	$filter = New-Object eDMZ.ParApi.SessionRequestFilter
	    $filter.status = $status
		if ($requestorName) {
			$filter.requestorName = $requestorName
		}
		if ($accountName) {
			$filter.accountName = $accountName
		}
		if ($systemName) {
			$filter.systemName = $systemName
		}
		# YYYY/MM/DD format
		if ($startDate) {
		    $time = @()
		    $startDate.Split('/')|%{$time+=[int]$_}
	        $startDate = New-Object eDMZ.ParApi.DateTimeWrapper $time
			$filter.startDate = $startDate
		}
		# YYYY/MM/DD format
		if ($endDate) {
		    $time = @()
		    $endDate.Split('/')|%{$time+=[int]$_}
	        $endDate = New-Object eDMZ.ParApi.DateTimeWrapper $time
			$filter.endDate = $endDate
		}
	    $filter.maxRows = $maxRows
	#endregion
	$requests = @()
	$res = $clnt.listSessionRequestDetails($filter,[ref]$requests)
	if ($res.entries) {
	    if($propsToList.count -eq 1){
            $propsToList=$propsToList[0].Split(',')
        }
	    $res.entries | select -Property $propsToList
	} else {
	    'listSessionRequestDetails: ' + $res.message
	}
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error arises: $errDescrption"
}finally{
    $clnt.disconnect()
}