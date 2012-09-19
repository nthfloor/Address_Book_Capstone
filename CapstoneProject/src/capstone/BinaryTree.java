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
	BinaryNode root;
	
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
		root = null;
	}
	public BinaryTree(String filename) throws IOException{
		root = null;
		loadData(filename);
	}

	//utility methods
	public boolean isEmpty(){return root == null;}
	public void makeEmpty(){root = null;}
	public BinaryNode getRoot(){return root;}	

	@Override
	void loadData(String filename) throws IOException {
		
		
	}

	private void insert(Record node){
		root = insertNode(node,root);
	}

	private BinaryNode insertNode(Record node,BinaryNode aRoot){
		if(aRoot == null)
			aRoot = new BinaryNode(node);
		else if()
	}

	@Override
	//inorder walkthrough traversal
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
