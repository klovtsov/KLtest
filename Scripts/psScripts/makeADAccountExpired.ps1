param(
    [String][ValidateSet('enable', 'clear')]$action,
	$ADserver, $user, $pword, #Credentials for AD connection
	$accntName
)

#Command which will be executed on remote server
$mySb={
    param($action,$accntName)
	Import-Module ActiveDirectory
	if($action -eq 'enable'){
        Get-ADUser -Identity $accntName |Set-ADAccountExpiration -TimeSpan -1
    } else {
        Get-ADUser -Identity $accntName |clear-ADAccountExpiration
    }#end if block
}#end scriptblock

#Build connection credentials
$PWord = ConvertTo-SecureString -String $pword -AsPlainText -Force
$domainCreds = New-Object -TypeName System.Management.Automation.PSCredential -ArgumentList $user, $PWord
#Execute command
Invoke-Command -ComputerName $ADserver -ScriptBlock $mySb -ArgumentList ($action,$accntName) -Credential $domainCreds