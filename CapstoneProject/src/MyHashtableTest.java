import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Unit tests for the MyHashtable class.
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 */
public class MyHashtableTest extends DataStructureTest {
	
	private static DataStructure ds; // The data structure being tested
	
	/**
	 * Initialises ds and dataFile, before all tests are run
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Set up before class {");

		dataFile = DEFAULT_DATA_FILE;
		ds = new MyHashtable(349996);

		System.out.println("} Set up before class");
	}
	
	/**
	 * Breaks down ds after all tests were run
	 */
	@AfterClass
	public static void shutDownAfterClass() {
		ds = null;
	}

	/**
	 * Returns ds
	 */
	@Override
	protected DataStructure getDataStructure() {
		return ds;
	}
}
