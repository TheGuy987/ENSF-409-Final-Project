package src.package3;

import java.util.ArrayList;
/**
 * Class that represents a course offering for a course in the course catalogue. It
 * contains variables for section number, section capacity, a Course object, an Array
 * List of registrations, and a boolean to check if the course offering has the minimum
 * required students to run.
 * @author Vaibhav Kapoor, Thomas Pan, and Matthew Wells
 *
 */
public class CourseOffering {
	
	private int secNum;
	private int secCap;
	private Course theCourse;
	//private ArrayList<Student> studentList;
	private ArrayList <Registration> offeringRegList;
	boolean minReached;
	/**
	 * Constructor that takes in the section number and capacity, and assigns then to the
	 * corrosponding variables.
	 * @param secNum Integer holding the section number.
	 * @param secCap Integer holding the section capacity.
	 */	
	public CourseOffering (int secNum, int secCap) {
		this.setSecNum(secNum);
		this.setSecCap(secCap);
		offeringRegList = new ArrayList <Registration>();
		minReached = false;
	}
	
	/**
	 * Returns section number.
	 * @return Integer holding the section number.
	 */
	public int getSecNum() {
		return secNum;
	}
	/**
	 * Sets the section number.
	 * @param secNum Integer holding the new section number.
	 */
	public void setSecNum(int secNum) {
		this.secNum = secNum;
	}
	/**
	 * Returns the scetion capacity.
	 * @return Integer holding the section capacity
	 */
	public int getSecCap() {
		return secCap;
	}
	/**
	 * Sets the section capacity.
	 * @param secCap Integer holding the new section capacity.
	 */
	public void setSecCap(int secCap) {
		this.secCap = secCap;
	}
	/**
	 * Returns the course relating to this course offering.
	 * @return Course object relating to this course offering.
	 */
	public Course getTheCourse() {
		return theCourse;
	}
	/**
	 * Sets the course relating to this course offering.
	 * @param theCourse object relating to this course offering.
	 */
	public void setTheCourse(Course theCourse) {
		this.theCourse = theCourse;
	}
	/**
	 * Checks to see of the minimum number of students has been reached to run
	 * a course, and updates variable minReached if it has.
	 */
	public void checkMinReached() {
		if(offeringRegList.size()>8) minReached=true;
		else minReached = false;
	}
	/**
	 * Returns the size of variable offeringRegSize.
	 * @return Integer holding the size of variable offeringRegSize.
	 */
	public int getOfferingRegListSize() {
		return offeringRegList.size();
	}
	/**
	 * Returns the courseOffering in String form.
	 * @return String holding the contents of the object in String form.
	 */
	@Override
	public String toString () {
		String st = "\n";
		st += getTheCourse().getCourseName() + " " + getTheCourse().getCourseNum() + "\n";
		st += "Section Num: " + getSecNum() + ", section cap: "+ getSecCap() +"\n";
		//We also want to print the names of all students in the section
		return st;
	}
	/**
	 * Adds a registration to variable offeringRegList.
	 * @param registration object to be added to variable offeringRegList.
	 */
	public void addRegistration(Registration registration) {
		// TODO Auto-generated method stub
		offeringRegList.add(registration);
		
	}
	
	

}