<#
	.SYNOPSIS
		This script will create or remove the application pool on remote machine.
		Also it allows to receive  a password of AppPool identity user.

	.PARAMETER  Action
		Specify action (create|remove|getPwd)

	.PARAMETER  Credential
		Specify the alternative credential to use.
	
	.EXAMPLE
		manageIISappPools  -ComputerName iismachine -User Admin -UserPwd TopSecret
		    create -appPoolName TestAppPool -identity localUser -identityPwd grain41R

    .EXAMPLE
		manageNLAsettings  -ComputerName iismachine -User Admin -UserPwd TopSecret
		    getPwd  -appPoolName TestAppPool
		
	.NOTES
		DATE	: 2015/03/19
		AUTHOR	: Alexander Buyanov
#>
param(
    $ComputerName,$User, $UserPwd, #Credentials for connection to computer
	[String][ValidateSet('create','remove','getPwd')]$action,
	[string]$appPoolName,$identity,$identityPwd
)

#Build connection credentials
$PWord = ConvertTo-SecureString -String $UserPwd -AsPlainText -Force
$Creds = New-Object -TypeName System.Management.Automation.PSCredential -ArgumentList $User, $PWord

switch ($action) {
	'create' {
		$str = "Import-Module WebAdministration;New-WebAppPool $appPoolName;
		Set-ItemProperty iis:\AppPools\$appPoolName -name processModel -value @{username='$identity';password='$identityPwd';identitytype=3}"    
		break
	}
	'remove' {
		$str = "Import-Module WebAdministration;Remove-WebAppPool $appPoolName"
		break
	}
	'getPwd'{
	    $str = "Import-Module WebAdministration;(Get-Item iis:\AppPools\$appPoolName).processModel.password"
		break
	}
}

#Execute command
$sb = [scriptblock]::Create($str)
$res = Invoke-Command -ComputerName $ComputerName -ScriptBlock $sb -Credential $Creds

#Next lines allow Fitnesse to decide if script execution successful or not.  
if ($Error.count -eq "0") {
  #Construct answer for get command
    if ($action -eq 'getPwd') {
	    $res
    }else{
        Write-Host "successful"
	}
} else {
  $error[0].Exception
}