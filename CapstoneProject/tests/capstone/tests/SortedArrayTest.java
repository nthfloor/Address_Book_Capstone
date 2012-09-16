package capstone.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import capstone.IncorrectNumberOfFieldsException;
import capstone.Monitor;
import capstone.Record;
import capstone.RecordNotFoundException;
import capstone.SortedArray;

/**
 * Tests for SortedArray
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 */
public class SortedArrayTest {
	private static final String DEFAULT_SEARCH_ITEM = "734-665-7833";//#735
	
	private static SortedArray sortedArray;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClassAndTestLoadData() throws Exception {
		System.out.println("Set up before class {");
		
		sortedArray = new SortedArray(349996);
		
		System.out.println("} Set up before class");
	}

	@Test
	public void loadDataWithTimers() throws IncorrectNumberOfFieldsException, IOException {
		long outerStartTime = System.currentTimeMillis();
		Monitor progressThread = new Monitor(sortedArray);
		progressThread.start();
		
		long innerStartTime = System.currentTimeMillis();
		sortedArray.loadData("350000.csv");
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

	/**
	 * Test method for {@link capstone.SortedArray#walkThrough()}.
	 */
	@Test
	public final void testWalkThrough() {
		sortedArray.walkThrough();
		
		assertEquals(1.0, sortedArray.getProgress(), 0);
	}

	/**
	 * Test method for {@link capstone.SortedArray#getRecord(java.lang.String)}.
	 * @throws RecordNotFoundException 
	 * @throws IncorrectNumberOfFieldsException 
	 */
	@Test
	public final void testGetRecordString() throws RecordNotFoundException, IncorrectNumberOfFieldsException {
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
		
		assertEquals(new Record(fields), sortedArray.getRecord(DEFAULT_SEARCH_ITEM));
	}
	
	/**
	 * Test method for {@link capstone.SortedArray#getRecord(int)}.
	 * @throws IncorrectNumberOfFieldsException 
	 */
	@Test
	public final void testGetRecordInt() throws IncorrectNumberOfFieldsException {
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
		
		assertEquals(new Record(fields), sortedArray.getRecord(20));
	}
}
