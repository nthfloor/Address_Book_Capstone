package capstone;

import java.io.IOException;
import java.util.ArrayList;

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

	//utility methods
	public boolean isEmpty(){return root == null;}
	public void makeEmpty(){root = null;}
	public BinaryNode getRoot(){return root;}	

	@Override
	public	void loadData(String filename) throws IOException, IncorrectNumberOfFieldsException {
		
		
	}

	private void insert(Record node){
		root = insertNode(node,root);
	}

	
	private BinaryNode insertNode(Record value,BinaryNode node){
		if(node == null)
			node = new BinaryNode(value,null,null);
		else if(value.compareTo(node.getElement().getKeyValue()) == 0){
			//replace the value in this node with 
			node.element = value;
		}
		else if(value.compareTo(node.getElement().getKeyValue()) < 0){
			node.left = insertNode(value, node.left);
		}
		else
			node.right = insertNode(value, node.right);
		
		return node; 
	}

	@Override
	//inorder walkthrough traversal
	public void walkThrough() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Record> getRecords(String key) {
		// TODO Auto-generated method stub
		return null;
	}
}
