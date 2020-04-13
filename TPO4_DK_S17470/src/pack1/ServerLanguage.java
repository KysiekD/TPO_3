package pack1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerLanguage extends Thread {

	private String language;
	private String path;
	private int port;
	private HashMap<String, String> wordsHashMap;
	private static volatile boolean serverRunning = true;
	private ServerSocket ss;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private InetSocketAddress isa;

	public ServerLanguage(String host, String language, int port, HashMap<String, String> wordsList) {
		this.port = port;
		this.language = language;
		this.wordsHashMap = wordsList;
		try {
			this.ss = new ServerSocket();
			this.isa = new InetSocketAddress(host, port);
			ss.bind(isa);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Language server " + this.getLanguage() + " started. Listening at port: " + ss.getLocalPort()
		+ ", bind address: " + ss.getInetAddress());
		
		start();
	}
	
	public void run() {
		while(ServerLanguage.serverRunning) {
			try {
				Socket conn = ss.accept();
				System.out.println("Language server " + this.getLanguage() + " established connection...");
				serviceRequests(conn);
				System.out.println("REAL: " + readMsgFromMainServer(conn));
			} catch (IOException e) {
				e.printStackTrace();
			}

			
			
		}
	}
	
	public String readMsgFromMainServer(Socket connection) {
		
		
		try {
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			out = new PrintWriter(connection.getOutputStream(), true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Language server " + this.getLanguage() + " reads message from Main Server...");
		String messageFromServerTemp;
		try {
			if((messageFromServerTemp = in.readLine()) != null) {
				return "Language server received message from main server: " + messageFromServerTemp;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "No message from Server main.";
	}
	
	public void serviceRequests(Socket connection) {
		
	}
	
	
	public void disconnect() {
		try {
			ServerLanguage.serverRunning = false;
			//ss.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String readDictionary() {
		return "....Words in dictionary: " + wordsHashMap.toString();
	}

	public String getLanguage() {
		return this.language;
	}

	public int getPort() {
		return this.port;
	}

	public HashMap<String, String> getWords() {
		return wordsHashMap;
	}

	public void addWordsToDictionary(HashMap<String, String> map) {
		wordsHashMap.putAll(map);
	}

}
