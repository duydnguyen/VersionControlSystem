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
 * Thrown when trying to dequeue an element from an empty queue.
 * Bugs: none known
 * @author Duy Nguyen
 */
@SuppressWarnings("serial")
public class EmptyQueueException extends Exception {
	// Constructor:
	public EmptyQueueException() {
		super();
	}
	
	public EmptyQueueException(String message) {
		super(message);
	}
}
