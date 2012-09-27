

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
		element = new ArrayList<Record>();
	}
	public BinaryNode(Record e){
		element = new ArrayList<Record>();
		element.add(e);
		left = null;
		right = null;
	}
	public BinaryNode(ArrayList<Record> e){
		element = e;
		left = null;
		right = null;
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
	
	public void searchInOrder(String value){
		if(left != null)
			left.searchInOrder(value);
		
		if(Record.currentSearchType == Record.SearchType.FIRSTNAME){
			for(int i=0;i<element.size();i++){			
				if(element.get(i).getFirstnameValue().equals(value)){
					System.out.println(element.get(i).toString());
					BinaryTree.outputList.add(element.get(i));
				}
			}
		}
		else if(Record.currentSearchType == Record.SearchType.LASTNAME){
			for(int i=0;i<element.size();i++){			
				if(element.get(i).getLastnameValue().equals(value)){
					System.out.println(element.get(i).toString());
					BinaryTree.outputList.add(element.get(i));
				}
			}
		}
		else if(Record.currentSearchType == Record.SearchType.PHONE){
			for(int i=0;i<element.size();i++){			
				if(element.get(i).getPhoneValue().equals(value)){
					System.out.println(element.get(i).toString());
					BinaryTree.outputList.add(element.get(i));
				}
			}
		}
		
		if(right != null)
			right.searchInOrder(value);
	}
}