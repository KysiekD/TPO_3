package pack1;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		// https://www.tutorialspoint.com/design_pattern/mvc_pattern.htm
		// Pozosta³y zakres numerów portów 49152-65535 okreœla tzw. porty dynamiczne lub
		// prywatne. Mo¿emy ich u¿ywac.
		
		final int SERVERS_NUM = 1; //liczba serverow

		List<ServerMain> mainServersList = new ArrayList<ServerMain>();
		
		String tempServerDictionariesPath = "C:\\Users\\Wygrany\\Desktop\\Dictionaries";


		
		for(int i = 1; i <= SERVERS_NUM; i++) {
			ServerMain mainServer = new ServerMain("Server thread " + i, "localhost", 49534, tempServerDictionariesPath);
			mainServersList.add(mainServer);
			
		}
		
		ClientModel model = new ClientModel("localhost", 48999);
		model.askForTranslation("DE", "Hund", "localhost", 49534);
		
		/*
		Socket sock;
		try {
			sock = new Socket("localhost", 49534);
			model.connect(sock);
			model.writeMsg("DE-pies-48001");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		
		//model.disconnect();
		
		
		
		for(ServerMain serv : mainServersList) {
			serv.viewInfoAboutDictionariesAndDisconnect(); //test
		}
		
		for(ServerMain n : mainServersList) {
			//n.disconectMainServer();
			
		}
		
		
		//ClientViewMain mainView = new ClientViewMain();
		//ClientController controller = new ClientController(model, mainView);

	}

}
