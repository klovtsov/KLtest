#!perl
use strict;

#  FTP settings 

my $FTPkey = "quest_spb.ppk";
my $FTPuser = "quest-spb";
my $FTPpassword = "Q1w2e3r4t5";
my $FTPhost = "24.199.213.35";
my $FTPcommands = "ftpcommands.txt";
my $FTPoutput = "ftpoutput.txt";
my $FTPfilelist ="";


#  TPAM settings

my ($TPAM, $CLIsysadminname, $CLIsysadminkey) = @ARGV[0..2];    # "10.30.44.206"; "yksysadmin"; "id_dsa_yksysadmin_206.PPK"

my $patchlog = "patchlog.txt";
my $deploylog = "deploy.log";
my $deploylogmail = $deploylog.".mail";
my $deploylogcmd = $deploylog.".cmd";

my $EmailAddr = "yuri.kuniver\@quest.com,konstantin.lovtsov\@quest.com,alexander.buyanov\@quest.com,igor.baranov\@quest.com";

my $distrib_storage = "\\\\spb9240\\KnowledgeBase\\TPAM\\Distribs\\TPAM 2.5";

my (@distribs, $i, $line, $newbuild, $currentbuild, $newbuildavailable, $zippatchfile, $patchkey, $file, $patch_found, $key_found, @patchlog);



# --------------------------------------------------------------------------------------------------------------

$| = 1;

undef($patch_found);
undef($key_found);

print "\nTPAM: $TPAM, CLIA user: $CLIsysadminname, CLIA Key: $CLIsysadminkey\n\n";




#  SFTP server: which build is the newest one?

open (List, "> $FTPcommands") or die "Can't write to $FTPcommands";
print List "cd tpam\ndir";
close (List);

system ("..\\psftp.exe -i $FTPkey -b $FTPcommands -bc -pw $FTPpassword $FTPuser\@$FTPhost > $FTPoutput");

print "Builds available on SFTP:\n";

open (List, $FTPoutput) or die "Can't read $FTPoutput $! \n";

while (<List>) {
	
    if (/(\d[\d\.]+)$/) {
		push(@distribs,$1);
	}
}

close(List);

my @sorted_distribs = sort { $b cmp $a } @distribs;

foreach $line (@sorted_distribs) {
	print "$line\n";
}

$newbuild = $sorted_distribs[0];
print ("\nLatest build on the SFTP server: $newbuild\n");



# SFTP server: if we haven't downloaded the newest build yet, let's do it now...

unless (-d $newbuild) {

	print "\nLooks like we need to get it...\n";
	system ("mkdir $newbuild");

	open (List, "> $FTPcommands") or die "Can't write to $FTPcommands";
	print List "cd tpam\/$newbuild\n\dir";
	close (List);

	system ("..\\psftp.exe -i $FTPkey -b $FTPcommands -bc -pw $FTPpassword $FTPuser\@$FTPhost > $FTPoutput");

	open (List, $FTPoutput) or die "Can't read $FTPoutput $! \n";

	while (<List>) {
		
		if (/^-rw.+\s([^\s]+)$/) {	
			print "File found: $1\n";
			$FTPfilelist = $FTPfilelist . "get $1\n";
		}
	}

	close (List);
				
	open (List, "> $FTPcommands") or die "Can't write to $FTPcommands";
	print List "lcd $newbuild\ncd tpam\/$newbuild\n\dir\n$FTPfilelist";
	close (List);

	system ("..\\psftp.exe -i $FTPkey -b $FTPcommands -bc -pw $FTPpassword $FTPuser\@$FTPhost > $FTPoutput");

} else {

	print "\nLooks like we have this build donwloaded earlier...\n";
}




# Do we need to copy this build to our remote storage? If haven't copied it yet, let's do it now... 


my $distrib_storage_newbuild = $distrib_storage."\\".$newbuild;

unless (-d $distrib_storage_newbuild) {

	mkdir($distrib_storage_newbuild);
	$distrib_storage_newbuild = "\"".$distrib_storage_newbuild."\"";
	print("\nLooks like we need to copy this build to our distrib storage: $distrib_storage_newbuild \n");
	system ("xcopy $newbuild $distrib_storage_newbuild");

}	



#  TPAM appliance: which build is the current? 

system("..\\plink.exe -i $CLIsysadminkey $CLIsysadminname\@$TPAM ViewLog --patch > $patchlog");

open (Log, $patchlog) or die "Can't read $patchlog $! \n";

while (<Log>) {
	
    if (/.+Committed new version number: ([\d\.]+)/) {
		$currentbuild = $1;
	}
}
close(Log);

print ("\nCurrent TPAM build on $TPAM is $currentbuild\n\n");



#  If we get a new build, time to do something ....


if	(1 == 1) {			#	($newbuild gt $currentbuild) {    # !!!!!!!!   change it !!!!!!!!! 
	
	print "Hmm, it looks like we need to apply a new build...\n\n";

	# Going into patch directory to do some operations with "$newbuild/$file": zip patch file, keyfile

	opendir(DIR, $newbuild) or die "Can't opendir $newbuild: $!";
	while (defined($file = readdir(DIR))) {
		
		
		if ($file =~ "zip") {
			$zippatchfile = $file;
			print ("Patch found: $zippatchfile\n");
			$patch_found = 1;
		}
		
		if ($file =~ "key") {
			$file = $newbuild . "\\" . $file;
			open (KeyFile, $file) or die "Can't read $file: $!";
			$patchkey = <KeyFile>;
			close(KeyFile);
			chomp($patchkey);
			print ("Patch Key is: $patchkey\n");
			$key_found = 1;
		}

	}
	closedir(DIR);

    if ($patch_found && $key_found) {       # We have both the patch and its key
	
		# Scp patch file to the TPAM appliance

		system("..\\pscp.exe -i $CLIsysadminkey -scp $newbuild\\$zippatchfile $CLIsysadminname\@$TPAM:$zippatchfile 2>&1 1>$deploylogcmd");
		
		# Apply the patch to the TPAM appliance
		
		system ("..\\plink.exe -i $CLIsysadminkey $CLIsysadminname\@$TPAM ApplyPatch --PatchName $zippatchfile --Key $patchkey --Options /GEN 2>&1 1>$deploylogcmd");

	} else {  # We miss the patch and/or the key

		system("echo A patch and/or a key is/are not found. So there is nothing to upload >$deploylogcmd");
	}

}


#  Email results

my $EmailSubject = "\"AutoDeploy preliminary results: $TPAM\"";
system("copy $deploylog+$deploylogcmd $deploylogmail");
system("echo.  >>$deploylogmail");
system("echo In a few minutes, another e-mail will be sent with the patch log. Some time is required to apply the patch... >>$deploylogmail");
system("..\\blat.exe $deploylogmail -server relayemea.prod.quest.corp -to $EmailAddr -f tpamresults\@tpam.com -subject $EmailSubject"); 


# A pause (we wait for the patch process to be completed)

sleep 300;

# Let's see if the patch process is finished.

foreach (1..10) {

	system("..\\plink.exe -i $CLIsysadminkey $CLIsysadminname\@$TPAM ViewLog --patch > $patchlog");

	open (Log, $patchlog) or die "Can't read $patchlog $! \n";
	@patchlog = <Log>;
	close(Log);

	last if $patchlog[-1] =~ "Patch successfully applied to system";  # the last record

	sleep 120;
}


# Time to filter results and send by e-mail


my ($day, $month, $year) = (localtime)[3,4,5];
$year += 1900;
$month ++;
$month = "0".$month if $month < 10;
$day = "0".$day if $day < 10;
my $current_date = $year."-".$month."-".$day;  # Current date to get the latest records from the patch log  

my $epochYesterday = time - 24 * 60 * 60;
($year, $month, $day) = (localtime($epochYesterday))[5,4,3];
$year += 1900;
$month ++;
$day = "0".$day if $day < 10;
my $yesterday_date = $year."-".$month."-".$day;  # Yesterday date to get the latest records from the patch log  



open (Log, "> $deploylogmail") or die "Can't write to $deploylogmail $! \n";

print Log "Patch log records for the last two days:\n\n";

foreach (reverse @patchlog) {
	if (($_ =~ $current_date) || ($_ =~ $yesterday_date)){
		print Log "$_";
	}
}

close(Log);

$EmailSubject = "\"AutoDeploy results (patch log): $TPAM\"";

system("..\\blat.exe $deploylogmail -server relayemea.prod.quest.corp -to $EmailAddr -f tpamresults\@tpam.com -subject $EmailSubject"); 


#  Some cleanup

unlink ($FTPcommands, $FTPoutput, $patchlog, $deploylogmail, $deploylogcmd);


