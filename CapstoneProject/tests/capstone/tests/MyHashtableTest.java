/**
 * 
 */
package capstone.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import capstone.IncorrectNumberOfFieldsException;
import capstone.Monitor;
import capstone.MyHashtable;
import capstone.SortedArray;

/**
 * Tests for MyHashtable
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 */
public class MyHashtableTest {
	private static final String DEFAULT_SEARCH_ITEM = "734-665-7833";//#735

	private static MyHashtable hashtable;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Set up before class {");

		hashtable = new MyHashtable(349996);

		System.out.println("} Set up before class");
	}
	
	@Test
	public void loadDataWithTimers() throws IncorrectNumberOfFieldsException, IOException {
		long outerStartTime = System.currentTimeMillis();
		Monitor progressThread = new Monitor(hashtable);
		progressThread.start();
		
		long innerStartTime = System.currentTimeMillis();
		hashtable.loadData("1000.csv");
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
	 * Test method for {@link capstone.MyHashtable#walkThrough()}.
	 */
	@Test
	public final void testWalkThrough() {
		hashtable.walkThrough();
		
		assertEquals(1.0, hashtable.getProgress(), 0);
	}

	/**
	 * Test method for {@link capstone.MyHashtable#getRecord(java.lang.String)}.
	 */
	@Test
	public final void testGetRecord() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link capstone.MyHashtable#MyHashtable()}.
	 */
	@Test
	public final void testMyHashtable() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link capstone.MyHashtable#MyHashtable(int)}.
	 */
	@Test
	public final void testMyHashtableInt() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link capstone.MyHashtable#add(capstone.Record)}.
	 */
	@Test
	public final void testAdd() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link capstone.MyHashtable#getTableSize()}.
	 */
	@Test
	public final void testGetTableSize() {
		fail("Not yet implemented"); // TODO
	}

}
