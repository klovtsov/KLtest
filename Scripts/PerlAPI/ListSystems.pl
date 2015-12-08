#!perl
use ParApi::ApiClient;
use Getopt::Long;

$systemName= ''; 
$networkAddress= ''; 
$collectionName= ''; 
$platform= ''; 
$autoFlag= ''; 
$sortOrder= ''; 
$maxRows= ''; 

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"systemName:s"   		  	=> \$systemName,  
"networkAddress:s"   		=> \$networkAddress,            
"collectionName:s"      	=> \$collectionName,   
"platform:s"      			=> \$platform,  
"autoFlag:s"      			=> \$autoFlag,  
"sortOrder:s"     		 	=> \$sortOrder,  
"maxRows:s"      			=> \$maxRows  
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $count, $msg,  @list) = $client->listSystems(
systemName => $systemName,
networkAddress => $networkAddress,
collectionName => $collectionName,
platform => $platform,
autoFlag => $autoFlag,
sortOrder => $sortOrder,
maxRows => $maxRows
);

$debug = " rc: ". $rc . " count: " . $count . " msg: " . $msg . " list: " . @list;
print $debug . "\n";

if ($rc == 0)
       {
          for (my $i=0; $i<$count; $i++)
          {
		   @myNames = ('systemName', 'networkAddress', 'primaryEmail', 'platformName', 'changeFrequency', 'releaseDuration', 'systemAutoFl', 'functionalAccount', 'functionalAcctCredentials', 'useSpecificKey', 'changeTime', 'passwordRule', 'portNumber', 'enablePassword', 'alternateIP', 'description', 'domainFunctionalAccount', 'domainPlatformOS', 'lineDef', 'timeout', 'domainName', 'parentSystem', 'oracleType', 'oracleSIDSN', 'checkFl', 'resetFl', 'releaseChgFl', 'netBiosName', 'maxReleaseDuration', 'nonPrivFuncFl', 'useSslFl', 'allowFuncReqFl', 'escalationTime', 'escalationEmail', 'egpOnlyFl', 'platSpecificValue', 'ticketsystemname', 'requireticketforrequest', 'requireticketforisa', 'requireticketforcli', 'requireticketforapi', 'requireticketforpsm', 'ticketemailnotify', 'custom1', 'custom2', 'custom3', 'custom4', 'custom5', 'custom6', 'allowisadurationflag', 'usesshflag', 'sshaccount', 'sshport', 'sshkey', 'templateFlag', 'autoDiscoveryProfile', 'autoDiscoveryExcludeList', 'passwordCheckProfile', 'passwordChangeProfile', 'autoDiscoveryTimeout', 'psmDpaAffinity', 'ppmDpaAffinity', 'profileCertType', 'profileCertThumbprint', 'profileCertPassword', 'funcAcctDN');
			foreach (@myNames)
			{
			print " $_: $list[$i]{$_} \n";
			} 
		   print "\n";
          }
       }
