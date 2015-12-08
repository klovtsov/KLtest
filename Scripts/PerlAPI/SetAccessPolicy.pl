#!perl
use ParApi::ApiClient;
use Getopt::Long;

	$accessPolicyName= ''; 
    $action= ''; 
    $systemName= ''; 
	$accountName= ''; 
    $fileName= ''; 
    $collectionName= ''; 
	$userName= ''; 
	$groupName= ''; 

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"systemName:s"   			=> \$systemName, 
"accountName:s"   			=> \$accountName, 
"accessPolicyName:s"   		=> \$accessPolicyName, 
"action:s"   				=> \$action, 
"fileName:s"   				=> \$fileName, 
"collectionName:s"   		=> \$collectionName, 
"userName:s"   				=> \$userName,
"groupName:s"   			=> \$groupName
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $msg) = $client->setAccessPolicy(
systemName => $systemName,
accountName => $accountName,
accessPolicyName => $accessPolicyName,
action => $action,
fileName => $fileName,
collectionName => $collectionName,
userName => $userName,
groupName => $groupName
);

$debug = " rc: ". $rc . " msg: " . $msg;
print $debug . "\n";