$Computer = $args[0]
$username = $args[1]
$password = $args[2]
$User = $args[3]
$secstr = New-Object -TypeName System.Security.SecureString
$password.ToCharArray() | ForEach-Object {$secstr.AppendChar($_)}
$cred = new-object -typename System.Management.Automation.PSCredential  -argumentlist $username, $secstr
$sc = {
Import-Module "C:\Program Files (x86)\Quest Software\Authentication Services\PsModules\Quest.AuthenticationServices"
Enable-QasUnixUser -Identity $args[0]
set-qasunixuser -Identity $args[0] -UidNumber 123456789
} 
Invoke-Command -ComputerName $Computer -ScriptBlock $sc -ArgumentList $User -Credential $cred

