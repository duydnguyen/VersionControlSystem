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
 * An ordered collection of items, where items are added to the back and removed
 * from the front.
 * Bugs: none known
 * @author Duy Nguyen
 */
public interface QueueADT<E> {
    /**
     * Checks if the queue is empty.
     * @return true if queue is empty; otherwise false.
     */
    boolean isEmpty();

    /**
     * removes and returns the front item of the queue.
     * @return the front item of the queue.
     * @throws EmptyQueueException if the queue is empty.
     */
    E dequeue() throws EmptyQueueException;


    /**
     * Adds an item to the rear of the queue.
     * @param item the item to add to the queue.
     * @throws IllegalArgumentException if item is null.
     */
    void enqueue(E item);

    /**
     * Returns (but does not remove) the front item of the queue.
     * @return the front item of the queue.
     * @throws EmptyQueueException if the queue is empty.
     */
    E peek() throws EmptyQueueException;
    
    /**
     * Returns the size of the queue.
     * @return the size of the queue
     */
    int size();

    /**
     * Returns a string representation of the queue (for printing).
     * @return a string representation of the queue.
     */
    String toString(); 
}