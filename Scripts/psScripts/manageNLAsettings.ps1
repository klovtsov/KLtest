<#
	.SYNOPSIS
		This script will get or set the NLA setting on remote machine

	.DESCRIPTION
		This script will get or set the NLA setting on remote machine
		It' assumed that script will be run out from Fitnesse, so all incoming
		parameters are strings.

	.PARAMETER  Action
		Specify action (get|set)

	.PARAMETER  Credential
		Specify the alternative credential to use.
	
	.EXAMPLE
		manageNLAsettings  -ComputerName MYDESKTOP get 
		     -User MYDESKTOP\Admin -UserPwd TopSecret
		
		This will get the NLA setting on the localhost
		NLAEnabled       : True

    .EXAMPLE
		manageNLAsettings  -ComputerName MYDESKTOP -User MYDESKTOP\Admin -UserPwd
		    TopSecret set 1 
		
	.NOTES
		DATE	: 2015/03/13
		AUTHOR	: Alexander Buyanov
#>
param(
    $ComputerName,
	[String][ValidateSet('get','set')]$action,
	$User, $UserPwd, #Credentials for connection to computer
	[string]$EnableNLA = '0'
)

#Build connection credentials
$PWord = ConvertTo-SecureString -String $UserPwd -AsPlainText -Force
$domainCreds = New-Object -TypeName System.Management.Automation.PSCredential -ArgumentList $User, $PWord

switch ($action) {
	'get' {
		$sb = {(Get-WmiObject -class "Win32_TSGeneralSetting" -Namespace root\cimv2\terminalservices).UserAuthenticationRequired}
		break
	}
	'set' {
		$str = "(Get-WmiObject -class 'Win32_TSGeneralSetting' -Namespace root\cimv2\terminalservices).SetUserAuthenticationRequired($EnableNLA)"
	    $sb = [scriptblock]::Create($str)
		break
	}
}

#Execute command
$res = Invoke-Command -ComputerName $ComputerName -ScriptBlock $sb -Credential $domainCreds
#Construct answer for get command
if ($action -eq 'get') {
	"NLAEnabled: " + ($res -as [bool])
}

#Next lines allow Fitnesse to decide if script execution successful or not.  
if ($Error.count -eq "0") {
  Write-Host "successful"
} else {
  $error[0].Exception
}