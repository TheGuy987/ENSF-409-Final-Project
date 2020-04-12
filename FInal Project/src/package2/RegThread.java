package package2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import package3.*;


public class RegThread extends Thread {
	
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private Student theStudent;
	private CourseCatalogue theCalalogue;
	
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
						result = theCalalogue.searchCatalogue(socketIn.readLine(), Integer.parseInt(socketIn.readLine()));
						socketOut.println(result);
						break;

					case(2):
						result = theStudent.removeRegistration(socketIn.readLine(), Integer.parseInt(socketIn.readLine()));
						socketOut.println(result);
						break;

					case(3):
						result = theCalalogue.toString();
						socketOut.println(result);
						break;

					case(4):
					
						break;
				
					case(5):
					
						break;
				
					case(6):
						
						break;
				
					case(7):
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

