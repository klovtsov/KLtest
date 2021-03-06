#Lists collections and collection members, specified by collection name or system name.
param(
	$tpamAddress,$keyFile,$apiUser,
	$collectionName,[string]$systemName,
	[string]$accountName,[string]$fileName,[int]$maxRows=0,
	[string[]]$propsToList = @('collectionName','collectionDesc')
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"
try {	
	#region Set filtering options
	$filter = New-Object eDMZ.ParApi.CollectionFilter
	if ($systemName) {
		$filter.systemName = $systemName
	}
	if ($collectionName) {
	    $filter.collectionName = $collectionName
	}
	if ($accountName) {
	    $filter.accountName = $accountName
	}
	if ($fileName) {
	    $filter.fileName = $fileName
	}
	$filter.maxRows = $maxRows
	#endregion
	$collections = @()
	$res = $clnt.listCollections($filter,[ref]$collections)
	
	# Script can be called from command line. In this case only arrayy is passed as string
    if($propsToList.count -eq 1){
       $propsToList=$propsToList[0].Split(',')
    }
	
	$res.entries|ft -Property $propsToList
	
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error occured: $errDescrption"
} finally{
    $clnt.disconnect()
}