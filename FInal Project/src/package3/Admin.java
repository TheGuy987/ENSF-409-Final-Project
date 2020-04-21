package src.package3;

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

public class Admin implements DBCredentials {

	private int adminID;
	private String adminName;
	private BufferedReader socketIn;
	/**
	 * socketOut is used to write to the socket 
	 */
	private PrintWriter socketOut;
	/**
	 * Constructor that takes in BufferedReader and PrintWriter objects, and assigns then to
	 * their corrosponding instance variables.
	 */
	private DBManager DB;
	
	private Connection myConn;
	
	public Admin(BufferedReader socketIn, PrintWriter socketOut) throws SocketException, LoginException, SQLException{
		
		this.socketIn = socketIn;
		this.socketOut = socketOut;
		DB = new DBManager();
		myConn = DriverManager.getConnection(URL, USER, PASS);

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
	
	public void createCourse() {
		
	}

	public void createCourseInterface(CourseCatalogue list) throws IOException, SQLException{
		String option = socketIn.readLine(); // #1
		ArrayList<CourseOffering> offeringList = new ArrayList<CourseOffering>();
		Statement state1 = myConn.createStatement();
		Statement state2 = myConn.createStatement();
		
		ResultSet rs1;
		
		String courseName=null;
		int courseNum=0;
		int courseSec;
		
		Course new_course = null;
		int courseId = 0;
		
		if(option.contentEquals("1")) {
			return;
			
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

	public int getAdminID() {
		return adminID;
	}

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
