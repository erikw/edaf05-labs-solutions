#!/bin/bash
# Run all test files found.
NAME=WordLadders
SRC=src
DEST=bin
DFLAGS=-q
TESTDIR=testfiles


for inFile in $TESTDIR/*.in
do
	datFile=`echo $inFile| sed 's/$TESTDIR\/\(.*\)/\1/' | sed 's/-test\.in/\.dat/'`
	outFile=`echo $inFile| sed 's/$TESTDIR\/\(.*\)/\1/' | sed 's/\.in/\.out/'`
	#echo "Testing $inFile"
	echo "time java -cp $DEST $NAME $datFile $inFile | diff $DFLAGS $outFile -"
	time java -cp $DEST $NAME $datFile $inFile | diff $DFLAGS $outFile -
done
