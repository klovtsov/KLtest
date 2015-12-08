<#
    Users can create a session request for themselves as well as other users.
    .EXAMPLE
     addSessionRequest.ps1 -tpamAddress 10.30.44.206 -keyFile ab10test_user1_api
	    -apiUser ab10test_user1_api -systemName mySystem -accountName myAccount 
		-forUserName testWebUser
    .NOTES
	    The target user must be a web-based user,i.e., not a CLI or API user.
	.RequestedReleaseDate
	    Required if RequestImmediate option is N.
	    Can be set by following formats:
		 YYYY,MM,DD
		 hh,mm
		 hh,mm,ss,ms
		 YYYY,MM,DD,hh,mm,ss,ms
  #>
param(
    $tpamAddress,$keyFile,$apiUser,
	$systemName,$accountName,$requestNotes,$forUserName,
	$accessPolicyName,$commandName,$reasonCode,$requestImmediateFlag,$requestedReleaseDate,
	[int32]$releaseDuration = 120,$ticketNumber,$ticketSystemName
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"
try {
#region Set request properties
$parms = New-Object -TypeName eDMZ.ParApi.AddSessionRequestParms

$parms.releaseDuration = $releaseDuration
if ($accessPolicyName) {
	$parms.accessPolicyName = $AccessPolicyName
}
if ($commandName) {
	$parms.commandName = $commandName
}

if ($reasonCode) {
	$parms.reasonCode = $reasonCode
}

if ($requestImmediateFlag) {
	$parms.requestImmediateFlag = Set-Flag $requestImmediateFlag
}

if ($requestedReleaseDate) {
    $time = @()
	$requestedReleaseDate.Split('/')|%{$time+=[int]$_}
    $reqtime = New-Object eDMZ.ParApi.DateTimeWrapper $time
	$parms.requestedReleaseDate = $reqtime
}

if ($ticketNumber) {
	$parms.ticketNumber = $ticketNumber
}

if ($ticketSystemName) {
	$parms.ticketSystemName = $ticketSystemName
}
#endregion

# Execute the operation on TPAM.
if ($forUserName) {
	$idresult = $clnt.addSessionRequest($systemName,$accountName,$forUserName,$requestNotes,$parms)
} else {
    $idresult = $clnt.addSessionRequest($systemName,$accountName,$requestNotes,$parms)
}
# Check the outcome of the operation.
$idresult.message
$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error arises: $errDescrption"
}