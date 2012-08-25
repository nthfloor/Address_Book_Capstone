import java.io.IOException;

/**
 * Abstract class for all data structures
 * 
 * Ryan Saunders
 * Nathan Floor
 *
 */

public abstract class DataStructure {	
	
	//load csv file into hash-table, throws exception if file does not exist or error reading it in
	abstract void loadData(String filename) throws IOException;
	
	//perform walk-through all, with timers
	abstract void walkThrough();
	
	//return random access to a record, with timers
	abstract String getRecord(String key);
	
	//returns the progress of either loading, walk-through,or searching through table
	abstract double getProgress();	
}
