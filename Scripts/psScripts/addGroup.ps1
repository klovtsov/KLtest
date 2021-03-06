#Creates a new group.
param(
    $tpamAddress,$keyFile,$apiUser,
    $groupName,
	$description
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

# Execute the operation on TPAM.
$result = $clnt.addGroup($groupName,$description)
# Check the outcome of the operation.
"addGroup: rc = " + $result.returnCode + ", message =" + $result.message
$clnt.disconnect()