package pack1;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientViewMain extends JPanel
{
	private JLabel mainLabel, langageLabel, wordLabel, mainLabel2, translationLabel;
	private JTextField languageTextField, wordTextField;
	private JPanel panel, panel2;
	private ButtonCheck buttonCheck;
	private JFrame mainFrame;
	private ClientModel model;


	public ClientViewMain(ClientModel model, String languages) 
	{
		//??
		this.model = model;
		this.mainFrame = new JFrame();

		//Panel before translation:
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		mainLabel = new JLabel("Dictionary: " + languages);
		
		langageLabel = new JLabel("Please write language code. For example: EN.");
		languageTextField = new JTextField();
		wordLabel = new JLabel("Please write Polish word to translate:");
		wordTextField = new JTextField();
		buttonCheck = new ButtonCheck("Search", model, mainFrame, languageTextField, wordTextField, translationLabel, panel);
		buttonCheck.addActionListener(buttonCheck);
		translationLabel = new JLabel("--------------------");
		
		
		panel.add(mainLabel);
		panel.add(langageLabel);
		panel.add(languageTextField);
		panel.add(wordLabel);
		panel.add(wordTextField);
		panel.add(translationLabel);
		panel.add(buttonCheck);
		mainFrame.add(panel);
		
		//Panel after translation:
		panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		mainLabel2 = new JLabel("Translation");
		
		
		
		mainFrame.setVisible(true);
		
		mainFrame.setSize(300, 200);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		

	}
	

	
}
