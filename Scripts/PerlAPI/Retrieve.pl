#!perl
use ParApi::ApiClient;
use Getopt::Long;

	$systemName= ''; 
    $accountName= ''; 
    $timeRequired= ''; 
	$reason= ''; 
    $ticketSystemName= ''; 
    $ticketNumber= ''; 
	$reasonText= ''; 
	$reasonCode= ''; 
	$requestID= ''; 


GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"systemName:s"   			=> \$systemName, 
"accountName:s"   			=> \$accountName, 
"timeRequired:s"   			=> \$timeRequired, 
"reason:s"   				=> \$reason, 
"ticketSystemName:s"   		=> \$ticketSystemName, 
"ticketNumber:s"   			=> \$ticketNumber, 
"reasonText:s"   			=> \$reasonText, 
"reasonCode:s"   			=> \$reasonCode, 
"requestID:s"   			=> \$requestID 
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $msg) = $client->retrieve(
systemName => $systemName,
accountName => $accountName,
timeRequired => $timeRequired,
reason => $reason,
ticketSystemName => $ticketSystemName,
ticketNumber => $ticketNumber,
reasonText => $reasonText,
reasonCode => $reasonCode,
requestID => $requestID
);

$debug = " rc: ". $rc . " msg: " . $msg;
print $debug . "\n";