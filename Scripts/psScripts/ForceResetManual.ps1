#Forces a password change for the specified manual management account.
param(
    $tpamAddress,$keyFile,$apiUser,
    $systemName,
	$accountName
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

# Execute the operation on TPAM.
 $result = $clnt.forceResetManual($systemName,$accountName)
# Check the outcome of the operation.
if(!$result.returnCode){
    $result.id + " " + $result.message
}else{
	"forceResetManual: " + $result.message
}
$clnt.disconnect()