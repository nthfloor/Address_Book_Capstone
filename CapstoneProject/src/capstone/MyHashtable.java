package capstone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Hash-table implementation with chaining
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 * 
 */
public class MyHashtable extends DataStructure {
	//instance variables
	//table variables
	private LinkedList<Record>[] data;
	private int tableSize;
	private int loadFactor;

	@SuppressWarnings("unchecked")
	public MyHashtable() {
		data = new LinkedList[1000];
		for (int i = 0; i < 1000; i++)
			data[i] = new LinkedList<Record>();//initializing array of records

		tableSize = 1000;
		numberOfRecs = 0;
		loadFactor = numberOfRecs / tableSize;
	}

	public MyHashtable(int size) {
		this();
		totalNumberOfRecs = size;
	}

	/*
	 * public MyHashtable(int size, String filename){
	 * this(size);
	 * loadData(filename);
	 * }
	 */

	//load csv file into hash-table
	@Override
	public void loadData(String filename) throws IOException, IncorrectNumberOfFieldsException {
		isLoading = true;
		isWalking = false;
		isRandomAccess = false;
		BufferedReader input = new BufferedReader(new FileReader(filename));
		String[] newRecord;

		String newline = "";
		//loop through all records and add them to hashtable
		while ((newline = input.readLine()) != null) {
			synchronized (this) {
				numberOfRecs++;
			}
			resizeTable();
			newRecord = newline.split(";");
			add(new Record(newRecord)); // add to hashtable
		}

		input.close();

	}

	//perform walk-through all, with timers
	@SuppressWarnings("unused")
	@Override
	public void walkThrough() {
		isLoading = false;
		isWalking = true;
		isRandomAccess = false;
		walkCounter = 0;

		String temp = "";
		for (int i = 0; i < tableSize; i++) {
			for (Record n : data[i]) {
				synchronized (this) {
					walkCounter++;
				}
				temp = n.toString();
			}
		}
	}

	//return random access to a record, with timers 
	@Override
<<<<<<< HEAD
	public ArrayList<Record> getRecord(String key) throws RecordNotFoundException {
=======
	public ArrayList<Record> getRecords(String key) throws RecordNotFoundException {
>>>>>>> ryanbranch
		isLoading = false;
		isWalking = false;
		isRandomAccess = true;
		searchCounter = 0;
		int code = getHash(key);

		for (Record rec : data[code]) {
			synchronized (this) {
				searchCounter++;
			}

			if (rec.getKeyValue().equals(key)) {
<<<<<<< HEAD
				//return list of records matching supplied key
=======
>>>>>>> ryanbranch
				ArrayList<Record> temp = new ArrayList<Record>();
				temp.add(rec);
				
				return temp;
			}
		}

		throw new RecordNotFoundException();
	}

	//add supplied record to a hash-table
	public void add(Record newRec) {
		int code = getHash(newRec.getKeyValue()); // compute hash-code for indexing

		// TODO what is this for?
		if (newRec.getKeyValue().equals("615-883-8408"))
			newRec.toString();

		data[code].addFirst(newRec);
	}

	//resize table to ensure good load factoring, dynamic resizing
	@SuppressWarnings("unchecked")
	private void resizeTable() {
		loadFactor = numberOfRecs / tableSize;

		//if load-factor is too large then enlarge table(double its size)
		if (loadFactor > 0.75) {
			LinkedList<Record>[] temp = new LinkedList[tableSize * 2];
			for (int i = 0; i < (tableSize * 2); i++)
				temp[i] = new LinkedList<Record>();

			int tempCode = 0;
			for (int i = 0; i < tableSize; i++) {
				//copy data from data table into temp table
				for (Record n : data[i]) {
					tempCode = getHash(n.getKeyValue());
					temp[tempCode].addFirst(n);
				}
			}
			data = temp; //make temp table the new data table
			tableSize = tableSize * 2;
		}
	}

	public int getTableSize() {
		return tableSize;
	}

	//return hash code for the supplied hash code
	private int getHash(String key) {
		return Math.abs(key.hashCode() + key.length()) % tableSize;
	}
}
