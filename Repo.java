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
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a repository which stores and tracks changes to a collection of 
 * documents.
 * Bugs: none known
 * @author Duy Nguyen
 */
public class Repo {
	
	/* The current version of the repo. */
	private int version;
	
	/* The name of the repo. It's a unique identifier for a repository. */
	private final String repoName;
	
	/* The user who is the administrator of the repo. */
	private final User admin;
	
	/* The collection(list) of documents in the repo. */
	private  List<Document> docs;
	
	/* The check-ins queued by different users for admin approval. */
	private final QueueADT<ChangeSet> checkIns;
	
	/* The stack of copies of the repo at points when any check-in was applied. */
	private final StackADT<RepoCopy> versionRecords; 

	/**
	 * Constructs a repo object.
	 * @param admin The administrator for the repo.
	 * @param reponame The name of the repo.
	 * @throws IllegalArgumentException if any argument is null. 
	 */
	public Repo(User admin, String repoName) {
		// CHECK:
		this.admin =  admin;
		this.repoName = repoName;
		this.version = 0;
		this.docs =  new ArrayList<Document>();
		this.checkIns = new SimpleQueue<ChangeSet>();	
		// push a copy (version 0) of the repo to the versionRecords
		this.versionRecords = new SimpleStack<RepoCopy>();
		this.versionRecords.push( new  RepoCopy(this.repoName, this.version, this.docs) );				
	}
	
	/**
	 * Return the name of the repo.
	 * @return The name of the repository.
	 */
	public String getName() {
		return this.repoName;
	}
	
	/**
	 * Returns the user who is administrator for this repository.
	 * @return The admin user.
	 */
	public User getAdmin() {
		return this.admin;
	}
	
	/**
	 * Returns a copy of list of all documents in the repository.
	 * @return A list of documents.
	 */
	public List<Document> getDocuments() {
		return new ArrayList<Document>(this.docs);
	}
	
	/**
	 * Returns a document with a particular name within the repository.
	 * @param searchName The name of document to be searched.
	 * @return The document if found, null otherwise.
	 * @throws IllegalArgumentException if any argument is null.
	 */
	public Document getDocument(String searchName) {
    	if (searchName == null) {
			throw new IllegalArgumentException();
		}
    	
		for (Document d : this.docs) {
			if (d.getName().equals(searchName)) {
				return d;
			}
		}
		
		return null;
	}
	
	/**
	 * Returns the current version of the repository.
	 * @return The version of the repository.
	 */
	public int getVersion() {
		return this.version;
	}
	
	/**
	 * Returns the number of versions (or changes made) for this repository.
	 * @return The version count.
	 */
	public int getVersionCount() {
		// DONE Implement this method. The following lines 
		// are just meant for the method to compile. You can 
		// remove or edit it in whatever way you like.
		return versionRecords.size();
	}
	
	/**
	 * Returns the history of changes made to the repository. 
	 * @return The string containing the history of changes.
	 */
	public String getVersionHistory() {
		// CHECK!!!
		//this.checkIns
		StackADT<RepoCopy> vrCopy = new SimpleStack<RepoCopy>();
		String str ="";
		while ( !this.versionRecords.isEmpty()) {
			try {
				RepoCopy rc = this.versionRecords.pop();
				vrCopy.push(rc);	
				// get type (ADD, DEL, EDIR) of rc
				
				
				str += rc.toString() + "\n";
			}
			catch (EmptyStackException exception) {
				System.out.println("Stack is empty");
			}
		}

		
		// Return back the original versionRecords
		while(!vrCopy.isEmpty()) {
			try {
				RepoCopy rc = vrCopy.pop();
				this.versionRecords.push(rc);
			}
			catch (EmptyStackException exception) {
				System.out.println("Stack is empty");
			}
		}
		return str;
			
		
	}
	
	/**
	 * Returns the number of pending check-ins queued for approval.
	 * @return The count of changes.
	 */
	public int getCheckInCount() {
		return checkIns.size();
	}
	
	
	/**
	 * Queue a new check-in for admin approval.
	 * @param checkIn The check-in to be queued.
	 * @throws IllegalArgumentException if any argument is null. 
	 */
	public void queueCheckIn(ChangeSet checkIn) {
		if (checkIn == null) {
			throw new IllegalArgumentException();
		}
		checkIns.enqueue(checkIn);
		
	}
	
	/**
	 * Returns and removes the next check-in in the queue 
	 * if the requesting user is the administrator.
	 * @param requestingUser The user requesting for the change set.
	 * @return The checkin if the requestingUser is the admin and a checkin
	 * exists, null otherwise.
	 * @throws IllegalArgumentException if any argument is null. 
	 */
	public ChangeSet getNextCheckIn(User requestingUser) {
		
		if (requestingUser == null) {
			throw new IllegalArgumentException();
		}
		// Case: requestingUser is admin
		if (requestingUser.equals(admin)) {
			try {
				return checkIns.dequeue();
			}
			catch (EmptyQueueException exception) {
				// +++Include this???
				System.out.println("Queue is empty");
			}			
		}
		return null;
	}
	
	/**
	 * Applies the changes contained in a particular checkIn and adds
	 * it to the repository if the requesting user is the administrator.
 	 * Also saves a copy of changed repository in the versionRecords.
	 * @param requestingUser The user requesting the approval.
	 * @param checkIn The checkIn to approve.
	 * @return ACCESS_DENIED if requestingUser is not the admin, SUCCESS 
	 * otherwise.
	 * @throws IllegalArgumentException if any argument is null. 
	 */
	public ErrorType approveCheckIn(User requestingUser, ChangeSet checkIn) {
		// CHECK
		ErrorType er = null;
		if (checkIn == null || requestingUser == null) {
			throw new IllegalArgumentException();
		}
		
		if (requestingUser.equals(admin)) {
			// apply changes in checkIn to the repository
			int numChanges = checkIn.getChangeCount();
			Change change = null;
			if (numChanges > 0) {
				for (int i = 0; i < numChanges; i++) {
					change = checkIn.getNextChange();
					// change Repo.docs
					Document doc_new = change.getDoc();

					// Case: ADD
					if (change.getType().equals(Change.Type.ADD)) {
						this.docs.add(doc_new);
					}
					else if (change.getType().equals(Change.Type.DEL)) {
						this.docs.remove(doc_new);
					}
					else if (change.getType().equals(Change.Type.EDIT)){						
						for (Document doc : this.docs) {
							// Case: doc has name in this.docs
							if (doc.getName().equals(doc_new.getName())) {
								doc = doc_new;
							}						
						}	
					}
				}
			}
			// increments the version
			this.version++;
			// push a copy of the changed repo in versionRecords.			
			RepoCopy rcCopy = new RepoCopy(this.repoName, this.version, this.docs);
			this.versionRecords.push(rcCopy);
			er = ErrorType.SUCCESS;
		}
		else {
			er = ErrorType.ACCESS_DENIED;
		}
		return er;
	}
	
	/**
	 * Reverts the repository to the previous version if present version is
	 * not the oldest version and the requesting user is the administrator.
	 * @param requestingUser The user requesting the revert.
	 * @return ACCESS_DENIED if requestingUser is not the admin, 
	 * NO_OLDER_VERSION if the present version is the oldest version, SUCCESS 
	 * otherwise.
	 * @throws IllegalArgumentException if any argument is null. 
	 */
	public ErrorType revert(User requestingUser) {
		ErrorType er;
		if (requestingUser == null) {
			throw new IllegalArgumentException();
		}
		
		//Check is requestingUser is admin
		if (requestingUser.equals(admin)) {
			// check NO_OLDER_VERSION
			if (versionRecords.size() == 1 ) {
				er = ErrorType.NO_OLDER_VERSION;
			}
			else {
				er = ErrorType.SUCCESS;
				try {
					// get the previous version (of type RepoCopy)
					// also adjust versionRecords
					RepoCopy rc = this.versionRecords.pop();
					// adjust the version
					this.version = rc.getVersion() - 1;
					// adjust this.docs
					
					List<Document> docs_prev = this.versionRecords.peek().getDocuments(); 							
					//this.docs = new ArrayList<Document>(docs_prev);
					this.docs = docs_prev;
				}
				catch (EmptyStackException exception) {
					System.out.println("Stack is empty");
				}
			}
		}
		else {
			er = ErrorType.ACCESS_DENIED;
		}
		return er;
	}
}