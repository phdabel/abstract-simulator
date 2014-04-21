import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;


public class MainFrame extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 213319948218521776L;
	private static int WIDTH = 6000;
	private static int HEIGHT = 6000;
	Display display;
	/**
	 * @param args the command line arguments
	 */
	public void Main()
	{
		initDisplay();
	}

	public void initDisplay()
	{
		@SuppressWarnings("unused")
		Display display = new Display(WIDTH,HEIGHT); //creates the field according to the specifications
	}

	public static void main(String[] args) 
	{
		new MainFrame().initDisplay();				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
