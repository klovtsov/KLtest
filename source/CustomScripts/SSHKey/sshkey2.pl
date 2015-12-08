#!perl
use ParApi::ApiClient;

my ($keyFileName, $apiuserName, $host, $standardKey) = @ARGV[0..3];
my ($rc, $msg);

my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);

                                                                                                              
($rc, $msg) = $client->sshKey(StandardKey => $standardKey, KeyFormat => "SecSSH");

print $msg;
