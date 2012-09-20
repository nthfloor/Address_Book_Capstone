package capstone;

/**
 * Node class for binarytree implmentation
 * 
 * Nathan Floor
 * Ryan Saunders
 *
 */
public class BinaryNode implements Comparable<String>{
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
	
	@Override
	//TODO
	public int compareTo(String arg0) {
		
		if(element.getKeyValue() < arg0)
			return -1;
		else if(element.getKeyValue() > arg0)
			return 1;
		else		
			return 0;
	}
}