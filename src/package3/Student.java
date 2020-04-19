package package3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
/**
 * Class that represents a student. The Student class contains name, id, CourseOffering List and
 * Registration List
 * @author Vaibhav Kapoor, Thomas Pan, and Matthew Wells
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
	 * Constructor that takes in BufferedReader and PrintWriter objects, and assigns then to
	 * their corrosponding instance variables.
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
		
		System.out.println("Student "+studentName+" "+studentId);
		System.out.println(toStringAllCoursesTaken());
	}
	/**
	 * Returns the name of the student.
	 * @return String holding the student's name.
	 */
	public String getStudentName() {
		return studentName;
	}
	/**
	 * Sets the student's name, using variable socketIn as input.
	 * @throws SocketException that is thrown if the server has lost connection to the client.
	 */
	public void setStudentName() throws IOException {
		try {
			studentName = socketIn.readLine(); 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Returns the students ID number.
	 * @return Integer holding the student's ID number.
	 */
	public int getStudentId() {
		return studentId;
	}
	/**
	 * Sets the student's ID, using variable socketIn as input.
	 * @throws SocketException that is thrown if the server has lost connection to the client.
	 */
	public void setStudentId() {
		try {
			studentId = Integer.parseInt(socketIn.readLine()); 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Returns the students information in the form of a String.
	 */
	@Override
	public String toString () {
		String st = "Student Name: " + getStudentName() + "\n" +
				"Student Id: " + getStudentId() + "\n\n";
		return st;
	}
	/**
	 * Adds registration to student list.
	 * @param registration object of type Registration to be added to student list.
	 */
	public void addRegistration(Registration registration) {
		// TODO Auto-generated method stub
		studentRegList.add(registration);
	}
	/**
	 * Removes a registration form student list.
	 * @param courseName String holding the name of course the regisitration is for.
	 * @param courseNum Integer holding the number of the course the registration is for.
	 * @return String indicating if the operation was performed successfully.
	 * @throws IOException Exception thrown if something goes wrong with the operation relating to
	 * and IO operation.
	 */
	public void removeRegistration(String courseName, int courseNum) throws IOException {	
		
		for(int i=0;i < studentRegList.size();i++) {
			if(courseName.contentEquals(studentRegList.get(i).getTheOffering().getTheCourse().getCourseName()) && courseNum==studentRegList.get(i).getTheOffering().getTheCourse().getCourseNum()) {
				studentRegList.remove(i);
				socketOut.println("The course has been successfully removed from your registration");
				return;
			}
		}
		socketOut.println("The course you have entered could not be found");
	}
	
	public void removeRegistrationInterface() throws IOException {
		
		//cancels method if the user presses "Cancel"
		String option = socketIn.readLine();
		if(!option.contentEquals("0")) {
			return;
		}
		
		String courseName = socketIn.readLine();
		int courseNum = Integer.parseInt(socketIn.readLine());
		
		removeRegistration(courseName, courseNum);
	}
	/**
	 * Registers the student to a course based on client input. It will then send a message to the client based
	 * on whether or not the student was successfully added and why
	 * @param list of courses to register for 
	 * @param courseName is the name of the course to add
	 * @param courseNum is the number of the course to add
	 * @return void
	 * @throws IOException Exception thrown if something goes wrong with the operation relating to an IO Operation
	 */
	public void addRegistirationInterface(CourseCatalogue list) throws IOException {
		//cancels method if the user presses "Cancel"
		String option = socketIn.readLine();
		System.out.println("TEST "+option);
		if(!option.contentEquals("0")) {
			return;
		}
		
		String courseName = socketIn.readLine();
		int courseNum = Integer.parseInt(socketIn.readLine());
		int check=0;

		if(maxCourseReg()) {
			socketOut.println("You have already registered for a maximum of 6 courses.");
			return;
		}
		
		Course reg = list.searchCat(courseName, courseNum);
		if(reg==null) {
			socketOut.println("The course you have entered could not be found");
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
			socketOut.println("You do not meet the prerequisites for this course"); 
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
			socketOut.println("Registeration error. All course offering for this class is full");
			return;
		}
	}
	/**
	 * Returns true if the student is registered in the maximum number of courses (6).
	 * @return boolean holding a true if the student is already registered in 6 courses.
	 */
	public boolean maxCourseReg(){
		if(studentRegList.size()>6) return true;
		return false; 
	}
	/**
	 * Adds courses to an ArrayList of courese that the student has already taken.
	 * @return ArrayList of Course objects representing all of the courses the student
	 * has already taken.
	 * @throws IOException
	 */	
	public ArrayList<Course> addCoursesTaken() throws IOException {
		boolean check=true;
		String courseName;
		int userInput;
		int courseNum=0;
		ArrayList<Course> temp = new ArrayList<Course>();
	
		
		while(check) {
			courseName=socketIn.readLine();
			courseNum =Integer.parseInt(socketIn.readLine());
			Course c = new Course(courseName,courseNum);
			temp.add(c);
						
			userInput=Integer.parseInt(socketIn.readLine());
			if(userInput==1) {
				check=false;
			}
		}
		return temp;
	}
	/**
	 * Removes a coures from the list of courses that the student has already taken.
	 * @param name String holding the name of the course to be removed.
	 * @param num Integer holding the
	 */	
	public void removeCoursesTaken(String name,int num) {
		for(int i=0;i<coursesTaken.size();i++) {
			if(coursesTaken.get(i).getCourseName().equals(name)&&coursesTaken.get(i).getCourseNum()==num) {
				coursesTaken.remove(i);
				socketOut.println(name+" has been removed");
				return;
			}
		}
	}
	/**
	 * Returns a String holding all the courses that the student has taken.
	 * @return String holding all the courses that the student has taken.
	 */
	public String toStringAllCoursesTaken() {
		String st = "All courses the student has taken: \n";
		for (int i=0;i<coursesTaken.size();i++) {
			st += coursesTaken.get(i).toString();  //This line invokes the toString() method of Course
			st += "\n";
		}
		return st;
	}
	/**
	 * Returns a String holding all the courses that the student is registered for.
	 * @return String holding all the courses that the student is registered for.
	 */	
	public String toStringAllRegistrations() {
		String st = "All courses the student has registered for: \n";
		for (int i=0;i<studentRegList.size();i++) {
			st += studentRegList.get(i).toString();  //This line invokes the toString() method of Course
			st += "\n";
		}
		return st;
	}
}