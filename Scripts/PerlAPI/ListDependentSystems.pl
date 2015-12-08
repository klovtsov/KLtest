#!perl
use ParApi::ApiClient;
use Getopt::Long;

	$accountName= ''; 
    $systemName= '';
    $dependentStatus= ''; 
    $dependentName= ''; 
    $maxRows= ''; 

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"accountName:s"   			=> \$accountName, 
"systemName:s"   		  	=> \$systemName,             
"dependentStatus:s"   		=> \$dependentStatus,  
"dependentName:s"   		=> \$dependentName,  
"maxRows:s"   		 	 	=> \$maxRows  
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $count, $msg,  @list) = $client->listDependentSystems(
systemName => $systemName,
accountName => $accountName,
dependentStatus => $dependentStatus,
dependentName => $dependentName,
maxRows => $maxRows
);

$debug = " rc: ". $rc . " count: " . $count . " msg: " . $msg . " list: " . @list;
print $debug . "\n";

if ($rc == 0)
       {
          for (my $i=0; $i<$count; $i++)
          {
		   @myNames = ('systemName', 'networkAddress', 'platform', 'status');
			foreach (@myNames)
			{
			print " $_: $list[$i]{$_}\n ";
			} 
		   print "\n";
          }
       }