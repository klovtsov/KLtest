param(
    [String][ValidateSet('disable', 'enable', 'status')]$action,
	$comp, $user, $pword, #Credentials for AD connection
	$accntName
)
#Build connection credentials

$PWord = ConvertTo-SecureString -String $pword -AsPlainText -Force
$Creds = New-Object -TypeName System.Management.Automation.PSCredential -ArgumentList $user, $PWord


	if($action -eq 'disable'){
        Get-WmiObject -Class Win32_UserAccount -ComputerName $comp -Credential $Creds | Where-Object {$_.Name -eq $accntName}| %{$_.Disabled=$True;$_.put()} 
    }elseif ($action -eq 'enable') {
        Get-WmiObject -Class Win32_UserAccount -ComputerName $comp -Credential $Creds | Where-Object {$_.Name -eq $accntName}| %{$_.Disabled=$False;$_.put()} 
    } else {
        Get-WmiObject -Class Win32_UserAccount -ComputerName $comp -Credential $Creds | Where-Object {$_.Name -eq $accntName} | %{!$_.Disabled}
    }#end if block


