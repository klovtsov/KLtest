<#
    Modifies the PSM details of an existing account.
    .EXAMPLE
        updatePSMAccount -tpamAddress 10.30.44.206 -keyFile ab10test_user1_api -apiUser ab10test_user1_api
	     -systemName mySystem -accountName myAccount -enableFlag Y -proxyType 
		 "SSH - Automatic Login Using Password" -minApprovers 1
  #>
  param(
	$tpamAddress,$keyFile,$apiUser,
	$systemName,$accountName,
	$clipboardFlag,$CLIAccountName,$CLIDomainName,$CLISystemName,$colorDepth,
	$connectionProfile,$consoleFlag,$DSSKey,$DSSKeyName,$DSSKeyType,[Int32]$DefaultSessionDuration,
	$domainAccount,$enableFlag,$escalationEmail,[Int32]$escalationTime,$fileTransAuthMethod,
	$fileTransDownFlag,$fileTransPath,$fileTransType='DIS',$fileTransUp,[Int32]$maxSessionCount,
	$minApprovers,[Int32]$notifyFrequency,[Int32]$notifyThreshold,$PARCLIUserName,
	$passwordMethod,$postSessionProfile,$proxyType,$recordingRequiredFlag,
	[Int32]$reviewCount,$reviewerName,$reviewerType,$sessionStartNotifyEmail
	)

$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
	$params = New-Object -TypeName eDMZ.ParApi.UpdatePsmAccountParms
	#region Set psm account properties

	if ($clipboardFlag) {$params.clipboardFlag = Set-Flag $clipboardFlag}

	if ($CLIAccountName) {
		$params.cliAccountName = $CLIAccountName
	}
	if ($CLIDomainName) {
		$params.cliDomainName = $CLIDomainName
	}
	if ($CLISystemName) {
		$params.cliSystemName = $CLISystemName
	}

	if ($colorDepth) {
		$params.colorDepth = $colorDepth
	}

	if ($connectionProfile) {
		$params.connectionProfile = $connectionProfile
	}
	 if ($consoleFlag) {
		$params.consoleFlag = Set-Flag $consoleFlag
	}

	if ($DSSKey) {
		$params.dssKey = $DSSKey
	}

	if ($DSSKeyName) {
		$params.dssKeyName = $DSSKeyName
	}
	# Standard or Specific
	if ($DSSKeyType) {
		$params.dssKeyType = $DSSKeyType
	}

	if ($DefaultSessionDuration) {
		$params.defaultSessionDuration = $DefaultSessionDuration
	}

	if ($domainAccount) {
		$params.domainAccount = $domainAccount
	}

	if ($enableFlag) {
		$params.enableFlag = Set-Flag $enableFlag
	}

	if ($escalationEmail) {
		$params.escalationEmail = $escalationEmail
	}

	if ($escalationTime) {
		$params.escalationTime = $escalationTime
	}
	#Choices are: Same or Prompt
	if ($fileTransAuthMethod) {
		$params.fileTransAuthMethod = $fileTransAuthMethod
	}
	if ($fileTransDownFlag) {
	    $params.fileTransDownFlag = Set-Flag $fileTransDownFlag	
	}

	if ($fileTransPath) {
		$params.fileTransPath = $fileTransPath
	}
    #There is a default value: DIS
		$params.fileTransType = $FileTransType
	if ($fileTransUp) {
		$params.fileTransUpFlag = Set-Flag $fileTransUp
	}

	if ($maxSessionCount) {
		$params.maxSessionCount = $maxSessionCount
	}

	if ($minApprovers -ne $null) {
		$params.minApprovers = $minApprovers
	}
	if ($notifyFrequency) {
		$params.notifyFrequency = $notifyFrequency
	}

	if ($notifyThreshold) {
		$params.notifyThreshold = $notifyThreshold
	}

	if ($PARCLIUserName) {
		$params.parCliUserName = $PARCLIUserName
	}

	if ($passwordMethod) {
		$params.passwordMethod = $passwordMethod
	}

	if ($postSessionProfile) {
		$params.postSessionProfile = $postSessionProfile
	}

	if ($proxyType) {
		$params.proxyType = $proxyType
	}

	if ($recordingRequiredFlag) {
		$params.recordingRequiredFlag = Set-Flag $recordingRequiredFlag
	}
	if ($reviewCount) {
		$params.reviewCount = $reviewCount
	}

	if ($reviewerName) {
		$params.reviewerName = $reviewerName
	}

	if ($reviewerType) {
		$params.reviewerType = $reviewerType
	}

	if ($sessionStartNotifyEmail) {
		$params.sessionStartNotifyEmail = $sessionStartNotifyEmail
	}
	#endregion
	# Execute the operation on TPAM.
	$result = $clnt.updatePsmAccount($systemName,$accountName,$params)
	# Check the outcome of the operation.
	"updatePsmAccount: rc = " + $result.returnCode + ", message =" + $result.message
	$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error arises: $errDescrption"
}