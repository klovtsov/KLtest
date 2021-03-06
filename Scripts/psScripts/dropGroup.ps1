#Deletes an existing group.
param(
	$tpamAddress,$keyFile,$apiUser,
	$groupName,$groupID 
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

# Execute the operation on TPAM.
if ($groupID) {
	$result = $clnt.dropGroup($groupID)
} else {
    $result = $clnt.dropGroup($groupName)
}
# Check the outcome of the operation.
"dropGroup: rc = " + $result.returnCode + ", message =" + $result.message
$clnt.disconnect()