#Initiates a system test.
param(
    $tpamAddress,$keyFile,$apiUser,
    $systemName
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

# Execute the operation on TPAM.
 $result = $clnt.testSystem($systemName)
# Check the outcome of the operation.
"testSystem: rc = " + $result.returnCode + ", message =" + $result.message
$clnt.disconnect()