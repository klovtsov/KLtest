param(
    [String][ValidateSet('disable', 'enable', 'status')]$action,
	$ADserver, $user, $pword, #Credentials for AD connection
	$accntName
)

#Command which will be executed on remote server
$mySb={
    param($action,$accntName)
	Import-Module ActiveDirectory
	if($action -eq 'disable'){
        Set-ADUser -Identity $accntName -Enabled $false
    }elseif ($action -eq 'enable') {
        Set-ADUser -Identity $accntName -Enabled $true
    } else {
        Get-ADUser -Identity $accntName | %{$_.Enabled}
    }#end if block
}#end scriptblock

#Build connection credentials
$PWord = ConvertTo-SecureString -String $pword -AsPlainText -Force
$domainCreds = New-Object -TypeName System.Management.Automation.PSCredential -ArgumentList $user, $PWord
#Execute command
Invoke-Command -ComputerName $ADserver -ScriptBlock $mySb -ArgumentList ($action,$accntName) -Credential $domainCreds