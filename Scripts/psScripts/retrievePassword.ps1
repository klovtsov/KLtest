#Provides a mechanism to retrieve a password for a managed system/account.
#The requestID must be an approved password release request and the caller must 
#be the requestor. If the caller has ISA permissions the system and account name
#must be supplied instead of the requestID.
param(
    $tpamAddress,$keyFile,$apiUser,
	$RequestID,$systemName,$accountName,
	$comment,[Int32]$timeRequired,
	$ReasonCode,$ReasonText,$TicketNumber,$TicketSystemName
)
$psScriptsDir = (Get-Item $MyInvocation.MyCommand.Path).Directory
. "$psScriptsDir\Common.ps1"

try {
		if ($RequestID) {
		$result = $clnt.retrieve($RequestID)

	} elseif ($timeRequired) {
	    $result = $clnt.retrieve($systemName,$accountName,$timeRequired,$comment)

	} else {
	   $parms = New-Object -TypeName eDMZ.ParApi.RetrieveParms
	   if ($ReasonCode) {$parms.reasonCode = $ReasonCode}
	   if ($ReasonText) {$parms.reasonText = $ReasonText}
	   if ($TicketNumber) {$parms.ticketNumber = $TicketNumber}
	   if ($TicketSystemName) {$parms.ticketSystemName = $TicketSystemName}
	   
	   if ($comment) {
	       $result = $clnt.retrieve($systemName,$accountName,$comment,$parms)
	    } else {
		   $result = $clnt.retrieve($systemName,$accountName,$parms)
		}
	}
	# Check the outcome of the operation.
	if ($result.returnCode -eq 0){
	    #If returnCode indicates success, the message is the password.
	    $result.message
	}else{
	    #If returnCode indicates failure, the message is an actual message.
	    "Failed retrieving password: " + $result.message
	}
	$clnt.disconnect()
} catch {
	$errDescrption = $Error[0].Exception.Message
	Write-Output "Error occured: $errDescrption"
}