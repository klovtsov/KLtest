#!perl
use ParApi::ApiClient;
use Getopt::Long;

	$systemName= ''; 
    $accountName= ''; 
    $timeRequired= ''; 
	$comment= ''; 
    $ticketSystemName= ''; 
    $ticketNumber= ''; 

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"systemName:s"   			=> \$systemName, 
"accountName:s"   			=> \$accountName, 
"timeRequired:s"   			=> \$timeRequired, 
"comment:s"   				=> \$comment, 
"ticketSystemName:s"   		=> \$ticketSystemName, 
"ticketNumber:s"   			=> \$ticketNumber 
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $msg) = $client->retrieveWithTicket(
systemName => $systemName,
accountName => $accountName,
timeRequired => $timeRequired,
comment => $comment,
ticketSystemName => $ticketSystemName,
ticketNumber => $ticketNumber
);

$debug = " rc: ". $rc . " msg: " . $msg;
print $debug . "\n";