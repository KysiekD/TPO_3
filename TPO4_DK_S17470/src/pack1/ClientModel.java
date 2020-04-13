package pack1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientModel {

	private String word, language;
	// private String host;
	private int port;
	private BufferedReader in;
	private PrintWriter out;
	private Socket socket;
    private String messageFromServerTemp;
    private Boolean clientRunning = true;

	public ClientModel(int port) {
		this.word = null;
		this.language = null;
		this.port = port;

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

	public void makeRequest(String msg) {
		
		out.println(msg);
		
	}

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
