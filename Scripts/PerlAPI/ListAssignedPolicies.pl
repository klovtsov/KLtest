#!perl
use ParApi::ApiClient;
use Getopt::Long;

	$userName= ''; 
    $accountName= '';              
    $systemName= ''; 
	$fileName= '';
	$collectionName= '';	
	$groupName= '';	
	$accessPolicyName= '';	
	$expandCollectionFlag= '';	
	$expandGroupFlag= '';	
	$expandPolicyFlag= '';	
	$allOrEffectiveFlag= '';
	$permissionType= '';
	$permissionName= '';
	$sortOrder= '';
	$maxRows= '';

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"userName:s"   		  		=> \$userName, 
"systemName:s"   		  	=> \$systemName,             
"accountName:s"      		=> \$accountName,   
"fileName:s"      			=> \$fileName,   
"collectionName:s"   		=> \$collectionName,
"groupName:s"    			=> \$groupName,
"accessPolicyName:s"      	=> \$accessPolicyName,  
"expandCollectionFlag:s"    => \$expandCollectionFlag,  
"expandGroupFlag:s"      	=> \$expandGroupFlag,
"expandPolicyFlag:s"      	=> \$expandPolicyFlag,  
"allOrEffectiveFlag:s"      => \$allOrEffectiveFlag,  
"permissionType:s"      	=> \$permissionType, 
"permissionName:s"      	=> \$permissionName, 
"sortOrder:s"      			=> \$sortOrder, 
"maxRows:s"      			=> \$maxRows 
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $count, $msg,  @list) = $client->listAssignedPolicies(
userName => $userName,
systemName => $systemName,
accountName => $accountName,
fileName => $fileName,
collectionName => $collectionName,
groupName => $groupName,
accessPolicyName => $accessPolicyName,
expandCollectionFlag => $expandCollectionFlag,
expandPolicyFlag => $expandPolicyFlag,
expandGroupFlag => $expandGroupFlag,
allOrEffectiveFlag => $allOrEffectiveFlag,
permissionType => $permissionType,
permissionName => $permissionName,
sortOrder => $sortOrder,
maxRows => $maxRows
);

$debug = " rc: ". $rc . " count: " . $count . " msg: " . $msg . " list: " . @list;
print $debug . "\n";

if ($rc == 0)
       {
          for (my $i=0; $i<$count; $i++)
          {
		   @myNames = ('userName', 'fullName', 'extAuthReqd', 'lastAccessDt', 'groupName', 'systemName', 'accountName', 'fileName', 'collectionName', 'accessPolicyName', 'policySource', 'permissionType', 'permissionName', 'cmdName', 'effectiveFl');
			foreach (@myNames)
			{
			print "$_: $list[$i]{$_}\n";
			} 
		   print "\n";
          }
       }