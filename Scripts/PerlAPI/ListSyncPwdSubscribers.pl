#!perl
use ParApi::ApiClient;
use Getopt::Long;

	$syncPassName= '';

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,  
"syncPassName:s"  			=> \$syncPassName 
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $count, $msg,  @list) = $client->listSyncPwdSubscribers(
syncPassName => $syncPassName
);

$debug = " rc: ". $rc . " count: " . $count . " msg: " . $msg . " list: " . @list;
print $debug . "\n";

if ($rc == 0)
       {
          for (my $i=0; $i<$count; $i++)
          {
		   @myNames = ('system', 'account', 'acctAutoFl', 'networkAddress', 'passwordStatus', 'pendingChange', 'pendingCheck');
			foreach (@myNames)
			{
			print "$_: $list[$i]{$_}\n";
			} 
		   print "\n";
          }
       }