#!perl
use ParApi::ApiClient;
use Getopt::Long;

$systemName = '';              
$accountName = '';              
$password = '';                 
$changeFrequency = '';           
$releaseDuration = '';           
$autoFlag = '';                 
$changeTime = '';                
$nextChangeDate = '';            
$passwordCheckProfile = '';              
$passwordChangeProfile = '';              
$passwordRule = '';              
$changeServiceFlag = '';         
$useSelfFlag = '';               
$minimumApprovers = '';         
$domainAccountName = '';         
$releaseNotifyEmail = '';        
$checkFlag = '';                
$resetFlag = '';                 
$releaseChangeFlag = '';        
$maxReleaseDuration = '';        
$description = '';               
$reviewCount = '';               
$reviewerType = '';              
$reviewerName = '';              
$escalationTime = '';            
$escalationEmail = '';           
$lockFlag = '';                 
$blockAutoChangeFlag = '';      
$simulPrivAccReleases = '';      
$aliasAccessOnlyFlag = '';      
$custom1 = '';                   
$custom2 = '';                  
$custom3 = '';                  
$custom4 = '';                  
$custom5 = '';                  
$custom6 = '';                 
$allowISADurationFlag = '';    
$ticketSystemName = '';          
$requireTicketForRequest = '';  
$requireTicketForISA = '';      
$requireTicketForCLI = '';      
$requireTicketForAPI = '';     
$requireTicketForPSM = '';     
$ticketEmailNotify = '';        
$overrideAccountability = '';   
$ignoreSystemPoliciesFlag = ''; 
$restartServiceFlag = '';       
$changeTaskFlag = '';       
$enableBeforeReleaseFlag = '';
$accountDN = '';


GetOptions(
"keyFileName:s"						=> \$keyFileName,
"apiuserName:s"						=> \$apiuserName,
"host:s"							=> \$host,
"systemName:s"   	  				=> \$systemName,             
"accountName:s"      				=> \$accountName,             
"password:s"     					=> \$password,                
"changeFrequency:s"    				=> \$changeFrequency,          
"releaseDuration:s"      			=> \$releaseDuration,          
"autoFlag:s"      					=> \$autoFlag,                
"changeTime:s"    					=> \$changeTime,               
"nextChangeDate:s"    				=> \$nextChangeDate,           
"passwordCheckProfile:s"			=> \$passwordCheckProfile,             
"passwordChangeProfile:s"			=> \$passwordChangeProfile,             
"passwordRule:s"    				=> \$passwordRule,             
"changeServiceFlag:s"  			    => \$changeServiceFlag,        
"useSelfFlag:s"     				=> \$useSelfFlag,              
"minimumApprovers:s"     			=> \$minimumApprovers,        
"domainAccountName:s"     			=> \$domainAccountName,        
"releaseNotifyEmail:s"      		=> \$releaseNotifyEmail,       
"checkFlag:s"      					=> \$checkFlag,               
"resetFlag:s"      					=> \$resetFlag,                
"releaseChangeFlag:s"      			=> \$releaseChangeFlag,       
"maxReleaseDuration:s"      		=> \$maxReleaseDuration,       
"description:s"      				=> \$description,              
"reviewCount:s"      				=> \$reviewCount,              
"reviewerType:s"     				=> \$reviewerType,             
"reviewerName:s"     				=> \$reviewerName,             
"escalationTime:s"      			=> \$escalationTime,           
"escalationEmail:s"      			=> \$escalationEmail,          
"lockFlag:s"     					=> \$lockFlag,                
"blockAutoChangeFlag:s"      		=> \$blockAutoChangeFlag,     
"simulPrivAccReleases:s"      		=> \$simulPrivAccReleases,     
"aliasAccessOnlyFlag:s"     		=> \$aliasAccessOnlyFlag,     
"custom1:s"      					=> \$custom1,                  
"custom2:s"      					=> \$custom2,                 
"custom3:s"      					=> \$custom3,                 
"custom4:s"      					=> \$custom4,                 
"custom5:s"      					=> \$custom5,                 
"custom6:s"      					=> \$custom6,                
"allowISADurationFlag:s"      		=> \$allowISADurationFlag,   
"ticketSystemName:s"      			=> \$ticketSystemName,         
"requireTicketForRequest:s"      	=> \$requireTicketForRequest, 
"requireTicketForISA:s"      		=> \$requireTicketForISA,     
"requireTicketForCLI:s"      		=> \$requireTicketForCLI,     
"requireTicketForAPI:s"      		=> \$requireTicketForAPI,    
"requireTicketForPSM:s"      		=> \$requireTicketForPSM,    
"ticketEmailNotify:s"      			=> \$ticketEmailNotify,       
"overrideAccountability:s"      	=> \$overrideAccountability,  
"ignoreSystemPoliciesFlag:s"     	=> \$ignoreSystemPoliciesFlag,
"restartServiceFlag:s"     			=> \$restartServiceFlag,      
"changeTaskFlag:s"      			=> \$changeTaskFlag,
"enableBeforeReleaseFlag:s"  		=> \$enableBeforeReleaseFlag,
"accountDN:s"						=> \$accountDN
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $id, $msg) = $client->updateAccount(
systemName => $systemName,
accountName => $accountName,
password => $password,
changeFrequency => $changeFrequency,
releaseDuration => $releaseDuration,
autoFlag => $autoFlag,
changeTime => $changeTime,
nextChangeDate => $nextChangeDate,
passwordCheckProfile => $passwordCheckProfile,
passwordChangeProfile => $passwordChangeProfile,
passwordRule => $passwordRule,
changeServiceFlag => $changeServiceFlag,
useSelfFlag => $useSelfFlag,
minimumApprovers => $minimumApprovers,
domainAccountName => $domainAccountName,
releaseNotifyEmail => $releaseNotifyEmail,
checkFlag => $checkFlag,
resetFlag => $resetFlag,
releaseChangeFlag => $releaseChangeFlag,
maxReleaseDuration => $maxReleaseDuration,
description => $description,
reviewCount => $reviewCount,
reviewerType => $reviewerType,
reviewerName => $reviewerName,
escalationTime => $escalationTime,
escalationEmail => $escalationEmail,
lockFlag => $lockFlag,
blockAutoChangeFlag => $blockAutoChangeFlag,
simulPrivAccReleases => $simulPrivAccReleases,
aliasAccessOnlyFlag => $aliasAccessOnlyFlag,
custom1 => $custom1,
custom2 => $custom2,
custom3 => $custom3,
custom4 => $custom4,
custom5 => $custom5,
custom6 => $custom6,
allowISADurationFlag => $allowISADurationFlag,
ticketSystemName => $ticketSystemName,
requireTicketForRequest => $requireTicketForRequest,
requireTicketForISA => $requireTicketForISA,
requireTicketForCLI => $requireTicketForCLI,
requireTicketForAPI => $requireTicketForAPI,
requireTicketForPSM => $requireTicketForPSM,
ticketEmailNotify => $ticketEmailNotify,
overrideAccountability => $overrideAccountability,
ignoreSystemPoliciesFlag => $ignoreSystemPoliciesFlag,
restartServiceFlag => $restartServiceFlag,
changeTaskFlag => $changeTaskFlag,
enableBeforeReleaseFlag => $enableBeforeReleaseFlag,
accountDN => $accountDN
);

$debug = " rc: ". $rc . " id: " . $id . " msg: " . $msg;
print $debug . "\n";


