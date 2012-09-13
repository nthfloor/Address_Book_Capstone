import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Sorted array implementation of DataStructure
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 */
public class SortedArray extends DataStructure {
	//instance variables
	//table variables
	private Record[] sortedRecords;	
	private volatile int numberOfRecs = 0;
	private int totalNumRecords;
	
	//progress bar variables
	private Monitor progressThread;
	private volatile int walkCounter = 0;
	private volatile int searchCounter = 0;
	private boolean isLoading = false;
	private boolean isWalking = false;
	private boolean isRandomAccess = false;

	//timer variables
	private double startTime;
	private double currentTime;

	/**
	 * Creates a new sorted array with starting size of 1000.
	 */	
	public SortedArray() {
		totalNumRecords = 1000;
		progressThread = new Monitor(this);
	}

	/**
	 * Creates a new sorted array.
	 * 
	 * @param size - the starting size of the array
	 */	
	public SortedArray(int size) {
		sortedRecords = new Record[size];
		totalNumRecords = size;
	}
	/**
	 * Walks through the sorted array.
	 */

	@Override
	public void walkThrough() {
		isLoading = false;
		isWalking = true;
		isRandomAccess = false;
		progressThread = new Monitor(this);
		progressThread.start();		
		walkCounter = 0;
		startTime = System.currentTimeMillis();
		for (int i = 0; i < numberOfRecs; i++) {
			synchronized(this){
				walkCounter++;
			}
			sortedRecords[i].toString();
		}
		currentTime = System.currentTimeMillis();
		progressThread.interrupt();
		try{
			progressThread.join();
		}catch(Exception e){e.printStackTrace();}
		System.out.println("Walkthrough completed. Time: "+(currentTime-startTime)+" seconds");
	}
	
	@Override
	public String getRecord(String phone) {
		isLoading = false;
		isWalking = false;
		isRandomAccess = true;
		progressThread = new Monitor(this);
		progressThread.start();
		startTime = System.currentTimeMillis();
		int lower = 0;
		int higher = sortedRecords.length;
		int mid = higher / 2;
		
		while (true) {
			synchronized(this){
				searchCounter++;
			}
			//System.out.println(mid);
			
			if (phone.equals(sortedRecords[mid].getKey())) {
				currentTime = System.currentTimeMillis();
				progressThread.interrupt();
				try{
					progressThread.join();
				}catch(Exception e){e.printStackTrace();}
				System.out.println("Random Access completed. Time: "+(currentTime-startTime)+" seconds");
				return sortedRecords[mid].toString();
			} else if (phone.compareTo(sortedRecords[mid].getKey()) < 0) {
				higher = mid - 1;
				mid = (higher + lower) / 2;
			} else {
				lower = mid + 1;
				mid = (higher + lower) / 2;
			}
			
			if(lower > higher){ // test to see if record exists
				progressThread.interrupt();
				try{
					progressThread.join();
				}catch(Exception e){e.printStackTrace();}
				return "No record found. Time: "+(currentTime-startTime)+" seconds";
			}
		}
	}

	@Override
	public void loadData(String filename) throws IOException {
		//System.out.println();
		isLoading = true;
		isWalking = false;
		isRandomAccess = false;
		progressThread = new Monitor(this);
		BufferedReader b = new BufferedReader(new FileReader(filename));		
		b.readLine();

		numberOfRecs = 0;
		String line;
		String[] split = null;

		progressThread.start();
		while ((line = b.readLine()) != null) {
			split = line.split(";");

			try {
				sortedRecords[numberOfRecs] = new Record(split);
			}
			 catch (IncorrectNumberOfFieldsException e) { // when split contains too few elements
				System.out.println("############################");
				System.out.println("not correct number of fields");
				System.out.println("############################");
			}  catch (ArrayIndexOutOfBoundsException e) {  // when sortedRecords too small to hold another record
				
//				System.out.println("############################");
//				System.out.println("not correct number of records");
//				System.out.println("############################");
				
				sortedRecords = extendArray(sortedRecords);
				sortedRecords[numberOfRecs] = new Record (split);;
			}

			// weird way of commenting out code
			/*if (false) {
				if (numberOfRecs % 100000 == 0) {
					for (int i = 0; i < split.length; i++) {
						System.out.print(split[i] + ";");
					}
					System.out.println();
				}
			}*/
			
			synchronized(this){
				numberOfRecs++;
			}
		}
		progressThread.interrupt();
		try{
			progressThread.join();
		}catch(Exception e){e.printStackTrace();}		
	}

private Record[] extendArray(Record[] array) {
		Record[] extendedArray = new Record[array.length * 2];
		
		for (int i = 0; i < array.length; i++) {
			extendedArray[i] = new Record (array[i]);
		}
		
		return extendedArray;
	}

		//returns the progress of either loading, walk-through,or searching through array
	@Override
	synchronized double getProgress() {
		if(isLoading)
			return numberOfRecs/(double)totalNumRecords;
		else if(isWalking)
			return walkCounter/(double)totalNumRecords;
		else if(isRandomAccess)
			return searchCounter/(double)totalNumRecords;
		else
			return 0.0;
	}
}
