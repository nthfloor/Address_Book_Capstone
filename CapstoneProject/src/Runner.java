

import java.util.*;
import java.io.IOException;

/**
 * Driver class for Address Book(Capstone project)
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 * 
 */

public class Runner {
		
	private static final String DEFAULT_SEARCH_ITEM = "734-665-7833";//#735
	private static final boolean showConsoleOutput = false;

	//instance variables
	private static Scanner input; //for user keyboard input
	private static DataStructure listOfRecords;

	private static String filename;
	private static int numRecords;
	
	private static long timeData;

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
				loadData(listOfRecords, filename, new CommandLineMonitor(listOfRecords));
				
				mainMenu();
				break;
			case 2:
				//use hash-table DS
				listOfRecords = new MyHashtable(numRecords);
				loadData(listOfRecords, filename, new CommandLineMonitor(listOfRecords));
				mainMenu();
				break;
			case 3:
				//use binary-tree
				listOfRecords = new BinaryTree(numRecords);
				loadData(listOfRecords, filename, new CommandLineMonitor(listOfRecords));
				mainMenu();
				break;
			default:
				System.out.println("Please select one of the options listed above.");
				break;
			}
		}
	}

	public synchronized static void loadData(DataStructure listOfRecords, String filename, Monitor progressThread) {
		timeData = -1;
		progressThread.start();

		System.out.println("Loading file...");
		long startTime = System.currentTimeMillis();
		try {
			listOfRecords.loadData(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IncorrectNumberOfFieldsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			long endTime = System.currentTimeMillis();
			timeData = endTime - startTime;
			progressThread.interrupt();
			try {
				progressThread.join();
				System.out.println("Data loaded successfully. Time: " + (endTime - startTime) + " milliseconds");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				walkThrough(listOfRecords, new CommandLineMonitor(listOfRecords));
				break;
			case 3:
				//execute random access operation
				try {
					getRecords(listOfRecords, DEFAULT_SEARCH_ITEM, new CommandLineMonitor(listOfRecords));
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

	public static void walkThrough(DataStructure listOfRecords, Monitor progressThread) {
		timeData = -1;
		progressThread.start();

		System.out.println("Performing sequencial walkthrough...");

		long startTime = System.currentTimeMillis();
		long endTime = 0;
		listOfRecords.walkThrough();
		endTime = System.currentTimeMillis();
		timeData = endTime - startTime;

		progressThread.interrupt();
		try {
			progressThread.join();
			System.out.println("Data walk-through complete. Time: " + (endTime - startTime) + " milliseconds");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ArrayList<Record> getRecords(DataStructure listOfRecords, String searchPhone, Monitor progressThread) throws RecordNotFoundException {
		timeData = -1;
		progressThread.start();

		System.out.println("Performing random access for " + searchPhone + "...");

		boolean recFound = false;
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		try {
			ArrayList<Record> list = listOfRecords.getRecords(searchPhone);//search for phone number since unique
			String stringOfRecords = list.toString();
			System.out.println(stringOfRecords);
			endTime = System.currentTimeMillis();
			
			timeData = endTime - startTime;
			recFound = true;
			return list;
		} catch (RecordNotFoundException e){
			endTime = System.currentTimeMillis();
			timeData = endTime - startTime;
			throw new RecordNotFoundException();
		} finally {
			progressThread.interrupt();
			try {
				progressThread.join();
				System.out.println("");

				if (recFound) {
					System.out.println("Record found. Time: " + (endTime - startTime) + " milliseconds");
				} else {
					System.out.println("Record not found. Time: " + (endTime - startTime + " milliseconds"));
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static long getTimeData() {
		return timeData/1000;
	}
}
