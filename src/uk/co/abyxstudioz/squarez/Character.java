package uk.co.abyxstudioz.squarez;

import javax.swing.JOptionPane;

public class Character {

	private int posX = 0;
	private int posY = 0;

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	public void setPos(int posX, int posY){
		this.posX = posX;
		this.posY = posY;
	}
	
	public void move(int relativePosX, int relativePosY){
		if (this.posX + relativePosX < 0 || this.posY + relativePosY < 0){
			return;
		}
		if (this.posX + relativePosX >= Game.i || this.posY + relativePosY >= Game.j){
			return;
		}
		if (Game.Board[posX + relativePosX][posY + relativePosY] != 1){
			return;
		}
		this.posX += relativePosX;
		this.posY += relativePosY;
		
		boolean trapped = true;
		
		try{
			if (Game.Board[posX + 1][posY] == 0){
				Game.Board[posX + 1][posY] = 1;
				trapped = false;
			} else if (Game.Board[posX + 1][posY] == 1){
				Game.Board[posX + 1][posY] = 0;
			}
		} catch (Exception e){
			
		}
		try{
			if (Game.Board[posX - 1][posY] == 0){
				Game.Board[posX - 1][posY] = 1;
				trapped = false;
			} else if (Game.Board[posX - 1][posY] == 1){
				Game.Board[posX - 1][posY] = 0;
			}
		} catch (Exception e){
			
		}
		try{
			if (Game.Board[posX][posY + 1] == 0){
				Game.Board[posX][posY + 1] = 1;
				trapped = false;
			} else if (Game.Board[posX][posY + 1] == 1){
				Game.Board[posX][posY + 1] = 0;
			}
		} catch (Exception e){
			
		}
		try{
			if (Game.Board[posX][posY - 1] == 0){
				Game.Board[posX][posY - 1] = 1;
				trapped = false;
			} else if (Game.Board[posX][posY - 1] == 1){
				Game.Board[posX][posY - 1] = 0;
			}
		} catch (Exception e){
			
		}
		
		if (this.getPosX() == Game.i - 1&& this.getPosY() == Game.j - 1){
			this.setPos(0, 0);
			Game.i++;
			Game.j++;
			Game.init();
		}
		
		else if (trapped){
	        JOptionPane.showMessageDialog(null, "You can't make any more moves :(", "Squarez", JOptionPane.INFORMATION_MESSAGE);
			this.setPos(0, 0);
			Game.init();
		}
	}	
}
