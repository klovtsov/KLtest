#Removes an existing user account from one or more groups.
param(
	$tpamAddress,$keyFile,$apiUser,
	$groupName,[int]$groupID,$userName 
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

#endregion
# Execute the operation on TPAM.
if ($groupID) {
	$result = $clnt.dropGroupMember($userName,$groupID)
} else {
    $result = $clnt.dropGroupMember($userName,$groupName)
}
# Check the outcome of the operation.
"dropGroupMember: rc = " + $result.returnCode + ", message =" + $result.message
$clnt.disconnect()