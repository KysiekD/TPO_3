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
	private Socket conn;
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
		while (ServerLanguage.serverRunning) {
			try {
				conn = ss.accept();

				serviceRequests(conn);
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void connect(Socket socket) { // String host, int port
		try {
			// socket = new Socket(host, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			System.out.println("Language Server stream connected to smth.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void serviceRequests(Socket connection) {
		//Reads:
		connect(connection); // "localhost", 13
		String text = readMsg(connection);
		System.out.println(text);
		writeMsg("OK");
		
		//Writes:
		try {
			Socket tempSocket;
			tempSocket = new Socket("localhost", 48001); //HARDCODED!!!
			connect(tempSocket);
			writeMsg(text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


	}

	public void writeMsg(String msg) {
		System.out.println("Language server writes: " + msg);
		out.println(msg);
	}

	public String readMsg(Socket connection) {

		try {
			//in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			//out = new PrintWriter(connection.getOutputStream(), true);
			String text = in.readLine();
			System.out.println( "Language server reads: " + text);
			return text;
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "ERROR";
	}

	public void disconnect() {
		try {
			ServerLanguage.serverRunning = false;
			// ss.close();

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
