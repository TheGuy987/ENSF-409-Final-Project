package package1;

import java.io.BufferedReader;
import java.io.PrintWriter;

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

}
