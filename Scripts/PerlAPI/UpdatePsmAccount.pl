#!perl
use ParApi::ApiClient;
use Getopt::Long;

$system= '';
$account= '';
$enableflag= '';
$minapprovers= '';
$passwordmethod= '';
$proxytype= '';
$parcliusername= '';
$defaultsessionduration= '';
$maxsessioncount= '';
$notifythreshold= '';
$notifyfrequency= '';
$colordepth= '';
$dsskeytype= '';
$dsskeyname= '';
$dsskey= '';
$domainaccount= '';
$filetranstype= '';
$filetranspath= '';
$filetransauthmethod= '';
$reviewcount= '';
$reviewertype= '';
$reviewername= '';
$escalationtime= '';
$escalationemail= '';
$sessionstartnotifyemail= '';
$clipboardflag= '';
$consoleflag= '';
$recordingRequiredFlag= '';
$filetransupflag= '';
$filetransdownflag= '';
$clisystemname= '';
$cliaccountname= '';
$clidomainname= '';
$connectionprofile= '';
$postsessionprofile= '';
	
GetOptions(
"keyFileName:s"  	 		=> \$keyFileName,
"apiuserName:s"  			=> \$apiuserName,
"host:s"  					=> \$host,
"system:s"					=> \$system,
"account:s"					=> \$account,
"enableflag:s"				=> \$enableflag,
"minapprovers:s"			=> \$minapprovers,
"passwordmethod:s"			=> \$passwordmethod,
"proxytype:s"				=> \$proxytype,
"parcliusername:s"			=> \$parcliusername,
"defaultsessionduration:s"	=> \$defaultsessionduration,
"maxsessioncount:s"			=> \$maxsessioncount,
"notifythreshold:s"			=> \$notifythreshold,
"notifyfrequency:s"			=> \$notifyfrequency,
"colordepth:s"				=> \$colordepth,
"dsskeytype:s"				=> \$dsskeytype,
"dsskeyname:s"				=> \$dsskeyname,
"dsskey:s"					=> \$dsskey,
"domainaccount:s"			=> \$domainaccount,
"filetranstype:s"			=> \$filetranstype,
"filetranspath:s"			=> \$filetranspath,
"filetransauthmethod:s"		=> \$filetransauthmethod,
"reviewcount:s"				=> \$reviewcount,
"reviewertype:s"			=> \$reviewertype,
"reviewername:s"			=> \$reviewername,
"escalationtime:s"			=> \$escalationtime,
"escalationemail:s"			=> \$escalationemail,
"sessionstartnotifyemail:s"	=> \$sessionstartnotifyemail,
"clipboardflag:s"			=> \$clipboardflag,
"consoleflag:s"				=> \$consoleflag,
"recordingRequiredFlag:s"	=> \$recordingRequiredFlag,
"filetransupflag:s"			=> \$filetransupflag,
"filetransdownflag:s"		=> \$filetransdownflag,
"clisystemname:s"			=> \$clisystemname,
"cliaccountname:s"			=> \$cliaccountname,
"clidomainname:s"			=> \$clidomainname,
"connectionprofile:s"		=> \$connectionprofile,
"postsessionprofile:s"		=> \$postsessionprofile
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $id, $msg) = $client->updatePsmAccount(
system => $system,
 account => $account,
 enableflag => $enableflag,
 minapprovers => $minapprovers,
 passwordmethod => $passwordmethod,
 proxytype => $proxytype,
 parcliusername => $parcliusername,
 defaultsessionduration => $defaultsessionduration,
 maxsessioncount => $maxsessioncount,
 notifythreshold => $notifythreshold,
 notifyfrequency => $notifyfrequency,
 colordepth => $colordepth,
 dsskeytype => $dsskeytype,
 dsskeyname => $dsskeyname,
 dsskey => $dsskey,
 domainaccount => $domainaccount,
 filetranstype => $filetranstype,
 filetranspath => $filetranspath,
 filetransauthmethod => $filetransauthmethod,
 reviewcount => $reviewcount,
 reviewertype => $reviewertype,
 reviewername => $reviewername,
 escalationtime => $escalationtime,
 escalationemail => $escalationemail,
 sessionstartnotifyemail => $sessionstartnotifyemail,
 clipboardflag => $clipboardflag,
 consoleflag => $consoleflag,
 recordingRequiredFlag => $recordingRequiredFlag,
 filetransupflag => $filetransupflag,
 filetransdownflag => $filetransdownflag,
 clisystemname => $clisystemname,
 cliaccountname => $cliaccountname,
 clidomainname => $clidomainname,
 connectionprofile => $connectionprofile,
 postsessionprofile => $postsessionprofile
);

$debug = " rc: ". $rc . " id: " . $id . " msg: " . $msg;
print $debug . "\n";