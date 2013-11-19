package uk.co.abyxstudioz.squarez;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigInteger;
import javax.swing.JOptionPane;

public class Listener implements KeyListener {

	public Listener(){
		Squarez.frame.addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP){
			Game.getCharacter().move(0, -1);
		}
		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT){
			Game.getCharacter().move(-1, 0);
		}
		if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN){
			Game.getCharacter().move(0, 1);
		}
		if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT){
			Game.getCharacter().move(1, 0);
		}
		if (e.getKeyCode() == KeyEvent.VK_R){
			Game.init();
		}
		if (e.getKeyCode() == KeyEvent.VK_C){
			StringSelection stringSelection = new StringSelection (Game.getCode());
			Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
			clpbrd.setContents (stringSelection, null);
			JOptionPane.showMessageDialog(null, "Code copied to clipboard.", "Squarez", JOptionPane.INFORMATION_MESSAGE);
		}
		if (e.getKeyCode() == KeyEvent.VK_V){
			String newCode = JOptionPane.showInputDialog(null, "Paste in your code.", "Squarez", JOptionPane.INFORMATION_MESSAGE);
			if (newCode == null){
				return;
			}
			if (newCode.length() != 1048 && newCode.split(",").length != 5){
				JOptionPane.showMessageDialog(null, "Error: Invalid Code.", "Squarez", JOptionPane.ERROR_MESSAGE);
			} else if (newCode.length() == 1048){
				String i = newCode.substring(0, 6);
				String j = newCode.substring(6, 12);
				String pX = newCode.substring(12, 18);
				String pY = newCode.substring(18, 24);
				String b = newCode.substring(24, 1048);

				Game.i = Integer.parseInt(i, 2);
				Game.j = Integer.parseInt(j, 2);
				Game.character.setPosX(Integer.parseInt(pX, 2));
				Game.character.setPosY(Integer.parseInt(pY, 2));

				int pos = 1023;

				Game.Board = new int[Game.i][Game.j];
				for (int x = 1; x <= Game.i; x++){
					for (int y = 1; y <= Game.j; y++){
						Game.Board[Game.i - x][Game.j - y] = Integer.parseInt(b.substring(pos, pos + 1));
						pos--;
					}
				}
				Squarez.frame.setTitle(Squarez.title + " - " + Squarez.version + " - " + Game.i + "x" + Game.j);
				JOptionPane.showMessageDialog(null, "The game was successfully loaded, however, the code you provided is for V0.4. It is highly recommended that you save the game and obtain the V0.5 code.", "Squarez", JOptionPane.INFORMATION_MESSAGE);
			} else {
				try {
					String[] code = newCode.split(",");
					
					Game.i = Integer.parseInt(code[0]);
					Game.j = Integer.parseInt(code[1]);
					Game.character.setPos(Integer.parseInt(code[2]), Integer.parseInt(code[3]));
					
					BigInteger bi = new BigInteger(code[4], 36);
					String b = bi.toString(2);
					
					while (b.length() < Game.i * Game.j){
						b = "0" + b;
					}
					
					int pos = 0;
					
					Game.Board = new int[Game.i][Game.j];
					for (int x = 0; x < Game.i; x++){
						for (int y = 0; y < Game.j; y++){
							Game.Board[x][y] = Integer.parseInt(b.substring(pos, pos + 1));
							pos++;
						}
					}
					Squarez.frame.setTitle(Squarez.title + " - " + Squarez.version + " - " + Game.i + "x" + Game.j);
					JOptionPane.showMessageDialog(null, "Loaded Game.", "Squarez", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex){
					JOptionPane.showMessageDialog(null, "Error: Invalid Code.", "Squarez", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

}
