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
	private int port;
	private String host;
	private HashMap<String, String> wordsHashMap;
	private static volatile boolean serverRunning = true;
	private Socket conn;
	private ServerSocket ss;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private InetSocketAddress isa;

	public ServerLanguage(String host, String language, int port, HashMap<String, String> wordsList) {
		this.port = port;
		this.host = host;
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
		while (ServerLanguage.serverRunning) {
			try {
				conn = ss.accept();

				serviceRequests(conn);
				conn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			

		}
	}

	public void connect(Socket socket) { // String host, int port
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			System.out.println("Language Server stream connected to smth.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void serviceRequests(Socket connection) {
		// Reads from main server:
		connect(connection); // "localhost", 13
		String text = readMsg(connection);
		System.out.println(text);
		String[] textTable = new String[3];
		textTable = text.split("-");
		// textTable[0] = word
		// textTable[1] = client port
		// textTable[2] = client host
		writeMsg("OK");
		disconnect(connection);
		
		// Writes back to client:
		try {
			Socket tempSocket;
			tempSocket = new Socket(textTable[2], Integer.parseInt(textTable[1])); // HARDCODED!!! powinien byc port klienta
			connect(tempSocket);
			writeMsg(translateWord(textTable[0]));
			disconnect(tempSocket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public String translateWord(String word) {
		//String translation = wordsHashMap.get(word);
		String translation;
		if(wordsHashMap.containsKey(word)){
			translation = wordsHashMap.get(word);
		}
		else translation = "Word '" + word + "' not found in dictionary.";
		System.out.println("FOUND TRANSLATION: " + translation);
		return translation;
	}
	

	public void writeMsg(String msg) {
		System.out.println("Language server writes: " + msg);
		out.println(msg);
	}

	public String readMsg(Socket connection) {

		try {
			String text = in.readLine();
			System.out.println("Language server reads: " + text);
			return text;

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "ERROR";
	}

	public void disconnect(Socket socket) {
		try {
			in.close();
			out.close();
			socket.close();

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
	
	public String getHost() {
		return this.host;
	}

	public HashMap<String, String> getWords() {
		return wordsHashMap;
	}

	public void addWordsToDictionary(HashMap<String, String> map) {
		wordsHashMap.putAll(map);
	}

}
