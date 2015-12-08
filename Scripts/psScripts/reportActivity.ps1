<#
    Ability to run the activity report from the API.
	
	.Parameter startDate
	    Start date of activities. Must be a valid date time in the form of
		YYYY,MM,DD
		hh,mm
		hh,mm,ss,ms
		YYYY,MM,DD,hh,mm,ss,ms
	
	.Parameter endDate
	    End date of activity. Must be a valid date time in the form
		YYYY,MM,DD
		hh,mm
		hh,mm,ss,ms
		YYYY,MM,DD,hh,mm,ss,ms
	
  #>
param(
	$tpamAddress,$keyFile,$apiUser,
	$startDate,$endDate,$userName,$role,$groupName,$operation,$target,$objectType,
	$sort,$direction,[Int32]$maxRows = 25
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
	$filter = New-Object eDMZ.ParApi.ActivityFilter
	
	if ($startDate) {
		$time = @()
		$startDate.Split(',')|%{$time+=[int]$_}
	    $chtime = New-Object eDMZ.ParApi.DateTimeWrapper $time
		$filter.startDate = $chtime
    }
	
	if ($endDate) {
		$time = @()
		$endDate.Split(',')|%{$time+=[int]$_}
	    $chtime = New-Object eDMZ.ParApi.DateTimeWrapper $time
		$filter.endDate = $chtime
    }
	
	if ($userName) {
		$filter.userName = $userName
	}
	
	if ($role) {
		$filter.role = $role
	}
	
	if ($groupName) {
		$filter.groupName = $groupName
	}
	
	if ($operation) {
		$filter.operation = $operation
	}
	
	if ($target) {
		$filter.target = $target
	}
	
	if ($objectType) {
		$filter.objectType = $objectType
	}
	
	if ($sort) {
	    $sortOptions = @{"Time" = 'LOG_TIME';"UserName" = 'USER_NAME';"ObjectType" = 'OBJECT_TYPE';"Operation"='OPERATION'}
		$filter.sort = $sortOptions[$sort]
	}
	
	if ($direction) {
		$filter.direction = $direction
	}
	
	$filter.maxRows = $maxRows	
	$activities = @()
    $res = $clnt.reportActivity($filter,[ref]$activities)
	$res.entries |ft -Property logTime,username,roleUsed,target,operation,failed
	$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error arised: $errDescrption"
}