#!perl
use ParApi::ApiClient;
use Getopt::Long;


GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"requestID:s"   			=> \$requestID 
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $msg, %hash) = $client->getPwdRequest(
requestID => $requestID
);

$debug = " rc: ". $rc . " hash: " . %hash . " msg: " . $msg;
print $debug . "\n";

if ($rc == 0)
       {

		   @myNames = ('requestID', 'requestorName', 'phone', 'emailID', 'systemName', 'accountName', 'minApprovers', 'submittedDt', 'reqReleaseDt', 'releaseDuration', 'requestNotes', 'expiresDt', 'closeDt', 'approvedDt', 'canceledDt', 'tsName', 'ticketNbr', 'requestStatus', 'reasonCodeText', 'accessPolicyName');
			foreach (@myNames)
			{
			print " $_: $hash{$_}\n ";
			} 
		   print "\n";
          
       }