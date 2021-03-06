package pack1;

import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileVisitor<E, K> extends SimpleFileVisitor<Path> {
	private HashMap<String, String> tempWordsList;
	private static List<ServerLanguage> dictionariesServersList;
	private static int portSequenceNumber = 49200;
	private static String languageServerHost;

	public FileVisitor(String languageServerHost) {
		this.languageServerHost = languageServerHost;
		tempWordsList = new HashMap<String, String>();
		dictionariesServersList = new ArrayList<ServerLanguage>();
	}

	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
		System.out.println("Checking directory for dictionaries: " + dir);
		return CONTINUE;
	}

	public FileVisitResult visitFile(Path dir, BasicFileAttributes attrs) {
		// System.out.println(dir); //test
		String language = (String) dir.toString().subSequence(dir.toString().length() - 6, dir.toString().length() - 4);
		// System.out.println(tempText); //test

		this.addWordListToServerDictionary(dir.toString(), tempWordsList);
		dictionariesServersList.add(new ServerLanguage(FileVisitor.languageServerHost, language, portSequenceNumber, new HashMap<String,String>()));
		dictionariesServersList.get(dictionariesServersList.size()-1).addWordsToDictionary(tempWordsList);
		tempWordsList.clear();
		portSequenceNumber++;
		return CONTINUE;
	}

	public void copyList(List<ServerLanguage> otherList) {
		otherList.addAll(this.dictionariesServersList);
	}

	public void addWordListToServerDictionary(String path, HashMap<String, String> wordList) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line = "";
			String polishWord;
			String translationWord;
			

			while ((line = reader.readLine()) != null) {
				// System.out.println(line); //test
				//line = reader.readLine();
				//System.out.println(line); //test
				String [] tempTable = line.split("-", 2);
				wordList.put(tempTable[0], tempTable[1]);
				//wordList.put(line, "a");
				tempTable = null;
			}

			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
