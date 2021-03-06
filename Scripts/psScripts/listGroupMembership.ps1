#Lists group name and username for all groups, specified groups, or specified users.
param(
	$tpamAddress,$keyFile,$apiUser,
	$groupName,[Int32]$groupID,$userName,[Int32]$maxRows 
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
	$groupMembership = @()
	#region Set filtering options
	$filter = New-Object eDMZ.ParApi.GroupMembershipFilter
	if ($groupName) {
		$filter.groupName = $groupName
	}
	if ($groupID) {
		$filter.groupID = $groupID
	}
	if ($userName) {
		$filter.userName = $userName
	}
	if ($maxRows) {
		$filter.maxRows = $maxRows
	}
	#endregion

	$res = $clnt.listGroupMembership($filter,[ref]$groupMembership)
	$res.entries|ft -Property groupName,userName
	$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error arises: $errDescrption"
}