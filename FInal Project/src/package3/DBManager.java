package package3;

import java.util.ArrayList;

/**
 * Class that will read the SQL database in milestone III. Currently contains numerous har-coded
 * coureses.
 * @author Vaibhav Kapoor, Thomas Pan, and Matthew Wells
 *
 */
public class DBManager {
	
	ArrayList <Course> courseList;

	/**
	 * Constructor that assigns variable courseList to a new ArrayList of type Coures.
	 */
	public DBManager () {
		courseList = new ArrayList<Course>();
	}

	/**
	 * Class that currently simulates reading form a database using hard coded values. Will be 
	 * updated in the future to read from an actual database.
	 * @return
	 */
	public ArrayList<Course> readFromDataBase() {
		courseList.add(new Course ("ENGG", 233));
		courseList.add(new Course ("ENSF", 409));
		courseList.add(new Course ("PHYS", 259));
		courseList.add(new Course ("MATH", 277));
		courseList.add(new Course ("CHEM", 209));
		return courseList;
	}

}