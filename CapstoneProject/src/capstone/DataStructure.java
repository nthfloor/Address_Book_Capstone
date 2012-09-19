package capstone;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Abstract class for all data structures
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 * 
 */

public abstract class DataStructure {

	//progress variables
	protected volatile int numberOfRecs = 0;
	protected int totalNumberOfRecs;
	protected volatile int walkCounter = 0;
	protected volatile int searchCounter = 0;
	protected boolean isLoading = false;
	protected boolean isWalking = false;
	protected boolean isRandomAccess = false;

	/**
	 * Loads a csv file into the DataStructure, with timers.
	 * 
	 * @param filename - the file to load
	 * @throws IOException if file does not exist or error reading it in
	 * @throws IncorrectNumberOfFieldsException if the file does not contain the correct number of columns/fields.
	 */
	public abstract void loadData(String filename) throws IOException, IncorrectNumberOfFieldsException;

	/**
	 * Perform walk-through all, with timers
	 */
	public abstract void walkThrough();

	/**
	 * Returns a list of record by using random access, with timers.
	 * 
	 * @param key - the field to search for
	 * @return the requested record
	 * @throws RecordNotFoundException if no record is found
	 */
	public abstract ArrayList<Record> getRecords(String key) throws RecordNotFoundException;

	/**
	 * Returns the progress of either loading, walk-through,or searching through table.
	 * 
	 * @return a number between 0.0 and 1.0 indicating progress
	 */
	public synchronized double getProgress() {
		if (isLoading)
			return numberOfRecs / (double) totalNumberOfRecs;
		else if (isWalking)
			return walkCounter / (double) totalNumberOfRecs;
		else if (isRandomAccess)
			return searchCounter / (double) totalNumberOfRecs;
		else
			return 0.0;
	}
}
