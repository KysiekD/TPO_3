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
	//private String host;
	private int port;
	private BufferedReader in;
	private PrintWriter out;
	private Socket socket;

	public ClientModel(int port) {
		this.word = null;
		this.language = null;
		//this.host=host;
		this.port = port;
		



	}

	public void connect(String host, int serverPort) {
		try {
			socket = new Socket(host,serverPort);
			 in = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()));
			
			out = new PrintWriter(
					new OutputStreamWriter(
							socket.getOutputStream()));
			
			

			System.out.println("Connected to host " + socket.getInetAddress());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void disconnect() {
		try {
			in.close();
			out.close();
			socket.close();
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
