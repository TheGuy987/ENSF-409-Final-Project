package package1;

import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Controller {
	
	private GUI theGUI;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	
	public Controller(BufferedReader in, PrintWriter out) {
		socketIn = in;
		socketOut = out;
	}
	
	public void startGUI() {
		theGUI = new GUI(this);
		theGUI.setVisible(true);
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
			socketOut.println(courseName);
			socketOut.println(courseNum);
			check =JOptionPane.showConfirmDialog(null, "Do you want to add another course?", "Courses Taken", JOptionPane.OK_OPTION);
			socketOut.println(check);
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
