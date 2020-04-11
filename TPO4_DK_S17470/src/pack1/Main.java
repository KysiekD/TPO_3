package pack1;

public class Main {

	public static void main(String[] args) {
		// https://www.tutorialspoint.com/design_pattern/mvc_pattern.htm
		// Pozosta�y zakres numer�w port�w 49152-65535 okre�la tzw. porty dynamiczne lub
		// prywatne. Mo�emy ich u�ywac.

		String tempServerDictionariesPath = "C:\\Users\\Wygrany\\Desktop\\Dictionaries";

		ClientModel model = new ClientModel();
		ClientViewMain mainView = new ClientViewMain();

		ClientController controller = new ClientController(model, mainView);
		ServerMain mainServer = new ServerMain(tempServerDictionariesPath);

	}

}
