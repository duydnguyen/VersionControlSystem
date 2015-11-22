///////////////////////////////////////////////////////////////////////////////
//                   
// Title:            Program 3
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
 * Thrown when trying to peek or pop an item from an empty stack.
 * Bugs: none known
 * @author Duy Nguyen
 */
@SuppressWarnings("serial")
public class EmptyStackException extends Exception {
	// Constructor:
	public EmptyStackException() {
		super();
	}
	
	public EmptyStackException(String message) {
		super(message);
	}
}
