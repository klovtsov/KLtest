#!perl
use ParApi::ApiClient;
use Getopt::Long;

    $groupID= ''; 
	$groupName= ''; 
    $maxRows= ''; 

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"groupName:s"   		  	=> \$groupName,             
"groupID:s"      			=> \$groupID   
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $count, $msg,  @list) = $client->listGroups(
groupName => $groupName,
groupID => $groupID
);

$debug = " rc: ". $rc . " count: " . $count . " msg: " . $msg . " list: " . @list;
print $debug . "\n";

if ($rc == 0)
       {
          for (my $i=0; $i<$count; $i++)
          {
		   @myNames = ('groupName', 'groupDesc', 'groupID');
			foreach (@myNames)
			{
			print " $_: $list[$i]{$_}\n ";
			} 
		   print "\n";
          }
       }