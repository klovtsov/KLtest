#!perl

my ($CacheAppInt, $WSDLFile) = @ARGV[0..1];

open Wsdl, $WSDLFile or die "Can't read Wsdl File: $!";

undef $/;

my $content = <Wsdl>;
close Wsdl;

$/ = "\n";

$content =~ s/^"(.*)"[^"]+$/$1/;
$content =~ s/:SBNetworkAddress:/$CacheAppInt/;

open Wsdl, ">$WSDLFile" or die "Can't write to Wsdl File: $!";
print Wsdl $content;
close Wsdl;

