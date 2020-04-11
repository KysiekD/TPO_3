package pack1;

public class ClientModel {

	private String word, language;
	private int port;

	public ClientModel() {
		this.word = null;
		this.language = null;
		this.port = 0;
	}

	// SETTERS
	public void setWord(String text) {
		this.word = text;
	}

	public void setLanguage(String text) {
		this.language = text;
	}

	public void setPort(int number) {
		this.port = number;
	}

}
