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
		System.out.println("Start loading data into tree.");
		while ((newline = input.readLine()) != null) {
			synchronized (this) {
				numberOfRecs++;
			}
			newRecord = newline.split(";");
			insert(new Record(newRecord));// add to tree
		}
		System.out.println("Finished loading data into tree.");

		input.close();		
	}

	private void insert(Record rec){
		root = insertNode(rec,root);
	}

	
	private BinaryNode insertNode(Record value,BinaryNode node){
		if(node == null){
			ArrayList<Record> temp = new ArrayList<Record>();
			temp.add(value);
			node = new BinaryNode(temp,null,null);
		}
		else if(value.compareTo(node.getElement().get(0).getKeyValue()) == 0){
			//replace the value in this node with 
			node.element.add(value);
		}
		else if(value.compareTo(node.getElement().get(0).getKeyValue()) < 0){
			node.left = insertNode(value, node.left);
		}
		else
			node.right = insertNode(value, node.right);
		
		return node; 
	}

	@Override
	//in-order walk-through traversal
	public void walkThrough() {
		isLoading = false;
		isWalking = true;
		isRandomAccess = false;
		outputList = new ArrayList<Record>();
		
		root.printInOrder();		
	}

	@Override
	public ArrayList<Record> getRecords(String key) throws RecordNotFoundException{
		isLoading = false;
		isWalking = false;
		isRandomAccess = true;
		
		ArrayList<Record> outputList = find(key,root);	
		
		if(outputList == null || outputList.size() > 0)
			throw new RecordNotFoundException();
		else 
			return outputList;
		
	}
	
	private ArrayList<Record> find(String x, BinaryNode searchItem){
		outputList = new ArrayList<Record>();
		
		while(searchItem != null){
			if(x.compareTo(searchItem.element.get(0).getKeyValue()) < 0)
				searchItem = searchItem.left;
			else if(x.compareTo(searchItem.element.get(0).getKeyValue()) < 0)
				searchItem = searchItem.right;
			else{
				for(int i=0;i<searchItem.element.size();i++)
					outputList.add(searchItem.element.get(i));
				
				return outputList; // match
			}
		}
		
		return null; // not found
	}
}
