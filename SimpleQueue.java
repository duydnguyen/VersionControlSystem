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
 * SimpleQueue class implements interface QueueADT<E> using circular array
 * Bugs: none known
 * @author Duy Nguyen
 */
public class SimpleQueue<E> implements QueueADT<E> {
	// *** fields ***
    private static final int INITSIZE = 10;  // initial array size
    private E[] items; // the items in the queue
    private int numItems;   // the number of items in the queue
    private int frontIndex;
    private int rearIndex;
    
  //*** constructor ***
    public SimpleQueue() {  
    	items = (E[]) new Object[INITSIZE];
    	numItems = 0;
    	frontIndex = 0;
    	rearIndex = 0;
    }  
        
  //*** required QueueADT methods ***

    public boolean isEmpty() { 
    	return (numItems == 0);
    }
    // remove items
    public E dequeue() throws EmptyQueueException {
    	// CHECK
    	
    	if (isEmpty()) {
    		throw new EmptyQueueException();
    	}
    	E temp = items[frontIndex];
    	items[frontIndex] = null;
    	numItems--;
    	if (frontIndex < rearIndex) {
    		frontIndex = incrementIndex(frontIndex);
    	}
    	
    	if (frontIndex == numItems) {
    		frontIndex = 0;
    	}    	
//    	System.out.println("++++frontIndex after dequeue =" + frontIndex);
//    	System.out.println("++++rearIndex after dequeue =" + rearIndex);
    	return temp;
    }

    // add items
    public void enqueue(E ob) {
    	//check for full array and expand if necessary   
    	//System.out.println("++++ items.length=  numItems? " + (items.length == numItems));
    	//System.out.println("++++ rearIndex=  " + rearIndex);
    	if (items.length == numItems) {
    		//System.out.println("++++ expanding Queue  ");
    		E[] tmp = (E[]) new Object[items.length*2];
    		//System.out.println("front = " + frontIndex + " , lengths =  " + items.length + ", " + numItems);
    		 System.arraycopy(items, frontIndex, tmp, frontIndex,
	                 items.length-frontIndex);
    		 if (frontIndex != 0) {
    			 System.arraycopy(items, 0, tmp, items.length, frontIndex);
    		 }
    		 items = tmp;
    		 rearIndex = frontIndex + numItems - 1;    		
    	}
    	// insert new item at rear of queue
    	// case when queue is empty
    	if (numItems == 0) {
    		items[0] = ob;
    		numItems++;    		
    		
    		//System.out.println("++++ rearIndex=  " + rearIndex);
    	} 
    	else {
    		// use auxillary method to increment rear index with wraparound
    		rearIndex = incrementIndex(rearIndex);
    		
    		//System.out.println("++++ rearIndex=  " + rearIndex);
    		items[rearIndex] = ob;
        	numItems++;
    	}
    	
    	
    }  

    private int incrementIndex(int index) {
    	if (index == items.length - 1) 
    		return 0;
    	else
    		return index + 1;
    	
    }
    public E peek() throws EmptyQueueException {  
    	if (isEmpty()) {
    		throw new EmptyQueueException();
    	}
    	return items[frontIndex];    	
    }

    public int size() {
    	return numItems;
    
    }
    
    public String toString() {
    	String s = "";
    	if (!isEmpty()) {
    		int i = (int) new Integer(frontIndex);
    		while (i != rearIndex) {
    			s += items[i] + "\n";
    			i = incrementIndex(i);    			
    		}
    		s += items[rearIndex] + "\n";
    	}
    	//System.out.println("++++frontIndex =" + frontIndex);
    	//System.out.println("++++rearIndex =" + rearIndex);
    	return s;
    }
    

   
      
}
