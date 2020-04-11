package pack1;

import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileVisitor<E, K> extends SimpleFileVisitor<Path> {
	private List<String> dictionariesList;
	private HashMap<String,String> tempWordsList;

	public FileVisitor() {
		dictionariesList = new ArrayList<String>();
	}

	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
		System.out.println(dir);
		return CONTINUE;
	}

	public FileVisitResult visitFile(Path dir, BasicFileAttributes attrs) {
		// System.out.println(dir); //test
		String tempText = (String) dir.toString().subSequence(dir.toString().length() - 6, dir.toString().length() - 4);
		// System.out.println(tempText); //test
		dictionariesList.add(tempText);
		this.addWordListToServerDictionary(dir.toString());
		return CONTINUE;
	}

	public void copyList(List<String> otherList) {
		otherList.addAll(this.dictionariesList);
	}

	public void addWordListToServerDictionary(String path) {
		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line = reader.readLine();

			while (line != null) {
				System.out.println(line); //test
				line = reader.readLine();
			}

			reader.close();

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}
