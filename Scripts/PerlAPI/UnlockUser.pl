#!perl
use ParApi::ApiClient;
use Getopt::Long;

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"userName:s"   				=> \$userName
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $msg) = $client->unlockUser(
userName => $userName
);

$debug = " rc: ". $rc . " msg: " . $msg;
print $debug . "\n";