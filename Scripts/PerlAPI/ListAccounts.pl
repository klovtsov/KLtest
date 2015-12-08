#!perl
use ParApi::ApiClient;
use Getopt::Long;

$accountName= '';              
$systemName= ''; 
$networkAddress= '';
$collectionName= '';	
$platform= '';	
$systemAutoFlag= '';	
$accountAutoFlag= '';	
$dualControlFlag= '';	
$sort= '';
$maxRows= '';


GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"systemName:s"   		  	=> \$systemName,             
"accountName:s"      		=> \$accountName,   
"networkAddress:s"      	=> \$networkAddress,   
"collectionName:s"   		=> \$collectionName,
"platform:s"    			=> \$platform,
"accountAutoFlag:s"      	=> \$accountAutoFlag,  
"systemAutoFlag:s"      	=> \$systemAutoFlag,  
"dualControlFlag:s"      	=> \$dualControlFlag,  
"sort:s"      				=> \$sort,  
"maxRows:s"      			=> \$maxRows  
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $count, $msg,  @list) = $client->listAccounts(
systemName => $systemName,
accountName => $accountName,
networkAddress => $networkAddress,
collectionName => $collectionName,
platform => $platform,
systemAutoFlag => $systemAutoFlag,
accountAutoFlag => $accountAutoFlag,
dualControlFlag => $dualControlFlag,
sort => $sort,
maxRows => $maxRows
);

$debug = " rc: ". $rc . " count: " . $count . " msg: " . $msg . " list: " . @list;
print $debug . "\n";

if ($rc == 0) {
          for (my $i=0; $i<$count; $i++)
          {
		    @myNames = ('systemName', 'accountName', 'password', 'changeFrequency', 'releaseDuration', 'acctAutoFl', 'changeTime', 'nextChangeDt', 'passwordRule', 'svcChangeFl', 'useSelfFl', 'minApprovers', 'domainAcctName', 'relNotifyEmail', 'checkFl', 'resetFl', 'releaseChgFl', 'maxReleaseDuration', 'description', 'ticketsystemname', 'requireticketforrequest', 'requireticketforisa', 'requireticketforcli', 'requireticketforapi', 'requireticketforpsm', 'ticketemailnotify', 'reviewcount', 'reviewertype', 'reviewername', 'escalationtime', 'escalationemail', 'lockflag', 'simulprivaccreleases', 'blockautochangeflag', 'aliasaccessonlyflag', 'custom1', 'custom2', 'custom3', 'custom4', 'custom5', 'custom6', 'allowisadurationflag', 'systemtemplateflag', 'ignoresystempoliciesflag', 'overrideaccountabilityfl', 'svcrestartfl', 'chgtaskfl', 'requiremultigroupapprovalfl', 'multigroupapprovernames', 'passwordCheckProfile', 'passwordChangeProfile', 'enableBeforeReleaseFlag', 'accountDN');
			foreach (@myNames)
			{
				print " $_: $list[$i]{$_} \n";
			} 
		    print "\n";
          }
}
