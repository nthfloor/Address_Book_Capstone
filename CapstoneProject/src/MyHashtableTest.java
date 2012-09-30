import org.junit.AfterClass;
import org.junit.BeforeClass;


public class MyHashtableTest extends DataStructureTest {
	
	private static DataStructure ds;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Set up before class {");

		dataFile = DEFAULT_DATA_FILE;
		ds = new MyHashtable(349996);

		System.out.println("} Set up before class");
	}
	
	@AfterClass
	public static void shutDownAfterClass() {
		ds = null;
	}

	@Override
	protected DataStructure getDataStructure() {
		return ds;
	}
}
