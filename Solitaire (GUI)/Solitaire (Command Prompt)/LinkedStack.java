package GAME;

import java.util.*;
/**
*	This "LinkedStack" class acts as the representation of the list of Nodes linked together but with the implementation of stack. Public access.
*/
public class LinkedStack{
	/**
	*	"top" attribute of type Node serves as the head of the linkedlist.  It is the first Node to be accessed. Private access
	*/
	private Node top = null;
	/**
	* "size" attribute of type Integer serves as the counter of how many Nodes are there in the list. Private access
	*/
	private int size = 0;

	/**
	*	This constructor is needed for instantiating the class LinkedStack into an object.
	*/

	public LinkedStack(){
	}

	/**
	* "push" method accepts a variable of type Object and adds a Node in the linked list, connecting it with the previous Node and now acts as the top. Public access
	*@param x (parameter) of type Object holds the data needed for the Node that is to be added in the linked list.
	*/	
	public void push(Object x){
		if (x == null) return;
		Node temp = new Node();
		temp.data = x;
		temp.link = top;
		top = temp;
		size++;
		return;
	}

	/**
	* "pop" method unlinks the current top node and makes the next node as the top. Public access.  
	*@return this method returns the current top as an Object, null if the linked list is empty.
	*/	

	public Object pop(){
		Object temp = new Object();
		if (top != null){
			temp = top.data;
			top = top.link;
			size--;
		}
		else return null;
		return temp;
	}

	/**
	* "peek" method enables the program to show the current top node without removing it. Public access
	* @return this method returns the current top as an Object, null if the linked list is empty.
	*/

	public Object peek(){ /*Titingnan lang yung current top*/
		Card temp = null;
		if (top != null) {
			temp = (Card) top.data;
			temp.setFaceUp(true);
			return (Object) temp;
		}
		else return null;
	}

	/**
	* "isEmpty" method checks the Linkedlist if it is empty. Public access.
	*@return  this method returns true the top is equal to null and size is equal to 0 and false if not.
	*/

	public boolean isEmpty(){
		if (top == null && size == 0) return true;
		else return false;
	}

	/**
	* "foundationFull" method checks the Linkedlist, particularly the foundation list, if it is full which meands it must be equal to 13. Public access.
	*@return  this method returns true if the size is equal to 13 and false if not.
	*/

	public boolean foundationFull(){
		if (size == 13) return true;
		else return false;
	}

	/**
	* "clear" method sets the top as null and size equal to 0, meaning erasing the Nodes in the linked ilst. Public access.
	*/

	public void clear(){
		top = null;
		size = 0;
		return;
	}

	/**
	* "iTableau" method accesses the Node of a specific index in the linked list, treating the top as the last element. Public access.
	*@param index (parameter) of type Integer is treated as the i-th index to be accessed in the list.
	*@return this method returns the accessed node of type Card.
	*/

	public Card iTableau(int index){
		Node n = top;
		if(index > size-1) return null;
		for (int i = 0 ; i < size-index-1 ; i++){
			if(n == null) return null;
			n = n.link;
		}
		if (n == null) return null;
		return (Card) n.data;
	}

	/**
	* "getSize" method returns the size off the list. Public access.
	*@return This method returns the size of the list as an integer.
	*/

	public int getSize(){
		return this.size;
	}

	/**
	* "toString" method gives the top node's value which means the rank (integer) and the suit (Suit). Public access.
	*@return This method returns the Card's toString method of the top node as a string, "[  ]" if the linked list is empty.
	*/

	public String toString(){ 
		if (top == null) return "[   ]";
		else {
			Card toReturn = (Card) top.data;
			return toReturn.toString();
		}
	}
}