package package2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import package3.*;

/**
* Class that represents a thread that a RegServer will run. It is responsible to passing information
* between package3 classes and the client side of the application. It contains variables relating to
* dataflow, as well as Student and CourseCatalogue objects.
* @author Vaibhav Kapoor, Thomas Pan, and Matthew Wells
*
*/
public class RegThread extends Thread {
	
	PrintWriter socketOut;
	
	BufferedReader socketIn;
 	private Student theStudent; 
 	private CourseCatalogue theCatalogue; 
	/**
	 * Constructor that takes PrintWriter and BufferedReader objects as arguements and assigns the
	 * respective variables to them.
	 * @param socketOut PrintWriter object for sending information to the client.
	 * @param socketIn BufferedReader object for receiving information from the client.
	 */
	public RegThread(PrintWriter socketOut, BufferedReader socketIn) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}
	/**
	 * Assigns variables theStudent and theCatalogue to new Student and CourseCatalogue objects
	 * respectively, using variables socketIn and socketOut. It then waits for input from the client
	 * in the form of an Integer, which is then used in a switch statement to select an operation to
	 * run. This process loops unitl the client disconnnects, at which point the run method will catch
	 * and exception, causing it to break the loop and exit.
	 */
	public void run() {
 		theStudent = new Student(socketIn, socketOut);
 		theCatalogue = new CourseCatalogue(socketIn, socketOut);
 		
		Course myCourse = theCatalogue.searchCat("ENGG", 233);
		Course myCourse2 = theCatalogue.searchCat("ENSF", 409);
		Course myCourse3 = theCatalogue.searchCat("PHYS", 259);
		
		if (myCourse2 != null) {
			theCatalogue.createCourseOffering(myCourse2, 1, 1);
			theCatalogue.createCourseOffering(myCourse2, 2, 50);
		}
		if (myCourse != null) {
			theCatalogue.createCourseOffering(myCourse, 1, 100);
			theCatalogue.createCourseOffering(myCourse, 2, 200);
		}
		if (myCourse3 != null) {
			theCatalogue.createCourseOffering(myCourse3, 1, 100);
			theCatalogue.createCourseOffering(myCourse3, 2, 200);
		}
		
		//Begin
		Boolean check=true;
		
		while(check) {
			int choice = 0;
			try {
				choice = Integer.parseInt(socketIn.readLine());
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			switch(choice) {
			case(0):
				break;
			case(1):
				try {
					theCatalogue.searchCatalogue();
					socketOut.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			break;
			case(2):
				try {
					theStudent.addRegistirationInterface(theCatalogue);
					socketOut.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;

			case(3):
				try {
					theStudent.removeRegistrationInterface();
					socketOut.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;

			case(4):
				socketOut.println(theCatalogue.toString());
				socketOut.flush();
			break;
			
			case(5):
				socketOut.println(theStudent.toStringAllCoursesTaken());
				socketOut.flush();
			break;
			
			case(6):
				socketOut.println(theStudent.toStringAllRegistrations());
				socketOut.flush();
			break;
			
			case(7):
				check = false;
			}
		}
	}
}

