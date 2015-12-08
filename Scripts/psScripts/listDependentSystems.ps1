<#
    .SYNOPSIS
	    Lists status of systems (dependent or not dependent) for a specific account.
	.SYNTAX
	    listDependentSystems.ps1 -systemName <String> -accountName <String> 
		    [-DependentStatus Both|Dependent|Not_Dependent] [-DependentName <String>]
			[-MaxRows <int32>]
    .EXAMPLE
        listDependentSystems.ps1 -systemName dc1 -accountName testAcc -DependentStatus Dependent
  #>
param(
	$tpamAddress,$keyFile,$apiUser,
	[string]$systemName,[string]$accountName,
	[string]$DependentStatus, [string]$DependentName,[int32]$maxRows
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

#region Set filtering options
try {
	$filter = New-Object eDMZ.ParApi.DependentSystemFilter
    
	if ($DependentStatus) {
		$filter.dependentStatus = $DependentStatus
	}
	if ($DependentName) {
		$filter.dependentName = $DependentName
	}
	if ($maxRows) {
		$filter.maxRows = $maxRows
	} else {
		$filter.maxRows = 25
	}
} catch {
    $errDescrption = $Error[0].Exception.Message
	Write-Output "Error arises: $errDescrption"
}
#endregion
$systems = @()
$res = $clnt.listDependentSystems($systemName,$accountName,$filter,[ref]$systems)
$res.entries|ft -Property systemName,status
$clnt.disconnect()