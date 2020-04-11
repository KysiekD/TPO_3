package pack1;

import java.util.HashMap;

public class ServerLanguage {

	private String language;
	private String path;
	private int port;
	private HashMap<String, String> wordsHashMap;

	public ServerLanguage(String language, int port) {
		this.port = port;
		this.language = language;
		this.wordsHashMap = new HashMap();
	}

	private void readDictionary() {

	}

	public String getLanguage() {
		return this.language;
	}

	public int getPort() {
		return this.port;
	}
}
