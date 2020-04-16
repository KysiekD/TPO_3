package pack1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class ButtonCheck extends JButton implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3263485128311360024L;
	private ClientModel model;
	private JFrame mainFrame;
	private JTextField language;
	private JTextField word;
	private JLabel translationLabel;
	private JPanel panel;

	public ButtonCheck(String text, ClientModel model, JFrame mainFrame, JTextField language, JTextField word, JLabel translationLabel, JPanel panel) {
		super(text);
		this.model = model;
		this.mainFrame = mainFrame;
		this.language = language;
		this.word = word;
		this.translationLabel = translationLabel;
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Button -> Translate");
		// model.askForTranslation("EN", "kot", "localhost", 49534);
		model.askForTranslation(language.getText(), word.getText(), "localhost", 49534);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String translation = model.getTranslation();
		this.panel.add(new JLabel("Translation for word '" + word.getText() + "' to '" + language.getText() + "' is: " + translation));
		translationLabel = new JLabel(translation);
		mainFrame.setSize(300, 210);
		mainFrame.pack();
		panel.repaint();
		mainFrame.repaint();
		
		
	}
}
