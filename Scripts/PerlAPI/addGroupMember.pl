#!perl
use ParApi::ApiClient;
use Getopt::Long;

    $groupName= '';              
    $userName= ''; 
	$groupID= '';	

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"groupName:s"   		  	=> \$groupName,             
"userName:s"      			=> \$userName,   
"groupID:s"      			=> \$groupID           
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $id, $msg) = $client->addGroupMember(
groupName => $groupName,
userName => $userName,
groupID => $groupID
);

$debug = " rc: ". $rc . " id: " . $id . " msg: " . $msg;
print $debug . "\n";
