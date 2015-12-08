#!perl
use ParApi::ApiClient;
use Getopt::Long;

    $username= ''; 
	$keyType= ''; 
	$passphrase= ''; 
	$regenerate= '';
	
GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"username:s"   				=> \$username, 
"keyType:s"   				=> \$keyType, 
"passphrase:s"   			=> \$passphrase, 
"regenerate:s"   			=> \$regenerate
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $msg) = $client->userSshKey(
username => $username,
keyType => $keyType,
passphrase => $passphrase,
regenerate => $regenerate
);

$debug = " rc: ". $rc . " msg: " . $msg;
print $debug . "\n";