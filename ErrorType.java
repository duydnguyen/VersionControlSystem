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
 * Error types.
 * Bugs: none known
 * @author Duy Nguyen
 */
public enum ErrorType {
	SUCCESS, 
	ACCESS_DENIED, 
	DOC_NOT_FOUND, 
	USER_NOT_FOUND, 
	REPO_NOT_FOUND, 
	USERNAME_ALREADY_EXISTS, 
	REPONAME_ALREADY_EXISTS,
	DOCNAME_ALREADY_EXISTS,
	NO_OLDER_VERSION, 
	REPO_NOT_SUBSCRIBED, 
	NO_PENDING_CHECKINS, 
	NO_LOCAL_CHANGES, 
	INTERNAL_ERROR,
	UNKNOWN_COMMAND
}