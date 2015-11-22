///////////////////////////////////////////////////////////////////////////////
//                   
// Title:           Program 3
// Files:           VersionControlApp.java, VersionControlDb.java, User.java, Repo.java
//					RepoCopy.java, Change.java, ChangeSet.java, StackADT.java,
//					QueueADT.java, EmptyStackException.java, EmptyQueueException.java
//					Document.java, ErrorType.java
// Semester:         CS367 Summer 2015
//
// Author:           Duy Nguyen
// Email:            ddnguyen4@wisc.edu
// CS Login:         ddnguyen4
// Lecturer's Name:  Ms. Cea Stapleton
// Lab Section:      NA
////////////////////////////////////////////////////////////////////////////////

/**
 * SimpleStack class implements interface StackADT<E> using circular array
 * Bugs: none known
 * @author Duy Nguyen
 */

public class SimpleStack<E> implements StackADT<E> {
	// *** fields ***
    private static final int INITSIZE = 10;  // initial array size
    private E[] items; // the items in the stack
    // the number of items in the stack: indexed at 0
    private int numItems;   


  // *** constructor ***
    public SimpleStack() { 
    	items = (E[]) new Object[INITSIZE];
    	numItems = -1;
    }     
     
  // *** required StackADT methods ***
    
    // add items
    public void push(E item) {  //
    	if (item == null) {
    		throw new IllegalArgumentException();
    	} 
    	else { 
    		if (size() == items.length) {
        		expandStorage();
        		items[++numItems] = item;
        	}
    		else {
    			items[++numItems] = item;
    		}
    	}  	
    }  

        
    // remove items
    public E pop() throws EmptyStackException { //
    	E element;
    	if (isEmpty()) 
    		throw new EmptyStackException();
    	element = items[numItems];
    	items[numItems--] = null; // dereference
    	return element;
}

    // other methods
    public E peek() throws EmptyStackException { // 
    	if (isEmpty()) {
    		throw new EmptyStackException();
    	}
    	return items[numItems];
}

    public boolean isEmpty() { //
    	return (numItems < 0);
    }  

    public int size() {
    	return (numItems + 1);
    }
    
    public String toString() {
    	String s;
    	s = "";
    	if ( size() > 0 ) s+= items[0];
    	if ( size() > 1 )
    		for (int i = 1; i <=size() - 1; i++) {
    			s += ", " + items[i];
    		}
    	return s;
    }
    
    // Internal method to handle capacity issues
    private void expandStorage() {
    	E[] oldItems = items;
    	items = (E[]) new Object[2 * items.length];
    	for (int i = 0; i < oldItems.length; i++) {
    		items[i] = oldItems[i];
    	}
    }
}

