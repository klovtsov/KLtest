#!/usr/bin/perl

my $cliuserName = "q202test_user_cli"; 
my $keyFileName = "q202test_user_cli.ppk";
my $host = "10.30.44.209";

my $systemName = "yk_autoperf";
my $systemNetAddr = "1.2.3.4";
my $systemPlatform = "AIX";
my $systemFuncAcct = "yk";
my $systemFuncAcctCred = "afaeqrrqASDF";
my $systemDescription = "Hello, world!";

my $count, $start, $finish;

my $numberOfSystems = 20;

my $commandLine = "./plink -i $keyFileName $cliuserName\@$host";


# Creating the first $numberOfSystems/2 systems simultaneously.

for ($count=1; $count<=$numberOfSystems/2; $count++) {

   print "Creating $systemName$count...\n";
   system("$commandLine AddSystem --SystemName $systemName$count --NetworkAddress $systemNetAddr --PlatformName $systemPlatform --FunctionalAccount $systemFuncAcct --FuncAcctCred $systemFuncAcctCred &");
   
}

sleep (120);


# Changing the first $numberOfSystems/2 systems & adding $numberOfSystems/2 systems simultaneously.

$i = 1; 
$j = $numberOfSystems/2 + 1;

for ($count=1; $count<=$numberOfSystems/2; $count++) {

   print "Updating $systemName$i...\n";
   system("$commandLine UpdateSystem --SystemName $systemName$i --Description $systemDescription &");
   
   print "Creating $systemName$j...\n";
   system("$commandLine AddSystem --SystemName $systemName$j --NetworkAddress $systemNetAddr --PlatformName $systemPlatform --FunctionalAccount $systemFuncAcct --FuncAcctCred $systemFuncAcctCred &");

   $i++;
   $j++;
   
}

sleep (120);


# Deleting all systems

for ($count=1; $count<=$numberOfSystems; $count++) {

   print "Deleting $systemName$count...\n";

   system("$commandLine DeleteSystem $systemName$count &");
}


