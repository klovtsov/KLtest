# Deletes an existing collection.
param(
	$tpamAddress,$keyFile,$apiUser,
	$collectionName 
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

#endregion
# Execute the operation on TPAM.
$result = $clnt.dropCollection($collectionName)
# Check the outcome of the operation.
"dropCollection: rc = " + $result.returnCode + ", message =" + $result.message
$clnt.disconnect()