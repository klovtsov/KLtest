<#
    .SYNOPSIS
	    Adds a new system account.
	.SYNTAX
	    updateDependentSystems.ps1 -systemName <String> -accountName <String> [-assign <String>] [-unassign <String>]
    .EXAMPLE
        updateDependentSystems.ps1 -systemName dc1 -accountName testAcc -assign member1 -unassign member2;member3
    .NOTES
	    To assign or unassign list of systems use semi-colon separated list
  #>
param(
	$tpamAddress,$keyFile,$apiUser,
	$systemName,$accountName,$assign,$unassign
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
	$parms = New-Object eDMZ.ParApi.UpdateDependentSystemsParms
    if ($assign) {
		$parms.assign = $assign
	}
	if ($unassign) {
		$parms.unassign = $unassign
	}

	# Execute the operation on TPAM.
	$result = $clnt.updateDependentSystems($systemName,$accountName,$parms)
	# Check the outcome of the operation.
	"updateDependentSystems: rc = " + $result.returnCode + ", message =" + $result.message
	$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error arises: $errDescrption"
}