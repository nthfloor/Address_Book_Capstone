

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Binary tree class for address book entries
 * Not self-balancing
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
		this(0);
	}
	public BinaryTree(int numRecs){
		outputList = new ArrayList<Record>();
		root = null;
		totalNumberOfRecs = numRecs;
		numberOfRecs = 0;
	}

	//utility methods
	public boolean isEmpty(){return root == null;}
	public void makeEmpty(){root = null;}
	public BinaryNode getRoot(){return root;}	

	//reads data from file specified by filename, and loads into tree structure.
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
		insert2(root, rec);
	}

	//private method for inserting records into binary tree, uses recursion
	private BinaryNode insertNode(Record value,BinaryNode node){
		
		if(node == null){
			ArrayList<Record> temp = new ArrayList<Record>();
			temp.add(value);
			node = new BinaryNode(temp);
		}
		else if(value.compareTo(node.element.get(0).getKeyValue()) < 0)
			insertNode(value,node.left);
		else if(value.compareTo(node.element.get(0).getKeyValue()) > 0)
			insertNode(value,node.right);
		else{
			//if equal
			node.element.add(value);			
		}
		return node;			
	}
	
	//inserts records into binary tree, using recursion
	private void insert2(BinaryNode node, Record value) {
		if(node == null){
			root = new BinaryNode(value);
			//System.out.println("  Inserted " + value.toString() + " to left of Node " + node.element.get(0).toString());			
		}
		else if (value.getPhoneValue().compareTo(node.element.get(0).getKeyValue()) < 0) {
            if (node.left != null) {
                insert2(node.left, value);
            } else {
                //System.out.println("  Inserted " + value.toString() + " to left of Node " + node.element.get(0).toString());
                node.left = new BinaryNode(value);
            }
        } 
		else if (value.getPhoneValue().compareTo(node.element.get(0).getKeyValue()) > 0){
            if (node.right != null) {
                insert2(node.right, value);
            } else {
                //System.out.println("  Inserted " + value.toString() + " to right of Node " + node.element.get(0).toString());
                node.right = new BinaryNode(value);
            }
        }
		else{
			//if two records map to same node
			//System.out.println("  Inserted " + value.toString() + " to right of Node " + node.element.get(0).toString());
            node.element.add(value);
		}
    }

	@Override
	//in-order walk-through traversal
	public void walkThrough() {
		isLoading = false;
		isWalking = true;
		isRandomAccess = false;
		walkCounter = 0;
		
		root.printInOrder();		
	}

	//returns result of search through tree for specified field. 
	//either performs sequential or binary search depending on what field the search operation is being performed on.
	@Override
	public ArrayList<Record> getRecords(String key) throws RecordNotFoundException{
		isLoading = false;
		isWalking = false;
		isRandomAccess = true;
		outputList = new ArrayList<Record>();
		searchCounter = 0;
		
		if(Record.currentSearchType == Record.selectedSearchType){
			outputList = find(key,root);			
		}
		else{
			//perform sequential search on non-indexed record fields
			//System.out.println(root);
			root.searchInOrder(key);
			//outputList = new ArrayList<Record>();
		}			
		
		if(outputList == null || outputList.size() == 0)
			throw new RecordNotFoundException();
		else 
			return outputList;		
	}
	
	//searches tree structure using iterative traversal of tree.
	private ArrayList<Record> find(String x, BinaryNode searchItem){
		ArrayList<Record> temp_outputList = new ArrayList<Record>();
		
		while(searchItem != null){
			synchronized (this) {
				searchCounter++;
			}
			if(x.toUpperCase().compareTo(searchItem.element.get(0).getKeyValue().toUpperCase()) < 0)
				searchItem = searchItem.left;
			else if(x.toUpperCase().compareTo(searchItem.element.get(0).getKeyValue().toUpperCase()) > 0)
				searchItem = searchItem.right;
			else{				
				for(int i=0;i<searchItem.element.size();i++){
//					System.out.println(searchItem.element.get(i).toString());
					temp_outputList.add(searchItem.element.get(i));
				}
				
				return temp_outputList; // match
			}
		}
		
		return null; // not found
	}
}
