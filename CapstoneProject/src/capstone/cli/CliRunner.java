package capstone.cli;

import java.util.Scanner;

import capstone.RunHelper;
import capstone.WalkThroughMessangerException;
import capstone.datastructures.BinaryTree;
import capstone.datastructures.DataStructure;
import capstone.datastructures.MyHashtable;
import capstone.datastructures.RecordNotFoundException;
import capstone.datastructures.SortedArray;

/**
 * Command Line Interface driver class for Address Book(Capstone project)
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 * 
 */

public class CliRunner {
		
	private static final String DEFAULT_SEARCH_ITEM = "734-665-7833";//#735

	//instance variables
	private static Scanner input; //for user keyboard input
	private static DataStructure listOfRecords;

	private static String filename;
	private static int numRecords;

	private static RunHelper helper;
	
	public static void main(String[] args) {
		input = new Scanner(System.in);
		filename = args[0];
		numRecords = Integer.parseInt(args[1]);

		//start menu system
		selectDataStructure();
	}

	//main menu
	private static void selectDataStructure() {
		listOfRecords = null;
		while (true) {
			//display menu options
			System.out.println("");
			System.out.println("Select a Data Structure");
			//System.out.println("");
			System.out.println("1. Sorted Array");
			System.out.println("2. Hashtable with chaining");
			System.out.println("3. Binary Tree");
			System.out.println("4. Back");
			System.out.print(">>");

			//receive user input
			int userSelection = input.nextInt();
			switch (userSelection) {
			case 1:
				//use sorted array DS
				listOfRecords = new SortedArray(numRecords);
				helper = new RunHelper(listOfRecords, new CommandLineMonitor(listOfRecords));
				helper.loadData(filename);
				
				mainMenu();
				break;
			case 2:
				//use hash-table DS
				listOfRecords = new MyHashtable(numRecords);
				helper = new RunHelper(listOfRecords, new CommandLineMonitor(listOfRecords));
				helper.loadData(filename);
				mainMenu();
				break;
			case 3:
				//use binary-tree
				// TODO this must still be implemented
				listOfRecords = new BinaryTree();
				helper = new RunHelper(listOfRecords, new CommandLineMonitor(listOfRecords));
				helper.loadData(filename);
				mainMenu();
				break;
			default:
				System.out.println("Please select one of the options listed above.");
				break;
			}
		}
	}

	private static void mainMenu() {
		while (true) {
			System.out.println("");
			System.out.println("Main Menu:");
			//System.out.println("");

			//display menu options
			System.out.println("1. Select a Data Structure");
			System.out.println("2. Perform sequencial walkthrough");
			System.out.println("3. Perform Random Access Search");
			System.out.println("4. Exit");
			System.out.print(">>");

			//receive user input
			int userSelection = input.nextInt();
			switch (userSelection) {
			case 1:
				//select a new DS
				selectDataStructure();
				break;
			case 2:
				//perform sequential walk-through of DS
				try {
					helper.walkThrough(null);
				} catch (WalkThroughMessangerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case 3:
				//execute random access operation
				try {
					helper.getRecords(DEFAULT_SEARCH_ITEM);
				} catch (RecordNotFoundException e) {
					System.out.println("Could not find the requested record.");
				}
				break;
			case 4:
				System.out.println("Exiting program...");
				System.exit(0);
			default:
				System.out.println("Please select one of the options listed above.");
			}//end switch
		}//end while
	}
}
