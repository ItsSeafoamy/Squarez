package uk.co.abyxstudioz.squarez;

import java.awt.Color;
import java.awt.Graphics;
import java.math.BigInteger;
import java.util.Random;
import javax.swing.JPanel;

public class Game extends JPanel {

	public boolean init;
	
	public static int[][] Board;
	public static String code;
	
	public static int i = 2;
	public static int j = 2;
	
	static final Character character = new Character();
	
	@Override
	public void paintComponent(Graphics g){
		if (!init){
			init();
			init = true;
			new Listener();
		}
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Squarez.frame.getWidth(), Squarez.frame.getHeight());
		
		for (int x = 0; x < i; x++){
			for (int y = 0; y < j; y++){
				g.setColor(Color.BLACK);
				g.drawRect(x * 20, y * 20, 20, 20);
				if (Board[x][y] == 0){
					g.fillRect(x * 20, y * 20, 20, 20);
					g.setColor(Color.WHITE);
					g.drawRect(x * 20, y * 20, 20, 20);
				}
			}
		}
		g.setColor(Color.GREEN);
		g.fillOval((character.getPosX() * 20) + 2, (character.getPosY() * 20) + 2, 16, 16);
		g.setColor(Color.decode("000127000"));
		g.drawOval((character.getPosX() * 20) + 2, (character.getPosY() * 20) + 2, 16, 16);
		
		updateCode();
		g.setColor(Color.BLACK);
		g.drawString("The object of the game is to get to the other side of the board (You're the circle).", i*20, 16);
		g.drawString("Move using either the WASD keys or the Arrow Keys.", i*20, 36);
		g.drawString("You can only move to adjacent white squares.", i*20, 56);
		g.drawString("When you move, all adjacent squares invert colours.", i*20, 76);
		g.drawString("If you get stuck, press 'R' to reset the board.", i*20, 96);
		g.setColor(new Color(255,0,0));
		g.drawString(getCode(), i*20, 116);
		g.setColor(Color.BLACK);
		g.drawString("That code above is your save code. Press 'C' to copy it to your clipboard.", i*20, 136);
		g.drawString("To load a game, press 'V' and paste in your code.", i*20, 156);
		g.drawString("Old 'binary' saves will still work.", i*20, 176);
		repaint();
	}

	protected static void init() {
		Board = new int[i][j];
		Random random = new Random();
		character.setPos(0, 0);
		Squarez.frame.setTitle(Squarez.title + " - " + Squarez.version + " - " + i + "x" + j);
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
