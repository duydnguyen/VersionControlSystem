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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Version control application. Implements the command line utility
 * for Version control.
 * Bugs: none known
 * @author Duy Nguyen
 *
 */
public class VersionControlApp {

	/* Scanner object on input stream. */
	private static final Scanner scnr = new Scanner(System.in);

	/**
	 * An enumeration of all possible commands for Version control system.
	 */
	private enum Cmd {
		AU, DU,	LI, QU, AR, DR, OR, LR, LO, SU, CO, CI, RC, VH, RE, LD, AD,
		ED, DD, VD, HE, UN
	}

	/**
	 * Displays the main menu help. 
	 */
	private static void displayMainMenu() {
		System.out.println("\t Main Menu Help \n" 
				+ "====================================\n"
				+ "au <username> : Registers as a new user \n"
				+ "du <username> : De-registers a existing user \n"
				+ "li <username> : To login \n"
				+ "qu : To exit \n"
				+"====================================\n");
	}

	/**
	 * Displays the user menu help. 
	 */
	private static void displayUserMenu() {
		System.out.println("\t User Menu Help \n" 
				+ "====================================\n"
				+ "ar <reponame> : To add a new repo \n"
				+ "dr <reponame> : To delete a repo \n"
				+ "or <reponame> : To open repo \n"
				+ "lr : To list repo \n"
				+ "lo : To logout \n"
				+ "====================================\n");
	}

	/**
	 * Displays the repo menu help. 
	 */
	private static void displayRepoMenu() {
		System.out.println("\t Repo Menu Help \n" 
				+ "====================================\n"
				+ "su <username> : To subcribe users to repo \n"
				+ "ci: To check in changes \n"
				+ "co: To check out changes \n"
				+ "rc: To review change \n"
				+ "vh: To get revision history \n"
				+ "re: To revert to previous version \n"
				+ "ld : To list documents \n"
				+ "ed <docname>: To edit doc \n"
				+ "ad <docname>: To add doc \n"
				+ "dd <docname>: To delete doc \n"
				+ "vd <docname>: To view doc \n"
				+ "qu : To quit \n" 
				+ "====================================\n");
	}

	/**
	 * Displays the user prompt for command.  
	 * @param prompt The prompt to be displayed.
	 * @return The user entered command (Max: 2 words).
	 */
	private static String[] prompt(String prompt) {
		System.out.print(prompt);
		String line = scnr.nextLine();
		String []words = line.trim().split(" ", 2);
		return words;
	}

	/**
	 * Displays the prompt for file content.  
	 * @param prompt The prompt to be displayed.
	 * @return The user entered content.
	 */
	private static String promptFileContent(String prompt) {
		System.out.println(prompt);
		String line = null;
		String content = "";
		while (!(line = scnr.nextLine()).equals("q")) {
			content += line + "\n";
		}
		return content;
	}

	/**
	 * Validates if the input has exactly 2 elements. 
	 * @param input The user input.
	 * @return True, if the input is valid, false otherwise.
	 */
	private static boolean validateInput2(String[] input) {
		if (input.length != 2) {
			System.out.println(ErrorType.UNKNOWN_COMMAND);
			return false;
		}
		return true;
	}

	/**
	 * Validates if the input has exactly 1 element. 
	 * @param input The user input.
	 * @return True, if the input is valid, false otherwise.
	 */
	private static boolean validateInput1(String[] input) {
		if (input.length != 1) {
			System.out.println(ErrorType.UNKNOWN_COMMAND);
			return false;
		}
		return true;
	}
	
	/**
	 * Returns the Cmd equivalent for a string command. 
	 * @param strCmd The string command.
	 * @return The Cmd equivalent.
	 */
	private static Cmd stringToCmd(String strCmd) {
		try {
			return Cmd.valueOf(strCmd.toUpperCase().trim());
		}
		catch (IllegalArgumentException e){
			return Cmd.UN;
		}
	}

	/**
	 * Handles add user. Checks if a user with name "username" already exists; 
	 * if exists the user is not registered. 
	 * @param username The user name.
	 * @return USER_ALREADY_EXISTS if the user already exists, SUCCESS otherwise.
	 */
	private static ErrorType handleAddUser(String username) {
		if (VersionControlDb.addUser(username) != null) {
			return ErrorType.SUCCESS;
		}
		else {
			return ErrorType.USERNAME_ALREADY_EXISTS;
		}
	}

	/**
	 * Handles delete user. Checks if a user with name "username" exists; if 
	 * does not exist nothing is done. 
	 * @param username The user name.
	 * @return USER_NOT_FOUND if the user does not exists, SUCCESS otherwise.
	 */
	private static ErrorType handleDelUser(String username) {
		User user = VersionControlDb.findUser(username); 
		if (user == null) {
			return ErrorType.USER_NOT_FOUND;
		}
		else {
			VersionControlDb.delUser(user);
			return ErrorType.SUCCESS;
		}
	}

	/**
	 * Handles a user login. Checks if a user with name "username" exists; 
	 * if does not exist nothing is done; else the user is taken to the 
	 * user menu. 
	 * @param username The user name.
	 * @return USER_NOT_FOUND if the user does not exists, SUCCESS otherwise.
	 */
	private static ErrorType handleLogin(String username) {
		User currUser = VersionControlDb.findUser(username);
		if (currUser != null) {
			System.out.println(ErrorType.SUCCESS);
			processUserMenu(currUser);
			return ErrorType.SUCCESS;
		}
		else {
			return ErrorType.USER_NOT_FOUND;
		}
	}

	/**
	 * Processes the main menu commands.
	 * 
	 */
	public static void processMainMenu() {

		String mainPrompt = "[anon@root]: ";
		boolean execute = true;

		while (execute) {
			String[] words = prompt(mainPrompt);
			Cmd cmd = stringToCmd(words[0]);

			switch (cmd) {
			case AU:
				if (validateInput2(words)) {
					System.out.println(handleAddUser(words[1].trim()));
				}
				break;
			case DU:
				if (validateInput2(words)) {
					System.out.println(handleDelUser(words[1].trim())); 
				}
				break;
			case LI:
				if (validateInput2(words)) {
					System.out.println(handleLogin(words[1].trim()));
				}
				break;
			case HE:
				if (validateInput1(words)) {
					displayMainMenu();
				}
				break;
			case QU:
				if (validateInput1(words)) {
					execute = false;
				}
				break;
			default:
				System.out.println(ErrorType.UNKNOWN_COMMAND);
			}

		}
	}

	/**
	 * Processes the user menu commands for a logged in user.
	 * @param logInUser The logged in user.
	 * @throws IllegalArgumentException in case any argument is null.
	 */
	public static void processUserMenu(User logInUser) {

		if (logInUser == null) {
			throw new IllegalArgumentException();
		}

		String userPrompt = "[" + logInUser.getName() + "@root" + "]: ";
		boolean execute = true;
		// check_repo = false if user's subRepos == null
		boolean check_repo = false;

		while (execute) {

			String[] words = prompt(userPrompt);
			Cmd cmd = stringToCmd(words[0]);


			switch (cmd) {
			case AR:
				if (validateInput2(words)) {
					// DONE: Implement logic to handle AR.
					// Add a new repo					
					Repo repo_add = VersionControlDb.addRepo(words[1].trim(), logInUser);
					// Subscribe the logged-on user to the repo
					logInUser.subscribeRepo(words[1].trim()); //Error
					if (repo_add != null) {
						System.out.println(ErrorType.SUCCESS);
						check_repo = true;
					} 
					else {
						System.out.println(ErrorType.REPONAME_ALREADY_EXISTS);
					}
					
				}
				break;
			case DR:
				if (validateInput2(words)) {
					// DONE
					// Check if the repo exists
					Repo repo_del = VersionControlDb.findRepo(words[1].trim());
					
					if (repo_del == null) {
						System.out.println(ErrorType.REPO_NOT_FOUND);
					}
					else {
						if (repo_del.getAdmin().equals(logInUser)) {
							VersionControlDb.delRepo(repo_del);
							System.out.println(ErrorType.SUCCESS);
						}
						else {
							System.out.println(ErrorType.ACCESS_DENIED);
						}						
					}					
				}
				break;
			case LR:
				if (validateInput1(words)) {
					if (check_repo == false) {
						String str = "=================================== \n";
						str += "Username: " + logInUser.getName() + "\n"
								+ "-----------Repos------------------ \n";
						str += 0 + " repos(s) subscribed.\n"
								+ "===================================";
						System.out.println(str);
					}
					else {
						System.out.println(logInUser.toString());
					}
				}
				break;
			case OR: 
				if (validateInput2(words)) {
					// CHECK: USE checkOut() method in class User
					String repoName = words[1].trim();				
					// Check if the repo exists
					Repo repo_del = VersionControlDb.findRepo(repoName);
					
					if (repo_del == null) {
						System.out.println(ErrorType.REPO_NOT_FOUND);
					}
					else {
						// check if the logged-in user is subscribed to the repo
						ErrorType et = logInUser.checkOut(repoName);
						System.out.println(et);
						processRepoMenu(logInUser, repoName);
					}
				}
				break;
			case LO:
				if (validateInput1(words)) {
					execute = false;
				}
				break;
			case HE:
				if (validateInput1(words)) {
					displayUserMenu();
				}
				break;
			default:
				System.out.println(ErrorType.UNKNOWN_COMMAND);
			}

		}
	}

	/**
	 * Process the repo menu commands for a logged in user and current
	 * working repository.
	 * @param logInUser The logged in user. 
	 * @param currRepo The current working repo.
	 * @throws IllegalArgumentException in case any argument is null.
	 */
	public static void processRepoMenu(User logInUser, String currRepo) {

		if (logInUser  == null || currRepo == null) {
			throw new IllegalArgumentException();
		}

		String repoPrompt = "["+ logInUser.getName() + "@" + currRepo + "]: ";
		boolean execute = true;

		while (execute) {

			String[] words = prompt(repoPrompt);
			Cmd cmd = stringToCmd(words[0]);

			switch (cmd) {
			case SU:
				if (validateInput2(words)) {
					// Done
					//check if User <username> exits
					String username = words[1].trim();
					User currUser = VersionControlDb.findUser(username);
					if (currUser != null) {
						Repo repo = VersionControlDb.findRepo(currRepo);
						// check if the logged-in user is the admin of the current repo
						if (repo.getAdmin().equals(logInUser)) {
							System.out.println(ErrorType.SUCCESS);
							// subscribe the User <username> to currRepo
							currUser.subscribeRepo(currRepo);
						}
						else {
							System.out.println(ErrorType.ACCESS_DENIED);
						}
					}
					else {
						System.out.println(ErrorType.USER_NOT_FOUND);
					}
					
				}
				break;
			case LD:
				if (validateInput1(words)) {
					RepoCopy currRC = logInUser.getWorkingCopy(currRepo);
					System.out.println(currRC.toString());
				}
				break;
			case ED:
				if (validateInput2(words)) {					
					String docname = words[1].trim();
					Document docEdit = null;
					// return the local working copy for repo currRepo
					RepoCopy rp = logInUser.getWorkingCopy(currRepo);
					// Check DOCNAME_ALREADY_EXISTS
					boolean check_doc = false;
					for (Document doc : rp.getDocuments()) {
						if (doc.getName().equals(docname)) {
							check_doc = true;
							docEdit = doc;
						}
					}
					if (check_doc == false) {
						System.out.println(ErrorType.DOC_NOT_FOUND);
					}
					else {
						String content = "";
						String line = null;
						System.out.println("Enter the file content and press q to quit:");
						while (!(line = scnr.nextLine()).equals("q")) {
							content += line + "\n";
						}					
						// sets the doc's content with new content
						docEdit.setContent(content);
						// add the change to the user's pending checkin
						Change.Type type_edit = Change.Type.EDIT;
						logInUser.addToPendingCheckIn(docEdit, type_edit, currRepo);						
						System.out.println(ErrorType.SUCCESS);
					}
				}					
				break;
			case AD:
				if (validateInput2(words)) {
					String docname = words[1].trim();
					// return the local working copy for repo currRepo
					RepoCopy rp = logInUser.getWorkingCopy(currRepo);
					// Check DOCNAME_ALREADY_EXISTS
					boolean check_doc = false;
					for (Document doc : rp.getDocuments()) {
						if (doc.getName().equals(docname)) {
							check_doc = true;
						}
					}
					if (check_doc == true) {
						System.out.println(ErrorType.DOCNAME_ALREADY_EXISTS);
					}
					else {
						String content = "";
						String line = null;
						System.out.println("Enter the file content and press q to quit:");
						while (!(line = scnr.nextLine()).equals("q")) {
							content += line + "\n";
						}
						// Create new document
						Document doc_add = new Document(docname, content, currRepo);
						// Add doc_add to the working copy
						rp.addDoc(doc_add);
						Change.Type type_add = Change.Type.ADD;
						// Add the change to the user's pending checkin
						logInUser.addToPendingCheckIn(doc_add, type_add, currRepo);
						System.out.println(ErrorType.SUCCESS);
					}
					
				}
				break;
			case DD:
				if (validateInput2(words)) {
					String docname = words[1].trim();
					Document docDel = null;
					// return the local working copy for repo currRepo
					RepoCopy rp = logInUser.getWorkingCopy(currRepo);
					// Check DOCNAME_ALREADY_EXISTS
					boolean check_doc = false;
					for (Document doc : rp.getDocuments()) {
						if (doc.getName().equals(docname)) {
							check_doc = true;
							docDel = doc;
						}
					}
					if (check_doc == true) {
						// delete document named docname from the working copy
						rp.delDoc(docDel);
						// add the change to the user's pending checkin
						Change.Type type_del = Change.Type.DEL;
						logInUser.addToPendingCheckIn(docDel, type_del, currRepo);
						
						System.out.println(ErrorType.SUCCESS);
					}
					else {
						System.out.println(ErrorType.DOC_NOT_FOUND);
					}					
				}
				break;
			case VD:
				if (validateInput2(words)) {
					String docname = words[1].trim();
					// return the local working copy for repo currRepo
					RepoCopy rp = logInUser.getWorkingCopy(currRepo);
					// Check DOCNAME_ALREADY_EXISTS
					boolean check_doc = false;
					for (Document doc : rp.getDocuments()) {
						if (doc.getName().equals(docname)) {
							check_doc = true;
							System.out.println(doc.toString());
						}
					}
					if (check_doc == false) {
						System.out.println(ErrorType.DOC_NOT_FOUND);
					}
				}
				break;
			case CI:
				if (validateInput1(words)) {
					// make changes to User
					ErrorType er = logInUser.checkIn(currRepo);
					System.out.println(er);														
				}
				break;
			case CO:
				if (validateInput1(words)) {
					ErrorType er = logInUser.checkOut(currRepo);
					System.out.println(er);					
					
				}
				break;
			case RC:
				if (validateInput1(words)) {
					Repo repo = VersionControlDb.findRepo(currRepo);
					// Case: logged-in user = admin
					if (repo.getAdmin().equals(logInUser)) {
						int numChanges = repo.getCheckInCount();
						if (numChanges == 0) {
							System.out.println(ErrorType.NO_PENDING_CHECKINS);
						}
						else {
							// print and remove the queue checkins one by one
							ChangeSet CS = null;							
							for (int i = 0; i < numChanges; i++) {
								CS = repo.getNextCheckIn(logInUser);								
								String st = CS.toString();
								System.out.println(st);																
								
								// prompts to approve the check-in
								System.out.print("Approve changes? Press y to accept: ");
								String line = null;
								line = scnr.nextLine();
								// Case: approve checkin 
								if (line.equals("y")) {
									// applies any checkin to the repo
									repo.approveCheckIn(logInUser, CS);									
								}
							}
							System.out.println(ErrorType.SUCCESS);
						}
											
					}
					else {
						System.out.println(ErrorType.ACCESS_DENIED);
					}					

				}
				break;
			case VH:
				if (validateInput1(words)) {
					Repo repo = VersionControlDb.findRepo(currRepo);
					String verHist = repo.getVersionHistory();
					System.out.println(verHist);					
				}
				break;
			case RE:	
				if (validateInput1(words)) {
					Repo repo = VersionControlDb.findRepo(currRepo);
					ErrorType er = repo.revert(logInUser);
					System.out.println(er);
					
				}
				break;
			case HE:
				if (validateInput1(words)) {
					displayRepoMenu();
				}
				break;
			case QU:
				if (validateInput1(words)) {
					System.out.println(ErrorType.SUCCESS);
					execute = false;
				}
				break;
			default:
				System.out.println(ErrorType.UNKNOWN_COMMAND);
			}

		}
	}

	/**
	 * The main method. Simulation starts here.
	 * @param args Unused
	 */
	public static void main(String []args) {
		try {
			processMainMenu(); 
		}
		// Any exception thrown by the simulation is caught here.
		catch (Exception e) {
			System.out.println(ErrorType.INTERNAL_ERROR);
			// Uncomment this to print the stack trace for debugging purpose.
			//e.printStackTrace();
		}
		// Any clean up code goes here.
		finally {
			System.out.println("Quitting the simulation.");
		}
	}
}