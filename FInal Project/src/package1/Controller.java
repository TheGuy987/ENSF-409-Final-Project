package package1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Controller {
	private GUI theGUI;	
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	
	public Controller(BufferedReader socketIn, PrintWriter socketOut) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}
	//Default controller to have eclipse stop yelling at me 
	public void startGUI() {
		getStudentInfo();
		theGUI = new GUI(this);
		theGUI.setVisible(true);
	}
	
	public void getStudentInfo() {
		String studentName = JOptionPane.showInputDialog("Please enter your name");
		socketOut.println(studentName);
		
		String studentId = JOptionPane.showInputDialog("Please enter your id");
		socketOut.println(studentId);
		
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
			socketOut.println(courseName.getText());
			socketOut.println(courseNum.getText());
			check =JOptionPane.showConfirmDialog(null, "Do you want to add another course?", "Courses Taken", JOptionPane.OK_OPTION);
			socketOut.println(check);
		}
	}
	
	public void searchCataPressed() {
		socketOut.println("1");
	}
	
	public void addRegPressed() {
		socketOut.println("2");
	}
	
	public void removeRegPressed() {
		socketOut.println("3");
	}
	
	public void displayCataPressed() {
		socketOut.println("4");
		theGUI.remove(theGUI.display);

		theGUI.display = new JScrollPane(updateTextArea(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		theGUI.display.setSize(1080,720);
		theGUI.add("Center",theGUI.display);
        theGUI.add(theGUI.display);
        theGUI.pack();
	}
	
	public void displayTakenPressed() {
		socketOut.println("5");
		theGUI.remove(theGUI.display);

		theGUI.display = new JScrollPane(updateTextArea(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		theGUI.display.setSize(1080,720);
		theGUI.add("Center",theGUI.display);
        theGUI.add(theGUI.display);
        theGUI.pack();
	}
	
	public void displayRegPressed() {
		socketOut.println("6");
		theGUI.remove(theGUI.display);

		theGUI.display = new JScrollPane(updateTextArea(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		theGUI.display.setSize(1080,720);
		theGUI.add("Center",theGUI.display);
        theGUI.add(theGUI.display);
        theGUI.pack();
	}
	
	public JTextArea updateTextArea() {
		String display="";
		try {
			while(socketIn.ready()) {
				display+=socketIn.readLine();
				display+="\n";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JTextArea jta = new JTextArea(display, 40, 87);
		return jta;
	}
	
}
