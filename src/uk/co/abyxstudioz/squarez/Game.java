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

	static Character[] character = new Character[2];

	public static int CURRENT_MODE = 0;
	public static final int SINGLE_PLAYER = 0;
	public static final int RACE = 1;
	public static final int TRAPPED = 2;

	public Game(int mode){
		CURRENT_MODE = mode;
	}

	@Override
	public void paintComponent(Graphics g){
		if (!init){
			if (CURRENT_MODE == SINGLE_PLAYER){
				character[0] = new Character();

				new SPListener();

				init();
			} if (CURRENT_MODE == RACE || CURRENT_MODE == TRAPPED){
				character[0] = new Character();
				character[1] = new Character();

				i = 8;
				j = 8;

				init();

				new MPListener();
				
				character[0].setColour("255000000");
				character[1].setColour("000000255");
			}

			try {
				OggClip ogg = new OggClip("music/bgm.ogg");
				ogg.loop();
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Could not locate \"music/bgm.ogg\". Background music will not play, but the game can still be played.", "Squarez", JOptionPane.WARNING_MESSAGE);
			}

			init = true;

		}

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Squarez.frame.getWidth(), Squarez.frame.getHeight());

		if (CURRENT_MODE == SINGLE_PLAYER){
			for (int x = 0; x < i; x++){
				for (int y = 0; y < j; y++){
					g.setColor(Color.BLACK);
					g.drawRect(((Squarez.frame.getWidth() / 2) - 10) + (x*20) - (character[0].getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) + (y*20) - (character[0].getPosY() * 20), 20, 20);
					if (Board[x][y] == 0){
						g.fillRect(((Squarez.frame.getWidth() / 2) - 10) + (x*20) - (character[0].getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) + (y*20) - (character[0].getPosY() * 20), 20, 20);
						g.setColor(Color.WHITE);
						g.drawRect(((Squarez.frame.getWidth() / 2) - 10) + (x*20) - (character[0].getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) + (y*20) - (character[0].getPosY() * 20), 20, 20);
					}
				}
			}
		} else {
			for (int x = 0; x < i; x++){
				for (int y = 0; y < j; y++){
					g.setColor(Color.BLACK);
					g.drawRect(x *20, y * 20, 20, 20);
					if (Board[x][y] == 0){
						g.fillRect(x *20, y * 20, 20, 20);
						g.setColor(Color.WHITE);
						g.drawRect(x *20, y * 20, 20, 20);
					}
				}
			}
		}
		if (CURRENT_MODE == SINGLE_PLAYER){
			g.setColor(new Color(character[0].getR(), character[0].getG(), character[0].getB()));
			g.fillOval((Squarez.frame.getWidth() / 2) - 8, (Squarez.frame.getHeight() / 2) - 8, 16, 16);
			g.setColor(new Color(character[0].getR() / 2, character[0].getG() / 2, character[0].getB() / 2));
			g.drawOval((Squarez.frame.getWidth() / 2) - 8, (Squarez.frame.getHeight() / 2) - 8, 16, 16);
		} else {
			g.setColor(new Color(character[0].getR(), character[0].getG(), character[0].getB()));
			g.fillOval((character[0].getPosX() * 20) + 2, (character[0].getPosY() * 20) + 2, 16, 16);
			g.setColor(new Color(character[0].getR() / 2, character[0].getG() / 2, character[0].getB() / 2));
			g.drawOval((character[0].getPosX() * 20) + 2, (character[0].getPosY() * 20) + 2, 16, 16);

			g.setColor(new Color(character[1].getR(), character[1].getG(), character[1].getB()));
			g.fillOval((character[1].getPosX() * 20) + 2, (character[1].getPosY() * 20) + 2, 16, 16);
			g.setColor(new Color(character[1].getR() / 2, character[1].getG() / 2, character[1].getB() / 2));
			g.drawOval((character[1].getPosX() * 20) + 2, (character[1].getPosY() * 20) + 2, 16, 16);
		}

		updateCode();

		if (CURRENT_MODE == SINGLE_PLAYER){
			g.setColor(Color.BLACK);
			g.drawString("The object of the game is to get to the other side of the board (You're the circle).", ((Squarez.frame.getWidth() / 2) - 10) + (i*20) - (character[0].getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) - (character[0].getPosY() * 20) + 16);
			g.drawString("Move using either the WASD keys or the Arrow Keys.", ((Squarez.frame.getWidth() / 2) - 10) + (i*20) - (character[0].getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) - (character[0].getPosY() * 20) + 36);
			g.drawString("You can only move to adjacent white squares.", ((Squarez.frame.getWidth() / 2) - 10) + (i*20) - (character[0].getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) - (character[0].getPosY() * 20) + 56);
			g.drawString("When you move, all adjacent squares invert colours.", ((Squarez.frame.getWidth() / 2) - 10) + (i*20) - (character[0].getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) - (character[0].getPosY() * 20) + 76);
			g.drawString("If you get stuck, press 'R' to reset the board.", ((Squarez.frame.getWidth() / 2) - 10) + (i*20) - (character[0].getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) - (character[0].getPosY() * 20) + 96);
			g.setColor(new Color(255,0,0));
			g.drawString(getCode(), ((Squarez.frame.getWidth() / 2) - 10) + (i*20) - (character[0].getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) - (character[0].getPosY() * 20) + 116);
			g.setColor(Color.BLACK);
			g.drawString("That code above is your save code. Press 'C' to copy it to your clipboard.", ((Squarez.frame.getWidth() / 2) - 10) + (i*20) - (character[0].getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) - (character[0].getPosY() * 20) + 136);
			g.drawString("To load a game, press 'V' and paste in your code.", ((Squarez.frame.getWidth() / 2) - 10) + (i*20) - (character[0].getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) - (character[0].getPosY() * 20) + 156);
			g.drawString("Old 'binary' and 'split' saves will still work.", ((Squarez.frame.getWidth() / 2) - 10) + (i*20) - (character[0].getPosX() * 20), ((Squarez.frame.getHeight() / 2) - 10) - (character[0].getPosY() * 20) + 176);
		} else if (CURRENT_MODE == RACE){
			g.setColor(Color.BLACK);
			g.drawString("The objective is to get to the other player's starting position before they get to yours.", i * 20, 16);
			g.drawString("If you become trapped by your own move, you will respawn back at your starting position", i * 20, 36);
			g.drawString("If you end up on a black square, you will respawn back at your starting position", i * 20, 56);
			g.drawString("Whoever gets to the opponent's starting position first earns a point.", i * 20, 76);
			g.setColor(new Color(255, 0, 0));
			g.drawString(getCode(), i * 20, 96);
			g.setColor(Color.BLACK);
			g.drawString("That code above is your save code. Press 'C' to copy it to your clipboard.", i * 20, 116);
			g.drawString("To load a game, press 'V' and paste in your code.", i * 20, 136);
		} else if (CURRENT_MODE == TRAPPED){
			g.setColor(Color.BLACK);
			g.drawString("The objective is to either trap your opponent (preventing them from making a move) or by squashing them (causing the square the opponent is on to turn black).", i * 20, 16);
			g.drawString("If you become trapped by your own move, you lose a point.", i * 20, 36);
			g.drawString("If you trap or splatter your opponent, you earn a point.", i * 20, 56);
			g.setColor(Color.RED);
			g.drawString(getCode(), i * 20, 76);
			g.setColor(Color.BLACK);
			g.drawString("That code above is your save code. Press 'C' to copy it to your clipboard.", i * 20, 96);
			g.drawString("To load a game, press 'V' and paste in your code.", i * 20, 116);
		}
		
		repaint();
	}

	protected static void init() {
		Board = new int[i][j];
		Random random = new Random();
		character[0].setPos(0, 0);

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
		
		if (CURRENT_MODE == RACE || CURRENT_MODE == TRAPPED){
			character[1].setPos(i - 1, j - 1);
			Board[i-1][j-1] = 1;
			if (Board[i-1][j-2] == 0 && Board[i-2][j-1] == 0){
				Game.init();
			}
		}
	}

	protected static void updateCode(){
		String a = Integer.toString(i);
		String b = Integer.toString(j);
		String e = Integer.toString(character[0].getPosX());
		String f = Integer.toString(character[0].getPosY());
		String c = "";
		for (int x = 0; x < i; x++){
			for (int y = 0; y < j; y++){ 
				c += Board[x][y];
			}
		}

		BigInteger g = new BigInteger(c, 2);
		c = g.toString(36);
		code = "i:" + a + ",j:" + b + ",x:" + e + ",y:" + f + ",c:" + character[0].getColour() + ",b:" + c;
		
		if (CURRENT_MODE == TRAPPED || CURRENT_MODE == RACE){
			String x1 = Integer.toString(character[1].getPosX());
			String y1 = Integer.toString(character[1].getPosY());
			String s = Integer.toString(character[0].getScore());
			String s1 = Integer.toString(character[1].getScore());
			String c1 = character[1].getColour();
			
			code += ",x1:" + x1 + ",y1:" + y1 + ",s:" + s + ",s1:" + s1 + ",c1:" + c1;
		}
	}

	protected static String getCode(){
		return code;
	}
}
