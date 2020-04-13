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

	private List<ServerLanguage> languageServersList;
	private static String pathToDictionaries;
	private static volatile boolean serverRunning = true;
	private ServerSocket ss;
	private String serverTID; // identyfikator watku
	private BufferedReader in = null;
	private PrintWriter out = null;
	private BufferedReader inCommunicationWithLanguageServer = null;
	private PrintWriter outCommunicationWithLanguageServer = null;
	private Socket socket;

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

	public void run() {
		while (serverRunning) {
			try {
				Socket conn = ss.accept();
				System.out.println("Connection established by " + serverTID);
				serviceRequests(conn);
				requestToLanguageServer("localhost",49200); //!! z tym ze trzeba bedzie zmienic porty na argumenty
				writeMessageToLanguageServer("Dawaj kota");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	public void requestToLanguageServer(String host, int port) { //..i dodac tez slowo i port klienta
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
	
	public void writeMessageToLanguageServer(String msg) {
		System.out.println("Server main writes message to language server.");
		out.println(msg);
	}

	public void disconectMainServer() {
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

	private void serviceRequests(Socket connection) {
		try {
			

			
			
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			out = new PrintWriter(connection.getOutputStream(), true);
			System.out.println("Main server reads request from client..."); // test

			System.out.println("Server received message: " + in.readLine());
				System.out.print("Receiving...");
			//}

			System.out.println("Server received FULL message.");
			writeResp("Server to client: OK");
			


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public void writeResp(String msg) {
		System.out.println("Server writes confirmation...");
		out.println(msg);
	}
	
	public void viewInfoAboutDictionariesAndDisconnect() { //test purpose
		String text = "\nInfo about dictionary server: ";
		
		for (ServerLanguage sl : languageServersList) {
		
			
			text = text.concat(sl.getLanguage() + "..." + sl.getPort());
			text = text.concat(sl.readDictionary());
			System.out.println(text);  //test
			text = "Info about dictionary server: ";
			//sl.disconnect();
		}
		
	}

}
