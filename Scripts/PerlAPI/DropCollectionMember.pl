#!perl
use ParApi::ApiClient;
use Getopt::Long;

	$accountName= ''; 
	$fileName= ''; 	


GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"collectionName:s"   		=> \$collectionName,
"systemName:s"      		=> \$systemName,     
"accountName:s"      		=> \$accountName,
"fileName:s"      			=> \$fileName   
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $id, $msg) = $client->dropCollectionMember(
collectionName => $collectionName,
systemName => $systemName,
accountName => $accountName,
fileName => $fileName
);

$debug = " rc: ". $rc . " id: " . $id . " msg: " . $msg;
print $debug . "\n";