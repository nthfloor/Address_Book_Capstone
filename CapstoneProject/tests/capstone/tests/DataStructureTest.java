package capstone.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import capstone.DataStructure;
import capstone.Monitor;
import capstone.MyHashtable;
import capstone.Record;
import capstone.Record.SearchType;
import capstone.RecordNotFoundException;
import capstone.SortedArray;

/**
 * Unit tests for the DataStructure class and its subclasses.
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 */
public class DataStructureTest {
	//Wallace;Coggins;Goodyear Auto Service Centers;1011 N University Ave;Ann Arbor;Washtenaw;MI;48109;734-665-7833;734-665-8136;wallace@coggins.com;http://www.wallacecoggins.com
	private static final String DEFAULT_PHONE_1 = "734-665-7833";

	//Alicia;Donez;Esp Sales;2000 K St Nw;Washington;District of Columbia;DC;20006;202-223-0386;202-223-0403;alicia@donez.com;http://www.aliciadonez.com
	private static final String DEFAULT_PHONE_2 = "202-223-0386";

	//Kasey;Rollie;Universal City Studios Inc;1 Exchange Pl;Jersey City;Hudson;NJ;7302;201-200-0785;201-200-9759;kasey@rollie.com;http://www.kaseyrollie.com
	//Hattie;Rollie;Cranes;123 Meadow Pl;Lewisville;Denton;TX;75067;972-221-2207;972-221-0982;hattie@rollie.com;http://www.hattierollie.com
	private static final String DEFAULT_LASTNAME = "Rollie";

	@SuppressWarnings("unused")
	private static DataStructure sortedArray, hashtable, binaryTree;

	private static boolean loadedSortedArray = false;
	private static boolean loadedHashtable = false;
	private static boolean loadedBinaryTree = false;

	@BeforeClass
	public static void setUpBeforeClassAndTestLoadData() throws Exception {
		System.out.println("Set up before class {");

		sortedArray = new SortedArray(349996);

		hashtable = new MyHashtable(349996);

		System.out.println("} Set up before class");
	}

	public final void loadDataStructureWithTimers(DataStructure ds, String filename) throws IOException {
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
			System.out.println("\n\tLoad time: " + (innerEndTime - innerStartTime) + " milliseconds");
			System.out.println("\n\tLoad time + progress thread handling: " + (outerEndTime - outerStartTime) + " milliseconds");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public final void loadSortedArrayWithTimers() throws IOException {
		loadDataStructureWithTimers(sortedArray, "350000.csv");
		loadedSortedArray = true;
	}

	@Test
	public final void loadHashtableWithTimers() throws IOException {
		loadDataStructureWithTimers(hashtable, "350000.csv");
		loadedHashtable = true;
	}

	@Ignore
	@Test
	public final void loadBinaryTreeWithTimers() throws IOException {
		fail("Unimplemented method");
		loadedBinaryTree = true;
	}

	public final void testWalkThrough(DataStructure ds) {
		ds.walkThrough();

		assertEquals(1.0, ds.getProgress(), 0);
	}

	@Test
	public final void testSortedArrayWalkThrough() throws IOException {
		if (!loadedSortedArray)
			loadSortedArrayWithTimers();
		testWalkThrough(sortedArray);
	}

	@Test
	public final void testHashtableWalkThrough() throws IOException {
		if (!loadedHashtable)
			loadHashtableWithTimers();
		testWalkThrough(hashtable);
	}

	@Ignore
	@Test
	public final void testBinaryTreeWalkThrough() throws IOException {
		fail("Unimplemented method");

		if (!loadedBinaryTree)
			loadBinaryTreeWithTimers();
	}

	// Tests getRecords() for a list containing one element
	public final void testGetSingleRecord(DataStructure ds) {
		try {
			Record.currentSearchType = SearchType.PHONE;
			ArrayList<Record> list1 = new ArrayList<Record>();
			list1.add(testRecord1());
			assertEquals(list1, ds.getRecords(DEFAULT_PHONE_1));

			ArrayList<Record> list2 = new ArrayList<Record>();
			list2.add(testRecord2());
			assertEquals(list2, ds.getRecords(DEFAULT_PHONE_2));
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
			fail();
		}
	}

	// Tests getRecords() for a list containing two elements
	public final void testGetMultiRecords(DataStructure ds) {
		try {
			ArrayList<Record> listWithTwoElements = listWithTwo();
			Record.currentSearchType = SearchType.LASTNAME;

			System.out.println(listWithTwoElements.get(0).equals(ds.getRecords(DEFAULT_LASTNAME).get(0)));
			System.out.println(listWithTwoElements.get(1).equals(ds.getRecords(DEFAULT_LASTNAME).get(1)));
			ArrayList<Record> r = ds.getRecords(DEFAULT_LASTNAME);
			assertEquals(listWithTwoElements, r);
		} catch (RecordNotFoundException e) {
			fail();			
		}
	}

	private Record testRecord1() {
		String[] fields = new String[12];
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

		return new Record(fields);
	}

	private Record testRecord2() {
		String[] fields = new String[12];

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

		return new Record(fields);
	}

	private ArrayList<Record> listWithTwo() {
		ArrayList<Record> list = new ArrayList<Record>();

		String[] fields = new String[12];

		fields[0] = "Kasey";
		fields[1] = "Rollie";
		fields[2] = "Universal City Studios Inc";
		fields[3] = "1 Exchange Pl";
		fields[4] = "Jersey City";
		fields[5] = "Hudson";
		fields[6] = "NJ";
		fields[7] = "7302";
		fields[8] = "201-200-0785";
		fields[9] = "201-200-9759";
		fields[10] = "kasey@rollie.com";
		fields[11] = "http://www.kaseyrollie.com";

		list.add(new Record(fields));

		fields[0] = "Hattie";
		fields[1] = "Rollie";
		fields[2] = "Cranes";
		fields[3] = "123 Meadow Pl";
		fields[4] = "Lewisville";
		fields[5] = "Denton";
		fields[6] = "TX";
		fields[7] = "75067";
		fields[8] = "972-221-2207";
		fields[9] = "972-221-0982";
		fields[10] = "hattie@rollie.com";
		fields[11] = "http://www.hattierollie.com";

		list.add(new Record(fields));

		return list;
	}

	@Test
	public final void testSortedArrayGetSingleRecord() throws RecordNotFoundException, IOException {
		if (!loadedSortedArray)
			loadSortedArrayWithTimers();

		testGetSingleRecord(sortedArray);
	}

	@Test
	public final void testHashtableGetSingleRecord() throws RecordNotFoundException, IOException {
		if (!loadedHashtable)
			loadHashtableWithTimers();

		testGetSingleRecord(hashtable);
	}
	
	@Test
	public final void testSortedArrayGetMultiRecords() throws RecordNotFoundException, IOException {
		if (!loadedSortedArray)
			loadSortedArrayWithTimers();

		testGetMultiRecords(sortedArray);
	}

	@Test
	public final void testHashtableGetMultiRecords() throws RecordNotFoundException, IOException {
		if (!loadedHashtable)
			loadHashtableWithTimers();

		testGetMultiRecords(hashtable);
	}

	@Ignore
	@Test
	public final void testBinaryTreeGetRecordString() throws RecordNotFoundException, IOException {
		fail("Unimplemented method");

		if (!loadedBinaryTree)
			loadBinaryTreeWithTimers();
	}

	/**
	 * Test method for {@link capstone.SortedArray#getRecord(int)}.
	 */
	@Test
	public final void testSortedArrayGetRecordInt() throws IOException {
		if (!loadedSortedArray)
			loadSortedArrayWithTimers();

		String[] fields = new String[12];

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

		assertEquals(new Record(fields), ((SortedArray) sortedArray).getRecord(20));
	}
}
