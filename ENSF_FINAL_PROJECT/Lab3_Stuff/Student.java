import java.util.ArrayList;
import java.util.Scanner;
/**
 * The Student class that contains name, id, CourseOffering List and Registration List
 *
 */
public class Student {
	/*
	 * Name
	 */
	private String studentName;
	/*
	 * id
	 */
	private int studentId;
	/*
	 * list of Registrations 
	 */
	private ArrayList<Registration> studentRegList;
	/*
	 * list of the courses the student has taken 
	 */
	private ArrayList<Course> coursesTaken;
	/*
	 * Scanner class used to obtain user input from console 
	 */
	Scanner scan = new Scanner(System.in);
	/*
	 * constructs student with Name and Id input 
	 */
	public Student (String studentName, int studentId) {
		this.setStudentName(studentName);
		this.setStudentId(studentId);
		studentRegList = new ArrayList<Registration>();
		coursesTaken = addCoursesTaken();
	}
	/*
	 * return studentName 
	 */
	public String getStudentName() {
		return studentName;
	}
	/*
	 * sets studentName
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	/*
	 * returns StudentId 
	 */
	public int getStudentId() {
		return studentId;
	}
	/*
	 * sets studentId 
	 */
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	@Override
	public String toString () {
		String st = "Student Name: " + getStudentName() + "\n" +
				"Student Id: " + getStudentId() + "\n\n";
		return st;
	}
	/*
	 * adds a Registration to studentRegList 
	 */
	public void addRegistration(Registration registration) {
		// TODO Auto-generated method stub
		studentRegList.add(registration);
	}
	
	public void removeRegistration() {
		String courseName;
		int courseNum;
		
		System.out.println("Hi "+studentName+", please enter the name of the course you would like to un-register for:");
		courseName=scan.next();
		System.out.println("Please eneter the course number:");
		courseNum=scan.nextInt();
		
		for(int i=0;i < studentRegList.size();i++) {
			if(courseName.contentEquals(studentRegList.get(i).getTheOffering().getTheCourse().getCourseName()) && courseNum==studentRegList.get(i).getTheOffering().getTheCourse().getCourseNum()) {
			studentRegList.remove(i);
			return;
			}
		}
		System.out.println("The course you are unregistering from could not be found. Returning to main menu");
	}
	
	public void addRegistirationInterface(CourseCatalogue list) {
		String courseName;
		int courseNum, check=0;
		
		if(maxCourseReg()) {
			System.out.println("You have already registered for a maximum of 6 courses. Returning to main menu...");
			return;
		}
		
		System.out.println("Hi "+studentName+", please enter the name of the course you would like to register for:");
		courseName=scan.next();
		System.out.println("Please eneter the course number:");
		courseNum=scan.nextInt();
		
		Course reg = list.searchCat(courseName, courseNum);
		if(reg==null) {
			System.out.println("The course you have entered could not be found. Returning to main menu");
			return;
		}
		
		for(int i = 0;i < reg.getPreReq().size();i++) {
			for(int j=0;j<coursesTaken.size();j++) {
				if(coursesTaken.get(j).getCourseName()==reg.getPreReq().get(i).getCourseName() && coursesTaken.get(j).getCourseNum()==reg.getPreReq().get(i).getCourseNum()) {
					check++;
				}
			}
		}
		
		if(check!=reg.getPreReq().size()) {
			System.out.println("You do not meet the prerequisites for this course. Returning to main menu"); 
			return;
		}
		
		CourseOffering of;
		check=0;
		for(int i=0; i<reg.getOfferingList().size();i++) {
			if(reg.getCourseOfferingAt(i).getOfferingRegListSize() < reg.getCourseOfferingAt(i).getSecCap()) {
				check++;
				of=reg.getCourseOfferingAt(i);
				Registration r = new Registration();
				r.completeRegistration(this, of);
				System.out.println("You have been added to "+of.getTheCourse().getCourseName()+" "+of.getTheCourse().getCourseNum());
				break;
			}
		}
		if(check==0) {
			System.out.println("Registeration error. All course offering for this class is full. Returning to main menu");
			return;
		}
	}
	
	public boolean maxCourseReg(){
		if(studentRegList.size()>6) return true;
		return false; 
	}
	
	public ArrayList<Course> addCoursesTaken() {
		boolean check=true;
		String [] line;
		String courseName, userInput;
		int courseNum=0;
		ArrayList<Course> temp = new ArrayList<Course>();
	
		
		while(check) {
			System.out.println("Hi "+studentName+", please enter the name and number of the course you have taken");
			courseName=scan.nextLine();
			line=courseName.split(" ");
			courseName=line[0];
			if(line[1].matches("\\d+")) {
				courseNum=Integer.parseInt(line[1]);
				Course c = new Course(courseName,courseNum);
				temp.add(c);
			}
			else {
				System.out.println("You have entered an invalid input");
			}
			

						
			System.out.println("Do you want to add another course? Please enter 'yes' or 'no':");
			userInput=scan.nextLine();
			if(userInput.equals("no")) {
				check=false;
			}
			else if(userInput.equals("yes")) {
				check = true;
			}
			else {
				System.out.println("You have enter and invalid input. Returning to main menu...");
				break;
			}
		}
		return temp;
	}
	
	public void removeCoursesTaken(String name,int num) {
		for(int i=0;i<coursesTaken.size();i++) {
			if(coursesTaken.get(i).getCourseName().equals(name)&&coursesTaken.get(i).getCourseNum()==num) {
				coursesTaken.remove(i);
				System.out.println(name+" has been removed");
				return;
			}
		}
	}
	
	public String toStringAllCoursesTaken() {
		String st = "All courses the student has taken: \n";
		for (int i=0;i<coursesTaken.size();i++) {
			st += coursesTaken.get(i).toString();  //This line invokes the toString() method of Course
			st += "\n";
		}
		return st;
	}
	
	public String toStringAllRegistrations() {
		String st = "All courses the student has registered for: \n";
		for (int i=0;i<studentRegList.size();i++) {
			st += studentRegList.get(i).toString();  //This line invokes the toString() method of Course
			st += "\n";
		}
		return st;
	}
}
