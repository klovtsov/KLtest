#!perl
use ParApi::ApiClient;
use Getopt::Long;

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"systemName:s"   			=> \$systemName
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $msg) = $client->testSystem(
systemName => $systemName
);

$debug = " rc: ". $rc . " msg: " . $msg;
print $debug . "\n";