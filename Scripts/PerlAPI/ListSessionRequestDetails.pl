#!perl
use ParApi::ApiClient;
use Getopt::Long;

    $status= ''; 
	$requestorName= ''; 
    $accountName= ''; 
	$systemName= ''; 
	$startDate= ''; 
    $endDate= ''; 
	$maxRows= ''; 

GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"status:s"   		  		=> \$status,             
"requestorName:s"      		=> \$requestorName,   
"accountName:s"   		  	=> \$accountName,             
"systemName:s"      		=> \$systemName,  
"startDate:s"   		  	=> \$startDate,             
"endDate:s"      			=> \$endDate,  
"maxRows:s"      			=> \$maxRows  
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $count, $msg,  @list) = $client->listSessionRequestDetails(
status => $status,
requestorName => $requestorName,
accountName => $accountName,
systemName => $systemName,
startDate => $startDate,
endDate => $endDate,
maxRows => $maxRows
);

$debug = " rc: ". $rc . " count: " . $count . " msg: " . $msg . " list: " . @list;
print $debug . "\n";

if ($rc == 0)
       {
          for (my $i=0; $i<$count; $i++)
          {
		   @myNames = ('requestID', 'userName', 'userFullName', 'phone', 'emailID', 'systemName', 'accountName', 'minPasswordApprovers', 'minSessionApprovers', 'submittedDt', 'reqReleaseDt', 'releaseDuration', 'requestNotes', 'expiresDt', 'closeDt', 'approvedDt', 'canceledDt', 'tsName', 'ticketNbr', 'status', 'reasonCodeText', 'accessPolicyName');
			foreach (@myNames)
			{
			print " $_: $list[$i]{$_}\n ";
			} 
		   print "\n";
          }
       }
