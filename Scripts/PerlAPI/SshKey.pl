#!perl
use ParApi::ApiClient;
use Getopt::Long;

    $systemName= ''; 
	$accountName= ''; 
    $standardKey= ''; 
    $keyFormat= ''; 
	$regenerate= ''; 
	
GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"systemName:s"   			=> \$systemName, 
"accountName:s"   			=> \$accountName, 
"standardKey:s"   			=> \$standardKey, 
"keyFormat:s"   			=> \$keyFormat, 
"regenerate:s"   			=> \$regenerate 
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $msg) = $client->sshKey(
systemName => $systemName,
accountName => $accountName,
standardKey => $standardKey,
keyFormat => $keyFormat,
regenerate => $regenerate
);

$debug = " rc: ". $rc . " msg: " . $msg;
print $debug . "\n";