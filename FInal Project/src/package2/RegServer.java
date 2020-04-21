package src.package2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Class that represents the server of the Course registration system. It has variables for
 * data flow between the server and client sides of the application, serverSocket and Socket
 * objects for connecting to clients, and an ExecutorService object for multithreading.
 * @author Vaibhav Kapoor, Thomas Pan, and Matthew Wells
 *
 */
public class RegServer {

	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private ServerSocket serverSocket;
	private ExecutorService pool;
	private Socket socket;
	/**
	 * Constructor that takes a port number as input, and uses it to create a serverSocket object.
	 * It also assisns the variable pool to a new cached threadpool.
	 * @param port Integer holding the port number the server will run off of.
	 */
	public RegServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
			pool = Executors.newCachedThreadPool();
		}  catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Constantly searches for new clients, and assings a new thread to them to handle their requests.
	 * The new thread is created using variable pool, and is passed PrintWriter and BufferedReader
	 * variables for data flow.
	 */	
	public void clientSearch() {
		while(true) {
			try {
				socket = serverSocket.accept();
				socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				socketOut = new PrintWriter(socket.getOutputStream(), true);
				pool.execute(new RegThread(socketOut, socketIn));
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
		
		try {
			socket.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Main method of the server. It creates a new RegServer object, then uses it to call the method
	 * clientSearch.
	 * @param args String Array that can be read form the command line.
	 */	
	public static void main(String[] args) {
		RegServer theServer = new RegServer(8099);
		System.out.println("Server is now running..");
		theServer.clientSearch();
	}
	
}