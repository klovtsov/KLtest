#!perl
use ParApi::ApiClient;
use Getopt::Long;

$userName= '';

GetOptions(
"keyFileName=s"  	 		=> \$keyFileName,
"apiuserName=s"  			=> \$apiuserName,
"host=s"  					=> \$host,
"userName=s"  				=> \$userName
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $id, $msg) = $client->deleteUser(
 userName => $userName
);

$debug = " rc: ". $rc . " id: " . $id . " msg: " . $msg;
print $debug . "\n";
