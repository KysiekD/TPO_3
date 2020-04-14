package pack1;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		// https://www.tutorialspoint.com/design_pattern/mvc_pattern.htm
		// Pozosta³y zakres numerów portów 49152-65535 okreœla tzw. porty dynamiczne lub
		// prywatne. Mo¿emy ich u¿ywac.
		
		final int SERVERS_NUM = 1; //liczba serverow
		ServerSocket ss = null;
		List<ServerMain> mainServersList = new ArrayList<ServerMain>();
		
		String tempServerDictionariesPath = "C:\\Users\\Wygrany\\Desktop\\Dictionaries";

		
		
		
		
		
		String host = "localhost";
		int port = 49534;
		InetSocketAddress isa = new InetSocketAddress(host,port);
		try {
			ss = new ServerSocket();
			ss.bind(isa);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		for(int i = 1; i <= SERVERS_NUM; i++) {
			ServerMain mainServer = new ServerMain("Server thread " + i,ss, tempServerDictionariesPath);
			mainServersList.add(mainServer);
			
		}
		
		ClientModel model = new ClientModel(13, "localhost");
		model.connect("localhost", 49534);
		model.writeMsg("DE-pies");
		
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
