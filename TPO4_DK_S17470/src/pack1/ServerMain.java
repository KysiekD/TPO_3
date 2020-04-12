package pack1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerMain extends Thread {

	private List<ServerLanguage> languageServersList;
	private static String pathToDictionaries;
	private volatile boolean serverRunning = true;
	private ServerSocket ss;
	private String serverTID; // identyfikator watku

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
		while(serverRunning) {
			try {
				Socket conn = ss.accept();
				System.out.println("Connection established by " + serverTID);
				serviceRequests(conn);
			}catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		try {
			ss.close();
		} catch (Exception z) {
			
		}
	}
	
	private void serviceRequests(Socket connection){
		//.......................
		//.......................
		
	}

}
