package package1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Class that represents the client to a RegServer. It contains variables relating to data
 * flow between the client and server sides, a socket for connection to the server, and an 
 * object of type Controller, used to run the logic of the client side and links this class
 * to a GUI.
 * @author Vaibhav Kapoor, Thomas Pan, and Matthew Wells
 *
 */
public class Client {

	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private Socket theSocket;
	private Controller theController;
	
	/**
	 * Constructor that takes a name and port number use to connect them to a server. It also
	 * assigns BufferedReader and PrintWriter objects to be used for server communication.
	 * @param serverName String that holds the server name.
	 * @param portNumber Integer holding the port number used to connect to the server.
	 */
	public Client(String serverName, int portNumber) {
		try {
			theSocket = new Socket(serverName, portNumber);
			socketIn = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
			socketOut = new PrintWriter((theSocket.getOutputStream()), true);
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}
	
	/**
	 * Assigns variable thseController to a new Controller object using the PrintWriter and 
	 * BufferedReader objects. Then uses variable theController to create a GUI.
	 */
	public void startController() {
		theController = new Controller(socketIn, socketOut);
		theController.startGUI();
	}
	
	/**
	 * Main method of the client. Creates a Client object and calls method startController.
	 * @param args Array of strings that can be read from the command line.
	 */
	public static void main(String args[]) {
		Client theClient = new Client("localhost", 8099);
		theClient.startController();
	}
}