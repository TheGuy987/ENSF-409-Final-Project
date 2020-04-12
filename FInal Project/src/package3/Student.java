package package3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
	 * socketIn is used to read client input from the socket 
	 */
	private BufferedReader socketIn;
	/**
	 * socketOut is used to write to the socket 
	 */
	private PrintWriter socketOut;
	/**
	 * constructs the class student 
	 */
	public Student (BufferedReader socketIn, PrintWriter socketOut) {
		studentRegList = new ArrayList<Registration>();
		
		this.socketIn = socketIn;
		this.socketOut = socketOut;
		
		try {
			setStudentName();
			setStudentId();
			coursesTaken = addCoursesTaken();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public void setStudentName() throws IOException {
		socketOut.println("Please enter your name");
		try {
			studentName = socketIn.readLine(); 
		}catch(Exception e) {
			socketOut.println("Error - Try again");
			setStudentName();
		}
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
	public void setStudentId() {
		socketOut.println("Please enter your id");
		try {
			studentId = Integer.parseInt(socketIn.readLine()); 
		}catch(Exception e) {
			socketOut.println("Error - Try again");
			setStudentId();
		}
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
	
	public String removeRegistration(String courseName, int courseNum) throws IOException {	
		
		for(int i=0;i < studentRegList.size();i++) {
			if(courseName.contentEquals(studentRegList.get(i).getTheOffering().getTheCourse().getCourseName()) && courseNum==studentRegList.get(i).getTheOffering().getTheCourse().getCourseNum()) {
			studentRegList.remove(i);
			return "1";
			}
		}
		return "0";
	}
	
	public void addRegistirationInterface(CourseCatalogue list) throws IOException {
		String courseName;
		int courseNum, check=0;
		
		if(maxCourseReg()) {
			socketOut.println("You have already registered for a maximum of 6 courses. Returning to main menu...");
			return;
		}
		
		socketOut.println("Hi "+studentName+", please enter the name of the course you would like to register for:");
		courseName=socketIn.readLine();
		socketOut.println("Please eneter the course number:");
		courseNum=Integer.parseInt(socketIn.readLine());
		
		Course reg = list.searchCat(courseName, courseNum);
		if(reg==null) {
			socketOut.println("The course you have entered could not be found. Returning to main menu");
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
			socketOut.println("You do not meet the prerequisites for this course. Returning to main menu"); 
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
				socketOut.println("You have been added to "+of.getTheCourse().getCourseName()+" "+of.getTheCourse().getCourseNum());
				break;
			}
		}
		if(check==0) {
			socketOut.println("Registeration error. All course offering for this class is full. Returning to main menu");
			return;
		}
	}
	
	public boolean maxCourseReg(){
		if(studentRegList.size()>6) return true;
		return false; 
	}
	
	public ArrayList<Course> addCoursesTaken() throws IOException {
		boolean check=true;
		String [] line;
		String courseName, userInput;
		int courseNum=0;
		ArrayList<Course> temp = new ArrayList<Course>();
	
		
		while(check) {
			socketOut.println("Hi "+studentName+", please enter the name and number of the course you have taken");
			courseName=socketIn.readLine();
			line=courseName.split(" ");
			courseName=line[0];
			if(line[1].matches("\\d+")) {
				courseNum=Integer.parseInt(line[1]);
				Course c = new Course(courseName,courseNum);
				temp.add(c);
			}
			else {
				socketOut.println("You have entered an invalid input");
			}
			

						
			socketOut.println("Do you want to add another course? Please enter 'yes' or 'no':");
			userInput=socketIn.readLine();
			if(userInput.equals("no")) {
				check=false;
			}
			else if(userInput.equals("yes")) {
				check = true;
			}
			else {
				socketOut.println("You have enter and invalid input. Returning to main menu...");
				break;
			}
		}
		return temp;
	}
	
	public void removeCoursesTaken(String name,int num) {
		for(int i=0;i<coursesTaken.size();i++) {
			if(coursesTaken.get(i).getCourseName().equals(name)&&coursesTaken.get(i).getCourseNum()==num) {
				coursesTaken.remove(i);
				socketOut.println(name+" has been removed");
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
