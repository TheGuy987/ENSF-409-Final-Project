package package3;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class representing the course catalogue of a course registration system. It has variables
 * holding data streams to a client, and an ArrayList containing all courses.
 * @author Vaibhav Kapoor, Thomas Pan, and Matthew Wells
 *
 */

public class CourseCatalogue {
	
	private ArrayList <Course> courseList;
	
	/*
	 * socketIn is used to read client input from the socket 
	 */
	private BufferedReader socketIn;
	/**
	 * socketOut is used to write to the socket 
	 */
	private PrintWriter socketOut;
	
	/**
	 * Constructor that takes BufferedReader and PrintWriter objects as input, and assigns them
	 * to the corrosponding variables.
	 * @param socketIn BufferedReader object that gets data from the client.
	 * @param socketOut PrintWriter object that sends data to the client.
	 */
	public CourseCatalogue (BufferedReader socketIn, PrintWriter socketOut) {
		loadFromDataBase ();
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}
	
	/**
	 * Initializes a DBManager object, and uses it to set the variable courseList.
	 */
	private void loadFromDataBase() {
		DBManager db = new DBManager();
		setCourseList(db.readFromDataBase());
		
	}
	/**
	 * Creates a new course offering
	 * @param c Course object representing the class the new course offering will be.
	 * @param secNum Integer holding the section number of the new course offering.
	 * @param secCap Integer holding the section capacity of the new course offering.
	 */
	public void createCourseOffering (Course c, int secNum, int secCap) {
		if (c!= null) {
			CourseOffering theOffering = new CourseOffering (secNum, secCap);
			c.addOffering(theOffering);
		}
	}
	/**
	 * Searches the catalogue for a matching course.
	 * @param courseName String holding the name of the searched course.
	 * @param courseNum Integer holding the number of the searched course.
	 * @return Course object that will be returned if a match is found, otherwise
	 * returns null.
	 */
	public Course searchCat (String courseName, int courseNum) {
		for (Course c : courseList) {
			if (courseName.equals(c.getCourseName()) &&
					courseNum == c.getCourseNum()) {
				return c;
			}	
		}
		return null;
	}
	//Typically, methods that are called from other methods of the class
	//are private and are not exposed for use by other classes.
	//These methods are refereed to as helper methods or utility methods
	
	/**
	 * Returns the variable courseList.
	 * @return ArrayList holding all of the courses.
	 */
	public ArrayList <Course> getCourseList() {
		return courseList;
	}
	
	/**
	 * Calls the method searchCat and processes the result.
	 * @param name String holding the name of the course being searched for.
	 * @param num Integer holding the number of the course being searched for.
	 * @return String holding the result of the search.
	 */
	public String searchCatalogue(String name, int num) {
		
		Course found = searchCat(name,num);
		if(found==null)
			return "0";
		else
			return found.toString();
		
	}
	/**
	 * Sets the variable courseList.
	 * @param courseList ArrayList to set courseList to.
	 */
	public void setCourseList(ArrayList <Course> courseList) {
		this.courseList = courseList;
	}
	@Override
	/**
	 * Returns the object in String form.
	 * @return String holding the object in String form.
	 */
	public String toString () {
		String st = "All courses in the catalogue: \n";
		for (Course c : courseList) {
			st += c.toString();  //This line invokes the toString() method of Course
			st += "\n";
		}
		return st;
	}

}
