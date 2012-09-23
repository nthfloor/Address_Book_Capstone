package capstone.datastructures;

import capstone.WalkThroughMessangerException;
import capstone.WalkThroughMessenger;


/**
 * Node class for binarytree implmentation
 * 
 * Nathan Floor
 * Ryan Saunders
 *
 */
public class BinaryNode {
	//instance variables
	Record element;
	BinaryNode left,right;

	public BinaryNode(){
		this(null,null,null);
	}
	public BinaryNode(Record e, BinaryNode l, BinaryNode r){
		element = e;
		left = l;
		right = r;
	}

	//getters
	public Record getElement(){return element;}
	public BinaryNode getLeft(){return left;}
	public BinaryNode getRight(){return right;}

	//setters
	public void setElement(Record r){element = r;}
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
	
	public void messageInOrder(WalkThroughMessenger messenger) throws WalkThroughMessangerException {
		if (left != null)
			left.messageInOrder(messenger);

		messenger.appendWalkThroughMessage(element.toString());

		//BinaryTree.outputList.add(element);		
		if (right != null)
			right.messageInOrder(messenger);

	}
}