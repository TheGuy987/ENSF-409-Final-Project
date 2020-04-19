package package3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
public class DBManager implements DBCredentials{
	
	ArrayList <Course> courseList;
	Connection myConn;

	/**
	 * Constructor that assigns variable courseList to a new ArrayList of type Coures.
	 */
	public DBManager () {
		courseList = new ArrayList<Course>();
	}
	
	public ArrayList<Course> readFromDataBase(){
		ArrayList<Course> list = new ArrayList<Course>();
		
		try {
			//Create  Statements
			myConn = DriverManager.getConnection(URL,USER, PASS);

			Statement state1 = myConn.createStatement();
			Statement state2 = myConn.createStatement();
			Statement state3 = myConn.createStatement();

			//ResultSet from courses table
			ResultSet rs1 = state1.executeQuery("SELECT * FROM " + DBNAME + ".courses");
			System.out.println("SQL Test");
			while(rs1.next()) {
				Course c = new Course(rs1.getString(2),Integer.parseInt(rs1.getString(3)));
				int idcourse = rs1.getInt(1);
				System.out.println(c.toString());
				//reads course sections number and size and adds it to course offering
				ResultSet rs2 = state2.executeQuery("SELECT * FROM " + DBNAME + ".sections WHERE idcourse = '"+idcourse+"'");
				while(rs2.next()) {
					CourseOffering co = new CourseOffering(rs2.getInt(3), rs2.getInt(4));
					c.addOffering(co);
					
				}
				
				//reads course prereqs and adds it to the course 
				ArrayList<Course> prereq = new ArrayList<Course>();
				ResultSet rs3 = state3.executeQuery("SELECT * FROM " + DBNAME + ".prereqs WHERE courseid = '"+idcourse+"'");
				while(rs3.next()) {
					Course pre = new Course(rs3.getString(3),rs3.getInt(4));
					prereq.add(pre);
				}
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list ;
	}
	
	public String checkStudentDetails(int id, String password) {
		
		try {
			myConn = DriverManager.getConnection(URL,USER, PASS);
			
			String query1 = "SELECT * FROM " + DBNAME + ".students WHERE studentid LIKE ?";
			
			PreparedStatement state1 = myConn.prepareStatement(query1);
			state1.setInt(1, id);
			//state1.setString(2, password);
			ResultSet rs = state1.executeQuery();
			if(rs.next()) {
				if(rs.getInt(2) == id && rs.getString(3).equals(password)) {
					return rs.getString(4);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public ArrayList<Course> readCoursesTaken(int studentId){
		
		ArrayList<Course> taken = new ArrayList<Course>();	
		
		try {
			
			myConn = DriverManager.getConnection(URL,USER, PASS);
			String query1 = "SELECT * FROM " + DBNAME + ".studentcoursestaken WHERE studentid LIKE ?";
			PreparedStatement state1 = myConn.prepareStatement(query1);
			state1.setInt(1, id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return taken;
		
	}
	
	public void close() {
		try {
			myConn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
	/**
	 * Class that currently simulates reading form a database using values form a text file.
	 * Will be updated in the future to read from an actual database.
	 * @return
	 */