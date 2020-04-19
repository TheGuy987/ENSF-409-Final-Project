package package3;
/**
 * Class representing a students registration in a specific course. It has variables for
 * the student, the course offering, and the grade the student currently has in the course.
 * @author Vaibhav Kapoor, Thomas Pan, and Matthew Wells
 *
 */
public class Registration {
	private Student theStudent;
	private CourseOffering theOffering;
	private char grade;
	/**
	 * assigns the variables theStudent and theOffering, then calls the method addRegistration.
	 * @param st Student object assigned to theStudent.
	 * @param of CourseOffering object assigned to theOffering.
	 */	
	void completeRegistration (Student st, CourseOffering of) {
		theStudent = st;
		theOffering = of;
		addRegistration ();
	}
	/**
	 * Adds this object to the Student object and the CourseOffering object.
	 */
	private void addRegistration () {
		theStudent.addRegistration(this);
		theOffering.addRegistration(this);
	}
	
	/**
	 * Returns theStudent.
	 * @return object of type Student to be returned.
	 */
	public Student getTheStudent() {
		return theStudent;
	}
	/**
	 * Sets theStudent
	 * @param theStudent object of type Student to set theStudent to.
	 */
	public void setTheStudent(Student theStudent) {
		this.theStudent = theStudent;
	}
	/**
	 * Returns theOffering
	 * @return object of type CourseOffering to be returned.
	 */
	public CourseOffering getTheOffering() {
		return theOffering;
	}
	/**
	 * Sets theOffering
	 * @param theOffering object of type CourseOffering to set theOffering to.
	 */
	public void setTheOffering(CourseOffering theOffering) {
		this.theOffering = theOffering;
	}
	/**
	 * Returns grade.
	 * @return Char holding the value of grade.
	 */
	public char getGrade() {
		return grade;
	}
	/**
	 * Sets grade.
	 * @param grade Char to set grade to.
	 */
	public void setGrade(char grade) {
		this.grade = grade;
	}
	/**
	 * Returns this class in String form.
	 * @return String holding this class in String form.
	 */
	@Override
	public String toString () {
		String st = "\n";
		st += "Student Name: " + getTheStudent().getStudentName() + "\n";
		st += "The Offering: " + getTheOffering () + "\n";
		st += "Grade: " + getGrade();
		st += "\n-----------\n";
		return st;
		
	}
	

}
