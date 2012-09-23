package capstone;

import java.io.IOException;
import java.util.ArrayList;

import capstone.datastructures.DataStructure;
import capstone.datastructures.IncorrectNumberOfFieldsException;
import capstone.datastructures.Record;
import capstone.datastructures.RecordNotFoundException;

/**
 * Helper class for the driver classes in Address Book(Capstone project)
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 * 
 */

public class RunHelper {
	private long timeData;
	private DataStructure ds;
	private Monitor progressThread;
	
	public RunHelper (DataStructure ds, Monitor newProgressThread) {
		this.ds = ds;
		this.progressThread = newProgressThread;
	}

	public synchronized void loadData(String filename) {
		timeData = -1;
		Monitor progressThread = getNewProgressThread();
		progressThread.start();

		System.out.println("Loading file...");
		long startTime = System.currentTimeMillis();
		try {
			ds.loadData(filename);
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

	public void walkThrough() {
		timeData = -1;
		Monitor progressThread = getNewProgressThread();
		progressThread.start();

		System.out.println("Performing sequencial walkthrough...");

		long startTime = System.currentTimeMillis();
		long endTime = 0;
		ds.walkThrough();
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

	public ArrayList<Record> getRecords(String searchString) throws RecordNotFoundException {
		timeData = -1;
		Monitor progressThread = getNewProgressThread();
		progressThread.start();

		System.out.println("Performing random access for " + searchString + "...");

		boolean recFound = false;
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		try {
			ArrayList<Record> list = ds.getRecords(searchString);//search for phone number since unique
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

	public long getTimeData() {
		return timeData;
	}
	
	private Monitor getNewProgressThread() {
		return progressThread.copy();
	}
}
