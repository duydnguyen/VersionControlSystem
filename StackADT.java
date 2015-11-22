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
 * An ordered collection of items, where items are both added and removed 
 * exclusively from the front.
 * @author
 */
public interface StackADT<E> {
    /**
     * Checks if the stack is empty.
     * @return true if stack is empty; otherwise false
     */
    boolean isEmpty();

    /**
     * Returns (but does not remove) the top item of the stack.
     * @return the top item of the stack
     * @throws EmptyStackException if the stack is empty
     */
    E peek() throws EmptyStackException;

    /**
     * Pops the top item off the stack and returns it. 
     * @return the top item of the stack
     * @throws EmptyStackException if the stack is empty
     */
    E pop() throws EmptyStackException;


    /**
     * Pushes the given item onto the top of the stack.
     * @param item the item to push onto the stack
     * @throws IllegalArgumentException if item is null.
     */
    void push(E item);

    /**
     * Returns the size of the stack.
     * @return the size of the stack
     */
    int size();

    /**
     * Returns a string representation of the stack (for printing).
     * @return a string representation of the stack
     */
    String toString(); 
}