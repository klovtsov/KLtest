#!perl
use ParApi::ApiClient;
use Getopt::Long;


GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"syncPassName:s"   		  	=> \$syncPassName            
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $id, $msg) = $client->deleteSyncPass(
syncPassName => $syncPassName
);

$debug = " rc: ". $rc . " id: " . $id . " msg: " . $msg;
print $debug . "\n";