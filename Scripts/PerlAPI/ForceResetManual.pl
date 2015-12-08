#!perl
use ParApi::ApiClient;
use Getopt::Long;

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"systemName:s"   			=> \$systemName, 
"accountName:s"   			=> \$accountName 
);

my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $id, $msg) = $client->forceResetManual(
systemName => $systemName,
accountName => $accountName
);

print $id . " " . $msg . "\n";