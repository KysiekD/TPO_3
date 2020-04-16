package pack1;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {

	public static void main(String[] args) {
		// https://www.tutorialspoint.com/design_pattern/mvc_pattern.htm
		// Pozosta³y zakres numerów portów 49152-65535 okreœla tzw. porty dynamiczne lub
		// prywatne. Mo¿emy ich u¿ywac.
		
		
		final int SERVERS_NUM = 1; //liczba serverow

		List<ServerMain> mainServersList = new ArrayList<ServerMain>();
		
		String tempServerDictionariesPath = "C:\\Users\\Wygrany\\Desktop\\Dictionaries";
		final String serverHost = "localhost";
		final int serverPort = 49534;
		final String languageServerhost = "localhost";
		
		String languages = "";

		
		for(int i = 1; i <= SERVERS_NUM; i++) {
			ServerMain mainServer = new ServerMain("Server thread " + i, serverHost, serverPort, tempServerDictionariesPath, languageServerhost);
			mainServersList.add(mainServer);
			
		}
		
		for(ServerMain a : mainServersList) {
			languages = languages.concat(a.getLanguagesList());
		}
		
		ClientModel model = new ClientModel("localhost", 48999, serverHost, serverPort);
		ClientViewMain mainView = new ClientViewMain(model,languages);
		//ClientController controller = new ClientController(model, mainView);
		
		//model.askForTranslation("EN", "kot", "localhost", 49534);
		//model.askForTranslation("EN", "ptak", "localhost", 49534);
		

		
		
		

		
		for(ServerMain serv : mainServersList) {
			serv.viewInfoAboutDictionariesAndDisconnect(); //test
		}
		
		for(ServerMain n : mainServersList) {
			//n.disconectMainServer();
			
		}
		
		

		
		
		

	}

}
