package uk.co.abyxstudioz.squarez;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Squarez {

	static JFrame frame = new JFrame();
	public static String title = "Squarez";
	public static final String version = "0.5";
	
	public static void main(String args[]){
		
   		frame.setSize(720, 480);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.requestFocus();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel game = new Game();
		frame.add(game);
	}
}
