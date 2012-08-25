#Nathan Floor

#change these variables as needed
SOURCE = src/
BIN = bin/
FILENAME = 350000.csv
NUMRECS = 350000
DRIVER = Runner
.SUFFIXES: .java .class

.java.class:
	javac -g $<

Runner.class:Runner.java DataStructure.class MyHashtable.class SortedArray.class Record.class

MyHashtable.class:MyHashtable.java Record.class Monitor.class

SortedArray.class:SortedArray.java Record.class Monitor.class

DataStructure.class:DataStructure.java

compile:
	clear
	@echo Compiling...
	@make clean; make;

run:
	clear
	@echo "Running Address Book..."
	@java $(DRIVER) $(FILENAME) $(NUMRECS)

clean:
	@echo "Cleaning Directory."
	@rm -f *.class *~
