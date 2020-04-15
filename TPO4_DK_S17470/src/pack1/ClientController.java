package pack1;

import javax.swing.JFrame;

public class ClientController 
{

	public ClientController(ClientModel model, ClientViewMain view)
	{
		
		JFrame mainFrame = new JFrame();
		mainFrame.add(view);
		mainFrame.setVisible(true);
		mainFrame.pack();
		mainFrame.setSize(300, 150);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
}
