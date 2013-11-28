package uk.co.abyxstudioz.squarez;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

public class Squarez {

	static JFrame frame = new JFrame();
	public static String title = "Squarez";
	public static final String version = "v0.7";
	public static String splash;
	public static ArrayList<String> splashes;
	public static JPanel menu;
	
	public static void main(String args[]){
   		frame.setSize(720, 480);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.requestFocus();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(720, 480));
		
		try {
			YamlReader reader = new YamlReader(new FileReader("splash.yml"));
			Object object = reader.read();
			Map map = (Map) object;
			splashes = (ArrayList<String>) map.get("splash");
		} catch (Exception e) {
			splashes = new ArrayList<String>();
			splashes.add("Something's wrong with Splash.yml. Make sure it exists.");
		}
		
		menu = new Menu();
		frame.add(menu);
		frame.pack();
	}
}
