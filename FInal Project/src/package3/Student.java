package package3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
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
	public Student (BufferedReader socketIn, PrintWriter socketOut)throws SocketException {
		studentRegList = new ArrayList<Registration>();
		
		this.socketIn = socketIn;
		this.socketOut = socketOut;
		
		try {
			setStudentName();
			setStudentId();
			coursesTaken = addCoursesTaken();

		}catch(SocketException e) {
			throw new SocketException();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Student "+studentName+" "+studentId);
		System.out.println(toStringAllCoursesTaken());
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
	public void setStudentName() throws SocketException {
		try {
			studentName = socketIn.readLine(); 
		}catch(SocketException e) {
			throw new SocketException();
		}catch(IOException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
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
	public void setStudentId() throws SocketException{
		try {
			studentId = Integer.parseInt(socketIn.readLine()); 
		}catch(SocketException e) {
			throw new SocketException();
		}catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
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
	
	public String addRegistrationController(CourseCatalogue list, String courseName, int courseNum) throws IOException {
		//Returns:
		//	"0" for already in 6 courses
		//	"1" for course not found
		//	"2" for wrong prereqs
		//	"3" for course is full
		//	"4" if the action was sucessful
		int check=0;
		
		if(maxCourseReg()) {
			return "0";
		}
		Course reg = list.searchCat(courseName, courseNum);
		if(reg==null) {
			return "1";
		}
		
		for(int i = 0;i < reg.getPreReq().size();i++) {
			for(int j=0;j<coursesTaken.size();j++) {
				if(coursesTaken.get(j).getCourseName()==reg.getPreReq().get(i).getCourseName() && coursesTaken.get(j).getCourseNum()==reg.getPreReq().get(i).getCourseNum()) {
					check++;
				}
			}
		}
		
		if(check!=reg.getPreReq().size()) {
			return "2";
		}
		
		CourseOffering of;
		check=0;
		for(int i=0; i<reg.getOfferingList().size();i++) {
			if(reg.getCourseOfferingAt(i).getOfferingRegListSize() < reg.getCourseOfferingAt(i).getSecCap()) {
				check++;
				of=reg.getCourseOfferingAt(i);
				Registration r = new Registration();
				r.completeRegistration(this, of);
				//socketOut.println("You have been added to "+of.getTheCourse().getCourseName()+" "+of.getTheCourse().getCourseNum());
				break;
			}
		}
		if(check==0) {
			return "3";
		}
		return "4";
	}
	
	public boolean maxCourseReg(){
		if(studentRegList.size()>6) return true;
		return false; 
	}
	
	public ArrayList<Course> addCoursesTaken() throws IOException {
		boolean check=true;
		String [] line;
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