<#
    .SYNOPSIS 
      Modifies an existing account.
    .EXAMPLE
     addAccount -tpamAddress 10.30.44.206 -keyFile ab10test_user1_api -apiUser ab10test_user1_api
	     -systemName mySystem -accountName myAccount -Description "Test account"
    .NOTES
	    The API user must have ISA or Administrator privilege.
	    For more information about parameters see TPAM documetation.
	.ChangeTime
	    Time of day at which the change agent will schedule password changes on
		this system. Can be set by following formats:
		 YYYY,MM,DD
		 hh,mm
		 hh,mm,ss,ms
		 YYYY,MM,DD,hh,mm,ss,ms
	.NextChangeDate
	    Set the next scheduled change date for this account. The format is same one
		as for ChangeTime parameter
	.NOTES
	    Obsolete parameters: ChangeFrequency,ChangeTime,NextChangeDate,CheckFlag,
		ResetFlag,ReleaseChangeFlag,BlockAutoChangeFlag
		These parameters will be accepted w/o complaint, but ignored
  #>
param(
	$tpamAddress,$keyFile,$apiUser,
	$systemName = 'testSystem',
	$accountName = 'testAccount',
	$AllowISADurationFlag,$AutoFlag,$AccountDN,$BlockAutoChangeFlag,[int32]$ChangeFrequency,
	$ChangeTime,$CheckFlag,$ChangeServiceFlag,$ChangeTaskFlag,$custom1,$custom2,
	$custom3,$custom4,$custom5,$custom6,$Description,$DomainAccountName,
	$EscalationEmail,[int32]$EscalationTime,$IgnoreSystemPoliciesFlag,$LockFlag,
	[Int32]$MaxReleaseDuration,$MinimumApprovers,$multiGroupApproverNames,$NextChangeDate,
	$OverrideAccountability,$Password,$PasswordRule,$ReleaseNotifyEmail,$ReleaseChangeFlag,
	[Int32]$ReleaseDuration,$RequireTicketForAPI,$RequireTicketForCLI,$RequireTicketForISA,
	$RequireTicketForRequest,$ResetFlag,$RestartServiceFlag,[int32]$ReviewCount,
	$ReviewerName,$ReviewerType,[Int32]$SimulPrivAccReleases,$TicketSystemName,
	$TicketEmailNotify,$UseSelfFlag,$passwordChangeProfile,$passwordCheckProfile,
	$RequireTicketForPSM,$EnableBeforeReleaseFlag
	)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
	$account = New-Object eDMZ.ParApi.Account
	
	#region Set account properties
	#systemName and accountName is required parameters
	$account.systemName = $systemName
	$account.accountName = $accountName
	
	if ($Description) {
		$account.description = $Description
	}

	if ($AllowISADurationFlag) {
	    $account.allowISADurationFlag = Set-Flag $AllowISADurationFlag
	}

	#Account Password Management type.
	if ($AutoFlag) {
		$account.accountAutoFl = $AutoFlag
	}

	if ($AccountDN) {
	    $account.accountDN = $AccountDN
    }
	
	if($BlockAutoChangeFlag){
	    $account.blockAutoChangeFlag = Set-Flag $BlockAutoChangeFlag
	}

	if ($ChangeFrequency -ne $null) {
		$account.changeFreq = $ChangeFrequency
	}

	if ($ChangeTime) {
		$time = @()
		$ChangeTime.Split(',')|%{$time+=[int]$_}
	    $chtime = New-Object eDMZ.ParApi.DateTimeWrapper $time
		$account.changeTime = $chtime
	}

	if ($CheckFlag) {
		$account.checkFl = Set-Flag $CheckFlag
	}

	if ($ChangeServiceFlag) {
		$account.chgSvcFl = Set-Flag $ChangeServiceFlag
	}

	if ($ChangeTaskFlag) {
		$account.changeTaskFlag = Set-Flag $ChangeTaskFlag
	}

	if ($custom1) {
		$account.custom1 = $custom1
	}

	if ($custom2) {
		$account.custom2 = $custom2
	}

	if ($custom3) {
		$account.custom3 = $custom3
	}

	if ($custom4) {
		$account.custom4 = $custom4
	}

	if ($custom5) {
		$account.custom5 = $custom5
	}

	if ($custom6) {
		$account.custom6 = $custom6
	}

	if ($DomainAccountName) {
		$account.domainAcctName = $DomainAccountName
	}
	if ($EnableBeforeReleaseFlag) {
       $account.enableBeforeReleaseFlag = Set-Flag $EnableBeforeReleaseFlag
	}

	if ($EscalationEmail) {
		$account.escalationEmail = $EscalationEmail
	}

	if ($EscalationTime) {
		$account.escalationTime = $EscalationTime
	}

	if ($IgnoreSystemPoliciesFlag) {
		$account.ignoreSystemPoliciesFlag = Set-Flag $IgnoreSystemPoliciesFlag
	}

	if ($LockFlag) {
		$account.lockFlag = Set-Flag $LockFlag
	}

	if ($MaxReleaseDuration) {
		$account.maxReleaseDuration = $MaxReleaseDuration
	}

	if ($MinimumApprovers) {
		$account.minApprovers = $MinimumApprovers
	}

	if ($multiGroupApproverNames) {
		$account.multiGroupApproverNames = $multiGroupApproverNames
	}

	if($NextChangeDate){
	    $date = @()
		$NextChangeDate.Split(',')|%{$date+=[int]$_}
	    $nextchdate = New-Object eDMZ.ParApi.DateTimeWrapper $date
	    $account.nextChangeDt = $nextchdate
	}

	if ($OverrideAccountability) {
		$account.overrideAccountability = Set-Flag $OverrideAccountability
	}

	if ($Password) {
		$account.password = $Password
	}

	if ($PasswordRule) {
		$account.passwordRule = $PasswordRule
	}

	if ($ReleaseNotifyEmail) {
		$account.relNotifyEmail = $ReleaseNotifyEmail
	}

	if ($ReleaseChangeFlag) {
		$account.releaseChgFl = Set-Flag $ReleaseChangeFlag
	}

	if ($ReleaseDuration) {
		$account.releaseDuration = $ReleaseDuration
	}
	if ($RequireTicketForRequest) {
	    $account.requireTicketForRequest = Set-Flag $RequireTicketForRequest
	}
	if ($RequireTicketForPSM) {
	    $account.requireTicketForPSM = Set-Flag $RequireTicketForPSM
	}
	#Ignored if RequireTicketForRequest is N.
	if ($RequireTicketForAPI) {
		if ($RequireTicketForRequest -ne 'N') {
			$account.requireTicketForAPI = Set-Flag $RequireTicketForAPI
			}
		}
	#Ignored if RequireTicketForRequest is N.
	if ($RequireTicketForCLI) {
		if ($RequireTicketForRequest -ne 'N') {
			$account.requireTicketForCLI = Set-Flag $RequireTicketForCLI
		    }
		}
	#Ignored if RequireTicketForRequest is N.
	if ($RequireTicketForISA) {
		if ($RequireTicketForRequest -ne 'N') {
			$account.requireTicketForISA = Set-Flag $RequireTicketForISA
			}
		}

	if ($ResetFlag) {
		$account.resetFl = Set-Flag $ResetFlag
	}

	if ($RestartServiceFlag) {
		$account.restartServiceFlag = Set-Flag $RestartServiceFlag
	}

	if ($ReviewCount) {
		$account.reviewCount = $ReviewCount
	}

	if ($ReviewerName) {
		$account.reviewerName = $ReviewerName
	}

	if ($ReviewerType) {
		$account.reviewerType = $ReviewerType
	}

	if ($SimulPrivAccReleases) {
		$account.simulPrivAccReleases = $SimulPrivAccReleases
	}

	if ($TicketSystemName) {
		$account.ticketSystemName = $TicketSystemName
	}

	if ($TicketEmailNotify) {
		$account.ticketEmailNotify = $TicketEmailNotify
	}

	if ($UseSelfFlag) {
		$account.useSelfFl = Set-Flag $UseSelfFlag
	}
	#Profiles
	if ($passwordChangeProfile){
	    $account.passwordChangeProfile = $passwordChangeProfile
	}
	if ($passwordCheckProfile){
	    $account.passwordCheckProfile = $passwordCheckProfile
	}

	#endregion
	# Execute the operation on TPAM.
	$result = $clnt.updateAccount($account)
	# Check the outcome of the operation.
	"updateAccount: rc = " + $result.returnCode + ", message =" + $result.message
	$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error arises: $errDescrption"
}