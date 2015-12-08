#!perl
use ParApi::ApiClient;
use Getopt::Long;

$collectionName = '';              
$description = '';              
$psmDpaAffinity = '';

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"collectionName:s"   	  	=> \$collectionName,             
"psmDpaAffinity:s"   	  	=> \$psmDpaAffinity,             
"description:s"      		=> \$description             
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $id, $msg) = $client->updateCollection(
collectionName => $collectionName,
psmDpaAffinity => $psmDpaAffinity,
description => $description
);

$debug = " rc: ". $rc . " id: " . $id . " msg: " . $msg;
print $debug . "\n";


