<#
    Lists access policies assigned to accounts, collections, files, groups, systems
	or users based on specified filter criteria.
	.NOTES
	    At least one of the following options must contain a non-wildcard value 
		in order to run this report: AccessPolicyName, AccountName, CollectionName,
		FileName, GroupName, SystemName, UserName.
#>
param(
	$tpamAddress,$keyFile,$apiUser,
	$AccessPolicyName,$AccountName,$AllorEfffectiveFlag,$systemName,$collectionName,
	[int]$ExpandCollectionFlag = 0,[int]$ExpandGroupFlag=0,[int]$ExpandPolicyFlag = 0,
	$FileName,$GroupName,[int]$MaxRows = 25,$PermissionName='ALL',$PermissionType = 'ALL',
	$SortOrder = 'UserName',$UserName,
	[string[]]$propsToList = @('username','groupName','accessPolicyName','systemName',
	        'accountName','fileName','collectionName')
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
	#region Set filtering options
	$filter = New-Object eDMZ.ParApi.PolicyFilter
	if ($systemName) {
		$filter.systemName = $systemName
	}
	if ($collectionName) {
		$filter.collectionName = $collectionName
	}
	if ($AccessPolicyName) {
		$filter.accessPolicyName = $AccessPolicyName
	}
	if ($AccountName) {
		$filter.accountName = $AccountName
	}
	if ($AllorEfffectiveFlag) {
		$filter.allOrEffectiveFlag = $AllorEfffectiveFlag
	}
	if ($FileName) {
		$filter.fileName = $FileName
	}
	if ($GroupName) {
		$filter.groupName = $GroupName
	}
	$filter.maxRows = $MaxRows
	$filter.permissionName = $PermissionName
	$filter.permissionType = $PermissionType
	
	$so = @{"UserName"='SORT_USER_NAME';"SystemName" = 'SORT_SYSTEM_NAME';
	    "AccountName" = 'SORT_ACCOUNT_NAME';"FileName" = 'SORT_FILE_NAME';
		"PolicyName" = 'SORT_POLICY_NAME';"GroupName" ='SORT_GROUP_NAME';
		"CollectionName"='SORT_COLLECTION_NAME'}
	$filter.sortOrder = $so[$SortOrder]
	
	if ($UserName) {
		$filter.userName = $UserName
	}
	
	$filter.expandCollectionFlag = [bool]$ExpandCollectionFlag
	$filter.expandGroupFlag = [bool]$ExpandGroupFlag
	$filter.expandPolicyFlag =[bool]$ExpandPolicyFlag
	#endregion
	$policies = @()
	$res = $clnt.listAssignedPolicies($filter,[ref]$policies)
	if($propsToList.count -eq 1){
       $propsToList=$propsToList[0].Split(',')
    }
	$res.entries|ft -AutoSize -Property $propsToList
	$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error occured: $errDescrption"
}