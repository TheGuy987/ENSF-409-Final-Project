package package2;

import java.io.BufferedReader;
import java.io.PrintWriter;

import package3.*;

public class RegThread implements Runnable{

	private Student theStudent;
	private CourseCatalogue theCatalogue;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	
	public RegThread(BufferedReader in, PrintWriter out) {
		socketIn = in;
		socketOut = out;
		theStudent = ;
		theCatalogue = ;
	}
	
	@Override
	public void run() {
		
	}
	
	public String removeCourse(String Course) {
		
	}
	
	public String viewCatCourses() {
		
	}
	
	public String searchCatCourses() {
		
	}
	
	public String addCourse(String course) {
		
	}
	
	public String viewStudentCourses(String course) {
		
	}
	
}