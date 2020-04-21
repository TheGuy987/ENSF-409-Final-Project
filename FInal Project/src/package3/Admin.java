package package3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Class represents an Admin User. The admin class holds variables associated with the admin user such as name, id.
 * @author Vaibhav Kapoor, Thomas Pan, and Matthew Wells
 */
public class Admin implements DBCredentials {

	/**
	 * ID
	 */
	private int adminID;
	/**
	 * Name
	 */
	private String adminName;
	/**
	 * socketIn is used to read client input from the socket 
	 */
	private BufferedReader socketIn;
	/**
	 * socketOut is used to write to the socket 
	 */
	private PrintWriter socketOut;
	/**
	 * DB is used to reference the database
	 */
	private DBManager DB;
	
	/**
	 * Constructor that takes in BufferedReader and PrintWriter objects, and assigns then to
	 * their corresponding instance variables.
	 */
	public Admin(BufferedReader socketIn, PrintWriter socketOut) throws SocketException, LoginException, SQLException{
		
		this.socketIn = socketIn;
		this.socketOut = socketOut;
		DB = new DBManager();

		try {
			while(true) {
				System.out.println("test");
				
				setAdminId();
				if(adminID < 0) {
					throw new LoginException();
				}
				String pass = getAdminPassword();
			
				adminName = DB.checkAdminDetails(adminID, pass);
				if(adminName != null) {
					socketOut.println("1");
					break;
				}
				else {
					socketOut.println("0");
				}
				
			}
		}catch(SocketException e) {
			throw new SocketException();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method prompts the User to enter the details to create a new course in the database.
	 * @param list List of courses already in the database to check for duplication.
	 * @throws IOException
	 * @throws SQLException
	 */
	public void createCourseInterface(CourseCatalogue list) throws IOException, SQLException{
		String option = socketIn.readLine(); // #1
		ArrayList<CourseOffering> offeringList = new ArrayList<CourseOffering>();		
		
		String courseName=null;
		int courseNum=0;
		int courseSec;
		
		Course new_course = null;
		int courseId = 0;
		
		// User presses cancel, return
		if(option.contentEquals("1")) {
			return;
			
		// User presses ok, read in the course info and update the database.
		}else if(option.contentEquals("0")) {
			courseName = socketIn.readLine();
			courseNum = Integer.parseInt(socketIn.readLine());
			courseSec = Integer.parseInt(socketIn.readLine());
			new_course = new Course(courseName, courseNum);

			
			DB.insertCourse(courseName, courseNum, courseSec);
			courseId = DB.getCourseId(courseName, courseNum);
			
			int count = 1;
			while(socketIn.readLine().contentEquals("1")) {
				int sectionSize = Integer.parseInt(socketIn.readLine());
				new_course.getOfferingList().add(new CourseOffering(count, sectionSize));
				DB.insertSection(courseId, count, sectionSize);
				count++;
			}
			
		}
		
		socketOut.flush();
		socketOut.println(courseName+" "+courseNum+" has been created");

		list.getCourseList().add(new_course);
	}
	
	/**
	 * This method grabs the password entered by the client side.
	 * @return password entered by client.
	 * @throws SocketException
	 */
	public String getAdminPassword()throws SocketException {
		try {
			String pass = socketIn.readLine(); 
			return pass;
		}catch(SocketException e) {
			throw new SocketException();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Getter for adminID
	 * @return adminID;
	 */
	public int getAdminID() {
		return adminID;
	}
	
	/**
	 * Sets the adminID received from client
	 * @throws SocketException
	 */
	public void setAdminId()throws SocketException {
		try {
			adminID = Integer.parseInt(socketIn.readLine()); 
		}catch(SocketException e) {
			throw new SocketException();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	
	
}