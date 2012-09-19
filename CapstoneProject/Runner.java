import java.util.*;
import java.io.IOException;

/**
 * Driver class for Address Book(Capstone project)
 * 
 * Nathan Floor
 * Ryan Saunders
 * 
 */

public class Runner {
	//instance variables
	static Scanner input; //for user keyboard input
	static DataStructure listOfRecords;
	
	static String filename;
	static int numRecords;

	//timing variables
	private static double startTime;
	private static double currentTime;
	
	public static void main(String[] args) {		
		input = new Scanner(System.in);		
		filename = args[0];
		numRecords = Integer.parseInt(args[1]);
		
		//start menu system
		selectDataStructure();		
	}

	//main menu
	private static void selectDataStructure(){
		listOfRecords = null;
		while(true){
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
			switch (userSelection){
			case 1:
				//use sorted array DS
				listOfRecords = new SortedArray(numRecords);
				System.out.println("Loading file...");
				try{
					listOfRecords.loadData(filename);
				}catch(IOException e){e.printStackTrace();}
				mainMenu();
			case 2:
				//use hash-table DS
				System.out.println("Loading file...");
				listOfRecords = new MyHashtable(numRecords,filename);
				mainMenu();
			case 3:
				//use Binary tree DS
				//System.out.println("Loading file into program...");
				listOfRecords = new BinaryTree(filename);
				System.out.println("Not implemented yet...");
				mainMenu();
			default:
				System.out.println("Please select one of the options listed above.");
			}				
		}
	}

	private static void mainMenu(){
		while(true){
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
			switch (userSelection){
			case 1:
				//select a new DS
				selectDataStructure();
				break;
			case 2:
				//perform sequential walk-through of DS
				System.out.println("Performing sequencial walkthrough...");
				startTime = System.currentTimeMillis();
				listOfRecords.walkThrough();
				currentTime = System.currentTimeMillis();
				System.out.println("Walkthrough completed. Time: "+(currentTime-startTime)/1000+" seconds");
				break;
			case 3:
				//execute random access operation
				//String searchItem = input.next();

				//select a search option
				String searchPhone = "734-665-7833";//#775
				
				System.out.println("Performing random access for "+searchPhone+"...");
				startTime = System.currentTimeMillis();
				String tempName = listOfRecords.getRecord(searchPhone);//search for phone number since unique
				currentTime = System.currentTimeMillis();
				System.out.println(tempName);
				System.out.println("Random Access completed. Time: "+(currentTime-startTime)/1000+" seconds");				

				break;
			case 4:
				System.out.println("Exiting program...");
				listOfRecords = null;
				System.exit(0);
			default:
				System.out.println("Please select one of the options listed above.");
			}//end switch
		}//end while
	}
}
