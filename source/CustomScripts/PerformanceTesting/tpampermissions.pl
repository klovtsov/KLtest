#!/usr/bin/perl

my $cliuserName = "q202test_user_cli"; 
my $keyFileName = "q202test_user_cli.ppk";
my $host = "10.30.44.209";

my $numberOfObjects = 10;

my $commandLine = "./plink -i $keyFileName $cliuserName\@$host";

#############################


my $systemName = "yk_autoperf";
my $systemNetAddr = "1.2.3.4";
my $systemPlatform = "AIX";
my $systemFuncAcct = "yk";
my $systemFuncAcctCred = "afaeqrrqASDF";
my $systemDescription = "Hello, world!";
my $accountName = "Yuri";
my $accountPassword = "Q1w2e34sfer";
my $userName = "YuriPerf";

my $count, $start, $finish;


# Creating $numberOfObjects systems and users simultaneously.

for ($count=1; $count<=$numberOfObjects; $count++) {

   print "Creating $systemName$count and $userName$count...\n";

   system("$commandLine AddSystem --SystemName $systemName$count --NetworkAddress $systemNetAddr --PlatformName $systemPlatform --FunctionalAccount $systemFuncAcct --FuncAcctCred $systemFuncAcctCred &");

   system("$commandLine AddUser --UserName $userName$count --FirstName $userName$count --Lastname $userName$count &");
   
}

sleep (120);


# Creating $numberOfObjects accounts simultaneously.

for ($count=1; $count<=$numberOfObjects; $count++) {

   print "Creating $accountName$count...\n";

   system("$commandLine AddAccount --System $systemName$count --AccountName $accountName$count --Password $accountPassword &");
   
}

sleep (120);



# Assigning permissions for the first $numberOfObjects/2 systems/accounts simultaneously.

for ($count=1; $count<=$numberOfObjects/2; $count++) {

   print "Assigning permissions for $systemName$count and $accountName$count...\n";

   system("$commandLine SetAccessPolicy --AccessPolicyName 'Approver-Requestor' --Action ADD --SystemName $systemName$count --AccountName $accountName$count --UserName $userName$count &");

   system("$commandLine SetAccessPolicy --AccessPolicyName 'Reviewer' --Action ADD --SystemName $systemName$count --UserName $userName$count &");
   
}



sleep (120);


# Revoking permissions for the first $numberOfObjects/2 systems/accounts & assigning permissions for the next $numberOfObjects/2 systems simultaneously.

$i = 1; 
$j = $numberOfObjects/2 + 1;

for ($count=1; $count<=$numberOfObjects/2; $count++) {

   print "Revoking permissions for $systemName$i and $accountName$i...\n";

   system("$commandLine SetAccessPolicy --AccessPolicyName 'Approver-Requestor' --Action DROP --SystemName $systemName$i --AccountName $accountName$i --UserName $userName$i &");

   system("$commandLine SetAccessPolicy --AccessPolicyName 'Reviewer' --Action DROP --SystemName $systemName$i --UserName $userName$i &");


   print "Assigning permissions for $systemName$j and $accountName$j...\n";

   system("$commandLine SetAccessPolicy --AccessPolicyName 'Approver-Requestor' --Action ADD --SystemName $systemName$j --AccountName $accountName$j --UserName $userName$j &");

   system("$commandLine SetAccessPolicy --AccessPolicyName 'Reviewer' --Action ADD --SystemName $systemName$j --UserName $userName$j &");
 
   $i++;
   $j++;
   
}

sleep (120);


# Deleting all objects 

for ($count=1; $count<=$numberOfObjects; $count++) {

   print "Deleting $systemName$count and $userName$count...\n";

   system("$commandLine DeleteSystem $systemName$count &");

   system("$commandLine DeleteUser --UserName $userName$count &");
}


