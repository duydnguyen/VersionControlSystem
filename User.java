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
 * Represents a user. Maintains the list of subscribed repositories, working
 * copy of the subscribed repositories and their changelist. 
 * Bugs: none known
 * @author Duy Nguyen
 */
public class User {
	
	/* The name of the user. It's a unique identifier for a user. */
	private final String userName;
	
	/*  The list of names of the repositories to which the user is subscribed. */
	private final List<String> subRepos;
	
	/* The list of all pending check-ins not yet made by the user. */
	private final List<ChangeSet> pendingCheckIns;
	
	/* The list of all local working copies of the user. */
	private final List<RepoCopy> workingCopies;
	
	/**
	 * Constructor for User. 
	 * @param username The user name.
	 * @throws IllegalArgumentException if any argument is null. 
	 */
	public User(String userName) {
		// DONE
		if (userName == null ) {
			throw new IllegalArgumentException();
		}
		this.userName = userName;
		this.subRepos = new ArrayList<String>();
		this.pendingCheckIns = new ArrayList<ChangeSet>();
		this.workingCopies = new ArrayList<RepoCopy>();
	}
	
	/**
	 * Returns the name of the user.
	 * @return the user name.
	 */
	public String getName() {
		return this.userName;
	}
		
	/**
	 * Returns a copy of list of subscribed repositories.
	 * @return The subscribed repo list.
	 */
	public List<String> getAllSubRepos() {
		return new ArrayList<String>(this.subRepos);
	}
	
    /**
     * Returns the working copy for a repository.
     * @param repoName The name of the repository.
     * @return The working copy if exists, null otherwise.
     * @throws IllegalArgumentException if any argument is null. 
     */
    public RepoCopy getWorkingCopy(String repoName) {
		// CHECK
    	if (repoName == null ) {
			throw new IllegalArgumentException();
		}
    	int i = 0;
    	boolean check = false;    	
    	while (!workingCopies.isEmpty() && !check) {
    		RepoCopy fooRepo = workingCopies.get(i);
    		if (fooRepo.getReponame().equals(repoName)) {
    			check = true;    			
    			return fooRepo;
    		}
    		// check if i <= size of workingCopies
    		if (i == workingCopies.size() - 1) {
    			check = true;
    		}
    		i++;
    	}
    	
    	return null;
	}
    
	/**
	 * Subscribes the user to a repository. Adds a new repository to the 
	 * subscribed list. 
	 * @param repoName The name of the repository to subscribe.
	 * @throws IllegalArgumentException if any argument is null. 
	 */
	public void subscribeRepo(String repoName) {
		
		if (repoName == null) {
			throw new IllegalArgumentException();
		}
		
		if (!this.subRepos.contains(repoName)) {
			this.subRepos.add(repoName); 
		}
	}
	
	/**
	 * Un-subscribes the user from a repository. Deletes a repository from 
	 * the subscribed list.
	 * @param repoName The name of the repository to unsubscribe. 
	 * @throws IllegalArgumentException if any argument is null. 
	 */
	public void unsubscribeRepo(String repoName) {
		if (repoName == null) {
			throw new IllegalArgumentException();
		}
		this.subRepos.remove(repoName);
	}
	
	/**
	 * Checks if the user is subscribed to a particular repository.
	 * @param repoName The name of the repository to subscribe.
	 * @return True if the repository is subscribed, false otherwise.
	 * @throws IllegalArgumentException if any argument is null. 
	 */
	public boolean isSubRepo(String repoName) {
		if (repoName == null) {
			throw new IllegalArgumentException();
		}
		return subRepos.contains(repoName);
	}
	
	/**
	 * Adds a new change (add, edit or delete) to the pending checkIn for the 
	 * repository. If a checkIn does not exits, a new checkIn is
	 * created.
	 * @param doc The document added, deleted or edited.
	 * @param type The type of change.
	 * @param repoName The name of the repository on which the change is done.
	 * @throws IllegalArgumentException if any argument is null. 
	 */
	public void addToPendingCheckIn(Document doc, Change.Type type, String repoName) {
		if (repoName == null || type == null || doc == null) {
			throw new IllegalArgumentException();
		}
		// Case: repoName already exists in pendingCheckIns
		int i = 0;
    	boolean check = false;    	
    	while (!pendingCheckIns.isEmpty() && !check) {
    		ChangeSet fooCS = pendingCheckIns.get(i);
    		if (fooCS.getReponame().equals(repoName)) {
    			fooCS.addChange(doc, type);
    			check = true;    			    			
    		}
    		// check if i <= size of pendingCheckIns
    		if (i == pendingCheckIns.size() - 1) {
    			check = true;
    		}
    		i++;
    	}
    	// Case: there is no pending check-in for repoName
    	if (check == false) {
    		ChangeSet fooCS = new ChangeSet(repoName);
    		fooCS.addChange(doc, type);
    		pendingCheckIns.add(fooCS);
    	}				
}
	
	/**
	 * Returns the pending check-in for a repository.
	 * @param repoName The name of the repository.
	 * @return The pending check-in for the repository if exists, 
	 * null otherwise.
	 * @throws IllegalArgumentException if any argument is null. 
	 */
    public ChangeSet getPendingCheckIn(String repoName) {
    	if (repoName == null) {
			throw new IllegalArgumentException();
		}
    	int i = 0;
    	boolean check = false;    
    	ChangeSet fooCS = null;    	
    	while (!this.pendingCheckIns.isEmpty() && !check) {
    		//fooCS = pendingCheckIns.get(i);
    		if (this.pendingCheckIns.get(i).getReponame().equals(repoName)) {
    			check = true;
    			fooCS = pendingCheckIns.get(i);
    			return this.pendingCheckIns.get(i);
    		}
    		// check if i <= size of pendingCheckIns
    		if (i == pendingCheckIns.size() - 1) {
    			check = true;
    		}
    		i++;
    	}
    	
    	return fooCS;
    	
    	
	}
    
    /**
     * Checks in or queues a pending checkIn into a repository and removes it
     * from the local pending CheckIns list.   
     * @param repoName The name of repository.
     * @return NO_LOCAL_CHANGES, if there are no pending changes for the
     * repository, SUCCESS otherwise.
     * @throws IllegalArgumentException if any argument is null. 
     */
	public ErrorType checkIn(String repoName) {
		if (repoName == null) {
			throw new IllegalArgumentException();
		}
		// get the pending check-in for repoName
		ErrorType er;
		ChangeSet checkIn = this.getPendingCheckIn(repoName);
		if (checkIn != null) {
			er = ErrorType.SUCCESS;
			// queue pending check-in checkIn to repoName
			Repo repo = VersionControlDb.findRepo(repoName);
			repo.queueCheckIn(checkIn);
			// remove checkIn from local pending CheckIns list
			pendingCheckIns.remove(checkIn);
		}
		else {
			er = ErrorType.NO_LOCAL_CHANGES;
		}
		
    	return er;
	}
	
	/**
	 * Gets a latest version of the documents from the repository and puts
	 * them onto a working copy, if the user is currently subscribed to the
	 * repository. When the latest version is checked out, a new working copy 
	 * is created and existing one is deleted.
	 * @param repoName The name of the repository to check out.
	 * @return REPO_NOT_SUBSCRIBED if the repository is not subscribed, 
	 * SUCCESS otherwise. 
	 * @throws IllegalArgumentException if any argument is null. 
	 */
	public ErrorType checkOut (String repoName) {
		// check if user is subscribed to repoName
		if (subRepos.contains(repoName)) {
			//1. delete ANY old working copy for repoName in workingCopies	
			int i = 0;
			while (!workingCopies.isEmpty() ) {
				RepoCopy rc = workingCopies.get(i);
				if (rc.getReponame().equals(repoName)) {
					workingCopies.remove(i);
				}
				i++;
			}
			//2. delete any pending check-in for repoName in pendingCheckIns
			int ii = 0;
			while (!pendingCheckIns.isEmpty() ) {
				ChangeSet cs = pendingCheckIns.get(ii);
				if (cs.getReponame().equals(repoName)) {				
					pendingCheckIns.remove(ii);
				}
				ii++;
			}
			//3. Create a new working copy (or RepoCopy object)
			// for the current version of repoName
			Repo repo = VersionControlDb.findRepo(repoName);				
			List<Document> docs_new = repo.getDocuments();
			RepoCopy rpNew = new RepoCopy(repoName, repo.getVersion(), docs_new);
			
			// 4. add the new working copy (new RepoCopy) to the workingCopies
			this.workingCopies.add(rpNew);
			return ErrorType.SUCCESS;
		}
		else {
			return ErrorType.REPO_NOT_SUBSCRIBED;
		}		
}
		
	@Override
	public String toString() {
		String str = "=================================== \n";
		str += "Username: " + this.userName + "\n"
				+ "-----------Repos------------------ \n";
		int count = 0;
		for (String r : this.subRepos) {
			str += ++count + ". " + r + "\n";
		}
		str += this.subRepos.size() + " repos(s) subscribed.\n"
				+ "===================================";
		return str;
	}
}