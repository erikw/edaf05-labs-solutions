#!/usr/bin/perl
use Time::HiRes qw(time);
use Term::ANSIColor;

$NAME = "ClosestPair";
$SRC = "src";
$DEST = "bin";
$TESTDIR = "testfiles";
$outFile = $TESTDIR . "/closest-pair.out";


open(fileHandle, $outFile);
$testCount = 0;
$testPass = 0;
$startTime = Time::HiRes::gettimeofday();
while ($line = <fileHandle>) {
	$testCount++;
	$line =~ m/^\.\.\/data\/([^\s]*):\s+\d+\s+(.*)$/;
	$testFile = $1;
	$expected = $2;
	print("java -cp $DEST/ $NAME $TESTDIR/$testFile\n");
	$actual = `java -cp $DEST/ $NAME $TESTDIR/$testFile`;
	my($decimals) = $2 =~ m/^\d+\.(\d*)$/;
	$nbrDecimals = length($decimals);
	if (($nbrDecimals > 0) && (sprintf("%." . $nrDecimals . "f", $expected) eq sprintf("%." . $nbrDecimals . "f", $actual))) {
		print color 'red';
		print("Expected $expected but was: $actual");
		print color 'reset';
	} else {
		$testPass++;
		print color 'blue';
		print("Correct ($expected ~= $actual)");
		print color 'reset';
	}
	print("\n");
}
$endTime = Time::HiRes::gettimeofday();
print("Tests passed [$testPass/$testCount]");
printf(", Time [%.3f s].\n", ($endTime - $startTime));
close(fileHandle);
