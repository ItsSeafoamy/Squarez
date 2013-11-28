package uk.co.abyxstudioz.squarez;

import javax.swing.JOptionPane;

public class Character {

	private int posX = 0;
	private int posY = 0;

	private int R = 0;
	private int G = 255;
	private int B = 0;

	private int score = 0;

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

		if (Game.CURRENT_MODE == Game.SINGLE_PLAYER){
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
		} else if (Game.CURRENT_MODE == Game.RACE){
			if (this.getPosX() == Game.i - 1 && this.getPosY() == Game.j - 1 && Game.character[0] == this){
				Game.i++;
				Game.j++;
				score ++;
				Game.init();
				JOptionPane.showMessageDialog(null, "Player 1 wins the Round! \n Player 1 : " + score + " | " + Game.character[1].score + " : Player 2", "Squarez", JOptionPane.INFORMATION_MESSAGE);
			} else if (this.getPosX() == 0 && this.getPosY() == 0 && Game.character[1] == this){
				Game.i++;
				Game.j++;
				score++;
				Game.init();
				JOptionPane.showMessageDialog(null, "Player 2 wins the Round! \n Player 1 : " + Game.character[0].score + " | " + score + " : Player 2", "Squarez", JOptionPane.INFORMATION_MESSAGE);
			} else if (trapped && Game.character[0] == this){
				setPos(0, 0);
				Game.Board[0][0] = 1;
				Game.Board[0][1] = 1;
				Game.Board[1][0] = 1;
			} else if (trapped && Game.character[1] == this){
				setPos(Game.i - 1, Game.j - 1);
				Game.Board[Game.i - 1][Game.j - 1] = 1;
				Game.Board[Game.i - 2][Game.j - 1] = 1;
				Game.Board[Game.i - 1][Game.j - 2] = 1;
			} if (Game.Board[Game.character[0].getPosX()][Game.character[0].getPosY()] == 0){
				Game.character[0].setPos(0, 0);
				Game.Board[0][0] = 1;
				Game.Board[0][1] = 1;
				Game.Board[1][0] = 1;
			} if (Game.Board[Game.character[1].getPosX()][Game.character[1].getPosY()] == 0){
				Game.character[1].setPos(Game.i - 1, Game.j - 1);
				Game.Board[Game.i - 1][Game.j - 1] = 1;
				Game.Board[Game.i - 2][Game.j - 1] = 1;
				Game.Board[Game.i - 1][Game.j - 2] = 1;
			}
		} else if (Game.CURRENT_MODE == Game.TRAPPED){
			if (trapped && Game.character[0] == this){
				Game.character[0].score -= 1;
				Game.i++;
				Game.j++;
				Game.init();
				JOptionPane.showMessageDialog(null, "Player 1 was trapped by his own move!\nPlayer 1 loses 1 point\nPlayer 1: " + Game.character[0].getScore() + " | " + Game.character[1].getScore() + " :Player 2", "Squarez", JOptionPane.INFORMATION_MESSAGE);
			} else if (trapped && Game.character[1] == this){
				Game.character[1].score -= 1;
				Game.i++;
				Game.j++;
				Game.init();
				JOptionPane.showMessageDialog(null, "Player 2 was trapped by his own move!\nPlayer 2 loses 1 point\nPlayer 1: " + Game.character[0].getScore() + " | " + Game.character[1].getScore() + " :Player 2", "Squarez", JOptionPane.INFORMATION_MESSAGE);
			} else if (Game.character[0].isTrapped()){
				Game.character[1].score += 1;
				Game.i++;
				Game.j++;
				Game.init();
				JOptionPane.showMessageDialog(null, "Player 2 has trapped Player 1!\nPlayer 2 gains 1 point\nPlayer 1: " + Game.character[0].getScore() + " | " + Game.character[1].getScore() + " :Player 2", "Squarez", JOptionPane.INFORMATION_MESSAGE);
			} else if (Game.character[1].isTrapped()){
				Game.character[0].score += 1;
				Game.i++;
				Game.j++;
				Game.init();
				JOptionPane.showMessageDialog(null, "Player 1 has trapped Player 2!\nPlayer 1 gains 1 point\nPlayer 1: " + Game.character[0].getScore() + " | " + Game.character[1].getScore() + " :Player 2", "Squarez", JOptionPane.INFORMATION_MESSAGE);
			} else if (Game.Board[Game.character[0].getPosX()][Game.character[0].getPosY()] == 0){
				Game.character[1].score += 1;
				Game.i++;
				Game.j++;
				Game.init();
				JOptionPane.showMessageDialog(null, "Player 2 has splattered Player 1!\nPlayer 2 gains 1 point\nPlayer 1: " + Game.character[0].getScore() + " | " + Game.character[1].getScore() + " :Player 2", "Squarez", JOptionPane.INFORMATION_MESSAGE);
			} else if (Game.Board[Game.character[1].getPosX()][Game.character[1].getPosY()] == 0){
				Game.character[0].score += 1;
				Game.i++;
				Game.j++;
				Game.init();
				JOptionPane.showMessageDialog(null, "Player 1 has splattered Player 2!\nPlayer 2 gains 1 point\nPlayer 1: " + Game.character[0].getScore() + " | " + Game.character[1].getScore() + " :Player 2", "Squarez", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public void setColour(String colour) {
		R = Integer.parseInt(colour.substring(0, 3));
		G = Integer.parseInt(colour.substring(3, 6));
		B = Integer.parseInt(colour.substring(6, 9));
	}

	public int getR() {
		return R;
	}

	public int getG() {
		return G;
	}

	public int getB() {
		return B;
	}

	public String getColour() {
		String r = Integer.toString(R);
		String g = Integer.toString(G);
		String b = Integer.toString(B);

		while (r.length() < 3){
			r = "0" + r;
		}
		while (g.length() < 3){
			g = "0" + g;
		}
		while (b.length() < 3){
			b = "0" + b;
		}

		String colour = r + g + b;
		return colour;
	}

	public int getScore(){
		return score;
	}

	public void setScore(int score){
		this.score = score;
	}

	public boolean isTrapped(){
		boolean trapped = true;

		try{
			if (Game.Board[getPosX()][getPosY() - 1] == 1){
				trapped = false;
			}
		}catch (Exception e){}

		try{
			if (Game.Board[getPosX()][getPosY() + 1] == 1){
				trapped = false;
			}
		}catch (Exception e){}

		try{
			if (Game.Board[getPosX() - 1][getPosY()] == 1){
				trapped = false;
			}
		}catch (Exception e){}

		try{
			if (Game.Board[getPosX() + 1][getPosY()] == 1){
				trapped = false;
			}
		}catch (Exception e){}
		return trapped;
	}
}
