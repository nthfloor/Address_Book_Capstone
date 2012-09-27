#Nathan Floor

#change these variables as needed
BINDIR = bin/
SRCDIR = src/
JARS = miglayout15-swing.jar
PARAMS = -d bin/ -g -cp "$(JARS)"
RUNPARAMS = -cp "bin/:$(JARS)"
.SUFFIXES: .java .class

defualt: 
	javac -d bin/ -cp miglayout15-swing.jar src/*.java

compile:
	clear
	@make clean
	javac -d bin/ -cp miglayout15-swing.jar src/*.java

run:
	clear
	@java $(RUNPARAMS) Runner 350000.csv 350000
run_gui:
	clear
	java $(RUNPARAMS) GuiRunner

clean:
	@echo "Cleaning Directory."
	@rm -f bin/* *~ src/*.class
