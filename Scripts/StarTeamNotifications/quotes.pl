#!perl

open (File, "quotes.txt") or die "Can't open quotes file! $!";
my @quotes = <File>;
close (File);

print("\n\nA random quote of the day:\n\n", $quotes[rand @quotes]);


