package package1;

import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Controller {
	GUI mainPane;
	BufferedReader socketIn;
	PrintWriter socketOut;
	
	public Controller(BufferedReader socketIn, PrintWriter socketOut) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}
	//Default controller to have eclipse stop yelling at me 
	public Controller() {
		
	}
	
	public void getStudentInfo() {
		String studentName = JOptionPane.showInputDialog("Please enter your name");
		socketOut.println(studentName);
		
		JTextField courseName = new JTextField();
		JTextField courseNum = new JTextField();
		
		Object[] fields = {
				"Course Name:", courseName,
				"Course Number: ", courseNum,
		};
		
		int check = 0;
		// Loop to ask students what courses they have taken 
		while(check==0) {
			JOptionPane.showConfirmDialog(null,fields,"Courses Taken", JOptionPane.CANCEL_OPTION);
			//send courseName and courseNum to server
			check =JOptionPane.showConfirmDialog(null, "Do you want to add another course?", "Courses Taken", JOptionPane.OK_OPTION);
		}
	}
	
	public void searchCataPressed() {
		socketOut.println("1");
	}
	
	public void removeRegPress() {
		socketOut.println("2");

	}
	
	public void displayCataPressed() {
		socketOut.println("3");

	}
	
	public void displayTakenPress() {
		socketOut.println("4");

	}
	
	public void displayRegPressed() {
		socketOut.println("5");

	}
}
