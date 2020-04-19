package package3;

import java.sql.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class that will read the SQL database in milestone III. Currently contains numerous har-coded
 * coureses.
 * @author Vaibhav Kapoor, Thomas Pan, and Matthew Wells
 *
 */
public class DBManager implements DBCredentials {
	
	ArrayList <Course> courseList;
	private Connection theConn;
	private Statement theStmt;
	private ResultSet rs;

	/**
	 * Constructor that assigns variable courseList to a new ArrayList of type Coures.
	 */
	public DBManager () {
		courseList = new ArrayList<Course>();
		try {
			theConn = DriverManager.getConnection(URL, USER, PASS);
			theStmt = theConn.createStatement();
			rs = theStmt.executeQuery("select * from employees");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Class that currently simulates reading form a database using values form a text file.
	 * Will be updated in the future to read from an actual database.
	 * @return
	 */
	public ArrayList<Course> readFromDataBase() {
		formatInput(connectFile());
		return courseList;
	}
	/**
	 * Connects a file to a BufferedReader.
	 * @return BufferedReader object used to link to a text file.
	 */
	public BufferedReader connectFile() {
		BufferedReader input = null;
		try {
			input = new BufferedReader(new FileReader("Courses.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return input;
	}
	
	/**
	 * Reads from a text file, and uses the contents to create Course objects.
	 * @param input BufferedReader object conencted to a text file.
	 */
	public void formatInput(BufferedReader input) {
		String name = "";
		int num = 0;
		int secNum = 0;
		int secCap = 0;
		String line = null;
		Course c = null;
		CourseOffering co = null;
		try {
			line = input.readLine();
			while(line != null) {
				name = line;
				line = input.readLine();
				num = Integer.parseInt(line);
				c = new Course(name, num);
				for(int i = 0; i < 2; i++) {
					line = input.readLine();
					secNum = Integer.parseInt(line);
					line = input.readLine();
					secCap = Integer.parseInt(line);
					co = new CourseOffering(secNum,secCap);
					c.addOffering(co);
				}
				courseList.add(c);
				line = input.readLine();
				}
				
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args) {
		DBManager 
	}

}