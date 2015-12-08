<#
    .SYNOPSIS 
      Modifies an existing system.
    .EXAMPLE
     updateSystem.ps1 -tpamAddress 10.30.44.206 -keyFile ab10test_user1_api -apiUser ab10test_user1_api
	     -systemName abTruba -networkAddress 10.30.46.172 -description "New description"
    .PARAMETER ChangeTime
	    Time of day at which the change agent will schedule password changes on
		this system. Can be set by following formats:
		 YYYY,MM,DD
		 hh,mm
		 hh,mm,ss,ms
		 YYYY,MM,DD,hh,mm,ss,ms
    .Notes
	    Obsolete parameters: ChangeFrequency,ChangeTime,CheckFlag,ResetFlag,ReleaseChangeFlag,BlockAutoChnageFlag
		These parameters will be accepted w/o complaint, but ignored
  #>
param(
	$tpamAddress,$keyFile,$apiUser,
	$systemName = 'testSystem',$newSystemName,$networkAddress, $platform, $description,
	$timeout,$functAcct,$functAcctPwd,$ChangeFrequency,
	$AllowFuncReqFlag, $AllowISADurationFlag,$AlternateIP,
	$AutoDiscoveryExcludeList,$AutoDiscoveryProfile,[int]$AutoDiscoveryTimeout,
	$ChangeTime,$custom1,$custom2,$custom3,$custom4,$custom5,$custom6,$CheckFlag,
	$DomainFuncAccount,	$DomainName,[switch]$EGPOnlyFlag,$EnablePassword,$EscalationEmail,
	[int]$EscalationTime,$funcAcctDN,$LineDef,[int]$MaxReleaseDuration,$NetBiosName,
	$NonPrivFuncFlag,$OracleSIDSN,$OracleType,$PasswordChangeProfile,$PasswordCheckProfile,
	$PasswordRule,$PlatSpecificValue,$PortNumber,$PrimaryEmail,$ReleaseChangeFlag,
	[int]$ReleaseDuration,$RequireTicketForAPI,$RequireTicketForCLI,$RequireTicketForISA,
	$RequireTicketForRequest,$ResetFlag,$SSHAccount,$SSHKey,[Int32]$SSHPort,$SystemAutoFlag,
	$TemplateSystemName,$TicketEmailNotify,$TicketSystemName,$UseSslFlag,$UseSshFlag,
	$RequireTicketForPSM,$PSMDPAAffinity,$PPMDPAAffinity
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
	$system = New-Object eDMZ.ParApi.EDMZSystem
#region Set system parameters
$system.systemName = $systemName
if ($networkAddress) {
	$system.networkAddress = $networkAddress
}
if ($newSystemName) {
	$system.newSystemName = $newSystemName
}
if ($platform) {
	$system.platformName = $platform
}
if ($description) {
    $system.description = $description
}

if ($functAcct) {
	$system.functionalAccount = $functAcct
}

if ($functAcctPwd) {
	$system.functionalAcctCredentials = $functAcctPwd
}
if($timeout -ne $null){
    $system.timeout=$timeout
}
if($ChangeFrequency -ne $null){
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
	$system.custom1 = $custom2
}

if ($custom3) {
	$system.custom1 = $custom3
}
if ($custom4) {
	$system.custom1 = $custom4
}
if ($custom5) {
	$system.custom1 = $custom5
}
if ($custom6) {
	$system.custom1 = $custom6
}
#Schedule accounts on the system for a regular password check. By default Y.
if ($CheckFlag = 'N') {
    $system.checkFl = [eDMZ.ParApi.Flag]::N	
}
 if ($DomainFuncAccount) {
	$system.domainFunctionalAccount = $DomainFuncAccount
}
 if ($DomainName) {
	$system.domainName = $DomainName
}
if ($EGPOnlyFlag) {
	$system.egpOnlyFl = [eDMZ.ParApi.Flag]::Y
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
if ($RequireTicketForPSM) {
    $system.requireTicketForPSM = Set-Flag $RequireTicketForPSM
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
#new parameters in 915
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
$result = $clnt.updateSystem($system)
# Check the outcome of the operation.
"updateSystem: rc = " + $result.returnCode + ", message =" + $result.message
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error occured: $errDescrption"
}finally{
    $clnt.disconnect()
}