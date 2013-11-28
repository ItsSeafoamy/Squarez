package uk.co.abyxstudioz.squarez;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

public class MenuListener implements MouseListener {

	public static Game game;
	
	@Override
	public void mouseClicked(MouseEvent event) {
		int x = event.getX();
		int y = event.getY();
		
		if (x >= (Squarez.frame.getWidth() / 2) - (Menu.menuSP.getWidth() * 1.5) && x <= (Squarez.frame.getWidth() / 2) - (Menu.menuSP.getWidth() * 0.5)){
			if (y >= (Squarez.frame.getHeight() / 2) - (Menu.menuSP.getHeight() / 2) && y <= (Squarez.frame.getHeight() / 2) + (Menu.menuSP.getHeight() / 2)){
				game = new Game(Game.SINGLE_PLAYER);
				Squarez.frame.add(game);
				Squarez.frame.remove(Squarez.menu);
				Squarez.frame.pack();
				Menu.ogg.stop();
			}
		} else if (x >= (Squarez.frame.getWidth() / 2) - (Menu.menuRace.getWidth() * 0.5) && x <= (Squarez.frame.getWidth() / 2) + (Menu.menuRace.getWidth() * 0.5)){
			if (y >= (Squarez.frame.getHeight() / 2) - (Menu.menuRace.getHeight() / 2) && y <= (Squarez.frame.getHeight() / 2) + (Menu.menuRace.getHeight() / 2)){
				game = new Game(Game.RACE);
				Squarez.frame.add(game);
				Squarez.frame.remove(Squarez.menu);
				Squarez.frame.pack();
				Menu.ogg.stop();
			}
		} else if (x >= (Squarez.frame.getWidth() / 2) + (Menu.menuTrapped.getWidth() * 0.5) && x <= (Squarez.frame.getWidth() / 2) + (Menu.menuTrapped.getWidth() * 1.5)){
			if (y >= (Squarez.frame.getHeight() / 2) - (Menu.menuTrapped.getHeight() / 2) && y <= (Squarez.frame.getHeight() / 2) + (Menu.menuTrapped.getHeight() / 2)){
				game = new Game(Game.TRAPPED);
				Squarez.frame.add(game);
				Squarez.frame.remove(Squarez.menu);
				Squarez.frame.pack();
				Menu.ogg.stop();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent event) {}

	@Override
	public void mouseExited(MouseEvent event) {}

	@Override
	public void mousePressed(MouseEvent event) {}

	@Override
	public void mouseReleased(MouseEvent event) {}

}
