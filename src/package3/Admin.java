package package3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Admin {

	private int adminID;
	private int adminName;
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
	
	public void createCourse() {
		
	}

	public void createCourseInterface() throws IOException{
		String option = socketIn.readLine();
		
		if(!option.contentEquals("0")) {
			return;
		}
	}
	
	
}
