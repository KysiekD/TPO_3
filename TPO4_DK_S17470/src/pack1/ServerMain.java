package pack1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
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
	private Socket conn;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private InetSocketAddress isa;
	//private String host;
	//private int port;

	private String serverTID; // identyfikator watku
	private static volatile boolean serverRunning = true;
	private static String pathToDictionaries;
	private List<ServerLanguage> languageServersList;

	public ServerMain(String serverTID, String host, int port, String pathToDictionaries) {
		this.serverTID = serverTID;
		try {
			this.ss = new ServerSocket();
			this.isa = new InetSocketAddress(host, port);
			ss.bind(isa);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ServerMain.pathToDictionaries = pathToDictionaries;
		this.languageServersList = new ArrayList<ServerLanguage>();
		this.createLanguageServersMap(ServerMain.pathToDictionaries);

		System.out.println("Server " + serverTID + " started. Listening at port: " + ss.getLocalPort()
				+ ", bind address: " + ss.getInetAddress());

		start();
	}

	public void run() {
		while (serverRunning) {
			try {
				conn = ss.accept();
				//connect("localhost", 49200); // 49200 !! z tym ze trzeba bedzie zmienic porty na argumenty
				
				//System.out.println("Connection established by server main no: " + serverTID);
				serviceRequests(conn);
				//writeMsg("???");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void connect(Socket socket) { //  String host, int port ..i dodac tez slowo i port klienta
		try {
			//socket = new Socket(host, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			System.out.println("Main server --" + serverTID + "-- stream connected to smth.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void serviceRequests(Socket connection) {
		connect(connection);
		System.out.println(readMsg(connection));
		writeMsg("OK");

	}

	public void writeMsg(String msg) {
		//System.out.println("Server writes...");
		System.out.println("Main server writes: " + msg);
		out.println(msg);
	}

	private String readMsg(Socket conn) {
		try {
			//in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			//out = new PrintWriter(conn.getOutputStream(), true);
			//System.out.println("Main server reads request from client..."); // test
			//System.out.print("Receiving...");
			//System.out.println("Server received FULL message.");
			return "Main server reads: " + in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ERROR.";

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
