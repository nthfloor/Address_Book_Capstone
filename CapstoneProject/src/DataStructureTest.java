import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

/**
 * Unit tests for the DataStructure class and its subclasses.
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 */
public abstract class DataStructureTest {
	//Wallace;Coggins;Goodyear Auto Service Centers;1011 N University Ave;Ann Arbor;Washtenaw;MI;48109;734-665-7833;734-665-8136;wallace@coggins.com;http://www.wallacecoggins.com
	protected static final String DEFAULT_PHONE_1 = "734-665-7833";

	//Alicia;Donez;Esp Sales;2000 K St Nw;Washington;District of Columbia;DC;20006;202-223-0386;202-223-0403;alicia@donez.com;http://www.aliciadonez.com
	protected static final String DEFAULT_PHONE_2 = "202-223-0386";

	//Kasey;Rollie;Universal City Studios Inc;1 Exchange Pl;Jersey City;Hudson;NJ;7302;201-200-0785;201-200-9759;kasey@rollie.com;http://www.kaseyrollie.com
	//Hattie;Rollie;Cranes;123 Meadow Pl;Lewisville;Denton;TX;75067;972-221-2207;972-221-0982;hattie@rollie.com;http://www.hattierollie.com
	protected static final String DEFAULT_LASTNAME = "Rollie";
	
	protected static final String DEFAULT_DATA_FILE = "350000.csv";
	
	protected static final int DS_SIZE = 349996;
	
	protected static String dataFile;

	protected abstract DataStructure getDataStructure();

	@Test
	public void loadDataStructure() throws IOException {
		
		DataStructure ds = getDataStructure();
		
		Runner.loadData(ds, dataFile, new CommandLineMonitor(ds));
		assertEquals(1.0, ds.getProgress(), 0);
	}


	@Test
	public final void testWalkThrough() {
		
		DataStructure ds = getDataStructure();

		ds.walkThrough();
		assertEquals(1.0, ds.getProgress(), 0.001);
	}

	// Tests getRecords() for a list containing one element
	@Test
	public final void testGetSingleRecord() {
		
		DataStructure ds = getDataStructure();

		try {
			Record.currentSearchType = Record.SearchType.PHONE;
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
	@Test
	public final void testGetMultiRecords() {
		
		DataStructure ds = getDataStructure();

		try {
			ArrayList<Record> listWithTwoElements = listWithTwo();
			Record.currentSearchType = Record.SearchType.LASTNAME;

			System.out.println(listWithTwoElements.get(0).equals(ds.getRecords(DEFAULT_LASTNAME).get(0)));
			System.out.println(listWithTwoElements.get(1).equals(ds.getRecords(DEFAULT_LASTNAME).get(1)));
			ArrayList<Record> r = ds.getRecords(DEFAULT_LASTNAME);
			
			for (Record rec: listWithTwoElements){
				assertTrue(r.contains(rec));
			}
			
//			assertEquals(listWithTwoElements, r);
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

	
	/**
	 * Test method for {@link capstone.datastructures.SortedArray#getRecord(int)}.
	 */
	/*
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
	*/
}
