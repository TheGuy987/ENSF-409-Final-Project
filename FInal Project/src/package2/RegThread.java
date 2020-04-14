package package2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;

import package3.*;

/**
 * Class that represents a thread that a RegServer will run. It is responsible to passing information
 * between package3 classes and the client side of the application. It contains variables relating to
 * dataflow, as well as Student and CourseCatalogue objects.
 * @author Vaibhav Kapoor, Thomas Pan, and Matthew Wells
 *
 */
public class RegThread extends Thread {
	
	private PrintWriter socketOut;
	private BufferedReader socketIn;
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
		
		try {
		theStudent = new Student(socketIn, socketOut);
 		theCatalogue = new CourseCatalogue(socketIn, socketOut);
		} catch(SocketException e) {
			return;
		}
		
		String result = "0";
		
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
		
		while(true) {
			int choice = 0;
			try {
				choice = Integer.parseInt(socketIn.readLine());
				switch(choice) {
					case(0):
						break;
					//search Catalogue
					case(1):
						result = theCatalogue.searchCatalogue(socketIn.readLine(), Integer.parseInt(socketIn.readLine()));
						socketOut.flush();
						socketOut.println(result);
						break;
						
					//Add Registration
					case(2):
						result = theStudent.addRegistrationController(theCatalogue, socketIn.readLine(), Integer.parseInt(socketIn.readLine()));
						socketOut.flush();
						socketOut.println(result);
						break;

					//Remove Registration
					case(3):
						result = theStudent.removeRegistration(socketIn.readLine(), Integer.parseInt(socketIn.readLine()));
						socketOut.flush();
						socketOut.println(result);
						break;

					//Display Catalogue
					case(4):
						System.out.println("4");
						socketOut.flush();
						System.out.println(theCatalogue.toString());
						socketOut.println(theCatalogue.toString());
					break;
					
					//Display Courses Taken
					case(5):
						System.out.println("5");
						socketOut.flush();
						System.out.println(theStudent.toStringAllCoursesTaken());
						socketOut.println(theStudent.toStringAllCoursesTaken());
					break;
					
					//Display Courses registered for
					case(6):
						socketOut.flush();
						System.out.println(theStudent.toStringAllRegistrations());
						socketOut.println(theStudent.toStringAllRegistrations());
					break;
					
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				break;
			}
			
		}
	}
}

