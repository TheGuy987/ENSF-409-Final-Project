package package2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegServer {

	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private ServerSocket serverSocket;
	private ExecutorService pool;
	private Socket socket;
	
	public RegServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
			pool = Executors.newCachedThreadPool();
		}  catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
	
	public static void main(String[] args) {
		RegServer theServer = new RegServer(8099);
		System.out.println("Server is now running");
		theServer.clientSearch();
	}
	
}
