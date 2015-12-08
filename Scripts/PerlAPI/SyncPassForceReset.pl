#!perl
use ParApi::ApiClient;
use Getopt::Long;

    $newPassword= ''; 
	
GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"syncPassName:s"   			=> \$syncPassName, 
"newPassword:s"   			=> \$newPassword 
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $msg) = $client->syncPassForceReset(
syncPassName => $syncPassName,
newPassword => $newPassword
);

$debug = " rc: ". $rc . " msg: " . $msg;
print $debug . "\n";