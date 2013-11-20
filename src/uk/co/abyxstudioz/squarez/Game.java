package uk.co.abyxstudioz.squarez;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.newdawn.easyogg.OggClip;

public class Game extends JPanel {

	public boolean init;
	
	public static int[][] Board;
	public static String code;
	
	public static int i = 2;
	public static int j = 2;
	
	public static int cameraX;
	public static int cameraY;
	
	static final Character character = new Character();
	
	@Override
	public void paintComponent(Graphics g){
		if (!init){
			init();
			init = true;
			new Listener();
			
			try {
				OggClip ogg = new OggClip("music/bgm.ogg");
				ogg.loop();
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Could not locate \"music/bgm.ogg\". Background music will not play, but the game can still be played.", "Squarez", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Squarez.frame.getWidth(), Squarez.frame.getHeight());
		
		cameraX = character.getPosX();
		cameraY = character.getPosY();
		
		for (int x = 0; x < i; x++){
			for (int y = 0; y < j; y++){
				g.setColor(Color.BLACK);
				g.drawRect(((Squarez.frame.getWidth() / 2) - 10) + (x*20) - (character.getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) + (y*20) - (character.getPosY() * 20), 20, 20);
				if (Board[x][y] == 0){
					g.fillRect(((Squarez.frame.getWidth() / 2) - 10) + (x*20) - (character.getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) + (y*20) - (character.getPosY() * 20), 20, 20);
					g.setColor(Color.WHITE);
					g.drawRect(((Squarez.frame.getWidth() / 2) - 10) + (x*20) - (character.getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) + (y*20) - (character.getPosY() * 20), 20, 20);
				}
			}
		}
		g.setColor(Color.GREEN);
		g.fillOval((Squarez.frame.getWidth() / 2) - 8, (Squarez.frame.getHeight() / 2) - 8, 16, 16);
		g.setColor(Color.decode("000127000"));
		g.drawOval((Squarez.frame.getWidth() / 2) - 8, (Squarez.frame.getHeight() / 2) - 8, 16, 16);
		
		updateCode();
		g.setColor(Color.BLACK);
		g.drawString("The object of the game is to get to the other side of the board (You're the circle).", ((Squarez.frame.getWidth() / 2) - 10) + (i*20) - (character.getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) - (character.getPosY() * 20) + 16);
		g.drawString("Move using either the WASD keys or the Arrow Keys.", ((Squarez.frame.getWidth() / 2) - 10) + (i*20) - (character.getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) - (character.getPosY() * 20) + 36);
		g.drawString("You can only move to adjacent white squares.", ((Squarez.frame.getWidth() / 2) - 10) + (i*20) - (character.getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) - (character.getPosY() * 20) + 56);
		g.drawString("When you move, all adjacent squares invert colours.", ((Squarez.frame.getWidth() / 2) - 10) + (i*20) - (character.getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) - (character.getPosY() * 20) + 76);
		g.drawString("If you get stuck, press 'R' to reset the board.", ((Squarez.frame.getWidth() / 2) - 10) + (i*20) - (character.getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) - (character.getPosY() * 20) + 96);
		g.setColor(new Color(255,0,0));
		g.drawString(getCode(), ((Squarez.frame.getWidth() / 2) - 10) + (i*20) - (character.getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) - (character.getPosY() * 20) + 116);
		g.setColor(Color.BLACK);
		g.drawString("That code above is your save code. Press 'C' to copy it to your clipboard.", ((Squarez.frame.getWidth() / 2) - 10) + (i*20) - (character.getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) - (character.getPosY() * 20) + 136);
		g.drawString("To load a game, press 'V' and paste in your code.", ((Squarez.frame.getWidth() / 2) - 10) + (i*20) - (character.getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) - (character.getPosY() * 20) + 156);
		g.drawString("Old 'binary' saves will still work.", ((Squarez.frame.getWidth() / 2) - 10) + (i*20) - (character.getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) - (character.getPosY() * 20) + 176);
		repaint();
	}

	protected static void init() {
		Board = new int[i][j];
		Random random = new Random();
		character.setPos(0, 0);
		
		Squarez.splash = Squarez.splashes.get(random.nextInt(Squarez.splashes.size()));
		
		Squarez.frame.setTitle(Squarez.title + " - " + Squarez.version + " - " + i + "x" + j + " - " + Squarez.splash);
		for (int x = 0; x < i; x++){
			for (int y = 0; y < j; y++){
				Board[x][y] = random.nextInt(2);
			}
		}
		Board[0][0] = 1;
		
		if (Board[0][1] == 0 && Board[1][0] == 0){
			Game.init();
		}
		if (i == 2 && j == 2){
			Board[1][1] = 0;
		}
	}
	
	protected static Character getCharacter(){
		return character;
	}
	
	protected static void updateCode(){
		String a = Integer.toString(i);
		String b = Integer.toString(j);
		String e = Integer.toString(character.getPosX());
		String f = Integer.toString(character.getPosY());
		String c = "";
		for (int x = 0; x < i; x++){
			for (int y = 0; y < j; y++){ 
				c += Board[x][y];
			}
		}
		
		BigInteger g = new BigInteger(c, 2);
		c = g.toString(36);
		code = a + "," + b + "," + e + "," + f + "," + c;
	}
	
	protected static String getCode(){
		return code;
	}
}
