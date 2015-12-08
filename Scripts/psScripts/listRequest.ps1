<#
    .SYNOPSIS
	    Lists basic details about password requests.
	.SYNTAX
	    listRequest.ps1 [-status ALL|PENDING|ACTIVE|CURRENT|OPEN] [-requestorName <String>]
		[-systemName <String>] [-accountName <String>] [-MaxRows <int32>]
    .EXAMPLE
        listRequest.ps1 -systemName dc1 -accountName testAcc  -requestorName 'User=Myself'
  #>
param(
	$tpamAddress,$keyFile,$apiUser,
	$status = 'OPEN',$requestorName,$accountName,$systemName,$startDate,$endDate,
	[Int32]$maxRows = 25,
	[string[]]$propsToList = @('requestID','systemName','accountName','requestStatus')
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
	#region Set filtering options
	$filter = New-Object eDMZ.ParApi.RequestFilter
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
	$res = $clnt.listRequest($filter,[ref]$requests)
	if ($res.entries) {
	    
		if($propsToList.count -eq 1){
           $propsToList=$propsToList[0].Split(',')
	   }
	    $res.entries | ft -Property $propsToList
	} else {
	    'listRequest: ' + $res.message
	}
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error arises: $errDescrption"
}finally{
    $clnt.disconnect()
}