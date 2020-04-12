package pack1;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class Main {

	public static void main(String[] args) {
		// https://www.tutorialspoint.com/design_pattern/mvc_pattern.htm
		// Pozosta³y zakres numerów portów 49152-65535 okreœla tzw. porty dynamiczne lub
		// prywatne. Mo¿emy ich u¿ywac.
		
		final int SERVERS_NUM = 1; //liczba serverow
		ServerSocket ss = null;

		String tempServerDictionariesPath = "C:\\Users\\Wygrany\\Desktop\\Dictionaries";

		
		
		
		
		
		String host = "localhost";
		int port = 49160;
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

		}
		
		ClientModel model = new ClientModel(13);
		model.connect("localhost", 49160);
		//ClientViewMain mainView = new ClientViewMain();
		//ClientController controller = new ClientController(model, mainView);

	}

}
