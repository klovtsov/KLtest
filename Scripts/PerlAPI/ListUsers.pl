#!perl
use ParApi::ApiClient;
use Getopt::Long;

	$userName= ''; 
    $emailAddress= ''; 
    $groupName= ''; 
	$userInterface= ''; 
    $userType= ''; 
    $status= ''; 
	$externalAuthType= ''; 
	$sortOrder= ''; 
	$secondaryAuthType= ''; 
	$maxRows= ''; 

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"userName:s"   		  		=> \$userName,  
"emailAddress:s"   			=> \$emailAddress,            
"groupName:s"      			=> \$groupName,   
"userInterface:s"      		=> \$userInterface,  
"userType:s"      			=> \$userType,  
"status:s"     			 	=> \$status,  
"externalAuthType:s"    	=> \$externalAuthType,  
"sortOrder:s"    			=> \$sortOrder,  
"secondaryAuthType:s"    	=> \$secondaryAuthType,  
"maxRows:s"  			  	=> \$maxRows  
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $count, $msg,  @list) = $client->listUsers(
userName => $userName,
emailAddress => $emailAddress,
groupName => $groupName,
userInterface => $userInterface,
userType => $userType,
status => $status,
externalAuthType => $externalAuthType,
sortOrder => $sortOrder,
secondaryAuthType => $secondaryAuthType,
maxRows => $maxRows
);

$debug = " rc: ". $rc . " count: " . $count . " msg: " . $msg . " list: " . @list;
print $debug . "\n";

if ($rc == 0)
       {
          for (my $i=0; $i<$count; $i++)
          {
		   @myNames = ('userName', 'lastName', 'firstName', 'emailAddress', 'phone', 'pager', 'userType', 'userInterface', 'password', 'disabledFl', 'extAuthType', 'extAuthID', 'description', 'lastAccessDate', 'logonHoursFl', 'logonHours', 'extAuthSystem', 'primAuthType', 'primAuthID', 'primAuthSystem', 'localtimezone', 'dstFlag', 'logonDays', 'mobileAllowedFlag', 'secAuthType', 'secAuthID', 'secAuthSystem', 'primAuthExtra', 'custom1', 'custom2', 'custom3', 'custom4', 'custom5', 'custom6', 'mobile');
			foreach (@myNames)
			{
			print " $_: $list[$i]{$_}\n ";
			} 
		   print "\n";
          }
       }
