#Adds an existing user to group.
param(
    $tpamAddress,$keyFile,$apiUser,
    $userName,
	$groupName,
	[int]$groupID
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
	# Execute the operation on TPAM.
	if ($groupName) {
		$result = $clnt.addGroupMember($userName,$groupName)
	} else {
	    $result = $clnt.addGroupMember($userName,$groupID)
	}
	# Check the outcome of the operation.
	"addGroupMember: rc = " + $result.returnCode + ", message =" + $result.message
	$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error occured: $errDescrption"
}