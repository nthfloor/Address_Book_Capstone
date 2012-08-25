import java.io.IOException;

/**
 * Record calss for address book entries
 * 
 * Nathan Floor
 * Ryan Saunders
 *
 */

public class BinaryTree extends DataStructure{
	
	//instance variables
	Record root;
	
	//progress variables
	private Monitor progressThread;
	private volatile int numberOfRecs;
	private int totalNumberOfRecs;
	private volatile int walkCounter = 0;
	private volatile int searchCounter = 0;
	private boolean isLoading = false;
	private boolean isWalking = false;
	private boolean isRandomAccess = false;

	//timing variables
	private double startTime;
	private double currentTime;
	
	public BinaryTree(){
		
	}

	@Override
	void loadData(String filename) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	void walkThrough() {
		// TODO Auto-generated method stub
		
	}

	@Override
	String getRecord(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	double getProgress() {
		// TODO Auto-generated method stub
		return 0;
	}

}
