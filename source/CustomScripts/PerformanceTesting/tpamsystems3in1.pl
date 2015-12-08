#!/usr/bin/perl

my $cliuserName = "q202test_user_cli"; 
my $keyFileName = "q202test_user_cli.ppk";
my $host = "10.30.44.209";

my $numberOfSystems = 30;

my $commandLine = "./plink -i $keyFileName $cliuserName\@$host";

###########################################################


my $systemName = "yk_autoperf";
my $systemNetAddr = "1.2.3.4";
my $systemPlatform = "AIX";
my $systemFuncAcct = "yk";
my $systemFuncAcctCred = "afaeqrrqASDF";
my $systemDescription = "Hello, world!";

my $count;

###########################################################



# Make sure the number of systems is multiple 3

for ($numberOfSystems;($numberOfSystems%3);$numberOfSystems++) {}

print "The number of systems in the test: $numberOfSystems\n";



# Creating the first two thirds $numberOfSystems systems simultaneously.

for ($count=1; $count<=$numberOfSystems/3*2; $count++) {

   print "Creating $systemName$count...\n";
   system("$commandLine AddSystem --SystemName $systemName$count --NetworkAddress $systemNetAddr --PlatformName $systemPlatform --FunctionalAccount $systemFuncAcct --FuncAcctCred $systemFuncAcctCred &");
   
}

sleep (120);


# Changing one third $numberOfSystems & deleting one third $numberOfSystems & adding one third $numberOfSystems simultaneously.

$i = 1; 
$j = $numberOfSystems/3 + 1;
$k = $numberOfSystems/3*2 + 1;

for ($count=1; $count<=$numberOfSystems/3; $count++) {

   print "Deleting $systemName$i...\n";
   system("$commandLine DeleteSystem $systemName$i &");

   print "Updating $systemName$j...\n";
   system("$commandLine UpdateSystem --SystemName $systemName$j --Description $systemDescription &");

   print "Creating $systemName$k...\n";
   system("$commandLine AddSystem --SystemName $systemName$k --NetworkAddress $systemNetAddr --PlatformName $systemPlatform --FunctionalAccount $systemFuncAcct --FuncAcctCred $systemFuncAcctCred &");

   $i++;
   $j++;
   $k++;
   
}

sleep (120);


# Deleting all systems

for ($count=$numberOfSystems/3 + 1; $count<=$numberOfSystems; $count++) {

   print "Deleting $systemName$count...\n";

   system("$commandLine DeleteSystem $systemName$count &");
}


