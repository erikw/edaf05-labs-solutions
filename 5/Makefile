# Macros
NAME=GorillaCucumber
#JFLAGS=-verbose
JFLAGS= 
JC=javac
DFLAGS=-q
SRC=src
DEST=bin
#TESTDIR=/usr/local/cs/edaf05/lab1
TESTDIR=testfiles
TESTOUTS=$(wildcard $(TESTDIR)/*.out)
TESTMATR=$(TESTDIR)/BLOSUM62.txt

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
