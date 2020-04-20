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
		try {
				myConn = DriverManager.getConnection(URL,USER, PASS);
			} catch (SQLException e) {
				e.printStackTrace();
			                }
		
	}
	
	public ArrayList<Course> readFromDataBase(){
		ArrayList<Course> list = new ArrayList<Course>();
		
		try {
			//Create  Statements

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
			
			String query1 = "SELECT * FROM " + DBNAME + ".students WHERE studentid LIKE ?";
			
			PreparedStatement state1 = myConn.prepareStatement(query1);
			state1.setInt(1, id);
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
			
			
			int courseId;
			String query1 = "SELECT * FROM " + DBNAME + ".studentcoursestaken WHERE studentid LIKE ?";
			String courseQuery = "SELECT * FROM " + DBNAME + ".courses WHERE idcourse LIKE ?";
			PreparedStatement state1 = myConn.prepareStatement(query1);
			PreparedStatement state2;
			state1.setInt(1, studentId);
			ResultSet rs = state1.executeQuery();
			ResultSet courseRS;
			while(rs.next()) {
				courseId = rs.getInt(3);
				state2 = myConn.prepareStatement(courseQuery);
				state2.setInt(1, courseId);
				courseRS = state2.executeQuery();
				if(courseRS.next()) {
					taken.add(new Course(courseRS.getString(2), courseRS.getInt(3)));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return taken;
		
	}
	
	public ArrayList<Registration> readCoursesRegistered(int studentId, Student theStudent) {
		ArrayList<Registration> registered = new ArrayList<Registration>();
		Course courseTemp;
		Registration regTemp;
		CourseOffering coTemp;
		
		try {
			
			
			int courseId;
			int sectionId;
			String query1 = "SELECT * FROM " + DBNAME + ".studentcoursesreg WHERE studentid LIKE ?";
			String courseQuery = "SELECT * FROM " + DBNAME + ".courses WHERE idcourse LIKE ?";
			String sectionIDQuery = "SELECT * FROM " + DBNAME + ".sections WHERE id LIKE ?";
			PreparedStatement state1 = myConn.prepareStatement(query1);
			PreparedStatement state2;
			PreparedStatement state3;
			state1.setInt(1, studentId);
			ResultSet rs = state1.executeQuery();
			ResultSet courseRS;
			ResultSet sectionRS;
			while(rs.next()) {
				sectionId = rs.getInt(3);
				state3 = myConn.prepareStatement(sectionIDQuery);
				state3.setInt(1, sectionId);
				sectionRS = state3.executeQuery();
				
				if(sectionRS.next()) {
					courseId = sectionRS.getInt(2);
					state2 = myConn.prepareStatement(courseQuery);
					state2.setInt(1, courseId);
					courseRS = state2.executeQuery();
			
					if(courseRS.next()) {
						courseTemp = new Course(courseRS.getString(2), courseRS.getInt(3));
						regTemp = new Registration();
						regTemp.setTheStudent(theStudent);
						coTemp = new CourseOffering(sectionRS.getInt(3), sectionRS.getInt(4));
						coTemp.setTheCourse(courseTemp);
						regTemp.setTheOffering(coTemp);
						System.out.println("HERERERERERER");
						registered.add(regTemp);
					}
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return registered;
	}
	
	public void registerStudent(int studentId, CourseOffering co) {
		try {
			Statement state1 = myConn.createStatement();
			Statement state2 = myConn.createStatement();
			Statement state3 = myConn.createStatement();

			//find courseid
			ResultSet rs1 = state1.executeQuery("SELECT * FROM "+DBNAME+".courses WHERE courseName = '"+co.getTheCourse().getCourseName()+"' AND courseNum ='"+co.getTheCourse().getCourseNum()+"'");
			if(rs1.next()) {
				int courseid = rs1.getInt(1);
				ResultSet rs3 = state3.executeQuery("SELECT * FROM "+DBNAME+".sections WHERE idcourse = '"+courseid+"' AND sectionnum = '"+co.getSecNum()+"'");
				if(rs3.next()) {
					int sectionID = rs3.getInt(1);
					state2.execute("INSERT INTO "+DBNAME+".studentcoursesreg (studentid, sectionid, courseid) VALUES ( "+studentId+","+sectionID+","+courseid+")");
					int currentSize = rs3.getInt(5);
					int primaryKey = rs3.getInt(1);
					currentSize++;
					state3.execute("UPDATE `"+DBNAME+"`.`sections` SET `currentsize` = '"+currentSize+"' WHERE (`id` = '"+primaryKey+"')");
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch blockUPDAT
			e.printStackTrace();
		}
	}
	
	public void unregisterStudent(int studentId, String courseName, int courseNum, int sectionNum) {
		try {
			Statement state1 = myConn.createStatement();
			Statement state2 = myConn.createStatement();
			Statement state3 = myConn.createStatement();
			
			//find courseid
			ResultSet rs1 = state1.executeQuery("SELECT * FROM "+DBNAME+".courses WHERE courseName = '"+courseName+"' AND courseNum ='"+courseNum+"'");
			if(rs1.next()) {
				int courseid = rs1.getInt(1);
				state2.execute("DELETE FROM "+DBNAME+".studentcoursesreg WHERE studentid = '"+studentId+"' AND courseid = '"+courseid+"'");
				
				ResultSet rs3 = state3.executeQuery("SELECT * FROM "+DBNAME+".sections WHERE idcourse = '"+courseid+"' AND sectionnum = '"+sectionNum+"'");
				if(rs3.next()) {
					int currentSize = rs3.getInt(5);
					int primaryKey = rs3.getInt(1);
					currentSize--;
					state3.execute("UPDATE `"+DBNAME+"`.`sections` SET `currentsize` = '"+currentSize+"' WHERE (`id` = '"+primaryKey+"')");
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			myConn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}