#!perl
use ParApi::ApiClient;
use Switch;

my ($keyFileName, $apiuserName, $host, $operation) = @ARGV[0..3];
my ($rc, $id, $msg);

my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);

$client->setCommandTimeout(120);  # set timeout to 120 seconds


switch ($operation)	{				# $rc, $id, and $msg contain command execution results.			

	case ("addUser") {

		my ($userName, $lastName, $firstName) = @ARGV[4..6];
#		print "addUser: $userName $lastName $firstName \n";
										
		# Let's add a new user.  Attributes must be set via the constructor.
		($rc, $id, $msg) = $client->addUser(userName  => $userName,
											lastName  => $lastName,
											firstName => $firstName);
	} 									


	case ("deleteUser") {
	
		my ($userName) = @ARGV[4];
		($rc, $id, $msg) = $client->deleteUser(userName  => $userName);
#		print "deleteUser: $userName \n";
	}


	case ("addSystem") {

		my ($systemName, $networkAddress, $platformName, $functionalAccount, $funcAcctCred, $timeout) = @ARGV[4..9];
		($rc, $id, $msg) = $client->addSystem(systemName  => $systemName,
											  networkAddress  => $networkAddress,
											  platformName => $platformName,
											  functionalAccount => $functionalAccount,
										      funcAcctCred => $funcAcctCred,
										      timeout => $timeout);
			 	
#		print "addSystem: $systemName, $networkAddress, $platformName, $functionalAccount, $funcAcctCred \n";
	}


	case ("addSystemWinAD") {

		my ($systemName, $networkAddress, $platformName, $functionalAccount, $funcAcctCred, $timeout, $domainName, $netBIOSName) = @ARGV[4..11];
		($rc, $id, $msg) = $client->addSystem(systemName  => $systemName,
											  networkAddress  => $networkAddress,
											  platformName => $platformName,
											  functionalAccount => $functionalAccount,
										      funcAcctCred => $funcAcctCred,
											  domainName => $domainName,
											  netBIOSName => $netBIOSName,
										      timeout => $timeout);
			 	
	}

	case ("addSystemCisco") {

		my ($systemName, $networkAddress, $platformName, $functionalAccount, $funcAcctCred, $timeout, $enablePassword) = @ARGV[4..10];
		($rc, $id, $msg) = $client->addSystem(systemName  => $systemName,
											  networkAddress  => $networkAddress,
											  platformName => $platformName,
											  functionalAccount => $functionalAccount,
										      funcAcctCred => $funcAcctCred,
											  enablePassword => $enablePassword,
										      timeout => $timeout);
			 	
	}


	case ("addSystemOracle") {

		my ($systemName, $networkAddress, $platformName, $functionalAccount, $funcAcctCred, $timeout, $portNumber, $oracleType, $oracleSIDSN) = @ARGV[4..12];
		($rc, $id, $msg) = $client->addSystem(systemName  => $systemName,
											  networkAddress  => $networkAddress,
											  platformName => $platformName,
											  functionalAccount => $functionalAccount,
										      funcAcctCred => $funcAcctCred,
											  portNumber => $portNumber,
											  oracleType => $oracleType,
											  oracleSIDSN => $oracleSIDSN,
										      timeout => $timeout);
			 	
	}


	case ("addSystemMySQL") {

		my ($systemName, $networkAddress, $platformName, $functionalAccount, $funcAcctCred, $timeout, $portNumber) = @ARGV[4..10];
		($rc, $id, $msg) = $client->addSystem(systemName  => $systemName,
											  networkAddress  => $networkAddress,
											  platformName => $platformName,
											  functionalAccount => $functionalAccount,
										      funcAcctCred => $funcAcctCred,
											  portNumber => $portNumber,
										      timeout => $timeout);
			 	
	}

	case ("addSystemNISPlus") {

		my ($systemName, $networkAddress, $platformName, $functionalAccount, $funcAcctCred, $timeout, $domainName) = @ARGV[4..10];
		($rc, $id, $msg) = $client->addSystem(systemName  => $systemName,
											  networkAddress  => $networkAddress,
											  platformName => $platformName,
											  functionalAccount => $functionalAccount,
										      funcAcctCred => $funcAcctCred,
											  domainName => $domainName,
										      timeout => $timeout);
			 	
	}

	case ("addSystemLDAP") {

		my ($systemName, $networkAddress, $platformName, $functionalAccount, $funcAcctCred, $domainName, $funcAcctDN, $timeout) = @ARGV[4..11];
		($rc, $id, $msg) = $client->addSystem(systemName  => $systemName,
											  networkAddress  => $networkAddress,
											  platformName => $platformName,
											  functionalAccount => $functionalAccount,
										      funcAcctCred => $funcAcctCred,
											  domainName => $domainName, 
											  funcAcctDN => $funcAcctDN, 
										      timeout => $timeout);
			 	
#		print "addSystemLDAP: $systemName, $networkAddress, $platformName, $functionalAccount, $funcAcctCred \n";
	}


	
	case ("deleteSystem") {

		my ($systemName, $networkAddress, $platformName, $functionalAccount, $funcAcctCred) = @ARGV[4..8];
		($rc, $id, $msg) = $client->deleteSystem(systemName  => $systemName);
#		print "deleteSystem: $systemName \n";
	}


	case ("addAccount") {

		my ($systemName, $accountName, $password, $nextChangeDate) = @ARGV[4..7];
										
		($rc, $id, $msg) = $client->addAccount(systemName  => $systemName,
											   accountName  => $accountName,
											   password => $password,
											   nextChangeDate => $nextChangeDate);

#		print "addAccount: $systemName $accountName $password \n";

	} 									

	case ("addPpmAccount") {

		my ($systemName, $accountName, $password, $passwordCheckProfile, $passwordChangeProfile) = @ARGV[4..8];
										
		($rc, $id, $msg) = $client->addAccount(systemName  => $systemName,
											   accountName  => $accountName,
											   password => $password,
											   passwordChangeProfile => $passwordChangeProfile,
											   passwordCheckProfile => $passwordCheckProfile);

#		print "addPpmAccount: $systemName $accountName $password \n";

	}

	case ("addPpmAccountLDAP") {

		my ($systemName, $accountName, $password, $passwordCheckProfile, $passwordChangeProfile, $accountDN) = @ARGV[4..9];
										
		($rc, $id, $msg) = $client->addAccount(systemName  => $systemName,
											   accountName  => $accountName,
											   password => $password,
											   accountDN => $accountDN,
											   passwordChangeProfile => $passwordChangeProfile,
											   passwordCheckProfile => $passwordCheckProfile);

#		print "addPpmAccountLDAP: $systemName $accountName $password \n";

	}



	case ("deleteAccount") {

		my ($systemName, $accountName) = @ARGV[4..5];
										
		($rc, $id, $msg) = $client->deleteAccount(systemName  => $systemName,
											   accountName  => $accountName);

#		print "deleteAccount: $systemName $accountName \n";

	} 									


	case ("testSystem") {

		my ($systemName) = @ARGV[4];
										
		($rc, $id, $msg) = $client->testSystem(systemName  => $systemName);

#		print "testSystem: $systemName \n";

	} 									


	case ("setPassword") {

		my ($systemName, $accountName, $password) = @ARGV[4..6];
										
		($rc, $id, $msg) = $client->updateAccount(systemName  => $systemName,
											   accountName  => $accountName,
											   password => $password);

#		print "setPassword (updateAccount): $systemName $accountName $password \n";

	} 									

	case ("scheduleResetPassword") {

		my ($systemName, $accountName, $changeTime) = @ARGV[4..6];
		
		($rc, $id, $msg) = $client->updateAccount(systemName  => $systemName,
											   accountName  => $accountName,
											   changeTime => $changeTime);

#		print "scheduleReseetPassword (updateAccount): $systemName $accountName $changeTime \n";
	
	}


	case ("setDescription") {

		my ($systemName, $accountName, $description) = @ARGV[4..6];
										
		($rc, $id, $msg) = $client->updateAccount(systemName  => $systemName,
											   accountName  => $accountName,
											   description => $description);

#		print "setDescription (updateAccount): $systemName $accountName $description \n";

	}
	
	
	case ("resetPassword") {

		my ($systemName, $accountName) = @ARGV[4..5];
										
		($rc, $id, $msg) = $client->forceReset(systemName  => $systemName,
											   accountName  => $accountName);

#		print "resetPassword (forceReset): $systemName $accountName \n";

	} 									

	
	case ("checkPassword") {

		my ($systemName, $accountName) = @ARGV[4..5];
										
		($rc, $id, $msg) = $client->checkPassword(systemName  => $systemName,
											   accountName  => $accountName);

#		print "checkPassword: $systemName $accountName \n";

	}
	
	
	
	case ("updateSystem") {

		my ($systemName, $networkAddress) = @ARGV[4..5];
		($rc, $id, $msg) = $client->updateSystem(systemName  => $systemName,
											  networkAddress  => $networkAddress);
			 	
#		print "updateSystem: $systemName, $networkAddress \n";
	}


	
	case ("setAccessPolicy") {

		my ($accessPolicyName, $action, $systemName, $accountName, $userName) = @ARGV[4..8];
		($rc, $id, $msg) = $client->setAccessPolicy(accessPolicyName => $accessPolicyName,
												 action => $action,
												 systemName  => $systemName, 
	  										     accountName  => $accountName,
												 userName => $userName);
			 	
#		print "setAccessPolicy: $accessPolicyName, $action, $systemName, $accountName, $userName \n";
	}


	case ("addPwdRequest") {

		my ($systemName, $accountName, $forUserName, $requestNotes) = @ARGV[4..7];
										
		($rc, $id, $msg) = $client->addPwdRequest(systemName  => $systemName,
											   accountName  => $accountName,
										       forUserName => $forUserName,
										       requestNotes => $requestNotes);

#		print "addPwdRequest: $systemName $accountName $forUserName $requestNotes \n";
	}


	case ("approve") {

		my ($requestID, $comment) = @ARGV[4..5];
		($rc, $id, $msg) = $client->approve(requestID => $requestID,
											comment  => $comment);
			 	
#		print "approve: $requestID $comment \n";
	}


	case ("retrieve") {

#		my ($systemName, $accountName) = @ARGV[4..5];
#		($rc, $id, $msg) = $client->retrieve(systemName => $systemName,
#											 accountName  => $accountName);
			 	
#		($rc, $msg) = $client->retrieve(systemName => $systemName, accountName  => $accountName);


		my ($requestID) = @ARGV[4];
		($rc, $msg) = $client->retrieve(requestID => $requestID);



#		print "retrieve: $systemName $accountName \n";
	}


	case ("retrieveISA") {

		my ($systemName, $accountName) = @ARGV[4..5];
		($rc, $id, $msg) = $client->retrieve(systemName => $systemName,
											 accountName  => $accountName);
			 	
		($rc, $msg) = $client->retrieve(systemName => $systemName, accountName  => $accountName);

#		print "retrieve: $systemName $accountName \n";
	}




	case ("retrieveWithTicket") {

		my ($systemName, $accountName, $ticketSystemName, $ticketNumber) = @ARGV[4..7];
		($rc, $id, $msg) = $client->retrieve(systemName => $systemName,
											 accountName  => $accountName, ticketSystemName => $ticketSystemName, ticketNumber => $ticketNumber);
			 	
		($rc, $msg) = $client->retrieve(systemName => $systemName, accountName => $accountName, ticketSystemName => $ticketSystemName, ticketNumber => $ticketNumber);

#		print "retrieve: $systemName $accountName \n";
	}


	case ("userSSHKey") {

		my ($userName, $keyType, $doRegenerate) = @ARGV[4..6];
				 	
		($rc, $msg) = $client->userSshKey(username => $userName, keyType  => $keyType, regenerate => $doRegenerate);

	}




	
	else {
		
		print "Unknown operation... \n";
		exit;
	}
}


if (($operation ne "retrieve") && ($operation ne "addPwdRequest") && ($operation ne "userSSHKey")) {

	$debug = " rc: ". $rc . " id: " . $id . " msg: " . $msg;
	print $debug . "\n";
	
} else {
	print $msg;
}

