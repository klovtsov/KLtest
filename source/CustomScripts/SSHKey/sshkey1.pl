#!perl
use ParApi::ApiClient;

my ($keyFileName, $apiuserName, $host, $systemName, $accountName) = @ARGV[0..4];
my ($rc, $msg);

my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);

                                                                                                              
($rc, $msg) = $client->sshKey(SystemName => $systemName, AccountName => $accountName, Regenerate => "Y");

print $msg;
