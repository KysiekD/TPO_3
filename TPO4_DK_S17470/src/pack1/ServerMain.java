package pack1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerMain {

	// private HashMap<String, Integer> languageServersMap; //del
	private List<String> languagesList;
	private List<ServerLanguage> languageServersList;
	private String pathToDictionaries;

	public ServerMain(String pathToDictionaries) {
		this.pathToDictionaries = pathToDictionaries;
		this.languagesList = new ArrayList<String>();
		// this.languageServersMap = new HashMap(); //del
		this.languageServersList = new ArrayList<ServerLanguage>();
		this.createLanguageServersMap(pathToDictionaries);
	}

	private void createLanguageServersMap(String path) {
		FileVisitor visitor = new FileVisitor();
		try {
			Files.walkFileTree(Paths.get(path), visitor);
			visitor.copyList(languagesList);
			int portSequenceNumber = 49153;

			for (String language : languagesList) {
				System.out.println("Language dictionary " + language + " detected."); // test
				languageServersList.add(new ServerLanguage(language, portSequenceNumber));
				portSequenceNumber++;
			}

			for (ServerLanguage G : languageServersList) {
				System.out.println("Language server " + G.getLanguage() + " with port no " + G.getPort() + " created.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
