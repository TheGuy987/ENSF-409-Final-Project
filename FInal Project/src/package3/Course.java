package src.package3;

import java.util.ArrayList;
/**
 * Class that represents a course. It holds variables relating to the name and number of the
 * course, and two Array Lists for the pre-reqs of the course and the course offerings relating
 * the course.
 * @author Vaibhav Kapoor, Thomas Pan, and Matthew Wells
 *
 */
public class Course {

	private String courseName;
	private int courseNum;
	private ArrayList<Course> preReq;
	private ArrayList<CourseOffering> offeringList;
	/**
	 * Constructor that takes in a course name and number, and assigns them to the corrospoding
	 * variables. It also creates new Array Lists for variables preReq and offeringList.
	 * @param courseName String holding the name of the course.
	 * @param courseNum Integer holding the coures number.
	 */
	public Course(String courseName, int courseNum) {
		this.setCourseName(courseName);
		this.setCourseNum(courseNum);
		// Both of the following are only association
		preReq = new ArrayList<Course>();
		offeringList = new ArrayList<CourseOffering>();
	}
	/**
	 * Adds an offering to the course. Checks to make sure the offering is for this course.
	 * @param offering Course offering to be added to the course.
	 */
	
	public void addOffering(CourseOffering offering) {
		if (offering != null && offering.getTheCourse() == null) {
			offering.setTheCourse(this);
			if (!offering.getTheCourse().getCourseName().equals(courseName)
					|| offering.getTheCourse().getCourseNum() != courseNum) {
				System.err.println("Error! This section belongs to another course!");
				return;
			}
			
			offeringList.add(offering);
		}
	}
	/**
	 * Returns the course name.
	 * @return String holding the name of the course.
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * Sets the course name.
	 * @param courseName String holding the name to set courseName to.
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	/**
	 * Returns the course number.
	 * @return Integer holding the course number.
	 */
	public int getCourseNum() {
		return courseNum;
	}
	/**
	 * Sets the course number.
	 * @param courseNum Integer to set the course number to.
	 */
	public void setCourseNum(int courseNum) {
		this.courseNum = courseNum;
	}
	/**
	 * Returns the list of pre reqs for the course.
	 * @return Array List holding the list of pre reqs for the course.
	 */
	public ArrayList<Course> getPreReq() {
		return preReq;
	}
	/**
	 * Returns the list of pre reqs for the course.
	 * @return Array List holding the list of pre reqs for the course.
	 */
	public ArrayList<CourseOffering> getOfferingList(){
		return offeringList;
	}
	/**
	 * adds a Pre Req course to variable preReq.
	 * @param course object to be added to variable preReq.
	 */
	public void addPreReq(Course course) {
		preReq.add(course);
	}
	/**
	 * removes a Pre Req course to variable preReq.
	 * @param course object to be removed to variable preReq.
	 */
	public void removePreReq(Course course) {
		preReq.remove(course);
	}
	
	/**
	 * Returns the object as a string
	 * @return String that represetns the object as a String.
	 */
	@Override
	public String toString () {
		String st = "\n";
		st += getCourseName() + " " + getCourseNum ();
		st += "\nAll course sections:\n";
		for (CourseOffering c : offeringList)
			st += c;
		st += "\n_________________________________________\n";
		return st;
	}
	/**
	 * Returns the course offering at a certain index of the ArrayList variable offeringList.
	 * @param i Integer holding the index of variable offeringList.
	 * @return CourseOffering at the specified index of variable offeringList.
	 */
	public CourseOffering getCourseOfferingAt(int i) {
		// TODO Auto-generated method stub
		if (i < 0 || i >= offeringList.size() )
			return null;
		else
			return offeringList.get(i);
	}

}