#!/usr/bin/perl
use Time::HiRes qw(time);
use Term::ANSIColor;

$NAME = "GorillaCucumber";
$SRC = "src";
$DEST = "bin";
$TESTDIR = "testfiles";
$BLOSUM = "BLOSUM62.txt";

@testFiles;
opendir(dirHandle, $TESTDIR) or die $!;
while (my $file = readdir(dirHandle)) {
	if ($file =~ m/^(.*)\.in$/) {
		push(@testFiles, $1);
	}
}
foreach (@testFiles) {
	$testFile = $_;
	open(fileHandle, $TESTDIR . "/" . $testFile . ".out");
	$testCount = 0;
	$testPass = 0;
	$startTime = Time::HiRes::gettimeofday();
	$inFile = $testFile . ".in";
	print("java -cp $DEST/ $NAME $TESTDIR/$BLOSUM $TESTDIR/$inFile\n");
	$progOut = `java -cp $DEST/ $NAME $TESTDIR/$BLOSUM $TESTDIR/$inFile`;
	@progLines = split(/\n/, $progOut);
	$lineCnt = 0;
	while ($line = <fileHandle>) {
		if ($line =~ m/^([-a-zA-Z]+):\s(-?\d+)$/) {
			$testCount++;
			$/ = "\n";
			chomp($line);
			$animals = $1;
			$expAlign = $2;
			$progLines[$lineCnt] =~ m/^[-a-zA-Z]+:\s(-?\d+)$/;
			$realAlign = $1;
			if ($line eq $progLines[$lineCnt]) {
				++$testPass;
				print color 'green';
				print("Equality: $realAlign eq $expAlign");
			} else {
				print color 'red';
				print("Inequality: Was $realAlign but expected $expAlign");
			}
			print("\tfor $animals\n");
			print color 'reset';
		}
		$lineCnt++;
	}
	$endTime = Time::HiRes::gettimeofday();
	print("Tests passed [$testPass/$testCount]");
	printf(", Time [%.3f s].\n", ($endTime - $startTime));
	close(fileHandle);
	print("\n");
}

