#!perl
use ParApi::ApiClient;
use Getopt::Long;

$password= '';
$changeFrequency= '';
$releaseDuration= '';
$changeTime= '';
$nextChangeDate= '';
$passwordRule= '';
$releaseNotifyEmail= '';
$checkFlag= '';
$resetFlag= '';
$releaseChangeFlag= '';
$description= '';
$disableFlag= '';
	
GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"syncPassName:s"			=> \$syncPassName,
"password:s"				=> \$password,
"changeFrequency:s"			=> \$changeFrequency,
"releaseDuration:s"			=> \$releaseDuration,
"changeTime:s"				=> \$changeTime,
"nextChangeDate:s"			=> \$nextChangeDate,
"passwordRule:s"			=> \$passwordRule,
"releaseNotifyEmail:s"		=> \$releaseNotifyEmail,
"checkFlag:s"				=> \$checkFlag,
"resetFlag:s"				=> \$resetFlag,
"releaseChangeFlag:s"		=> \$releaseChangeFlag,
"description:s"				=> \$description,
"disableFlag:s"				=> \$disableFlag
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $id, $msg) = $client->updateSyncPass(
syncPassName => $syncPassName,
 password => $password,
 changeFrequency => $changeFrequency,
 releaseDuration => $releaseDuration,
 changeTime => $changeTime,
 nextChangeDate => $nextChangeDate,
 passwordRule => $passwordRule,
 releaseNotifyEmail => $releaseNotifyEmail,
 checkFlag => $checkFlag,
 resetFlag => $resetFlag,
 releaseChangeFlag => $releaseChangeFlag,
 description => $description,
 disableFlag => $disableFlag
);

$debug = " rc: ". $rc . " id: " . $id . " msg: " . $msg;
print $debug . "\n";