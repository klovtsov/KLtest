#!perl
use strict;
use File::Path;

#  Remote storage settings 

my $remoteStorage = "\\\\morflsw01.prod.quest.corp\\RD-TPAM-DEV-BUILD\\latest\\upgrade-2.5.916\\prod";
my $versionFile = $remoteStorage."\\version.xml";

#  TPAM settings

my ($TPAM, $CLIsysadminname, $CLIsysadminkey) = @ARGV[0..2];    # "10.30.44.206"; "yksysadmin"; "id_dsa_yksysadmin_206.PPK"

my $patchlog = "patchlog.txt";
my $deploylog = "deploy.log";
my $deploylogmail = $deploylog.".mail";
my $deploylogcmd = $deploylog.".cmd";

my $EmailAddr = "yuri.kuniver\@quest.com,konstantin.lovtsov\@quest.com,alexander.buyanov\@quest.com,igor.baranov\@quest.com";

my $distrib_storage = "\\\\spb9240\\KnowledgeBase\\TPAM\\Distribs\\TPAM 2.5";

my (@distribs, $i, $line, $newbuild, $currentbuild, $newbuildavailable, $zippatchfile, $patchkey, $file, $patch_found, $key_found, $ova_found, @patchlog);



# --------------------------------------------------------------------------------------------------------------

$| = 1;

undef($patch_found);
undef($key_found);
undef($ova_found);


print "\nTPAM: $TPAM, CLIA user: $CLIsysadminname, CLIA Key: $CLIsysadminkey\n\n";


# Getting the yesterday & current date. We will need it to parse patch log and write a record to the smoke-run-log.

my ($min, $hour, $day, $month, $year) = (localtime)[1,2,3,4,5];

$year += 1900;
$month ++;
$month = "0".$month if $month < 10;
$day = "0".$day if $day < 10;
my $current_date = $year."-".$month."-".$day;  # Current date to get the latest records from the patch log  
my $current_date_time = $year."-".$month."-".$day." ".$hour.":".$min;  # Current datetime for the smoke-run-log.   

my $epochYesterday = time - 24 * 60 * 60;
($year, $month, $day) = (localtime($epochYesterday))[5,4,3];
$year += 1900;
$month ++;
$day = "0".$day if $day < 10;
my $yesterday_date = $year."-".$month."-".$day;  # Yesterday date to get the latest records from the patch log  


# Remote storage: which build is the newest one?

open (Version, $versionFile) or die "Can't read $versionFile : $!";

while (<Version>) {
	if (/<version>([\d\.]+)<\/version>/) {
		$newbuild = $1;
		last;
	}
}

close (Version);

print ("\nLatest build on the remote storage: $newbuild\n");



# If we haven't downloaded the newest build yet, let's do it now...

unless (-d $newbuild) {

	print "\nLooks like we need to get it...\n";
	system ("mkdir $newbuild");
	system("xcopy $remoteStorage $newbuild");


} else {

	print "\nLooks like we have this build donwloaded earlier...\n";
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


if	(($currentbuild =~ /^[0-9]/) && ($newbuild gt $currentbuild))  {	#	(1 == 1)  # !!!!!!!!  may need to be changed !!!!!!!!! 
	
	print "Hmm, it looks like we need to apply a new build...\n\n";

	system("echo $current_date_time Hmm, it looks like we need to apply a new build... >> smoke_history.log");

	# Going into patch directory to do some operations with "$newbuild/$file": zip patch file, keyfile

	opendir(DIR, $newbuild) or die "Can't opendir $newbuild: $!";
	while (defined($file = readdir(DIR))) {
		
		
		if ($file =~ /zip$/) {
			$zippatchfile = $file;
			print ("Patch found: $zippatchfile\n");
			$patch_found = 1;
		}
		
		if ($file =~ /key$/) {
			$file = $newbuild . "\\" . $file;
			open (KeyFile, $file) or die "Can't read $file: $!";
			$patchkey = <KeyFile>;
			close(KeyFile);
			chomp($patchkey);
			print ("Patch Key is: $patchkey\n");
			$key_found = 1;
		}

		if ($file =~ /ova$/) {
			print ("Ova file found: $file It may be a full vmware image...\n");
			$ova_found = 1;
		}

	}
	closedir(DIR);

    if ($patch_found && $key_found) {       # We have both the patch and its key
	
=comment		
		# Do we need to copy this build to our remote storage? If haven't copied it yet, let's do it now... 

		my $distrib_storage_newbuild = $distrib_storage."\\".$newbuild;

		unless (-d $distrib_storage_newbuild) {

			mkdir($distrib_storage_newbuild);
			$distrib_storage_newbuild = "\"".$distrib_storage_newbuild."\"";
			print("\nLooks like we need to copy this build to our distrib storage: $distrib_storage_newbuild \n");
			system ("xcopy $newbuild $distrib_storage_newbuild");

		}	
=cut

		# Scp patch file to the TPAM appliance

		system("..\\pscp.exe -i $CLIsysadminkey -scp $newbuild\\$zippatchfile $CLIsysadminname\@$TPAM:$zippatchfile 2>&1 1>$deploylogcmd");
		
		# Apply the patch to the TPAM appliance
		
		system ("..\\plink.exe -i $CLIsysadminkey $CLIsysadminname\@$TPAM ApplyPatch --PatchName $zippatchfile --Key $patchkey --Options /GEN 2>&1 1>$deploylogcmd");
	
		# Set up a flag to run smoke automated tests

		system("echo $current_date_time Hmm, it looks like we need to run automated tests... >> need_to_run_smoke_tests.flag");

	} else {  # We miss the patch and/or the key

		system("echo A patch and/or a key is/are not found. So there is nothing to upload... >$deploylogcmd");

		if ($ova_found) {

			system("echo Ova file is found. Please take a look at $newbuild directory: it may contain a full image build. Next time the script is not going to download this directory, so make sure it contains what you want, or delete this directory... >$deploylogcmd");

		} else {

			system("echo In $newbuild directory, we didn't find a patch/key pair or a full image like ova file. Maybe, we were downloading this build when it was in the middle of uploading to the Remote Storage. So this directory is going to be deleted to be downloaded again when the script runs next time on schedule... >$deploylogcmd");

			rmtree($newbuild);
		}
	}

	#  Email results

	my $EmailSubject = "\"AutoDeploy preliminary results: $TPAM | $newbuild\"";
	system("copy $deploylog+$deploylogcmd $deploylogmail");
	system("echo.  >>$deploylogmail");
    
	if ($patch_found && $key_found) {       # We tried to apply the patch earlier...

		system("echo In a few minutes, another e-mail will be sent with the patch log. Some time is required to apply the patch... >>$deploylogmail");

	} 

	system("..\\blat.exe $deploylogmail -server relayemea.prod.quest.corp -to $EmailAddr -f tpamresults\@tpam.com -subject $EmailSubject"); 



	if ($patch_found && $key_found) {       # We tried to apply the patch earlier, so we wait a while to see results. 

		# A pause (we wait for the patch process to be completed)

		sleep 300;

		# Let's see if the patch process is finished.

		foreach (1..10) {

			system("..\\plink.exe -i $CLIsysadminkey $CLIsysadminname\@$TPAM ViewLog --patch > $patchlog");

			open (Log, $patchlog) or die "Can't read $patchlog $! \n";
			@patchlog = <Log>;
			close(Log);

			if ($patchlog[-1] =~ "Patch successfully applied to system") {  # the last record

				# We need to reboot the appliance after installing the patch (kind of a requirement to remove a restore point)

				sleep 120;
				system("..\\plink.exe -i $CLIsysadminkey $CLIsysadminname\@$TPAM Shutdown --Reboot > $patchlog");
				sleep 360;
				
				last;   # the patch was installed, and the appliance was rebooted, so we go out
			}

			sleep 120;
		}

		# Time to filter results and send by e-mail

		open (Log, "> $deploylogmail") or die "Can't write to $deploylogmail $! \n";

		print Log "Patch log records for the last two days:\n\n";

		foreach (reverse @patchlog) {
			if (($_ =~ $current_date) || ($_ =~ $yesterday_date)){
				print Log "$_";
			}
		}

		close(Log);

		$EmailSubject = "\"AutoDeploy results (patch log): $TPAM | $newbuild\"";

		system("..\\blat.exe $deploylogmail -server relayemea.prod.quest.corp -to $EmailAddr -f tpamresults\@tpam.com -subject $EmailSubject -attacht $newbuild\\ChangeLog.txt"); 
	
	}


} else {

	system("echo $current_date_time Hmm, no new build... >> smoke_history.log");

}


#  Some cleanup

unlink ($patchlog, $deploylogmail, $deploylogcmd);


