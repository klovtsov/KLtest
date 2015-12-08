<#
    Modifies an existing user account.
    .EXAMPLE
     updateUser -tpamAddress 10.30.44.206 -keyFile ab10test_user1_api -apiUser ab10test_user1_api
	     -username testuser -firstame Joe -lastname Doe
	.PARAMETER SecondaryAuth - Secondary authentication system used for user login. Valid 
	    values are None (default), SecureID, Safeword,Radius, WinAD, and LDAP.
	.PARAMETER SecondaryUserID - is required when SecondaryAuth is other than None.
	.PARAMETER LogonHoursFlag - Indicates whether the LogonHours value represents allowed 
	    or prohibited hours. Valid values are A, P, or N (no restrictions).
  #>
param(
	$tpamAddress,$keyFile,$apiUser,
	$username,$firstname,$lastname,$UserType,$Disable,$Description,
	$Password,$Email,$Phone,$Mobile,$SecondaryAuth = 'None',$SecondaryAuthSystem,
	$SecondaryUserID,$PrimaryAuthExtra,$PrimaryAuthID,$PrimaryAuthType,$PrimaryAuthSystem,
	$LogonHoursFlag,$LogonHours,$LogonDays,$LocalTimezone,$MobileAllowedFlag,
	$TemplateUserName,$interface,$Custom1,$Custom2,$Custom3,$Custom4,$Custom5,$Custom6,
	$CertThumbprint
)

$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
	
$user= New-Object -TypeName eDMZ.ParApi.User
#region Set user properties
#username,firstname,lastname are required parameters
$user.userName = $username
if ($firstname) {
	$user.firstName = $firstname
}

if ($lastname) {
    $user.lastName = $lastname
}

if ($UserType) {
	$user.userType = $UserType        #Basic (default), Admin, Auditor, or UserAdmin
}

if ($Description) {
	$user.description = $Description
}

if ($Password) {
	$user.password = $Password
}

if ($Email) {
	$user.emailAddress = $Email
}

if ($Phone) {
	$user.phone = $Phone
}

if ($Mobile) {
	$user.mobile = $Mobile
}

if ($Disable) {
	$user.disableFl = $Disable
}

$user.secondaryAuthType = $SecondaryAuth

if ($SecondaryAuthSystem) {
	$user.secondaryAuthSystem = $SecondaryAuthSystem
}

if ($SecondaryUserID) {
	$user.secondaryAuthUserID = $SecondaryUserID
}

if ($PrimaryAuthExtra) {
	$user.primaryAuthExtra = $PrimaryAuthExtra
}
if ($PrimaryAuthID) {
	$user.primAuthID = $PrimaryAuthID
}
if ($PrimaryAuthType) {
	$user.primAuthType = $PrimaryAuthType
}

if ($PrimaryAuthSystem) {
	$user.primAuthSystem = $PrimaryAuthSystem
}

if ($LogonHoursFlag) {
	$user.logonHoursSelection = $LogonHoursFlag
}

if ($LogonHours) {
	$user.logonHours = $LogonHours
}

if ($LogonDays) {
	$user.logonHours = $LogonDays 
}

if ($LocalTimezone) {
	$user.localTimeZone = $LocalTimezone
}

if ($MobileAllowedFlag) {
	$user.mobileAllowedFlag = Set-Flag $MobileAllowedFlag
}

if ($TemplateUserName) {
	$user.templateUserName = $TemplateUserName
}
if ($interface) {
	$user.userInterface = $interface
}

if ($custom1) {
	$user.custom1 = $custom1
}

if ($custom2) {
	$user.custom2 = $custom2
}

if ($custom3) {
	$user.custom3 = $custom3
}

if ($custom4) {
	$user.custom4 = $custom4
}

if ($custom5) {
	$user.custom5 = $custom5
}

if ($custom6) {
	$user.custom6 = $custom6
}
if ($CertThumbprint) {
	$user.certThumbprint = $CertThumbprint
}
#endregion

# Execute the operation on TPAM.
$result = $clnt.updateUser($user)
# Check the outcome of the operation.
"updateUser: rc = " + $result.returnCode + ", message =" + $result.message
$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error occured: $errDescrption"
}