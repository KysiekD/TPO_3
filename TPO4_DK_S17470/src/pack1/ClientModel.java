package pack1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientModel extends Thread {

	 private String clientHost; 
	// private int listeningPort;
	private Socket listeningSocket;

	private ServerSocket ss;
	private Boolean clientRunning = true;
	private InetSocketAddress isa;

	private BufferedReader in;
	private PrintWriter out;
	
	private String serverHost;
	private int serverPort;

	public ClientModel(String host, int port, String serverHost, int serverPort) {
		try {
			// this.listeningPort = port;
			this.clientHost = host;
			this.serverHost = serverHost;
			this.serverPort = serverPort;
			// this.listeningSocket = new Socket(host,port);

			this.ss = new ServerSocket();
			this.isa = new InetSocketAddress(host, port);
			ss.bind(isa);

			System.out.println("Client starts listening at port " + port);
			start();

		} catch (IOException v) {
			// TODO Auto-generated catch block
			v.printStackTrace();
		}

	}

	public void askForTranslation(String language, String word, String serverHost, int serverPort) {
		try {
			Socket connection = new Socket(serverHost, serverPort);
			connect(connection);
			writeMsg(language + "-" + word + "-" + this.isa.getPort() + "-" + this.clientHost);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {
		while (clientRunning) {
			try {
				listeningSocket = ss.accept();

				serviceRequests(listeningSocket);
				listeningSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void connect(Socket socket) {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			out = new PrintWriter(socket.getOutputStream(), true);

			System.out.println("Client stream connected to smth.");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void serviceRequests(Socket connection) {
		connect(connection);
		String text = readMsg(connection);
		System.out.println(text);
		writeMsg("OK");
		disconnect(connection);
	}

	public String readMsg(Socket connection) {

		try {
			return "Client reads: " + in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ERROR";
	}

	public void writeMsg(String msg) {
		System.out.println("Client server writes: " + msg);
		out.println(msg);

	}

	public void disconnect(Socket connection) {
		try {

			in.close();
			out.close();
			connection.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * public void setListeningPort(int number) { this.listeningPort = number; }
	 */

}
