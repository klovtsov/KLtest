#!/usr/bin/perl

my $cliuserName = "q202test_user_cli"; 
my $keyFileName = "q202test_user_cli.ppk";
my $host = "10.30.44.209";

my $prefix = "yk2_";
my $numberOfAccounts = 30;
my $pause = 120;

my $commandLine = "./plink -i $keyFileName $cliuserName\@$host";

###########################################################


my $systemName = $prefix."autoperf";
my $systemNetAddr = "1.2.3.4";
my $systemPlatform = "AIX";
my $systemFuncAcct = "yk";
my $systemFuncAcctCred = "afaeqrrqASDF";

my $managedAcctDesc = "Hello, world!";
my $managedAcctName = "ykm";
my $managedAcctPwd = "afqwrqAFAafq";

my $count;

###########################################################


# Creating a managed system

system("$commandLine AddSystem --SystemName $systemName --NetworkAddress $systemNetAddr --PlatformName $systemPlatform --FunctionalAccount $systemFuncAcct --FuncAcctCred $systemFuncAcctCred --Timeout 30");


# Make sure the number of accts is multiple 3

for ($numberOfAccounts;($numberOfAccounts%3);$numberOfAccounts++) {}

print "The number of accounts in the test: $numberOfAccounts\n";



# Creating the first two thirds $numberOfAccounts accounts simultaneously.

for ($count=1; $count<=$numberOfAccounts/3*2; $count++) {

   print "Creating $managedAcctName$count...\n";
   system("$commandLine AddAccount --SystemName $systemName --AccountName $managedAcctName$count --Password $managedAcctPwd &");
   
}

sleep ($pause);


# Changing one third $numberOfAccounts & deleting one third $numberOfAccounts & adding one third $numberOfAccounts simultaneously.

$i = 1; 
$j = $numberOfAccounts/3 + 1;
$k = $numberOfAccounts/3*2 + 1;

for ($count=1; $count<=$numberOfAccounts/3; $count++) {

   print "Deleting $managedAcctName$i...\n";
   system("$commandLine DeleteAccount --SystemName $systemName --AccountName $managedAcctName$i &");

   print "Updating $managedAcctName$j...\n";
   system("$commandLine UpdateAccount --SystemName $systemName --AccountName $managedAcctName$j --Description $managedAcctDesc &");

   print "Creating $managedAcctName$k...\n";
   system("$commandLine AddAccount --SystemName $systemName --AccountName $managedAcctName$k --Password $managedAcctPwd &");

   $i++;
   $j++;
   $k++;
   
}

sleep ($pause);


# Deleting all accounts

for ($count=$numberOfAccounts/3 + 1; $count<=$numberOfAccounts; $count++) {

   print "Deleting $managedAccountName$count...\n";

   system("$commandLine DeleteAccount --SystemName $systemName --AccountName $managedAcctName$count &");
}


# Deleting the system

sleep ($pause);

system("$commandLine DeleteSystem --SystemName $systemName");

