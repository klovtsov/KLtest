#Lists groups and group members, specified by group name or member name, or GroupID.
param(
	$tpamAddress,$keyFile,$apiUser,
	$groupName,[Int32]$groupID,$userName 
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
	#region Set filtering options
	$filter = New-Object eDMZ.ParApi.GroupFilter
	if ($groupName) {
		$filter.groupName = $groupName
	}
	if ($groupID) {
		$filter.groupID = $groupID
	}
	if ($userName) {
		$filter.userName = $userName
	}

	#endregion
	$groups = @()
	$res = $clnt.listGroups($filter,[ref]$groups)
	$res.entries|ft -Property groupName,groupID,groupDesc
	$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error occured: $errDescrption"
}