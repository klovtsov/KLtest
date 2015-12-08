<#
    .SYNOPSIS 
      Modifies an existing collection.
    .EXAMPLE
        UpdateCollection --CollectionName collName [options]
  #>
param(
	$tpamAddress,$keyFile,$apiUser,
	$collectionName,
	$accountName = 'testAccount',$Description,$PSMDPAAffinity
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
	#endregion
	$updateParams = New-Object eDMZ.ParApi.UpdateCollectionParms
	if($Description){
	    $updateParams.description = $Description
	}
	if($PSMDPAAffinity){
	    $updateParams.psmDpaAffinity = $PSMDPAAffinity
	}
	# Execute the operation on TPAM.
	$result = $clnt.updateCollection($collectionName,$updateParams)
	# Check the outcome of the operation.
	"updateCollection: rc = " + $result.returnCode + ", message =" + $result.message
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error arises: $errDescrption"
}finally{
    $clnt.disconnect()
}