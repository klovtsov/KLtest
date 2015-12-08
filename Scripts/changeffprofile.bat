@echo off

perl -x -S %0 %*
goto endofperl

#!perl

my ($ProfilePath, $DownloadDir) = @ARGV[0..1];

$DownloadDir =~ s/\\/\\\\/g;

open Profile, $ProfilePath or die "Can't read Firefox Profile: $!";

undef $/;

my $content = <Profile>;
close Profile;

$/ = "\n";


$content =~ s/(.*"browser\.download.dir", ")[^"]*("\).*)/$1$DownloadDir$2/;

unless ($content =~ /neverAsk\.saveToDisk/) {
	$content .= "user_pref(\"browser.helperApps.neverAsk.saveToDisk\", \"application/octet-stream\");\n";
}


open Profile, ">$ProfilePath" or die "Can't write to Firefox Profile: $!";
print Profile $content;
close Profile;

__END__
:endofperl