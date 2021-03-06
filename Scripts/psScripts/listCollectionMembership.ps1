# Lists collection system, account, and file name for all collections, specified
# collections, or specified systems.
param(
	$tpamAddress,$keyFile,$apiUser,
	$collectionName,$systemName,[Int32]$maxRows,
	[string]$accountName,[string]$fileName,
	[string[]]$propsToList = @('collectionName','systemName','accountName')
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

$collectionMembership = @()
if ($maxRows) {
	$res = $clnt.listCollectionMembership($collectionName,$systemName,$maxRows,[ref]$collectionMembership)
} else {
    if($accountName -or $fileName){
	    $collectionMembershipFilter = New-Object eDMZ.ParApi.CollectionMembershipFilter
		if($accountName){
		   $collectionMembershipFilter.accountName = $accountName 
		}
		if($fileName){
		   $collectionMembershipFilter.fileName = $fileName
		}
		if($collectionName){
		   $collectionMembershipFilter.collectionName = $collectionName
		}
		if($maxRows){
		    $collectionMembershipFilter.maxRows = $maxRows
		}
	    $res = $clnt.listCollectionMembership($collectionMembershipFilter,[ref]$collectionMembership)
	}else{
    $res = $clnt.listCollectionMembership($collectionName,$systemName,[ref]$collectionMembership)
}
}
# Script can be called from command line. In this case only arrayy is passed as string
if($propsToList.count -eq 1){
    $propsToList=$propsToList[0].Split(',')
}
$res.entries|ft -AutoSize -Property $propsToList
$clnt.disconnect()