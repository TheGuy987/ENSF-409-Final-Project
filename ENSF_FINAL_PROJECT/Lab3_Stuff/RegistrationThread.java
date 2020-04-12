import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class RegistrationThread extends Thread {
	
	PrintWriter socketOut;
	
	BufferedReader socketIn;
	
	public RegistrationThread(PrintWriter socketOut, BufferedReader socketIn) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}
	
	public void printMenu(){
		socketOut.println("---------	MAIN MENU	----------");
		socketOut.println("1 - Search catalogue courses");
		socketOut.println("2 - Add course to student registration");
		socketOut.println("3 - Remove course from student registration");
		socketOut.println("4 - View all courses in catalogue");
		socketOut.println("5 - View all courses taken by student");
		socketOut.println("6 - View all courses student has registered for");
		socketOut.println("7 - Quit");
	}
	
	public int getUserInput() throws NumberFormatException, IOException {

		int input;
		boolean check=false;
		socketOut.println("Please select an option between 1 and 6:");
		
		do {
			input = Integer.parseInt(socketIn.readLine());
			if(input<1 || input>6) {
				socketOut.println("The number you have entered is invalid. Please try again");
				check=true;
			}
		}while(check);
		
		return input;
	}
	
	
	
	public void run() {
		
		CourseCatalogue cat = new CourseCatalogue (socketIn, socketOut);
		socketOut.println(cat);
		Student student = new Student(socketIn, socketOut);
		Course myCourse = cat.searchCat("ENGG", 233);
		Course myCourse2 = cat.searchCat("ENSF", 409);
		Course myCourse3 = cat.searchCat("PHYS", 259);
		
		if (myCourse2 != null) {
			cat.createCourseOffering(myCourse2, 1, 1);
			cat.createCourseOffering(myCourse2, 2, 50);
		}
		if (myCourse != null) {
			cat.createCourseOffering(myCourse, 1, 100);
			cat.createCourseOffering(myCourse, 2, 200);
		}
		if (myCourse3 != null) {
			cat.createCourseOffering(myCourse3, 1, 100);
			cat.createCourseOffering(myCourse3, 2, 200);
		}
		
		//Begin
		Boolean check=true;
		
		while(check) {
			this.printMenu();
			int choice = 0;
			try {
				choice = this.getUserInput();
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			switch(choice) {
			case(0):
				socketOut.println("There was a problem with your selection. Please try again");
				break;
			case(1):
				try {
					cat.searchCatalogue();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;

			case(2):
				try {
					student.addRegistirationInterface(cat);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;

			case(3):
				try {
					student.removeRegistration();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;

			case(4):
				socketOut.println(cat.toString());
			break;
			
			case(5):
				socketOut.println(student.toStringAllCoursesTaken());
			break;
			
			case(6):
				socketOut.println(student.toStringAllRegistrations());
			break;
			
			case(7):
				socketOut.println("The program is closing...");
				System.exit(1);
			}
		}
	}
}
