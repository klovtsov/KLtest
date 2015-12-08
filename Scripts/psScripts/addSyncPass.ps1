#Allows you to add a synchronized password.
param(
    $tpamAddress,$keyFile,$apiUser,
    $syncPassName, $ChangeFrequency, $ChangeTime, $CheckFlag, $DisableFlag,
	$Description,$NextChangeDate, $Password, $PasswordRule,$ReleaseNotifyEmail,
	$ReleaseChangeFlag,[Int32]$ReleaseDuration,$ResetFlag,$passwordChangeProfile,
	$passwordCheckProfile
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

#region Set sync password parameters
try {
    $syncPwdParams = New-Object eDMZ.ParApi.AddSyncPassParms
	
	if ($ChangeFrequency) {
		$syncPwdParams.changeFrequency = [Int32]$ChangeFrequency
	}
	
	if ($ChangeTime) {
	    $syncPwdParams.changeTime = create-datetimewrapper $ChangeTime
	}
	
	if ($CheckFlag) {
		$syncPwdParams.checkFlag = Set-Flag $CheckFlag
	}
	
	if ($DisableFlag) {
		$syncPwdParams.disableFlag = Set-Flag $DisableFlag
	}
	
	if ($Description) {
		$syncPwdParams.description = $Description
	}
	
	if ($NextChangeDate) {
		$syncPwdParams.nextChangeDate = create-datetimewrapper $NextChangeDate
	}
	
	if ($Password) {
		$syncPwdParams.password = $Password
	}
	
	if ($PasswordRule) {
		$syncPwdParams.passwordRule = $PasswordRule
	}
	
	if ($ReleaseNotifyEmail) {
		$syncPwdParams.releaseNotifyEmail = $ReleaseNotifyEmail
	}
	
	if ($ReleaseChangeFlag) {
		$syncPwdParams.releaseChangeFlag = Set-Flag $ReleaseChangeFlag
	}
	
	if ($ReleaseDuration) {
	    $syncPwdParams.releaseDuration = $ReleaseDuration		
	}
	
	if ($ResetFlag) {
		$syncPwdParams.resetFlag = Set-Flag $ResetFlag
	}
	if($passwordChangeProfile){
	   $syncPwdParams.passwordChangeProfile = $passwordChangeProfile
	}
	if($passwordCheckProfile){
	   $syncPwdParams.passwordCheckProfile = $passwordCheckProfile
	}else{
	    $syncPwdParams.accountLevelCheckProfile = 'Y'
	}
	
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error occured: $errDescrption"
}
#endregion
# Execute the operation on TPAM.
$result = $clnt.addSyncPass($syncPassName,$syncPwdParams)
# Check the outcome of the operation.
"addSyncPassword: rc = " + $result.returnCode + ", message =" + $result.message
$clnt.disconnect()