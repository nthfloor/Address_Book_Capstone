package capstone.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import capstone.DataStructure;
import capstone.IncorrectNumberOfFieldsException;
import capstone.Monitor;
import capstone.MyHashtable;
import capstone.Record;
import capstone.RecordNotFoundException;
import capstone.SortedArray;

public class DataStructureTest {
	private static final String DEFAULT_SEARCH_ITEM_1 = "734-665-7833";//#735
	private static final String DEFAULT_SEARCH_ITEM_2 = "201-222-5071";
	
	private static DataStructure sortedArray, hashtable, binaryTree;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClassAndTestLoadData() throws Exception {
		System.out.println("Set up before class {");
		
		sortedArray = new SortedArray(349996);
		
		hashtable = new MyHashtable(1000);
		
		System.out.println("} Set up before class");
	}

	public void loadDataStructureWithTimers(DataStructure ds, String filename) throws IncorrectNumberOfFieldsException, IOException {
		long outerStartTime = System.currentTimeMillis();
		Monitor progressThread = new Monitor(ds);
		progressThread.start();
		
		long innerStartTime = System.currentTimeMillis();
		ds.loadData(filename);
		long innerEndTime = System.currentTimeMillis();
		
		progressThread.interrupt();
		try {
			progressThread.join();
			long outerEndTime = System.currentTimeMillis();
			System.out.println("\n\tLoad time: "+ (innerEndTime-innerStartTime)+" milliseconds");
			System.out.println("\n\tLoad time + progress thread handling: "+ (outerEndTime-outerStartTime)+" milliseconds");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void loadSortedArrayWithTimers() throws IncorrectNumberOfFieldsException, IOException{
		loadDataStructureWithTimers(sortedArray, "350000.csv");
	}
	
	@Test
	public void loadHashtableWithTimers() throws IncorrectNumberOfFieldsException, IOException{
		loadDataStructureWithTimers(hashtable, "1000.csv");
	}
	
	@Ignore
	public void loadBinaryTreeWithTimers() throws IncorrectNumberOfFieldsException, IOException{
		fail("Unimplemented method");
	}

	/**
	 * Test method for {@link capstone.SortedArray#walkThrough()}.
	 */
	public final void testWalkThrough(DataStructure ds) {
		ds.walkThrough();
		
		assertEquals(1.0, ds.getProgress(), 0);
	}

	@Test
	public final void testSortedArrayWalkThrough(){
		testWalkThrough(sortedArray);
	}
	
	@Test
	public final void testHashtableWalkThrough(){
		testWalkThrough(hashtable);
	}
	
	@Test
	public final void testBinaryTreeWalkThrough(){
		fail("Unimplemented method");
	}

	/**
	 * Test method for {@link capstone.SortedArray#getRecord(java.lang.String)}.
	 * @throws RecordNotFoundException 
	 * @throws IncorrectNumberOfFieldsException 
	 */
	public final void testGetRecordString(DataStructure ds) throws RecordNotFoundException, IncorrectNumberOfFieldsException {
		String[] fields = new String [12];
		fields[0] = "Wallace";
		fields[1] = "Coggins";
		fields[2] = "Goodyear Auto Service Centers";
		fields[3] = "1011 N University Ave";
		fields[4] = "Ann Arbor";
		fields[5] = "Washtenaw";
		fields[6] = "MI";
		fields[7] = "48109";
		fields[8] = "734-665-7833";
		fields[9] = "734-665-8136";
		fields[10] = "wallace@coggins.com";
		fields[11] = "http://www.wallacecoggins.com";
		
		assertEquals(new Record(fields), ds.getRecord(DEFAULT_SEARCH_ITEM_1));
		
		fields = new String [12];
		
		fields[0] = "Monica";
		fields[1] = "Mews";
		fields[2] = "Safeguard Business Systems";
		fields[3] = "111 Pavonia Ave";
		fields[4] = "Jersey City";
		fields[5] = "Hudson";
		fields[6] = "NJ";
		fields[7] = "7310";
		fields[8] = "201-222-5071";
		fields[9] = "201-222-4641";
		fields[10] = "monica@mews.com";
		fields[11] = "http://www.monicamews.com";
		
		assertEquals(new Record(fields), ds.getRecord(DEFAULT_SEARCH_ITEM_2));
	}
	
	@Test
	public final void testSortedArrayGetRecordString() throws RecordNotFoundException, IncorrectNumberOfFieldsException {
		testGetRecordString(sortedArray);
	}
	
	@Test
	public final void testHashtableGetRecordString() throws RecordNotFoundException, IncorrectNumberOfFieldsException {
		testGetRecordString(hashtable);
	}
	
	@Test
	public final void testBinaryTreeGetRecordString() throws RecordNotFoundException, IncorrectNumberOfFieldsException {
		fail("Unimplemented method");
	}
	
	/**
	 * Test method for {@link capstone.SortedArray#getRecord(int)}.
	 * @throws IncorrectNumberOfFieldsException 
	 */
	@Test
	public final void testSortedArrayGetRecordInt() throws IncorrectNumberOfFieldsException {
		String[] fields = new String [12];
		
		fields[0] = "Monica";
		fields[1] = "Mews";
		fields[2] = "Safeguard Business Systems";
		fields[3] = "111 Pavonia Ave";
		fields[4] = "Jersey City";
		fields[5] = "Hudson";
		fields[6] = "NJ";
		fields[7] = "7310";
		fields[8] = "201-222-5071";
		fields[9] = "201-222-4641";
		fields[10] = "monica@mews.com";
		fields[11] = "http://www.monicamews.com";
		
		assertEquals(new Record(fields), ((SortedArray)sortedArray).getRecord(20));
	}
}
