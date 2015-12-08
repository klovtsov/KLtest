#!perl
use ParApi::ApiClient;

my ($keyFileName, $apiuserName, $host, $standardKey,$keyFormat) = @ARGV[0..4];
my ($rc, $msg);

my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);                                                                                                             
($rc, $msg) = $client->sshKey(StandardKey => $standardKey, KeyFormat => $keyFormat);
print $msg;