import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Unit tests for the BinaryTree class.
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 */
public class BinaryTreeTest extends DataStructureTest {
	
	private static DataStructure ds; // The data structure being tested
	
	/**
	 * Initialises ds and dataFile, before all tests are run
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Set up before class {");

		dataFile = "350000_2.csv";
		ds = new BinaryTree(349996);

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
