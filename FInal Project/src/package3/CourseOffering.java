package package3;

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
	 * 
	 * @return
	 */
	public int getSecNum() {
		return secNum;
	}
	public void setSecNum(int secNum) {
		this.secNum = secNum;
	}
	public int getSecCap() {
		return secCap;
	}
	public void setSecCap(int secCap) {
		this.secCap = secCap;
	}
	public Course getTheCourse() {
		return theCourse;
	}
	public void setTheCourse(Course theCourse) {
		this.theCourse = theCourse;
	}
	public void checkMinReached() {
		if(offeringRegList.size()>8) minReached=true;
		else minReached = false;
	}
	public int getOfferingRegListSize() {
		return offeringRegList.size();
	}
	@Override
	public String toString () {
		String st = "\n";
		st += getTheCourse().getCourseName() + " " + getTheCourse().getCourseNum() + "\n";
		st += "Section Num: " + getSecNum() + ", section cap: "+ getSecCap() +"\n";
		//We also want to print the names of all students in the section
		return st;
	}
	public void addRegistration(Registration registration) {
		offeringRegList.add(registration);
		
	}
	
	

}