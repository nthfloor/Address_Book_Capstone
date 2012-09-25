package capstone;

import java.util.ArrayList;

/**
 * Node class for binarytree implementation
 * 
 * Nathan Floor
 * Ryan Saunders
 *
 */
public class BinaryNode {
	//instance variables
	ArrayList<Record> element;
	BinaryNode left,right;

	public BinaryNode(){
		this(null,null,null);
	}
	public BinaryNode(ArrayList<Record> e, BinaryNode l, BinaryNode r){
		element = e;
		left = l;
		right = r;
	}

	//getters
	public ArrayList<Record> getElement(){return element;}
	public BinaryNode getLeft(){return left;}
	public BinaryNode getRight(){return right;}

	//setters
	public void setElement(ArrayList<Record> r){element = r;}
	public void setLeft(BinaryNode l){left = l;}
	public void setRight(BinaryNode r){right = r;}
	
	public void printInOrder(){
		if(left != null)
			left.printInOrder();
		//print record
		System.out.println(element.toString());
		//BinaryTree.outputList.add(element);		
		if(right != null)
			right.printInOrder();
		
	}
}