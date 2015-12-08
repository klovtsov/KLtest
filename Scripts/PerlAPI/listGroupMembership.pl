#!perl
use ParApi::ApiClient;
use Getopt::Long;

	$userName= ''; 
    $groupID= ''; 
    $maxRows= ''; 

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"groupName:s"   		  	=> \$groupName,  
"userName:s"   		  		=> \$userName,            
"groupID:s"      			=> \$groupID,   
"maxRows:s"      			=> \$maxRows  
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $count, $msg,  @list) = $client->listGroupMembership(
groupName => $groupName,
userName => $userName,
groupID => $groupID,
maxRows => $maxRows
);

$debug = " rc: ". $rc . " count: " . $count . " msg: " . $msg . " list: " . @list;
print $debug . "\n";

if ($rc == 0)
       {
          for (my $i=0; $i<$count; $i++)
          {
		   @myNames = ('groupName', 'userName', 'groupID');
			foreach (@myNames)
			{
			print "$_: $list[$i]{$_}\n";
			} 
		   print "\n";
          }
       }