package uk.co.abyxstudioz.squarez;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.newdawn.easyogg.OggClip;

public class Menu extends JPanel{

	Random random = new Random();
	
	boolean init;
	
	public static BufferedImage menuSP;
	public static BufferedImage menuRace;
	public static BufferedImage menuTrapped;
	public static BufferedImage logo;
	String missingFile;
	public static OggClip ogg;
	
	public Menu(){
		try {
			ogg = new OggClip("music/intro.ogg");
			ogg.loop();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Could not locate \"music/intro.ogg\". Background music will not play, but the game can still be played.", "Squarez", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void paintComponent(Graphics g){
		if (!init){
			Squarez.splash = Squarez.splashes.get(random.nextInt(Squarez.splashes.size()));
			Squarez.frame.setTitle(Squarez.title + " - " + Squarez.version + " - " + Squarez.splash);
			init = true;
			
			try {
				missingFile = "resources/menuSP.png";
				menuSP = ImageIO.read(new File("resources/menuSP.png"));
				missingFile = "resources/menuRace.png";
				menuRace = ImageIO.read(new File("resources/menuRace.png"));
				missingFile = "resources/menuTrapped.png";
				menuTrapped = ImageIO.read(new File("resources/menuTrapped.png"));
				missingFile = "resources/logo.png";
				logo = ImageIO.read(new File("resources/logo.png"));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "A fatal error has occured. The required file \"" + missingFile + "\" could not be found. Shutting down.", "Squarez", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			
			this.addMouseListener(new MenuListener());
		}
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Squarez.frame.getWidth(), Squarez.frame.getHeight());
		
		g.drawImage(logo, (Squarez.frame.getWidth() / 2) - (logo.getWidth() / 2), 20, null);
		
		g.drawImage(menuSP, (int) ((Squarez.frame.getWidth() / 2) - (menuSP.getWidth() * 1.5)), (Squarez.frame.getHeight() / 2) - (menuSP.getHeight() / 2), null);
		g.drawImage(menuRace, (Squarez.frame.getWidth() / 2) - (menuRace.getWidth() / 2), (Squarez.frame.getHeight() / 2) - (menuRace.getHeight() / 2), null);
		g.drawImage(menuTrapped, (int) ((Squarez.frame.getWidth() / 2) + (menuTrapped.getWidth() * 0.5)), (Squarez.frame.getHeight() / 2) - (menuTrapped.getHeight() / 2), null);

		repaint();
	}
}
