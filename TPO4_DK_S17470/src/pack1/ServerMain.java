package pack1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerMain extends Thread {

	
	
	private ServerSocket ss;
	private Socket socket;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private String host;
	private int port;
	
	private String serverTID; // identyfikator watku
	private static volatile boolean serverRunning = true;
	private static String pathToDictionaries;
	private List<ServerLanguage> languageServersList;


	public ServerMain(String serverTID, ServerSocket ss, String pathToDictionaries) {
		this.serverTID = serverTID;
		this.ss = ss;
		
		
		
		System.out.println("Server " + serverTID + " started. Listening at port: " + ss.getLocalPort()
				+ ", bind address: " + ss.getInetAddress());

		ServerMain.pathToDictionaries = pathToDictionaries;
		this.languageServersList = new ArrayList<ServerLanguage>();
		this.createLanguageServersMap(ServerMain.pathToDictionaries);

		start();
	}

	public void run() {
		while (serverRunning) {
			try {
				Socket conn = ss.accept();
				System.out.println("Connection established by " + serverTID);
				connect("localhost", 49200); // !! z tym ze trzeba bedzie zmienic porty na argumenty
				serviceRequests(conn);
				writeMsg("Dawaj kota");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void connect(String host, int port) { // ..i dodac tez slowo i port klienta
		try {
			socket = new Socket(host, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			out = new PrintWriter(socket.getOutputStream(), true);
			System.out.println("Main server --" + serverTID + "-- connected to language server.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void serviceRequests(Socket connection) {

		System.out.println(readMsg(connection));
		writeMsg("Server to client: OK");

	}

	public void writeMsg(String msg) {
		System.out.println("Server writes...");
		out.println(msg);
	}

	private String readMsg(Socket conn) {
		try {
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			out = new PrintWriter(conn.getOutputStream(), true);
			System.out.println("Main server reads request from client..."); // test
			System.out.print("Receiving...");
			System.out.println("Server received FULL message.");
			return "Server received message: " + in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Server main didn't receive any message from this client.";

		}

	}

	public void disconnect() {
		try {

			in.close();
			out.close();
			serverRunning = false;
			ss.close();
			System.out.println("Main server no. " + this.serverTID + " disconected.");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void createLanguageServersMap(String path) {
		FileVisitor visitor = new FileVisitor();
		try {
			Files.walkFileTree(Paths.get(path), visitor);
			visitor.copyList(languageServersList);

			for (ServerLanguage languageServers : languageServersList) {
				System.out.println("Language dictionary " + languageServers.getLanguage() + " with port no "
						+ languageServers.getPort() + " detected."); // test
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void viewInfoAboutDictionariesAndDisconnect() { // test purpose
		String text = "\nInfo about dictionary server: ";

		for (ServerLanguage sl : languageServersList) {

			text = text.concat(sl.getLanguage() + "..." + sl.getPort());
			text = text.concat(sl.readDictionary());
			System.out.println(text); // test
			text = "Info about dictionary server: ";
			// sl.disconnect();
		}

	}

}
