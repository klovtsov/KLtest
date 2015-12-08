#!perl
use ParApi::ApiClient;
use Getopt::Long;

    $groupName= '';              
    $description= '';              

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"groupName:s"   		  	=> \$groupName,             
"description:s"      		=> \$description             
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $id, $msg) = $client->addGroup(
groupName => $groupName,
description => $description
);

$debug = " rc: ". $rc . " id: " . $id . " msg: " . $msg;
print $debug . "\n";

