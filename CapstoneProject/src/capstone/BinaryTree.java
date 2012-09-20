package capstone;

import java.io.BufferedReader;
import java.io.FileReader;
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
	
	public static ArrayList<Record> outputList;
	
	public BinaryTree(){
		root = null;
	}
	public BinaryTree(int numRecs){
		root = null;
		numberOfRecs = numRecs;
	}

	//utility methods
	public boolean isEmpty(){return root == null;}
	public void makeEmpty(){root = null;}
	public BinaryNode getRoot(){return root;}	

	@Override
	public	void loadData(String filename) throws IOException, IncorrectNumberOfFieldsException {
		isLoading = true;
		isWalking = false;
		isRandomAccess = false;
		BufferedReader input = new BufferedReader(new FileReader(filename));
		String[] newRecord;
		String newline = "";
		//loop through all records and add them to tree
		while ((newline = input.readLine()) != null) {
			synchronized (this) {
				numberOfRecs++;
			}
			newRecord = newline.split(";");
			insert(new Record(newRecord));// add to tree
		}

		input.close();		
	}

	private void insert(Record rec){
		root = insertNode(rec,root);
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
		isLoading = false;
		isWalking = true;
		isRandomAccess = false;
		outputList = new ArrayList<Record>();
		
		root.printInOrder();
	}

	@Override
	public ArrayList<Record> getRecords(String key) {
		isLoading = false;
		isWalking = false;
		isRandomAccess = true;
		outputList = new ArrayList<Record>();
		
		
		
		
		return null;
	}
	
	private void search(BinaryNode node, BinaryNode searchItem){
		if(node.element.compareTo(searchItem.element.getKeyValue()) < 0){
			
		}
	}
}
