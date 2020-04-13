package package2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;

import package3.*;


public class RegThread extends Thread {
	
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private Student theStudent;
	private CourseCatalogue theCatalogue;
	
	public RegThread(PrintWriter socketOut, BufferedReader socketIn) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}
	
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
					case(1):
						result = theCatalogue.searchCatalogue(socketIn.readLine(), Integer.parseInt(socketIn.readLine()));
						socketOut.println(result);
						break;
						
					case(2):
						result = theStudent.addRegistrationController(theCatalogue, socketIn.readLine(), Integer.parseInt(socketIn.readLine()));
						socketOut.println(result);
						break;

					case(3):
						result = theStudent.removeRegistration(socketIn.readLine(), Integer.parseInt(socketIn.readLine()));
						socketOut.println(result);
						break;

					case(4):
						System.out.println("4");
						socketOut.flush();
						System.out.println(theCatalogue.toString());
						socketOut.println(theCatalogue.toString());
					break;
					
					case(5):
						System.out.println("5");
						socketOut.flush();
						System.out.println(theStudent.toStringAllCoursesTaken());
						socketOut.println(theStudent.toStringAllCoursesTaken());
					break;
					
					case(6):
						socketOut.flush();
						System.out.println(theStudent.toStringAllRegistrations());
						socketOut.println(theStudent.toStringAllRegistrations());
					break;
					
					case(7):
						return;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				break;
			}
			
		}
	}
}

