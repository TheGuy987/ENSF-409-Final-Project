package package2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import package3.*;


public class RegThread extends Thread {
	
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private Student theStudent;
	private CourseCatalogue theCatalogue;
	
	public RegThread(PrintWriter socketOut, BufferedReader socketIn) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}
	
	public void run() {
		
		String result = "0";
		
		while(true) {
			int choice = 0;
			try {
				choice = Integer.parseInt(socketIn.readLine());
				switch(choice) {
					case(0):
						break;
					case(1):
						result = theCatalogue.searchCatalogue(socketIn.readLine(), Integer.parseInt(socketIn.readLine()));
						socketOut.println(result);
						break;
						
					case(2):
						result = theStudent.addRegistrationController(theCatalogue, socketIn.readLine(), Integer.parseInt(socketIn.readLine()));
						socketOut.println(result);
						break;

					case(3):
						result = theStudent.removeRegistration(socketIn.readLine(), Integer.parseInt(socketIn.readLine()));
						socketOut.println(result);
						break;

					case(4):
						result = theCatalogue.toString();
						socketOut.println(result);
						break;

					case(5):
						result = theStudent.toStringAllCoursesTaken();
						socketOut.println(result);
						break;
				
					case(6):
						result = theStudent.toStringAllRegistrations();
						socketOut.println(result);
						break;
				
					case(7):
						
						break;
				
					case(8):
						return;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				break;
			}
			
		}
	}
}

