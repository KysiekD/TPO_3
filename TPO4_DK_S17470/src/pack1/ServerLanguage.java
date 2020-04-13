package pack1;

import java.util.HashMap;

public class ServerLanguage {

	private String language;
	private String path;
	private int port;
	private HashMap<String, String> wordsHashMap;

	public ServerLanguage(String language, int port, HashMap<String, String> wordsList) {
		this.port = port;
		this.language = language;
		this.wordsHashMap = wordsList;
	}

	public String readDictionary() {
		return "....Words in dictionary: " + wordsHashMap.toString();
	}

	public String getLanguage() {
		return this.language;
	}

	public int getPort() {
		return this.port;
	}
	
	public HashMap<String,String> getWords(){
		return wordsHashMap;
	}
	
	public void addWordsToDictionary(HashMap<String,String> map) {
		wordsHashMap.putAll(map);
	}
	
}
