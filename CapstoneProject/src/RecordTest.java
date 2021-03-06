import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit tests for the Record class.
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 */
public class RecordTest {

	private static Record record1;
	private static Record record2;

	/** 
	 * @returns an example record
	 * 
	 * @throws IncorrectNumberOfFieldsException
	 */
	private Record createExampleRecord() throws IncorrectNumberOfFieldsException {
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
	 * Tests the primary constructor of the Record class
	 * 
	 * @throws IncorrectNumberOfFieldsException
	 */
	@Test
	public final void testConstructor() throws IncorrectNumberOfFieldsException {
		record1 = createExampleRecord();

		assertEquals("734-665-7833", record1.getKeyValue());
	}

	/**
	 * Tests the copy constructor of the Record class
	 * 
	 * @throws IncorrectNumberOfFieldsException
	 */
	@Test
	public final void testCopyConstructorAndEqualsMethod() throws IncorrectNumberOfFieldsException {
		record1 = createExampleRecord();

		record2 = new Record(record1);

		assertEquals("734-665-7833", record1.getKeyValue());
		assertEquals("734-665-7833", record2.getKeyValue());

		assertNotSame(record1, record2);
		assertEquals(record1, record2);

		String[] emptyFields = { "", "", "", "", "", "", "", "", "", "", "", "" };
		record2 = new Record(emptyFields);

		assertNotSame(record1, record2);
		assertFalse(record1.equals(record2));

		record2 = createExampleRecord();

		assertEquals("734-665-7833", record1.getKeyValue());
		assertEquals("734-665-7833", record2.getKeyValue());

		assertNotSame(record1, record2);
		assertEquals(record1, record2);
	}
}
