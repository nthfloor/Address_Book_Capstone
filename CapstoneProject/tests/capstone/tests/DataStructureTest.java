package capstone.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

/**
 * Unit tests for the DataStructure class and its subclasses.
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 */
public class DataStructureTest {
	private static final String DEFAULT_SEARCH_ITEM_1 = "734-665-7833";//#735: Wallace;Coggins;Goodyear Auto Service Centers;1011 N University Ave;Ann Arbor;Washtenaw;MI;48109;734-665-7833;734-665-8136;wallace@coggins.com;http://www.wallacecoggins.com
	private static final String DEFAULT_SEARCH_ITEM_2 = "202-223-0386"; //Alicia;Donez;Esp Sales;2000 K St Nw;Washington;District of Columbia;DC;20006;202-223-0386;202-223-0403;alicia@donez.com;http://www.aliciadonez.com
	
	@SuppressWarnings("unused")
	private static DataStructure sortedArray, hashtable, binaryTree;
	
	private static boolean loadedSortedArray = false;
	private static boolean loadedHashtable = false;
	private static boolean loadedBinaryTree = false;

	@BeforeClass
	public static void setUpBeforeClassAndTestLoadData() throws Exception {
		System.out.println("Set up before class {");
		
		sortedArray = new SortedArray(349996);
		
		hashtable = new MyHashtable(999);
		
		System.out.println("} Set up before class");
	}
	
	public final void loadDataStructureWithTimers(DataStructure ds, String filename) throws IncorrectNumberOfFieldsException, IOException {
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
	public final void loadSortedArrayWithTimers() throws IncorrectNumberOfFieldsException, IOException{
		loadDataStructureWithTimers(sortedArray, "350000.csv");
		loadedSortedArray = true;
	}
	
	@Test
	public final void loadHashtableWithTimers() throws IncorrectNumberOfFieldsException, IOException{
		loadDataStructureWithTimers(hashtable, "1000.csv");
		loadedHashtable = true;
	}
	
	@Ignore
	@Test
	public final void loadBinaryTreeWithTimers() throws IncorrectNumberOfFieldsException, IOException{
		fail("Unimplemented method");
		loadedBinaryTree = true;
	}

	public final void testWalkThrough(DataStructure ds) {
		ds.walkThrough();
		
		assertEquals(1.0, ds.getProgress(), 0);
	}

	@Test
	public final void testSortedArrayWalkThrough() throws IncorrectNumberOfFieldsException, IOException{
		if (!loadedSortedArray) loadSortedArrayWithTimers();
		testWalkThrough(sortedArray);
	}
	
	@Test
	public final void testHashtableWalkThrough() throws IncorrectNumberOfFieldsException, IOException{
		if (!loadedHashtable) loadHashtableWithTimers();
		testWalkThrough(hashtable);
	}
	
	@Ignore
	@Test
	public final void testBinaryTreeWalkThrough() throws IncorrectNumberOfFieldsException, IOException{
		fail("Unimplemented method");
		
		if (!loadedBinaryTree) loadBinaryTreeWithTimers();
	}

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
		
		fields[0] = "Alicia";
		fields[1] = "Donez";
		fields[2] = "Esp Sales";
		fields[3] = "2000 K St Nw";
		fields[4] = "Washington";
		fields[5] = "District of Columbia";
		fields[6] = "DC";
		fields[7] = "20006";
		fields[8] = "202-223-0386";
		fields[9] = "202-223-0403";
		fields[10] = "alicia@donez.com";
		fields[11] = "http://www.aliciadonez.com";
		
		assertEquals(new Record(fields), ds.getRecord(DEFAULT_SEARCH_ITEM_2));
	}
	
	@Test
	public final void testSortedArrayGetRecordString() throws RecordNotFoundException, IncorrectNumberOfFieldsException, IOException {
		if (!loadedSortedArray) loadSortedArrayWithTimers();
		
		testGetRecordString(sortedArray);
	}
	
	@Test
	public final void testHashtableGetRecordString() throws RecordNotFoundException, IncorrectNumberOfFieldsException, IOException {
		if (!loadedHashtable) loadHashtableWithTimers();
		
		testGetRecordString(hashtable);
	}
	
	@Ignore
	@Test
	public final void testBinaryTreeGetRecordString() throws RecordNotFoundException, IncorrectNumberOfFieldsException, IOException {
		fail("Unimplemented method");
		
		if(!loadedBinaryTree) loadBinaryTreeWithTimers();
	}
	
	/**
	 * Test method for {@link capstone.SortedArray#getRecord(int)}.
	 */
	@Test
	public final void testSortedArrayGetRecordInt() throws IncorrectNumberOfFieldsException, IOException {
		if (!loadedSortedArray) loadSortedArrayWithTimers();
		
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
