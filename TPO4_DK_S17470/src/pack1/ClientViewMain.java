package pack1;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientViewMain extends JPanel
{
	private JLabel mainLabel, langageLabel, wordLabel;
	private JTextField languageTextField, wordTextField;
	private JPanel panel;
	private JButton buttonCheck;

	public ClientViewMain() 
	{

		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		mainLabel = new JLabel("Dictionary: EN, DE");
		
		langageLabel = new JLabel("Please write language code. For example: EN.");
		languageTextField = new JTextField();
		wordLabel = new JLabel("Please write Polish word to translate:");
		wordTextField = new JTextField();
		buttonCheck = new JButton("Search");
		
		
		panel.add(mainLabel);
		panel.add(languageTextField);
		panel.add(wordTextField);
		panel.add(buttonCheck);
		this.add(panel);
		
		


	}
	

	
}
