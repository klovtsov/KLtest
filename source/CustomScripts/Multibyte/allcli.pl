#!perl
use ParApi::ApiClient;

my ($keyFileName, $apiuserName, $host) = @ARGV[0..2];
my ($rc, $count, $msg, @results, $item);


my $tpam = "10.30.44.209";
my $user_cli = "ykcli_isa";
my $key_cli = "id_dsa_ykcli_isa.ppk";
my $user_api = "ykapiadmin";
my $key_api = "id_dsa_ykapiadmin209";


my $system = "ykspb9449";
my $account = "yk65";
my $inputdata = "descriptionsutf8.txt";

my @descriptions, $descr, $descrout, $command, $sshver, $res;



my $client = ParApi::ApiClient->new(host        => $tpam,
                                    keyFileName => $key_api,
                                    userName    => $user_api);


#open (Descriptions, $inputdata) or die "Can't open descriptions: $!";
#@descriptions = <Descriptions>;
#close (Descriptions);

@descriptions = ("\xE4\xB6\xB0", "\xE2\x82\xAC", "\xEB\xA4\x97",
		"\xEB\x82\x88","\xEB\x82\x88\xEB\xA0\xBF","\xC2\xA2", 
		"\xA2\xC2","\xA2\xA2","\xC2\xC2","\xC0","\xC1","\xC2\xC0",
		"\xF0\xA4\xAD\xA2", "\xAE", "\xAE\x6E\xB5\x4B", 
		"\xF0\xA4\xAD\xA2\xF0\xA4\xAD\xA2\x24\x3F\x31\x41\xEB\x82\x88\x3F\x3F\x31\xE2\x82\xAC\xC2\xA3");
     

foreach $descr (@descriptions) {

	chomp($descr);
	print "\nDescription (input): $descr\n";
	$res = `plink.exe -i $key_cli $user_cli\@$tpam \"updateaccount --systemname $system --accountname $account --Description \\\"$descr\\\"\"`;
	print "UpdateAccount Output: $res\n";

	($rc, $count, $msg, @results ) = $client->listAccounts(systemName => $system, accountName => $account);
        
	if (($rc == 0) && ($count == 1))
        {

 		$descrout = $results[0]{description};
		chomp($descrout);

          	print "listAccounts: The description for $system/$account: $descrout\n"; 

          
		if ($descr eq $descrout) {
			print "Matched!\n";
		} else {
			print "Not matched!\n";
      		}

        }
        else
        {
          print "Unexpected result for listAccounts: $msg\n";
        }
	print "\n=============\n";

}

