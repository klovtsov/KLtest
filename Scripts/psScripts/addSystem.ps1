<#
    .SYNOPSIS 
      Adds managed system to TPAM
    .EXAMPLE
     addSystem -tpamAddress 10.30.44.206 -keyFile ab10test_user1_api -apiUser ab10test_user1_api
	     -systemName abTruba -systemAddress 10.30.46.172 -platform Linux -description "Test system for powershell" -timeout 60 -functAcct root -functAcctPwd master
    .Notes
	    Obsolete parameters: ChangeFrequency,ChangeTime,CheckFlag,ResetFlag,ReleaseChangeFlag,BlockAutoChnageFlag.
		These parameters will be accepted w/o complaint, but ignored
  #>
param(
	$tpamAddress,$keyFile,$apiUser,
	$systemName = 'testSystem',
	$systemAddress = '1.2.3.4',
	$platform,
	$description,
	[int]$timeout,$functAcct,$functAcctPwd,[int]$ChangeFrequency,
	$AllowFuncReqFlag, $AllowISADurationFlag,$AlternateIP,
	$AutoDiscoveryExcludeList,$AutoDiscoveryProfile,[int]$AutoDiscoveryTimeout,$ChangeTime,$custom1,
	$custom2,$custom3,$custom4,$custom5,$custom6,$CheckFlag,$DomainFuncAccount,
	$DomainName,[switch]$EGPOnlyFlag,$EnablePassword,$EscalationEmail,[int]$EscalationTime,
	$LineDef,[int]$MaxReleaseDuration,$NetBiosName,$NonPrivFuncFlag,$OracleSIDSN,
	$OracleType,$PasswordRule,$PlatSpecificValue,$PortNumber,$PrimaryEmail,
	$ReleaseChangeFlag,[int]$ReleaseDuration,$RequireTicketForAPI,$RequireTicketForCLI,
	$RequireTicketForISA,$RequireTicketForRequest,$ResetFlag,$SSHAccount,$SSHKey,
	[Int32]$SSHPort,$SystemAutoFlag,$TemplateSystemName,$TicketEmailNotify,$TicketSystemName,
	$UseSslFlag,$UseSshFlag,$passwordChangeProfile,$passwordCheckProfile,$funcAcctDN,
	$RequireTicketForPSM,$PSMDPAAffinity,$PPMDPAAffinity
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
	$system = New-Object eDMZ.ParApi.EDMZSystem
#region Set system parameters
$system.systemName = $systemName
if($systemAddress){
    $system.networkAddress = $systemAddress
}
if ($platform) {
	$system.platformName = $platform
}
if($description){
    $system.description = $description
}
if ($functAcct) {
	$system.functionalAccount = $functAcct
}

if ($functAcctPwd) {
	$system.functionalAcctCredentials = $functAcctPwd
}
if($timeout){
   $system.timeout=$timeout
}
#THIS OPTION IS OBSOLETE AND WILL BE REMOVED IN A FUTURE RELEASE.
#The functionality of the option has been assumed by the Password Change Profile.
if($ChangeFrequency){
    $system.changeFreq = $ChangeFrequency
}

if ($AllowFuncReqFlag) {
    $system.allowFuncReqFl = Set-Flag $AllowFuncReqFlag	
}

if ($AllowISADurationFlag) {
	$system.allowISADurationFlag = Set-Flag $AllowISADurationFlag
}

if ($AlternateIP) {
    $system.alternateIP = $AlternateIP	
}

if ($AutoDiscoveryExcludeList) {
	$system.autoDiscoveryExcludeList = $AutoDiscoveryExcludeList
}

if ($AutoDiscoveryProfile) {
	$system.autoDiscoveryProfile = $AutoDiscoveryProfile
}
#THIS OPTION IS OBSOLETE AND WILL BE REMOVED IN A FUTURE RELEASE.
#The functionality of the option has been assumed by the Password Change Profile.
if ($ChangeTime) {
    $time = @()
	$ChangeTime.Split(',')|%{$time+=[int]$_}
    $chtime = New-Object eDMZ.ParApi.DateTimeWrapper $time
	$system.changeTime = $chtime
}

if ($custom1) {
	$system.custom1 = $custom1
}
if ($custom2) {
	$system.custom2 = $custom2
}

if ($custom3) {
	$system.custom3 = $custom3
}
if ($custom4) {
	$system.custom4 = $custom4
}
if ($custom5) {
	$system.custom5 = $custom5
}
if ($custom6) {
	$system.custom6 = $custom6
}
#THIS OPTION IS OBSOLETE AND WILL BE REMOVED IN A FUTURE RELEASE.
#The functionality of the option has been assumed by the Password Change Profile.
#Schedule accounts on the system for a regular password check. By default Y.
if ($CheckFlag) {
    $system.checkFl = Set-Flag $CheckFlag
}
 if ($DomainFuncAccount) {
	$system.domainFunctionalAccount = $DomainFuncAccount
}
 if ($DomainName) {
	$system.domainName = $DomainName
}
if ($EGPOnlyFlag) {
	$system.egpOnlyFl = Set-Flag $EGPOnlyFlag
}
#Password to use for the “ENABLE” account (Cisco platforms only).
if ($EnablePassword) {
    $system.enablePassword = $EnablePassword
}

if ($EscalationEmail) {
	$system.escalationEmail = $EscalationEmail
}

if ($EscalationTime) {
	$system.escalationTime = $EscalationTime
}
#Cisco telnet attribute.
if ($LineDef) {
	$system.lineDef = $LineDef
}
if ($MaxReleaseDuration) {
	$system.maxReleaseDuration = $MaxReleaseDuration
}
#Required for Windows AD or SPCW (DC) platforms.
if ($NetBiosName) {
	$system.netBIOSName = $NetBiosName
}
if ($NonPrivFuncFlag) {
	$system.nonPrivFuncFl = Set-Flag $NonPrivFuncFlag
}
# Either the SID or Service Name (as indicated in the OracleType option) used to
# connect to the Oracle system.
if ($OracleSIDSN) {
	$system.oracleSIDSN = $OracleSIDSN
}
# Only accepted for Oracle platform.
if ($OracleType) {
	$system.oracleType = $OracleType
}
if ($PasswordRule) {
	$system.passwordRule = $PasswordRule
}
if ($PlatSpecificValue) {
	$system.platSpecificValue = $PlatSpecificValue
}
if ($PortNumber) {
	$system.portNumber = $PortNumber
}
if ($PrimaryEmail) {
	$system.primaryEmail = $PrimaryEmail
}
#THIS OPTION IS OBSOLETE AND WILL BE REMOVED IN A FUTURE RELEASE.
#The functionality of the option has been assumed by the Password Change Profile.
#Change the password after any ISA, CLI, or API release. Yes by default.
if ($ReleaseChangeFlag -eq 'N') {
	$system.releaseChgFl = [eDMZ.ParApi.Flag]::N
}
if ($ReleaseDuration) {
	$system.releaseDuration = $ReleaseDuration
}

if ($RequireTicketForRequest) {
    $system.requireTicketForRequest = Set-Flag $RequireTicketForRequest
}
#Ignored if RequireTicketForRequest is N.
if ($RequireTicketForAPI) {
	if ($RequireTicketForRequest -ne 'N') {
		$system.requireTicketForAPI = Set-Flag $RequireTicketForAPI
		}
	}
#Ignored if RequireTicketForRequest is N.
if ($RequireTicketForCLI) {
	if ($RequireTicketForRequest -ne 'N') {
		$system.requireTicketForCLI = Set-Flag $RequireTicketForCLI
	    }
	}
#Ignored if RequireTicketForRequest is N.
if ($RequireTicketForISA) {
	if ($RequireTicketForRequest -ne 'N') {
		$system.requireTicketForISA = Set-Flag $RequireTicketForISA
		}
	}
if ($RequireTicketForPSM) {
    $system.requireTicketForPSM = Set-Flag $RequireTicketForPSM
}
if ($ResetFlag -eq 'N') {
	$system.resetFl = [eDMZ.ParApi.Flag]::N
}
if ($SSHAccount) {
	$system.sshAccount = $SSHAccount
}

#Either “Standard” to use the appliance's system standard keys or “Specific” to 
#generate a specific key for this system. “Standard” is the default.
if ($SSHKey -eq 'Specific') {
	$system.sshKey = 'Specific'
}

if ($SSHPort) {
	$system.sshPort = $SSHPort
}

if ($SystemAutoFlag) {
	$system.systemAutoFl = Set-Flag $SystemAutoFlag
}
if ($TemplateSystemName) {
	$system.templateSystemName = $TemplateSystemName
}

if ($TicketEmailNotify) {
	$system.ticketEmailNotify = $TicketEmailNotify
}

if ($TicketSystemName) {
	$system.ticketSystemName = $TicketSystemName
}
if ($UseSslFlag) {
	$system.useSslFl = Set-Flag $UseSslFlag
}

if ($UseSshFlag) {
	$system.useSshFlag = Set-Flag $UseSshFlag
}
#Profiles
if ($passwordChangeProfile){
    $system.passwordChangeProfile = $passwordChangeProfile
}
if ($passwordCheckProfile){
    $system.passwordCheckProfile = $passwordCheckProfile
}
if($funcAcctDN){
	$system.funcAcctDN = $funcAcctDN
}
if($AutoDiscoveryTimeout -gt 0){
    $system.autoDiscoveryTimeout = $AutoDiscoveryTimeout
}
if($PSMDPAAffinity){
   $system.psmDpaAffinity = $PSMDPAAffinity
}
if($PPMDPAAffinity){
   $system.ppmDpaAffinity = $PPMDPAAffinity
}

#endregion
# Execute the operation on TPAM.
$result = $clnt.addSystem($system)
# Check the outcome of the operation.
"addSystem: rc = " + $result.returnCode + ", message =" + $result.message
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error occured: $errDescrption"
}finally{
    $clnt.disconnect()
}