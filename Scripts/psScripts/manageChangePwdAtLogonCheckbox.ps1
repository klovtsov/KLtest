param(
    [String][ValidateSet('getvalue', 'set', 'clear')]$action,
	$ADserver, $user, $pword, #Credentials for AD connection
	$accntName
)

#Command which will be executed on remote server
$mySb={
    param($action,$accntName)
	Import-Module ActiveDirectory
	if($action -eq 'getvalue'){
        Get-ADUser -Identity $accntName -Properties PasswordExpired|%{$_.PasswordExpired}
    }elseif ($action -eq 'set') {
        Set-ADUser -Identity $accntName -ChangePasswordAtLogon $true
    } else {
        Set-ADUser -Identity $accntName -ChangePasswordAtLogon $false
    }#end if block
}#end scriptblock

#Build connection credentials
$PWord = ConvertTo-SecureString -String $pword -AsPlainText -Force
$domainCreds = New-Object -TypeName System.Management.Automation.PSCredential -ArgumentList $user, $PWord
#Execute command
Invoke-Command -ComputerName $ADserver -ScriptBlock $mySb -ArgumentList ($action,$accntName) -Credential $domainCreds