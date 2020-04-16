package pack1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

class ButtonCheck extends JButton implements ActionListener {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3263485128311360024L;
	private ClientModel model;
	JFrame mainFrame;
	JTextField language;
	JTextField word;
	
	
	public ButtonCheck(String text, ClientModel model, JFrame mainFrame, JTextField language, JTextField word) {
		super(text);
		this.model = model;
		this.mainFrame = mainFrame;
		this.language = language;
		this.word = word;
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Button -> Translate");
		//model.askForTranslation("EN", "kot", "localhost", 49534);
		model.askForTranslation(language.getText(), word.getText(), "localhost", 49534);
	}
}
