# Make file for lab 1 in EDAF05.

# Macros
#JFLAGS = -verbose
NAME=ClosestPair
JFLAGS = 
JC = javac
SRC = src
DEST = bin
DFLAGS=-q
#TESTDIR=/usr/local/cs/edaf05/lab3
#TESTDIR=testfiles
#TESTOUTS=$(wildcard $(TESTDIR)/*.out)

# Targets

# target: default - The default target that depends on compile.
default : compile

# target: clean - Deletes all compiled and generated files.
clean : 
	$(RM) $(wildcard $(DEST)/*.class)

# target: help - Display all targets.
help :
	egrep "#\starget:" Makefile

# target compile - Compiles all files fond in the source directory.
compile :
	$(JC) $(JFLAGS) -d $(DEST) $(wildcard $(SRC)/*.java)

# target: test - Test all files found in the test file directory.
test : 
	./test.pl
