<#
    Users can create a password request for themselves as well as other users.
    .EXAMPLE
     addPwdRequest.ps1 -tpamAddress 10.30.44.206 -keyFile ab10test_user1_api -apiUser ab10test_user1_api
	     -systemName mySystem -accountName myAccount -requestNotes test
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
	$systemName,$accountName,$RequestNotes,$ForUserName,
	$AccessPolicyName,$ReasonCode,$RequestImmediateFlag,$RequestedReleaseDate,
	[int32]$ReleaseDuration = 120,$TicketNumber,$TicketSystemName
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {

#region Set request properties
	$parms = New-Object -TypeName eDMZ.ParApi.AddPwdRequestParms

	$parms.releaseDuration = $ReleaseDuration
	if ($AccessPolicyName) {
		$parms.accessPolicyName = $AccessPolicyName
	}

	if ($ReasonCode) {
		$parms.reasonCode = $ReasonCode
	}

	if ($RequestImmediateFlag) {
		$parms.requestImmediateFlag = Set-Flag $RequestImmediateFlag
	}

	if ($RequestedReleaseDate) {
	    $time = @()
		$RequestedReleaseDate.Split('/')|%{$time+=[int]$_}
	    $reqtime = New-Object eDMZ.ParApi.DateTimeWrapper $time
		$parms.requestedReleaseDate = $reqtime
	}

	if ($TicketNumber) {
		$parms.ticketNumber = $TicketNumber
	}

	if ($TicketSystemName) {
		$parms.ticketSystemName = $TicketSystemName
	}
#endregion

	# Execute the operation on TPAM.
	if ($ForUserName) {
		$idresult = $clnt.addPwdRequest($systemName,$accountName,$ForUserName,$RequestNotes,$parms)
	} else {
	    $idresult = $clnt.addPwdRequest($systemName,$accountName,$RequestNotes,$parms)
	}
	# Check the outcome of the operation.
	$idresult.message
	$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error arises: $errDescrption"
}