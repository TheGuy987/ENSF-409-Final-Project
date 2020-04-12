import java.util.Scanner;

public class RegistrationApp {
	
	private Scanner scan = new Scanner(System.in);
	
	public void printMenu(){
		System.out.println("---------	MAIN MENU	----------");
		System.out.println("1 - Search catalogue courses");
		System.out.println("2 - Add course to student registration");
		System.out.println("3 - Remove course from student registration");
		System.out.println("4 - View all courses in catalogue");
		System.out.println("5 - View all courses taken by student");
		System.out.println("6 - View all courses student has registered for");
		System.out.println("7 - Quit");
	}
	
	public int getUserInput() {

		int input;
		boolean check=false;
		System.out.println("Please select an option between 1 and 6:");
		
		do {
			input = scan.nextInt();
			if(input<1 || input>6) {
				System.out.println("The number you have entered is invalid. Please try again");
				check=true;
			}
		}while(check);
		
		return input;
	}
	
	
	
	public static void main (String [] args) {

		RegistrationApp App = new RegistrationApp();
		CourseCatalogue cat = new CourseCatalogue ();
		System.out.println(cat);
		//Student st = new Student ("Sara", 1);
		//Student st2 = new Student ("Sam", 2);
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
			App.printMenu();
			int choice = App.getUserInput();
			
			switch(choice) {
			
			case(1):
				cat.searchCatalogue();
			break;

			case(2):
				st.addRegistirationInterface(cat);
			break;

			case(3):
				st.removeRegistration();
			break;

			case(4):
				System.out.println(cat.toString());
			break;
			
			case(5):
				System.out.println(st.toStringAllCoursesTaken());
			break;
			
			case(6):
				System.out.println(st.toStringAllRegistrations());
			break;
			
			case(7):
				System.out.println("The program is closing...");
				System.exit(1);
			}
		}


	}
}
