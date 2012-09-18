import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
	
	public SortedArray() {
		totalNumRecords = 1000;
		progressThread = new Monitor(this);
	}
	
	public SortedArray(int size) {
		sortedRecords = new Record[size];
		totalNumRecords = size;		
	}

	@Override
	public void walkThrough() {
		isLoading = false;
		isWalking = true;
		isRandomAccess = false;
		progressThread = new Monitor(this);
		progressThread.start();		
		walkCounter = 0;
		for (int i = 0; i < numberOfRecs; i++) {
			synchronized(this){
				walkCounter++;
			}
			sortedRecords[i].toString();
		}
		progressThread.interrupt();
		try{
			progressThread.join();
		}catch(Exception e){e.printStackTrace();}
	}
	
	@Override
	public String getRecord(String phone) {
		isLoading = false;
		isWalking = false;
		isRandomAccess = true;
		progressThread = new Monitor(this);
		progressThread.start();
		int lower = 0;
		int higher = sortedRecords.length;
		int mid = higher / 2;
		
		while (true) {
			synchronized(this){
				searchCounter++;
			}
			//System.out.println(mid);
			
			if (phone.equals(sortedRecords[mid].getKey())) {
				progressThread.interrupt();
				try{
					progressThread.join();
				}catch(Exception e){e.printStackTrace();}
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
				return "No record found.";
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
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("############################");
				System.out.println("not correct number of fields (or records)");
				System.out.println("############################");
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
