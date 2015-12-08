param(
    [String][ValidateSet('create', 'delete', 'addmember')]$action,
	$ADserver, $user, $pword, #Credentials for AD connection
	$GroupName, $MemberName
)

#Command which will be executed on remote server
$mySb={
    param($action,$GroupName,$MemberName)
	Import-Module ActiveDirectory
	if ($action -eq 'create'){
        New-ADGroup -Name $GroupName -GroupScope "Global"
	} elseif ($action -eq 'delete')
	{
        Remove-ADGroup $GroupName -Confirm:$false
    } elseif ($action -eq 'addmember')
    {
        Add-ADGroupMember -Identity $GroupName -Members $MemberName
    }
	#end if block
}#end scriptblock

#Build connection credentials
$PWord = ConvertTo-SecureString -String $pword -AsPlainText -Force
$domainCreds = New-Object -TypeName System.Management.Automation.PSCredential -ArgumentList $user, $PWord
#Execute command
Invoke-Command -ComputerName $ADserver -ScriptBlock $mySb -ArgumentList ($action,$GroupName,$MemberName) -Credential $domainCreds

if ($Error.count -eq "0") {
  Write-Host "successful"
} else {
  $error[0].Exception
}