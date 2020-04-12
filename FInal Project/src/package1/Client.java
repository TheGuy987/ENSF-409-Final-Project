package package1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private Socket theSocket;
	private Controller theController;
	
	public Client(String serverName, int portNumber) {
		try {
			theSocket = new Socket(serverName, portNumber);
			socketIn = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
			socketOut = new PrintWriter((theSocket.getOutputStream()), true);
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}
	
	public void startController() {
		theController = new Controller(socketIn, socketOut);
		theController.getStudentInfo();
		theController.startGUI();
	}
	
	public static void main(String[] args) {
		Client theClient = new Client("localhost", 8099);
		theClient.startController();
	}
}
