param(
    [String][ValidateSet('Allow', 'Deny')]$action,
	$ADserver, $logonUser, $logonPword, #Credentials for AD connection
	$user, $adminName, $usersContainer 
)


#Command which will be executed on remote server
$mySb={

    param($action,$user,$adminName,$usersContainer)
	Import-Module ActiveDirectory
        cd ad:\        
	$myuser = Get-ADUser -Identity  $user
	$admin = Get-ADUser -Identity $adminName
	$sid = new-object System.Security.Principal.SecurityIdentifier $admin.SID
	$acl = Get-Acl $myuser
	$ace = new-object System.DirectoryServices.ActiveDirectoryAccessRule $sid,"WriteProperty","Deny"
	if($action -eq 'Deny'){
	$acl.AddAccessRule($ace)
	}elseif ($action -eq 'Allow') {
	$acl.RemoveAccessRule($ace)
	}
	$path = "AD:\CN=" + $user + "," + $usersContainer
	$acl
	set-acl -path $path -aclobject $acl 

}#end scriptblock




#Build connection credentials
$PWord = ConvertTo-SecureString -String $logonPword -AsPlainText -Force
$domainCreds = New-Object -TypeName System.Management.Automation.PSCredential -ArgumentList $logonUser, $PWord
#Execute command
Invoke-Command -ComputerName $ADserver -ScriptBlock $mySb -ArgumentList ($action,$user,$adminName,$usersContainer) -Credential $domainCreds