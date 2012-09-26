#Makefile for Capstone Address Book Demo
#Nathan Floor
#Ryan Saunders

#Setup
JAVACPATH = javac
BINDIR = bin/
SRCDIR = src/capstone/
LIBRARIES_DIR = lib/
JARSDIR = lib/
#Declare JARS 
JARS = $(JARSDIR)miglayout15-swing.jar
#Define parameters for java and javac
PARAMS = -g -cp "$(SRCDIR):$(JARS)" -d $(BINDIR)
RUNPARAMS = -cp "bin/:$(JARS)" -D"java.library.path=$(LIBRARIES_DIR)"
.SUFFIXES: .java .class

#Tell make to look in the source folder
VPATH = $(SRCDIR) $(BINDIR)capstone/ $(SRCDIR)gui/
#Compile driver:
.java.class:
	@if [ ! -d $(BINDIR) ]; then mkdir $(BINDIR); fi; $(JAVACPATH) $(PARAMS) $<
Runner.class:Runner.java SortedArray.class MyHashtable.class BinaryTree.class DataStructure.class
MyHashtable.class: MyHashtable.java DataStructure.class Record.class RecordNotFoundException.class IncorrectNumberOfFieldsException.class
SortedArray.class: SortedArray.java DataStructure.class Record.class RecordNotFoundException.class IncorrectNumberOfFieldsException.class
BinaryTree.class: BinaryTree.java DataStructure.class Record.class BinaryNode.class RecordNotFoundException.class IncorrectNumberOfFieldsException.class
BinaryNode.class: BinaryNode.java Record.class
DataStructure.class:DataStructure.java Record.class IncorrectNumberOfFieldsException.class RecordNotFoundException.class
Monitor.class:Monitor.java DataStructure.class IncorrectNumberOfFieldsException.class RecordNotFoundException.class
CommandLineMonitor.class:CommandLineMonitor.java Monitor.class
Record.class:Record.java RecordNotFoundException.class IncorrectNumberOfFieldsException.class
IncorrectNumberOfFieldsException.class:IncorrectNumberOfFieldsException.java
RecordNotFoundException.class:RecordNotFoundException.java
GuiRunner.class:GuiRunner.java DataStructure.class SortedArray.class MyHashtable.class BinaryTree.class Record.class RecordNotFoundException.class AddressBookWindow.class
GuiMonitor.class:GuiMonitor.java Monitor.class DataStructure.class ProgressUpdater.class
AddressBookWindow.class:AddressBookWindow.java ProgressUpdater.class

run:
	clear
	java $(RUNPARAMS) Runner
run_gui:
	clear
	java $(RUNPARAMS) GuiRunner
crun:
	make clean
	make
	make run
clean:
	@echo Deleted all class and temporary files.
	@if [ -d $(BINDIR) ]; then rm -r $(BINDIR); fi
