package package3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Class that read from a SQL data base. It contains variables for connecting to said database, as well as an
 * Array List that holds a list of courses read from the database. Its various methods handle all writing and 
 * reading to the database in the program. It implements interface DBCredentials.
 * @author Vaibhav Kapoor, Thomas Pan, and Matthew Wells
 *
 */
public class DBManager implements DBCredentials{
	
	ArrayList <Course> courseList;
	Connection myConn;

	/**
	 * Constructor that takes no inout, creates an empty Array List of courses, and then assigns
	 * variable myConn to a Connection object using the information in DBCredentials.
	 */
	public DBManager () {
		courseList = new ArrayList<Course>();
		try {
				myConn = DriverManager.getConnection(URL,USER, PASS);
			} catch (SQLException e) {
				e.printStackTrace();
			                }
		
	}
	
	/**
	 * Used to create the course catalogue, it reads a list of courses from the database, along side
	 * the numerous course offerings and the pre-reqs for each course.
	 * @return
	 */
	public ArrayList<Course> readFromDataBase(){
		ArrayList<Course> list = new ArrayList<Course>();
		
		try {
			//Create  Statements

			Statement state1 = myConn.createStatement();
			Statement state2 = myConn.createStatement();
			Statement state3 = myConn.createStatement();

			//ResultSet from courses table
			ResultSet rs1 = state1.executeQuery("SELECT * FROM " + DBNAME + ".courses");
			while(rs1.next()) {
				Course c = new Course(rs1.getString(2),Integer.parseInt(rs1.getString(3)));
				int idcourse = rs1.getInt(1);
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
	
	/**
	 * Verifies the login information of a student user. It takes in their id and password, and attempts to match
	 * them with a user stored in the database.
	 * @param id Integer holding the student's student ID.
	 * @param password String holding the student's password.
	 * @return String indicating the name of the student.
	 */
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
	
	/**
	 * Verifies the login information of an admin user. It takes in their id and password, and attempts to match
	 * them with a user stored in the database.
	 * @param id Integer holding the admin's student ID.
	 * @param password String holding the admin's password.
	 * @return String indicating the name of the admin.
	 */
	public String checkAdminDetails(int id, String password) {
		
		try {
			
			String query1 = "SELECT * FROM " + DBNAME + ".adminusers WHERE adminid LIKE ?";
			
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
	
	/**
	 * Reads the courses that a student user has previously taken from the data base. It stores them is a list
	 * which it returns to the caller.
	 * @param studentId Integer holding the student's ID number, used to reference the database for the courses
	 * that they have already taken.
	 * @return Array List of type Course, holding all of the courese that the student has already taken.
	 */
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
	
	/**
	 * Reads the courses that a student user has already registered on from the database. It stores them in
	 * a list, which it returns the to caller.
	 * @param studentId Integer holding the student's ID number, which is used to reference the database for
	 * courses that they have already taken.
	 * @param theStudent Student object used to create registration objects from the database.
	 * @return Array List of type registration holding all of the classes the student has previously registered
	 * for.
	 */
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
						registered.add(regTemp);
					}
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return registered;
	}
	
	/**
	 * Registers a student for a course offering in the database.
	 * @param studentId Integer holding the id of the student to be registered.
	 * @param co CourseOffering object that is used to add a registration to the database.
	 */
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
	
	/**
	 * Un-registers a student from a registration in the data base, specified by its incoming arguements.
	 * @param studentId Integer holding the student's student ID.
	 * @param courseName String holding the name of the course which the registration belongs to.
	 * @param courseNum Integer holding the nunber of the course which the registration belongs to.
	 * @param sectionNum Integer holding the section number of the course offering in the registration.
	 */
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
	
	/**
	 * Inserts a new course into the database.
	 * @param courseName String holding the name of the course to be created.
	 * @param courseNum Integer holding the number of the course to be created.
	 * @param courseSec Integer holding the number of lecture sections the course will have.
	 * @throws SQLException Exception thrown if their is an error writing to the database.
	 */
	public void insertCourse(String courseName, int courseNum, int courseSec) throws SQLException {
		Statement state4 = myConn.createStatement();
		state4.execute("INSERT INTO "+DBNAME+".courses (courseName, courseNum, sections) VALUES ('"+courseName+"', '"+courseNum+"', '"+courseSec+"')");
	}
	
	/**
	 * Returns the database id of a course (course id).
	 * @param courseName String holding the name of the course.
	 * @param courseNum Integer holding the course number.
	 * @return Integer holding the database course id.
	 * @throws SQLException
	 */
	public int getCourseId(String courseName, int courseNum) throws SQLException {
		Statement state5 = myConn.createStatement();
		
		ResultSet rs1 = state5.executeQuery("SELECT * FROM "+DBNAME+".courses WHERE courseName = '"+courseName+"' AND courseNum ='"+courseNum+"'");
		if(rs1.next()) {
			return rs1.getInt(1);
		}
		return -1;
	}
	
	/**
	 * Inserts a lecture section into the database.
	 * @param courseId Integer holding the database course id of the section.
	 * @param count Integer holding the number of the lecture section.
	 * @param sectionSize Integer holding the number of students in the lecture section.
	 * @throws SQLException
	 */
	public void insertSection(int courseId, int count, int sectionSize) throws SQLException {
		Statement state6 = myConn.createStatement();
		state6.execute("INSERT INTO "+DBNAME+".sections (idcourse, sectionnum, sectionsize) VALUES ('"+courseId+"', '"+count+"', '"+sectionSize+"')");
	}
	
	/**
	 * Closes a connection object.
	 */
	public void close() {
		try {
			myConn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}