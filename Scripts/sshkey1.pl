#!perl
use ParApi::ApiClient;

my ($keyFileName, $apiuserName, $host, $systemName, $accountName,$regenerate) = @ARGV[0..5];
my ($rc, $msg);

my $client = ParApi::ApiClient->new(host        => $host,
                                    keyFileName => $keyFileName,
                                    userName    => $apiuserName);

                                                                                                              
($rc, $msg) = $client->sshKey(SystemName => $systemName, AccountName => $accountName, Regenerate => $regenerate);

print $msg;