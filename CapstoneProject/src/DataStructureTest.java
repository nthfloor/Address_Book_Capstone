import static org.junit.Assert.*;

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
	// A phone number that matches the following record:
	// Wallace;Coggins;Goodyear Auto Service Centers;1011 N University Ave;Ann Arbor;Washtenaw;MI;48109;734-665-7833;734-665-8136;wallace@coggins.com;http://www.wallacecoggins.com
	protected static final String DEFAULT_PHONE_1 = "734-665-7833";

	// A phone number that matches the following record:
	// Alicia;Donez;Esp Sales;2000 K St Nw;Washington;District of Columbia;DC;20006;202-223-0386;202-223-0403;alicia@donez.com;http://www.aliciadonez.com
	protected static final String DEFAULT_PHONE_2 = "202-223-0386";

	// A surname that matches the following records:
	//Kasey;Rollie;Universal City Studios Inc;1 Exchange Pl;Jersey City;Hudson;NJ;7302;201-200-0785;201-200-9759;kasey@rollie.com;http://www.kaseyrollie.com
	//Hattie;Rollie;Cranes;123 Meadow Pl;Lewisville;Denton;TX;75067;972-221-2207;972-221-0982;hattie@rollie.com;http://www.hattierollie.com
	protected static final String DEFAULT_LASTNAME = "Rollie";
	
	// A surname that matches the following records:
	//Terrell;Delap;Rehab New England Inc;Ft;Jersey City;Hudson;NJ;7305;201-200-1425;201-200-4077;terrell@delap.com;http://www.terrelldelap.com
	// ...
	protected static final String DEFAULT_FIRSTNAME = "Terrell";
	
	// A string that does not match any record
	protected static final String NONSENSE_STRING = "abcxyz321";
	
	protected static final String DEFAULT_DATA_FILE = "350000.csv"; // The default file to load
	
	protected static final int DS_SIZE = 349996; // The default file size
	
	protected static String dataFile;

	/**
	 * Returns the data structure being tested
	 */
	protected abstract DataStructure getDataStructure();

	/**
	 * Tests the loadData() method corresponding to the data structure
	 * 
	 * @throws IOException
	 */
	@Test
	public void loadDataStructure() throws IOException {
		
		DataStructure ds = getDataStructure();
		
		Runner.loadData(ds, dataFile, new CommandLineMonitor(ds));
		assertEquals(1.0, ds.getProgress(), 0);
	}

	/**
	 * Tests the walkThrough() method corresponding to the data structure 
	 */
	@Test
	public final void testWalkThrough() {
		
		DataStructure ds = getDataStructure();

		ds.walkThrough();
		assertEquals(1.0, ds.getProgress(), 0.001);
	}

	/**
	 * Tests the getRecords() method, when search type is set to "Phone"
	 */
	@Test
	public final void testGetRecordsByPhone() {
		
		DataStructure ds = getDataStructure();
		
		Record.currentSearchType = Record.SearchType.PHONE;
		
		ArrayList<Record> list1 = new ArrayList<Record>();
		list1.add(sampleRecord1());

		ArrayList<Record> list2 = new ArrayList<Record>();
		list2.add(sampleRecord2());
		
		try {
			assertEquals(list1, ds.getRecords(DEFAULT_PHONE_1));
			assertEquals(list2, ds.getRecords(DEFAULT_PHONE_2));
		} catch (RecordNotFoundException e) {
			// This block should not be reached
			
			e.printStackTrace();
			fail();
		}

		testSearchForNonexistentRecord(ds);
	}

	/**
	 * Tests the getRecords() method, when search type is set to "Lastname"
	 */
	@Test
	public final void testGetRecordsByLastname() {
		
		DataStructure ds = getDataStructure();
		Record.currentSearchType = Record.SearchType.LASTNAME;

		ArrayList<Record> list = listOfRollieRecords();

		try {
			ArrayList<Record> r = ds.getRecords(DEFAULT_LASTNAME);
			
			for (Record rec: list){
				assertTrue(r.contains(rec));
			}
		} catch (RecordNotFoundException e) {
			fail();			
		}
		
		testSearchForNonexistentRecord(ds);
	}
	
	/**
	 * Tests the getRecords() method, when search type is set to "Firstname"
	 */
	@Test
	public final void testGetRecordsByFirstname() {
		
		DataStructure ds = getDataStructure();
		Record.currentSearchType = Record.SearchType.FIRSTNAME;

		ArrayList<Record> list = listOfTerrellRecords();
		
		try {
			ArrayList<Record> r = ds.getRecords(DEFAULT_FIRSTNAME);
			
			for (Record rec: list){
				assertTrue(r.contains(rec));
			}
			
			assertEquals(126, r.size());
		} catch (RecordNotFoundException e) {
			fail();			
		}

		testSearchForNonexistentRecord(ds);
	}

	/**
	 * Ensures that a RecordNotFoundException is thrown when searching for a non-existent record.
	 * 
	 * @param ds - the data struture being tested
	 */
	private void testSearchForNonexistentRecord(DataStructure ds) {
		// Test search for non-existent record
		ArrayList<Record> list3 = null;
		try {
			list3 = ds.getRecords(NONSENSE_STRING);
			
			// The preceding line should throw an exception, so this line should not be reached
			fail();
		} catch (RecordNotFoundException e) {
			assertNull(list3);
		}
	}

	/**
	 * @returns Wallace Coggins' record
	 */
	private Record sampleRecord1() {
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

	/**
	 * @returns Alicia Donez's record
	 */
	private Record sampleRecord2() {
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

	/**
	 * @returns all records where surname="Rollie"
	 */
	private ArrayList<Record> listOfRollieRecords() {
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
	 * @returns two records where firstname="Terrell"
	 */
	private ArrayList<Record> listOfTerrellRecords() {
		ArrayList<Record> list = new ArrayList<Record>();

		String[] fields = new String[12];

		fields[0] = "Terrell";
		fields[1] = "Delap";
		fields[2] = "Rehab New England Inc";
		fields[3] = "Ft";
		fields[4] = "Jersey City";
		fields[5] = "Hudson";
		fields[6] = "NJ";
		fields[7] = "7305";
		fields[8] = "201-200-1425";
		fields[9] = "201-200-4077";
		fields[10] = "terrell@delap.com";
		fields[11] = "http://www.terrelldelap.com";

		list.add(new Record(fields));

		fields[0] = "Terrell";
		fields[1] = "Shedrick";
		fields[2] = "Courter Quinn Doran & Anderson";
		fields[3] = "755 W Side Ave";
		fields[4] = "Jersey City";
		fields[5] = "Hudson";
		fields[6] = "NJ";
		fields[7] = "7306";
		fields[8] = "201-332-5204";
		fields[9] = "201-332-7908";
		fields[10] = "terrell@shedrick.com";
		fields[11] = "http://www.terrellshedrick.com";

		list.add(new Record(fields));

		return list;
	}
}
