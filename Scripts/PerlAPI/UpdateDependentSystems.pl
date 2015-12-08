#!perl
use ParApi::ApiClient;
use Getopt::Long;

    $assign= ''; 
	$unassign= ''; 
	
GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"systemName:s"   			=> \$systemName, 
"accountName:s"   			=> \$accountName, 
"assign:s"   				=> \$assign, 
"unassign:s"   				=> \$unassign
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $msg) = $client->updateDependentSystems(
systemName => $systemName,
accountName => $accountName,
assign => $assign,
unassign => $unassign
);

$debug = " rc: ". $rc . " msg: " . $msg;
print $debug . "\n";