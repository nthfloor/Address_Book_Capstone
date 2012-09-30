import org.junit.AfterClass;
import org.junit.BeforeClass;


public class BinaryTreeTest extends DataStructureTest {
	
	private static DataStructure ds;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Set up before class {");

		dataFile = "350000_2.csv";
		ds = new BinaryTree(349996);

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
