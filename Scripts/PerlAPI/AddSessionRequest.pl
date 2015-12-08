#!perl
use ParApi::ApiClient;
use Getopt::Long;

    $forUserName= '';              
    $requestImmediateFlag= ''; 
	$requestedReleaseDate= '';
	$releaseDuration= '';	
	$requestNotes= '';	
	$ticketNumber= '';	
	$ticketSystemName= '';	
	$reasonCode= '';	
	$accessPolicyName= '';
	$commandName= '';

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"systemName:s"   		  	=> \$systemName,             
"accountName:s"      		=> \$accountName,   
"forUserName:s"      		=> \$forUserName,   
"requestImmediateFlag:s"    => \$requestImmediateFlag,
"requestedReleaseDate:s"    => \$requestedReleaseDate,
"releaseDuration:s"      	=> \$releaseDuration,  
"requestNotes:s"      		=> \$requestNotes,  
"ticketNumber:s"      		=> \$ticketNumber,  
"ticketSystemName:s"      	=> \$ticketSystemName,  
"reasonCode:s"      		=> \$reasonCode,  
"accessPolicyName:s"      	=> \$accessPolicyName,  
"commandName:s"      		=> \$commandName         
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $id, $msg) = $client->addSessionRequest(
systemName => $systemName,
accountName => $accountName,
forUserName => $forUserName,
requestImmediateFlag => $requestImmediateFlag,
requestedReleaseDate => $requestedReleaseDate,
releaseDuration => $releaseDuration,
requestNotes => $requestNotes,
ticketNumber => $ticketNumber,
ticketSystemName => $ticketSystemName,
reasonCode => $reasonCode,
accessPolicyName => $accessPolicyName,
commandName => $commandName
);

$debug = $msg;
print $debug . "\n";
