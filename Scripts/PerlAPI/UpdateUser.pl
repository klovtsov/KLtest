
#!perl
use ParApi::ApiClient;
use Getopt::Long;
#CAUTION: if user property was not specified it will be cleared.
 $userName= '';  
 $lastName= '';  
 $firstName= '';  
 $email= ''; 
 $phone= ''; 
 $pager= ''; 
 $userType= ''; 
 $disable= ''; 
 $externalAuth= ''; 
 $externalUserID= ''; 
 $logonHoursFlag= ''; 
 $logonHours= ''; 
 $logonDays= ''; 
 $primaryAuthType= ''; 
 $primaryAuthID= ''; 
 $primaryAuthSystem= ''; 
 $externalAuthSystem= ''; 
 $description= ''; 
 $mobileAllowedFlag= ''; 
 $localTimeZone= ''; 
 $dstFlag= ''; 
 $secondaryAuth= ''; 
 $secondaryUserID= ''; 
 $secondaryAuthSystem= ''; 
 $primaryAuthExtra= ''; 
 $custom1= ''; 
 $custom2= ''; 
 $custom3= ''; 
 $custom4= ''; 
 $custom5= ''; 
 $custom6= ''; 
 $mobile= '';
 $certThumbprint= ''; 


GetOptions(
"keyFileName=s"  	 		=> \$keyFileName,
"apiuserName=s"  			=> \$apiuserName,
"host=s"  					=> \$host,
"userName=s"  				=> \$userName,  
"lastName:s"  				=> \$lastName,  
"firstName:s"  			    => \$firstName,  
"email:s" 					=> \$email, 
"phone:s" 					=> \$phone, 
"pager:s" 					=> \$pager, 
"userType:s" 				=> \$userType, 
"disable:s" 				=> \$disable, 
"externalAuth:s" 			=> \$externalAuth, 
"externalUserID:s" 		    => \$externalUserID, 
"logonHoursFlag:s" 		    => \$logonHoursFlag, 
"logonHours:s" 			    => \$logonHours, 
"logonDays:s" 				=> \$logonDays, 
"primaryAuthType:s" 		=> \$primaryAuthType, 
"primaryAuthID:s" 			=> \$primaryAuthID, 
"primaryAuthSystem:s" 		=> \$primaryAuthSystem, 
"externalAuthSystem:s"		=> \$externalAuthSystem, 
"description:s" 			=> \$description, 
"mobileAllowedFlag:s" 		=> \$mobileAllowedFlag, 
"localTimeZone:s" 			=> \$localTimeZone, 
"dstFlag:s" 				=> \$dstFlag, 
"secondaryAuth:s" 			=> \$secondaryAuth, 
"secondaryUserID:s" 		=> \$secondaryUserID, 
"secondaryAuthSystem:s" 	=> \$secondaryAuthSystem, 
"primaryAuthExtra:s" 		=> \$primaryAuthExtra, 
"custom1:s" 				=> \$custom1, 
"custom2:s" 				=> \$custom2, 
"custom3:s" 				=> \$custom3, 
"custom4:s" 				=> \$custom4, 
"custom5:s" 				=> \$custom5, 
"custom6:s" 				=> \$custom6, 
"mobile:s" 				=> \$mobile,
"certThumbprint:s" 		=> \$certThumbprint 
);
	
my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);
									
$client->setCommandTimeout(120);  # set timeout to 120 seconds

($rc, $id, $msg) = $client->updateUser(
 userName => $userName,  
 lastName => $lastName,  
 firstName => $firstName,  
 email => $email, 
 phone => $phone, 
 pager => $pager, 
 userType => $userType,  
 disable => $disable, 
 externalAuth => $externalAuth, 
 externalUserID => $externalUserID, 
 logonHoursFlag => $logonHoursFlag, 
 logonHours => $logonHours, 
 logonDays => $logonDays, 
 primaryAuthType => $primaryAuthType, 
 primaryAuthID => $primaryAuthID, 
 primaryAuthSystem => $primaryAuthSystem, 
 externalAuthSystem=> $externalAuthSystem, 
 description => $description, 
 mobileAllowedFlag => $mobileAllowedFlag, 
 localTimeZone => $localTimeZone, 
 dstFlag => $dstFlag,  
 secondaryAuth => $secondaryAuth, 
 secondaryUserID => $secondaryUserID, 
 secondaryAuthSystem => $secondaryAuthSystem, 
 primaryAuthExtra => $primaryAuthExtra, 
 custom1 => $custom1, 
 custom2 => $custom2, 
 custom3 => $custom3, 
 custom4 => $custom4, 
 custom5 => $custom5, 
 custom6 => $custom6, 
 mobile => $mobile,
 certThumbprint => $certThumbprint 
);

$debug = " rc: ". $rc . " id: " . $id . " msg: " . $msg;
print $debug . "\n";
