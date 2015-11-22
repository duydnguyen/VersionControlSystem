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

public class Tester {
	public static void main(String[] args) {
//		SimpleStack<Integer> A = new SimpleStack<Integer>();		
//		A.push(1);
//		A.push(3);
//		A.push(5);
//		A.push(7);
//		System.out.println(A.toString() + ", size = " + A.size());
//		Object foo;
//		try {
//			foo = A.pop();		
//			System.out.println(A.toString() + ", size = " + A.size());
//			System.out.println(A.peek());
//			System.out.println(A.isEmpty());
//			
//		}
//		catch (EmptyStackException exception) {
//			System.out.println("Stack is empty");
//		}
//		
		
		SimpleQueue<Integer> A = new SimpleQueue<Integer>();			
		A.enqueue(1);
		A.enqueue(2);
		A.enqueue(3);
		
		
		// expand array
		//A.enqueue(4);
//		A.enqueue(15);
//		A.enqueue(16);
		
		System.out.println(A.toString() + "size = " + A.size());		
		try {
			//System.out.println("peek = " + A.peek());
			System.out.println("front item from dequeue() = " + A.dequeue());
			System.out.println("front item from dequeue() = " + A.dequeue());
			//System.out.println("current Queue: \n" + A.toString() + ", size = " + A.size());
			//A.enqueue(4);
			//A.enqueue(5);
			//A.enqueue(6);
			//A.enqueue(7);
			//A.enqueue(8);
			System.out.println("current Queue: \n" + A.toString() + ", size = " + A.size());
			
		}
		catch (EmptyQueueException exception) {
			System.out.println("Stack is empty");
		}

//		Integer[] B = {1,2,3,4,5,6};
//		Integer[] C = new Integer[6];
//		System.arraycopy(B, 0, C, 0, B.length - 0 +1);
		


	
	
	}
}
