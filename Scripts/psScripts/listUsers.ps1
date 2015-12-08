#Lists users defined in TPAM.
param(
	$tpamAddress,$keyFile,$apiUser,
	$userName,$groupName,$emailAddress,$UserInterface = 'ALL',$UserType = 'ALL',
	$status = 'ALL',$SecondaryAuthType = 'ALL',
	$SortOrder = 'UserName',[int32]$maxRows = 25,
	$UserCustom1,$UserCustom2,$UserCustom3,$UserCustom4,$UserCustom5,$UserCustom6,
	[string[]]$propsToList = @('userName','usertype','userinterface')
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
	#region Set filtering options
	$filter = New-Object eDMZ.ParApi.UserFilter
	    $filter.userInterface = $UserInterface
	    $filter.userType = $UserType
	    $filter.status = $status
	    $filter.secondaryAuthType = $SecondaryAuthType
	  
		if ($groupName) {
			$filter.groupName = $groupName
		}
		if ($userName) {
			$filter.userName = $userName
		}
		if ($emailAddress) {
			$filter.emailAddress = $emailAddress
		}
	    
		$filter.sort = $SortOrder
	    $filter.maxRows = $maxRows
		#User custom fields
		if($UserCustom1){
		    $filter.userCustom1 = $UserCustom1
		}
		if($UserCustom2){
		    $filter.userCustom2 = $UserCustom2
		}
		if($UserCustom3){
		    $filter.userCustom3 = $UserCustom3
		}
		if($UserCustom4){
		    $filter.userCustom4 = $UserCustom4
		}
		if($UserCustom5){
		    $filter.userCustom5 = $UserCustom5
		}
		if($UserCustom6){
		    $filter.userCustom6 = $UserCustom6
		}
	#endregion
	$users = @()
	$res = $clnt.listUsers($filter,[ref]$users)
	# Script can be called from command line. In this case only arrayy is passed as string
    if($propsToList.count -eq 1){
       $propsToList=$propsToList[0].Split(',')
    }
	$res.entries|ft -AutoSize -Property $propsToList
	$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error arises: $errDescrption"
}
