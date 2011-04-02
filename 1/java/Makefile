# Make file for lab 1 in EDAF05.

# Macros
NAME=StableMatch
#JFLAGS=-verbose
JFLAGS=
JC=javac
SRC=src
DEST=bin
#TESTDIR=/usr/local/cs/edaf05/lab1
TESTDIR=../testfiles
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

# target: test2 - Test two files.
test2 :
	time java   -cp bin/ $(NAME)  $(TESTDIR)/sm-kt-p-4.in | diff -q $(TESTDIR)/sm-kt-p-4.out -
	time java   -cp bin/ $(NAME)  $(TESTDIR)/sm-kt-p-5.in | diff -q $(TESTDIR)/sm-kt-p-5.out -	
