param(
    [String][ValidateSet('create','login','delete')]$action,
	$ADserver, $user, $pword, #Credentials for AD connection
	$accntName,$accntpwd
)

#Command which will be executed on remote server
$mySb={
    param($action,$accntName,$accntpwd)
	switch ($action) {
	
		'create' {
		    Import-Module ActiveDirectory
			$acntPwd = ConvertTo-SecureString $accntpwd -AsPlainText -Force
            New-ADUser $accntName -SamAccountName $accntName -GivenName 'FitzRoy' `
		        -Surname 'Somerset' -AccountPassword $acntPwd -Enabled $true
			break
		}
		
		'login' {
		    #By default, only administrators can create a remote session!
		    whoami
			break
		}
		
		'delete' {
		    Import-Module ActiveDirectory
		    Remove-ADUser $accntName -Confirm:$false
			break
		}
	}#end of switch block
}#end scriptblock

#Build connection credentials
$PWord = ConvertTo-SecureString -String $pword -AsPlainText -Force
$domainCreds = New-Object -TypeName System.Management.Automation.PSCredential -ArgumentList $user, $PWord
#Execute command
Invoke-Command -ComputerName $ADserver -ScriptBlock $mySb -ArgumentList ($action,$accntName,$accntpwd) -Credential $domainCreds
if ($Error.count -eq "0") {
  Write-Host "successful"
} else {
  $error[0].Exception
}