import java.io.IOException;

/**
 * Abstract class for all data structures
 * 
 * Ryan Saunders
 * Nathan Floor
 *
 */

public abstract class DataStructure {	

	public enum KeyType{
		FIRSTNAME,
		LASTNAME,
		PHONE
	}
	
	//load csv file into hash-table, throws exception if file does not exist or error reading it in
	abstract void loadData(String filename) throws IOException;
	
	//perform walk-through all, with timers
	abstract void walkThrough();
	
	//return random access to a record, with timers
	abstract String getRecord(String key);

	//perform binary search
	//abstract String BinarySearch(String key);

	//perform sequencial search if data not sorted by key value
	//abstract String sequencialSearch(String key);
	
	//returns the progress of either loading, walk-through,or searching through table
	abstract double getProgress();	
}
