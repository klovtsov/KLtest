#!perl
use ParApi::ApiClient;
use Getopt::Long;

	$startDate= ''; 
    $endDate= ''; 
    $userName= ''; 
	$groupName= ''; 
    $operation= ''; 
    $objectType= ''; 
	$target= ''; 
	$role= ''; 
	$sort= ''; 
	$maxRows= ''; 
	$direction= ''; 
	

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"userName:s"   		  		=> \$userName,  
"startDate:s"   			=> \$startDate,            
"endDate:s"      			=> \$endDate,   
"operation:s"      			=> \$operation,  
"objectType:s"      		=> \$objectType,  
"target:s"     			 	=> \$target,  
"role:s"    				=> \$role,  
"sort:s"    				=> \$sort,  
"maxRows:s"  			  	=> \$maxRows,  
"direction:s"  			  	=> \$direction
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $count, $msg,  @list) = $client->reportActivity(
userName => $userName,
startDate => $startDate,
endDate => $endDate,
operation => $operation,
objectType => $objectType,
target => $target,
role => $role,
sort => $sort,
direction => $direction,
maxRows => $maxRows
);

$debug = " rc: ". $rc . " count: " . $count . " msg: " . $msg . " list: " . @list;
print $debug . "\n";

if ($rc == 0)
       {
          for (my $i=0; $i<$count; $i++)
          {
		   @myNames = ('logTime', 'userName', 'userFullName', 'roleUsed', 'objectType', 'operation', 'failed', 'target', 'details', 'rptUtcOffset', 'applianceName');
			foreach (@myNames)
			{
			print "$_: $list[$i]{$_}\n";
			} 
		   print "\n";
          }
       }
