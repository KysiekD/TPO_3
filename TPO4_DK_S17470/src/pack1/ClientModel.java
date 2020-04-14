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

public class ClientModel extends Thread{

	private String word, language;
	// private String host;
	private int port;
	private BufferedReader in;
	private String host;
	private PrintWriter out;
	private Socket socket;
	private Socket socketListeningForMessageFromLanguageServer;
	private ServerSocket ss;
    private String messageFromServerTemp;
    private Boolean clientRunning = true;
    private InetSocketAddress isa;

	public ClientModel(int port, String host)  {
		try{
			this.word = null;
			this.language = null;
			this.port = port;
			this.host= host;
			
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
	
	public void run() {
		while(clientRunning) {
			try {
				Socket socketL = ss.accept();
				System.out.println(readMsg(socketL));
				readMsg(socketL);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	public void connect(String host, int serverPort) {
		try {
			socket = new Socket(host, serverPort);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			out = new PrintWriter(socket.getOutputStream(), true);

			System.out.println("Client connected to host " + socket.getInetAddress());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public String readMsg(Socket connection) {
		System.out.println("Client established connection with language server.");
		String text = "Client received nothing back";
		try {
			text = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}
	
	public void writeMsg(String msg) {
		
		out.println(msg);
		
	}

/*
	public String  readMsgFromServer() {

			
			System.out.println("Waiting for msg from server...");
			
			try {
				if((messageFromServerTemp = in.readLine()) != null) {
					return "Client received message from server: " + messageFromServerTemp;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return "No response from server";
	}
	*/



	public void disconnect() {
		try {
			
			in.close();
			out.close();
			socket.close();
			clientRunning = false;
			System.out.println("Client disconnected.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// SETTERS
	public void setWord(String text) {
		this.word = text;
	}

	public void setLanguage(String text) {
		this.language = text;
	}

	public void setPort(int number) {
		this.port = number;
	}

}
