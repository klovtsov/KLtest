param(
    $ADserver, $adminUser, $PWord, #Credentials for AD connection
	$accntName, $lastName, $firstName, 
	$phone, $email,
	$description, [String][ValidateSet('disable', 'enable')]$ustate	
)

#Command which will be executed on remote server
$mySb={
    param($accntName, $lastName, $firstName, $phone, $email, $description, $ustate)
	Import-Module ActiveDirectory
	if($ustate -eq 'disable'){
        Set-ADUser -Identity $accntName -Enabled $false -OfficePhone $phone -EmailAddress $email -Description $description -GivenName $firstName -Surname $lastName 
    }elseif ($ustate -eq 'enable') {
        Set-ADUser -Identity $accntName -Enabled $true -OfficePhone $phone -EmailAddress $email -Description $description -GivenName $firstName -Surname $lastName 
    }
	}#end scriptblock

#Build connection credentials
$PWord = ConvertTo-SecureString -String $pword -AsPlainText -Force
$domainCreds = New-Object -TypeName System.Management.Automation.PSCredential -ArgumentList $adminUser, $PWord
#Execute command
Invoke-Command -ComputerName $ADserver -ScriptBlock $mySb -ArgumentList ($accntName, $lastName, $firstName, $phone, $email, $description, $ustate) -Credential $domainCreds