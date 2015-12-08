#!perl
use ParApi::ApiClient;
use Getopt::Long;

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"passwordID:s"   			=> \$passwordID, 
"status:s"   			=> \$status 
);

my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $msg) = $client->manualPasswordReset(
passwordID => $passwordID,
status => $status
);
$res = " rc: ". $rc . " message: " . $msg;
print "$res\n";