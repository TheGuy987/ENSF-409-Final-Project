package package2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import package3.*;


public class RegThread extends Thread {
	
	PrintWriter socketOut;
	
	BufferedReader socketIn;
 	private Student theStudent; 
 	private CourseCatalogue theCatalogue; 
	
	public RegThread(PrintWriter socketOut, BufferedReader socketIn) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}

	public void run() {
 		
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
				System.out.println("There was a problem with your selection. Please try again");
				break;
			case(1):
				System.out.println("1");
				/*try {
					cat.searchCatalogue();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
			break;
			case(2):
				System.out.println("2");
				/*try {
					student.addRegistirationInterface(cat);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			break;

			case(3):
				System.out.println("3");
				/*try {
					student.removeRegistration();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
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
		}
	}
}

