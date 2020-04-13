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
				// ss.close(); //n
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void disconectMainServer() {
		try {
			in.close();
			out.close();
			serverRunning = false;
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
			System.out.println("Request from client to server: " + in.readLine().toString());
			for (String line; (line = in.readLine()) != null;) {
				System.out.println("Server received: " + line);
			}
			in.close();
			out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// while()

		// .......................
		// .......................

	}
	
	public void viewInfoAboutDictionaries() { //test purpose
		String text = "\nInfo about dictionary server: ";
		
		for (ServerLanguage sl : languageServersList) {
		
			
			text = text.concat(sl.getLanguage() + "..." + sl.getPort());
			text = text.concat(sl.readDictionary());
			System.out.println(text);  //test
			text = "Info about dictionary server: ";
		}
		
	}

}
