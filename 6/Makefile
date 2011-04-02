# Make file for lab 6 in EDAF05.

# Macros
NAME=RailroadPlanning
#JFLAGS=-verbose
JC=javac
SRC=src
DEST=bin
#TESTDIR=/usr/local/cs/edaf05/lab6
TESTDIR=testfiles
TESTOUTS=$(wildcard $(TESTDIR)/*.out)

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
test :  $(TESTOUTS:.out=.test)

%.test : %.in
	time java -cp bin/ $(NAME)  $< | diff -q $*.out -
