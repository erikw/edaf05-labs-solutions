#!/bin/bash
# Run all test files found.
NAME=SpanningUSA
SRC=src
DEST=bin
DFLAGS=-q
TESTDIR=testfiles
inFile=$TESTDIR/USA-highway-miles.in

echo "time java -cp $DEST $NAME $inFile"
RES=`time java -cp $DEST $NAME $inFile`
if [ "$RES" == "16598" ]
then
	echo "Correct ($RES)."
else
	echo "Wrong. Was \"$RES\" but expected 16598."
fi
