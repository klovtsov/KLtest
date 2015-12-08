param(
    [String][ValidateSet('create','login','password','delete')]$action,
	$server, $user, $pword, #Credentials for connection
	$accntName,$accntpwd
)
#Command which will be executed on remote server
$mySb={
    param($action,$server,$accntName,$accntpwd)
	switch ($action) {	
		'create' {
            $cn = [ADSI]"WinNT://localhost"
            $accnt = $cn.Create("User",$accntName)
            $accnt.SetPassword($accntpwd)
            $accnt.setinfo()
            sleep 3
            #Add user to Administrators group.
            $group = [ADSI]("WinNT://$server/Administrators,Group")
            $group.Add("WinNT://$accntName")
			break
		}
		
		'login' {
		    #By default, only administrators can create a remote session!
		    whoami
			break
		}
		'password' {
            $accnt = [ADSI]("WinNT://$server/$accntName,User")
            $accnt.SetPassword($accntpwd)
            $accnt.setinfo()
			break
		}
		
		'delete' {
             [ADSI]$server="WinNT://localhost"
             $server.delete("User",$accntName)
			break
		}
	}#end of switch block
}#end scriptblock

#Build connection credentials
$PWord = ConvertTo-SecureString -String $pword -AsPlainText -Force
$Creds = New-Object -TypeName System.Management.Automation.PSCredential -ArgumentList $user, $PWord
#Execute command
Invoke-Command -ComputerName $server -ScriptBlock $mySb -ArgumentList ($action,$server,$accntName,$accntpwd) -Credential $Creds
if ($Error.count -eq "0") {
  Write-Host "successful"
} else {
  $error[0].Exception
}