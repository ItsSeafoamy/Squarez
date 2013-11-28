package uk.co.abyxstudioz.squarez;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigInteger;
import java.util.Random;
import javax.swing.JOptionPane;

public class SPListener implements KeyListener {

	Random random = new Random();

	public SPListener(){
		Squarez.frame.addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP){
			Game.character[0].move(0, -1);
		}
		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT){
			Game.character[0].move(-1, 0);
		}
		if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN){
			Game.character[0].move(0, 1);
		}
		if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT){
			Game.character[0].move(1, 0);
		}
		if (e.getKeyCode() == KeyEvent.VK_R){
			Game.init();
		}
		if (e.getKeyCode() == KeyEvent.VK_C){
			StringSelection stringSelection = new StringSelection(Game.getCode());
			Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
			clpbrd.setContents (stringSelection, null);
			JOptionPane.showMessageDialog(null, "Code copied to clipboard.", "Squarez", JOptionPane.INFORMATION_MESSAGE);
		}
		if (e.getKeyCode() == KeyEvent.VK_V){
			String newCode = JOptionPane.showInputDialog(null, "Paste in your code.", "Squarez", JOptionPane.INFORMATION_MESSAGE);
			if (newCode == null){
				return;
			}
			if (newCode.length() != 1048 && newCode.split(",").length != 5 && !newCode.contains(":")){
				JOptionPane.showMessageDialog(null, "Error: Invalid Code.", "Squarez", JOptionPane.ERROR_MESSAGE);
			} else if (newCode.length() == 1048){
				String i = newCode.substring(0, 6);
				String j = newCode.substring(6, 12);
				String pX = newCode.substring(12, 18);
				String pY = newCode.substring(18, 24);
				String b = newCode.substring(24, 1048);

				Game.i = Integer.parseInt(i, 2);
				Game.j = Integer.parseInt(j, 2);
				Game.character[0].setPosX(Integer.parseInt(pX, 2));
				Game.character[0].setPosY(Integer.parseInt(pY, 2));

				int pos = 1023;

				Game.Board = new int[Game.i][Game.j];
				for (int x = 1; x <= Game.i; x++){
					for (int y = 1; y <= Game.j; y++){
						Game.Board[Game.i - x][Game.j - y] = Integer.parseInt(b.substring(pos, pos + 1));
						pos--;
					}
				}

				Squarez.splash = Squarez.splashes.get(random.nextInt(Squarez.splashes.size()));
				Squarez.frame.setTitle(Squarez.title + " - " + Squarez.version + " - " + Game.i + "x" + Game.j + " - " + Squarez.splash);

				JOptionPane.showMessageDialog(null, "The game was successfully loaded, however, the code you provided is for V0.4. It is highly recommended that you save the game and obtain the V0.5 code.", "Squarez", JOptionPane.INFORMATION_MESSAGE);
			} else if (newCode.contains(":")){
				String[] code = newCode.split(",");
				
				int i = Game.i;
				int j = Game.j;
				int x = Game.character[0].getPosX();
				int y = Game.character[0].getPosY();
				String co = Game.character[0].getColour();
				int[][] b = new int[][]{{-1}};
				
				for (String c : code){
					if (c.startsWith("i:")){
						try {
							int k = Integer.parseInt(c.split("i:")[1]);
							if (k >= 2){
								i = k;
							} else {
								JOptionPane.showMessageDialog(null, "Value for i was invalid. Skipping.", "Squarez", JOptionPane.WARNING_MESSAGE);
							}
						}catch (Exception ex){
							JOptionPane.showMessageDialog(null, "Value for i was invalid. Skipping.", "Squarez", JOptionPane.WARNING_MESSAGE);
						}
						continue;
					} if (c.startsWith("j:")){
						try {
							int l = Integer.parseInt(c.split("j:")[1]);
							if (l >= 2){
								j = l;
							} else {
								JOptionPane.showMessageDialog(null, "Value for j was invalid. Skipping.", "Squarez", JOptionPane.WARNING_MESSAGE);
							}
						}catch (Exception ex){
							JOptionPane.showMessageDialog(null, "Value for j was invalid. Skipping.", "Squarez", JOptionPane.WARNING_MESSAGE);
						}
						continue;
					} if (c.startsWith("x:")){
						try {
							x = Integer.parseInt(c.split("x:")[1]);
						}catch (Exception ex){
							JOptionPane.showMessageDialog(null, "Value for x was invalid. Skipping.", "Squarez", JOptionPane.WARNING_MESSAGE);
						}
						continue;
					} if (c.startsWith("y:")){
						try {
							y = Integer.parseInt(c.split("y:")[1]);
						}catch (Exception ex){
							JOptionPane.showMessageDialog(null, "Value for y was invalid. Skipping.", "Squarez", JOptionPane.WARNING_MESSAGE);
						}
						continue;
					} if (c.startsWith("c:")){
						if (c.equalsIgnoreCase("c:r")){
							Random random = new Random();
							String r = Integer.toString(random.nextInt(256));
							String g = Integer.toString(random.nextInt(256));
							String bl = Integer.toString(random.nextInt(256));
							
							while (r.length() < 3){
								r = "0" + r;
							}
							while (g.length() < 3){
								g = "0" + g;
							}
							while (bl.length() < 3){
								bl = "0" + bl;
							}
							co = r + g + bl;
							continue;
						}
						try {
							String col = c.split("c:")[1];
							if (col.length() != 9){
								JOptionPane.showMessageDialog(null, "Value for c was invalid. Skipping.", "Squarez", JOptionPane.WARNING_MESSAGE);
							} else {
								int r = Integer.parseInt(col.substring(0, 3));
								int g = Integer.parseInt(col.substring(3, 6));
								int bl = Integer.parseInt(col.substring(6, 9));
								if (r < 0 || r > 255 || g < 0 || g > 255 || bl < 0 || bl > 255){
									JOptionPane.showMessageDialog(null, "Value for c was invalid. Skipping.", "Squarez", JOptionPane.WARNING_MESSAGE);
								} else {
									co = col;
								}
							}
						}catch (Exception ex){
							JOptionPane.showMessageDialog(null, "Value for c was invalid. Skipping.", "Squarez", JOptionPane.WARNING_MESSAGE);
						}
						continue;
					} if (c.startsWith("b:")){
						try {
							BigInteger bi = new BigInteger(c.split("b:")[1], 36);
							String big = bi.toString(2);

							while (big.length() < i * j){
								big = "0" + big;
							}

							int pos = 0;

							b = new int[i][j];
							for (int k = 0; k < i; k++){
								for (int l = 0; l < j; l++){
									b[k][l] = Integer.parseInt(big.substring(pos, pos + 1));
									pos++;
								}
							}
						}catch (Exception ex){
							JOptionPane.showMessageDialog(null, "Value for b was invalid. Skipping.", "Squarez", JOptionPane.WARNING_MESSAGE);
						}
						continue;
					}
				}
				
				Game.i = i;
				Game.j = j;
				Game.character[0].setPos(x, y);
				Game.character[0].setColour(co);
				
				if (b[0][0] == -1){
					Game.Board = new int[Game.i][Game.j];
					Random random = new Random();
					
					for (int k = 0; k < Game.i; k++){
						for (int l = 0; l < Game.j; l++){
							Game.Board[k][l] = random.nextInt(2);
						}
					}
				} else {
					Game.Board = b;
				}
				Squarez.splash = Squarez.splashes.get(random.nextInt(Squarez.splashes.size()));
				Squarez.frame.setTitle(Squarez.title + " - " + Squarez.version + " - " + Game.i + "x" + Game.j + " - " + Squarez.splash);
				
				JOptionPane.showMessageDialog(null, "Loaded Game.", "Squarez", JOptionPane.INFORMATION_MESSAGE);
			} else {
				try {
					String[] code = newCode.split(",");

					Game.i = Integer.parseInt(code[0]);
					Game.j = Integer.parseInt(code[1]);
					Game.character[0].setPos(Integer.parseInt(code[2]), Integer.parseInt(code[3]));

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
					Squarez.splash = Squarez.splashes.get(random.nextInt(Squarez.splashes.size()));
					Squarez.frame.setTitle(Squarez.title + " - " + Squarez.version + " - " + Game.i + "x" + Game.j + " - " + Squarez.splash);

					JOptionPane.showMessageDialog(null, "The game was successfully loaded, however, the code you provided is for V0.5/V0.6.x. It is highly recommended that you save the game and obtain the V0.7 code.", "Squarez", JOptionPane.WARNING_MESSAGE);
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
