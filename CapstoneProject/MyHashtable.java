import java.io.*;
import java.util.*;

/**
 * Hash-table implementation with chaining
 * 
 * Nathan Floor
 * Ryan Saunders 
 *
 */
public class MyHashtable extends DataStructure{
	//instance variables
	//table variables
	private LinkedList<Record>[] data;
	private int tableSize;
	private int loadFactor;	
	
	//progress variables
	private Monitor progressThread;
	private volatile int numberOfRecs;
	private int totalNumberOfRecs;
	private volatile int walkCounter = 0;
	private volatile int searchCounter = 0;
	private boolean isLoading = false;
	private boolean isWalking = false;
	private boolean isRandomAccess = false;
	
	@SuppressWarnings("unchecked")
	public MyHashtable(){
		data = new LinkedList[1000];
		for(int i=0;i<1000;i++)
			data[i] = new LinkedList<Record>();//initializing array of records
		
		tableSize = 1000;
		numberOfRecs = 0;
		loadFactor = numberOfRecs/tableSize;
		progressThread = new Monitor(this);
	}
	
	public MyHashtable(int size){
		this();		
		totalNumberOfRecs = size;		
	}
	
	public MyHashtable(int size, String filename){
		this(size);		
		loadData(filename);
	}
	
	//returns the progress of either loading, walk-through,or searching through table
	public synchronized double getProgress(){
		if(isLoading)
			return numberOfRecs/(double)totalNumberOfRecs;
		else if(isWalking)
			return walkCounter/(double)totalNumberOfRecs;
		else if(isRandomAccess)
			return searchCounter/(double)totalNumberOfRecs;
		else
			return 0.0;
	}
	
	//load csv file into hash-table
	public void loadData(String filename){
		try{
			isLoading = true;
			isWalking = false;
			isRandomAccess = false;
			BufferedReader input = new BufferedReader(new FileReader(filename));
			String[] newRecord;

			String newline = "";
			//loop through all records and add them to hashtable
			progressThread.start();
			while((newline = input.readLine()) != null){
				synchronized(this){
					numberOfRecs++;
				}
				resizeTable();
				newRecord = newline.split(";");				
				add(new Record(newRecord)); // add to hashtable
			}			
			progressThread.interrupt();
			progressThread.join();
			//System.out.println("Finished loading in data successfully.");
		}catch(Exception e){e.printStackTrace();}
		
	}

	//perform walk-through all, with timers
	public void walkThrough(){		
		isLoading = false;
		isWalking = true;
		isRandomAccess = false;		
		progressThread = new Monitor(this);
		progressThread.start();
		walkCounter = 0;				
		@SuppressWarnings("unused")
		String temp = "";
		for(int i=0;i<tableSize;i++){
			for(Record n:data[i]){
				synchronized(this){
					walkCounter++;
				}
				temp = n.toString();				
			}			
		}
		//System.out.println(temp.toString());		
		progressThread.interrupt();//stop progress thread
		try{
			progressThread.join();
		}catch(Exception e){e.printStackTrace();}
	}
	
	//return random access to a record, with timers 
	public String getRecord(String key){
		isLoading = false;
		isWalking = false;
		isRandomAccess = true;
		progressThread = new Monitor(this);
		progressThread.start();
		searchCounter = 0;
		int code = getHash(key);
		
		for(Record rec : data[code]){
			synchronized(this){
				searchCounter++;
			}
			
			if(rec.getKey().equals(key)){				
				progressThread.interrupt();
				try{
					progressThread.join();//wait for progress thread to finish
				}catch(Exception e){e.printStackTrace();}				
				return rec.toString();	
			}
		}
		progressThread.interrupt();
		try{
			progressThread.join();
		}catch(Exception e){e.printStackTrace();}
		return "No record found.";
	}
	
	//add supplied record to a hash-table
	public void add(Record newRec){
		int code = getHash(newRec.getKey()); // compute hash-code for indexing
		if(newRec.getKey().equals("615-883-8408"))
			newRec.toString();
		
		data[code].addFirst(newRec);		
	}
	
	//resize table to ensure good load factoring, dynamic resizing
	@SuppressWarnings("unchecked")
	private void resizeTable(){
		loadFactor = numberOfRecs/tableSize;
		
		//if load-factor is too large then enlarge table(double its size)
		if(loadFactor > 0.75){
			LinkedList<Record>[] temp = new LinkedList[tableSize*2];
			for(int i=0;i<(tableSize*2);i++)
				temp[i] = new LinkedList<Record>();
			
			int tempCode = 0;
			for(int i=0;i<tableSize;i++){
				//copy data from data table into temp table
				for(Record n : data[i]){
					tempCode = getHash(n.getKey());
					temp[tempCode].addFirst(n);					
				}				
			}			
			data = temp; //make temp table the new data table
			tableSize = tableSize*2;
		}		
	}
	
	public int getTableSize(){
		return tableSize;
	}
	
	//return hash code for the supplied hash code
	private int getHash(String key){
		return Math.abs(key.hashCode()+key.length())%tableSize;
	}
}
