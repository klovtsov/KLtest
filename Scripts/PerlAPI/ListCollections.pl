#!perl
use ParApi::ApiClient;
use Getopt::Long;

$collectionName = ''; 
$systemName = '';              
$accountName = '';
$fileName = '';
$maxRows = '';

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"collectionName:s"   		=> \$collectionName, 
"accountName:s"   		  	=> \$accountName,             
"fileName:s"				=> \$fileName,             
"systemName:s"   		  	=> \$systemName,
"maxRows:s"					=> \$maxRows
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $count, $msg,  @list) = $client->listCollections(
systemName => $systemName,
accountName => $accountName,
fileName => $fileName,
collectionName => $collectionName,
maxRows	=> $maxRows
);

$debug = " rc: ". $rc . " count: " . $count . " msg: " . $msg . " list: " . @list;
print $debug . "\n";

if ($rc == 0) {
	for (my $i=0; $i<$count; $i++) {
		@myNames = ('collectionName', 'collectionDesc', 'psmDpaAffinity');
		foreach (@myNames) {
			print " $_: $list[$i]{$_}\n ";
		} 
		print "\n";
	}
}
