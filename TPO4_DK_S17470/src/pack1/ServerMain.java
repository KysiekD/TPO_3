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
import java.util.ListIterator;
import java.util.Map;

public class ServerMain extends Thread {

	private ServerSocket ss;
	private Socket conn;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private InetSocketAddress isa;
	private String languageServersHost;

	private String serverTID; // identyfikator watku
	private static volatile boolean serverRunning = true;
	private static String pathToDictionaries;
	private List<ServerLanguage> languageServersList;

	public ServerMain(String serverTID, String host, int port, String pathToDictionaries, String languageServersHost) {
		this.serverTID = serverTID;
		try {
			this.ss = new ServerSocket();
			this.isa = new InetSocketAddress(host, port);
			ss.bind(isa);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ServerMain.pathToDictionaries = pathToDictionaries;
		this.languageServersHost = languageServersHost;
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

				serviceRequests(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void connect(Socket socket) { // String host, int port ..i dodac tez slowo i port klienta
		try {

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			System.out.println("Main server --" + serverTID + "-- stream connected to smth.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ServerLanguage getDictionary(String language) {
		ListIterator<ServerLanguage> iterator = languageServersList.listIterator();
		while (iterator.hasNext()) {
			ServerLanguage dictionary = iterator.next();
			// System.out.println(dictionary.getLanguage());
			if (dictionary.getLanguage().equals(language)) {
				//System.out.println("=====" + dictionary.getLanguage() + "....." + dictionary.getPort()); // Test
				return dictionary;

			}
		}
		System.out.println("Error, language server not found on the list.");
		return null;

	}

	private void serviceRequests(Socket connection) {
		// Reads from client:
		connect(connection);
		String text = readMsg(connection);
		String[] textTable = new String[4];
		textTable = text.split("-");
		// textTable[0] = language code
		// textTable[1] = word
		// textTable[2] = client port
		// textTable[3] = client host
		writeMsg("OK");
		
		disconnect(connection);

		// Writes to language server:
		try {

			Socket tempSocket = new Socket(getDictionary(textTable[0]).getHost(), getDictionary(textTable[0]).getPort()); // HARDCODED!
			System.out.println("FOUND DICTIONARY HOST: " + getDictionary(textTable[0]).getHost() + ", FOUND PORT: " + getDictionary(textTable[0]).getPort());
			connect(tempSocket);
			writeMsg(textTable[1] + "-" + textTable[2] + "-" + textTable[3] );
			disconnect(tempSocket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeMsg(String msg) {

		System.out.println("Main server writes: " + msg);
		out.println(msg);
	}

	private String readMsg(Socket conn) {
		try {

			String text = in.readLine();
			System.out.println("Main server reads: " + text);

			return text;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ERROR.";

		}

	}

	public void disconnect(Socket socket) {
		try {

			in.close();
			out.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void createLanguageServersMap(String path) {
		FileVisitor visitor = new FileVisitor(languageServersHost);
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

		}

	}

}
