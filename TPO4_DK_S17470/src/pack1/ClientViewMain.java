package pack1;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientViewMain extends JPanel
{
	private JLabel mainLabel, langageLabel, wordLabel, mainLabel2;
	private JTextField languageTextField, wordTextField;
	private JPanel panel, panel2;
	private ButtonCheck buttonCheck;
	private JFrame mainFrame;
	private ClientModel model;


	public ClientViewMain(ClientModel model) 
	{
		//??
		this.model = model;
		this.mainFrame = new JFrame();

		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		mainLabel = new JLabel("Dictionary: EN, DE");
		
		langageLabel = new JLabel("Please write language code. For example: EN.");
		languageTextField = new JTextField();
		wordLabel = new JLabel("Please write Polish word to translate:");
		wordTextField = new JTextField();
		buttonCheck = new ButtonCheck("Search", model, mainFrame, languageTextField, wordTextField);
		buttonCheck.addActionListener(buttonCheck);
		
		panel.add(mainLabel);
		panel.add(languageTextField);
		panel.add(wordTextField);
		panel.add(buttonCheck);
		mainFrame.add(panel);
		
		
		
		mainFrame.setVisible(true);
		mainFrame.pack();
		mainFrame.setSize(300, 150);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		mainLabel2 = new JLabel("Translation");
	}
	

	
}
