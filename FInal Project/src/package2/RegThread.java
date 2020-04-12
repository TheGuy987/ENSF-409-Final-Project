package package2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import package3.*;


public class RegThread extends Thread {
	
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private Student theStudent;
	
	public RegThread(PrintWriter socketOut, BufferedReader socketIn) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}
	
	public void run() {
		
		while(true) {
			int choice = 0;
			try {
				choice = Integer.parseInt(socketIn.readLine());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				break;
			}
			
			switch(choice) {
			case(0):
				break;
			case(1):
				
				break;

			case(2):
				
				break;

			case(3):
				
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
		}
	}
}

