# Make file for lab 1 in EDAF05.

# Not a file target.
.PHONY: clean

### Macros ###
NAME = stable_match
CC = gcc
CFLAGS = -Wall -O3 -std=c99
SRC = src
SRCS = $(wildcard $(SRC)/*.c)
#SRCS = $(wildcard *.c)
DEST = bin
OBJS = $(patsubst $(SRC)/%.c,$(DEST)/%.o,$(SRCS))
#TESTDIR=/usr/local/cs/edaf05/lab1
TESTDIR = ../testfiles
TESTOUTS = $(wildcard $(TESTDIR)/*.out)
DFLAGS = -q


### Targets ###
# target: default - The default target that depends on compile and testall.
default : $(NAME)


# target: clean - Deletes all compiled and generated files.
clean : 
	$(RM) $(wildcard $(DEST)/*)

# target: help - Display all targets.
help :
	egrep "#\starget:" Makefile  | cut -d " " -f3-

# target compile - Compiles all files find in the source directory.
$(NAME) : $(OBJS)
	$(CC) -o $(DEST)/$@ $^

$(DEST)/%.o: $(SRC)/%.c
	$(CC) $(CFLAGS) -c -o $@ $<


#$(CC) $(CFLAGS) -o $(DEST) $(wildcard $(SRC)/*.java)

# target: test - Test all files found in the test file directory.
test :  $(TESTOUTS:.out=.test)

%.test : %.in
	time $(DEST)/$(NAME) $< | diff $(DFLAGS) $*.out -


# target: test2 - Test two files.
test2 :
	time $(DEST)/$(NAME) $(TESTDIR)/sm-kt-p-4.in | diff $(DFLAGS) $(TESTDIR)/sm-kt-p-4.out -
	time $(DEST)/$(NAME) $(TESTDIR)/sm-kt-p-5.in | diff $(DFLAGS) $(TESTDIR)/sm-kt-p-5.out -
