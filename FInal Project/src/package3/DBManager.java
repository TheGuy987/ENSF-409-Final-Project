package package3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Class that will read the SQL database in milestone III. Currently contains numerous har-coded
 * coureses.
 * @author Vaibhav Kapoor, Thomas Pan, and Matthew Wells
 *
 */
public class DBManager {
	
	ArrayList <Course> courseList;
	
	Statement state;
	Connection myConn;

	/**
	 * Constructor that assigns variable courseList to a new ArrayList of type Coures.
	 */
	public DBManager () {
		courseList = new ArrayList<Course>();
		
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306","root", "password");
			//Create a Statement
			state = myConn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Class that currently simulates reading form a database using values form a text file.
	 * Will be updated in the future to read from an actual database.
	 * @return
	 */
	public ArrayList<Course> readFromDataBase() {
		ResultSet rs;
		ResultSet temp;

		try {
			rs = state.executeQuery("SELECT * FROM registration.courses");

			while(rs.next()) {
				Course c = new Course(rs.getString(1),Integer.parseInt(rs.getString(2)));
				int num_of_sections =rs.getInt(3);
				int idcourse = rs.getInt(0);
				
				//reads course sections number and size and adds it to course offering 
				temp = state.executeQuery("SELECT * FROM sections WHERE idcourse = '"+idcourse+"'");
				for(int i=0; i<num_of_sections; i++) {
					CourseOffering co = new CourseOffering(rs.getInt(2), rs.getInt(3));
					c.addOffering(co);
				}
				
				ArrayList<Course> preReq = new ArrayList<Course>();
				temp = state.executeQuery("SELECT * FROM prereqs WHERE idcourse = '"+idcourse+"'");
				while(rs.next()) {
					preReq.add(new Course(rs.getString(2), rs.getInt(3)));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return courseList;
	}
}