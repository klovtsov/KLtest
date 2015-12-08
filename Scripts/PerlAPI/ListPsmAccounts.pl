#!perl
use ParApi::ApiClient;
use Getopt::Long;

	$accountAutoFlag= ''; 
    $systemCustom1= '';
	$systemCustom2= '';	
	$systemCustom3= '';	
	$systemCustom4= '';	
	$systemCustom5= '';	
	$systemCustom6= '';	
	$accountCustom1= '';
	$accountCustom2= '';
	$accountCustom3= '';
	$accountCustom4= '';
	$accountCustom5= '';
	$accountCustom6= '';
    $accountPsmFlag= ''; 
    $accountLockFlag= ''; 
	$accountName= ''; 
    $collectionName= '';
    $dualControlFlag   = ''; 
    $networkAddress    = ''; 
    $platform= ''; 
	$systemAutoFlag= ''; 
    $systemName= '';
    $sort= ''; 
    $sortType= ''; 
    $maxRows= ''; 

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"systemName:s"   		  	=> \$systemName,             
"accountName:s"      		=> \$accountName,   
"accountCustom1:s"      	=> \$accountCustom1,   
"accountCustom2:s"   		=> \$accountCustom2,
"accountCustom3:s"    		=> \$accountCustom3,
"accountCustom4:s"      	=> \$accountCustom4,  
"accountCustom5:s"      	=> \$accountCustom5,  
"accountCustom6:s"      	=> \$accountCustom6,
"systemCustom1:s"      		=> \$systemCustom1,  
"systemCustom2:s"      		=> \$systemCustom2,  
"systemCustom3:s"      		=> \$systemCustom3, 
"systemCustom4:s"      		=> \$systemCustom4, 
"systemCustom5:s"      		=> \$systemCustom5, 
"systemCustom6:s"      		=> \$systemCustom6, 
"accountAutoFlag:s"      	=> \$accountAutoFlag, 
"accountPsmFlag:s"      	=> \$accountPsmFlag, 
"accountLockFlag:s"      	=> \$accountLockFlag, 
"collectionName:s"      	=> \$collectionName, 
"dualControlFlag:s"      	=> \$dualControlFlag, 
"networkAddress:s"      	=> \$networkAddress, 
"platform:s"      			=> \$platform, 
"systemAutoFlag:s"      	=> \$systemAutoFlag, 
"sort:s"      				=> \$sort, 
"sortType:s"      			=> \$sortType, 
"maxRows:s"      			=> \$maxRows  
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $count, $msg,  @list) = $client->listPsmAccounts(
systemName => $systemName,
accountName => $accountName,
accountCustom1 => $accountCustom1,
accountCustom2 => $accountCustom2,
accountCustom3 => $accountCustom3,
accountCustom4 => $accountCustom4,
accountCustom5 => $accountCustom5,
accountCustom6 => $accountCustom6,
systemCustom1 => $systemCustom1,
systemCustom2 => $systemCustom2,
systemCustom3 => $systemCustom3,
systemCustom4 => $systemCustom4,
systemCustom5 => $systemCustom5,
systemCustom6 => $systemCustom6,
accountAutoFlag => $accountAutoFlag,
accountPsmFlag => $accountPsmFlag,
accountLockFlag => $accountLockFlag,
collectionName => $collectionName,
dualControlFlag => $dualControlFlag,
networkAddress => $networkAddress,
platform => $platform,
systemAutoFlag => $systemAutoFlag,
sort => $sort,
sortType => $sortType,
maxRows => $maxRows
);

$debug = " rc: ". $rc . " count: " . $count . " msg: " . $msg . " list: " . @list;
print $debug . "\n";

if ($rc == 0)
       {
          for (my $i=0; $i<$count; $i++)
          {
		   @myNames = ('systemName', 'accountName', 'passwordMethod', 'parCliUserName', 'minApprovers', 'accountLockFlag', 'defaultSessionDuration', 'maxSessionCount', 'notifyThreshold', 'notifyFrequency', 'proxyType', 'enableFlag', 'colorDepth', 'domainAccountName', 'dssKeyType', 'fileTransPath', 'fileTransType', 'fileTransAuthMethod', 'reviewCount', 'reviewerType', 'reviewerName', 'sessionStartNotifyEmail', 'escalationEmail', 'escalationTime', 'clipboardFlag', 'consoleFlag', 'recordingRequiredFlag', 'fileTransUpFlag', 'fileTransDownFlag', 'cliSystemName', 'cliAccountName', 'cliDomainName', 'connectionProfile', 'postSessionProfile');
			foreach (@myNames)
			{
			print " $_: $list[$i]{$_}\n ";
			} 
		   print "\n";
          }
       }
