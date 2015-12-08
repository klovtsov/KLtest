#!perl
use ParApi::ApiClient;

my ($keyFileName, $apiuserName, $host) = @ARGV[0..2];
my ($rc, $id, $msg, $count, @list, $item);

my ($username, $keyType, $passphrase, $regenerate);

my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);




($rc, $msg) = $client->userSshKey();

							
#($rc, $msg) = $client->userSshKey(username => $username, keyType => $keytype, passphrase => $passphrase, regenerate => $regenerate);


print $msg;
