package package3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;

public class Admin {

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
	
	public Admin(BufferedReader socketIn, PrintWriter socketOut) throws SocketException, LoginException{
		
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
	
	public void createCourse() {
		
	}

	public void createCourseInterface() throws IOException{
		String option = socketIn.readLine();
		
		if(!option.contentEquals("0")) {
			return;
		}
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
