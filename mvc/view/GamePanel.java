package _08final.mvc.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

import _08final.images.SpriteTexLoader;
import _08final.mvc.controller.Game;
import _08final.mvc.model.*;

public class GamePanel extends JPanel{
	public Font font = new Font("Verdana", Font.BOLD, 18);
	public Random random = new Random();
	private int bossKill;
	private BufferedImage backgroundImg =  SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BACKGROUND);
	private BufferedImage gameoverImg =  SpriteTexLoader.load(SpriteTexLoader.SpriteTex.GAMEOVER);
	private BufferedImage spaceImg =  SpriteTexLoader.load(SpriteTexLoader.SpriteTex.SPACE);
	private BufferedImage scoreImg =  SpriteTexLoader.load(SpriteTexLoader.SpriteTex.SCORETABLE);
	private BufferedImage playerImg = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.PLAYER);

	public GamePanel() {
		this.setPreferredSize(Constant.FRAME_DIM);
		this.setBackground(Color.BLACK);
	}

	@Override

	public void paint(Graphics g) {
		super.paint(g);
		switch(Game.currentMode){
			case 0:
				g.drawImage(backgroundImg, 0, 0, this.getWidth(), this.getHeight(), this);
				g.setColor(Color.GREEN);
				g.setFont(new Font("Consolas", Font.BOLD, 35));
				if(Game.flag == 1){
				g.drawString("[ PRESS SPACE KEY TO START ]",250,700);}
				g.drawString("[ PRESS 'S' TO CHECK SCORES ]",250,750);
				g.drawString("[ PRESS 'X' TO EXIT ]",250,800);
				break;
			case 1:

				g.drawImage(spaceImg, 0, 0, this.getWidth(), this.getHeight(), this);
				//paint the information of player
				drawInfo(g);
				// paint player
				Game.player.draw(g);
				// paint player's bullet
				for (int i = 0; i < Game.pbullets.size(); i++) {
					if (!Game.pbullets.elementAt(i).isUsed()) {
						Game.pbullets.elementAt(i).setPanel(this);
						Game.pbullets.elementAt(i).draw(g);
					}
				}
				// Paint enemy
				for (int i = 0; i < Game.enemies.size(); i++) {
					Game.enemies.elementAt(i).setPanel(this);
					Game.enemies.elementAt(i).draw(g);
				}
				// Paint explosion
				for (int i = 0; i < Game.explodes.size(); i++) {
					Game.explodes.elementAt(i).setPanel(this);
					Game.explodes.elementAt(i).draw(g);
				}
				// Paint enemies' bullet
				for (int i = 0; i < Game.ebullets.size(); i++) {
					if (!Game.ebullets.elementAt(i).isUsed()) {
						Game.ebullets.elementAt(i).setPanel(this);
						Game.ebullets.elementAt(i).draw(g);
					}
				}
				// Paint boss
				if (Game.boss != null) {
					Game.boss.draw(g);
				}

				// Paint the information of boss
				drawBossInfo(g);
				break;
			case 2:
				ScoreManager sm2 = new ScoreManager();
				ArrayList<Score> scores2 = sm2.getScores();
				g.drawImage(gameoverImg, 0, 0, this.getWidth(), this.getHeight(), this);
				g.setFont(new Font("Consolas", Font.BOLD, 35));
				g.setColor(Color.RED);
				g.drawString("HIGEST SCORE: " + scores2.get(0).getScore() ,360,550);
				g.setColor(Color.YELLOW);
				g.drawString("YOUR AVERAGE SCORE: " + Game.totScores/3 ,360,600);
				g.setColor(Color.GREEN);
				if(Game.flag == 1){
					g.drawString("[ PRESS SPACE KEY TO RESTART ]",250,700);}
				g.drawString("[ PRESS 'X' TO EXIT ]",250,750);
				break;
			case 3:
				ScoreManager sm3 = new ScoreManager();
				ArrayList<Score> scores3 = sm3.getScores();
				g.drawImage(scoreImg, 0, 0, this.getWidth(), this.getHeight(), this);
				g.setColor(Color.YELLOW);
				g.setFont(new Font("Consolas", Font.BOLD, 40));
				g.drawString("TOP 10 SCORES" ,370,150);
				g.setFont(new Font("Consolas", Font.BOLD, 35));
				for (int i=0; i < scores3.size(); i++)
				{
					int num = scores3.get(i).getScore();
					g.drawString("("+(i+1) +") " + num,460, 220+50*i);
					if(i==9){break;} //only print top 10
				}
				g.setColor(Color.GREEN);
				if(Game.flag == 1){
					g.setFont(new Font("Consolas", Font.BOLD, 35));
					g.drawString("[ PRESS SPACE KEY TO RETURN ]",250,800);}
				break;
		} //end switch

	}

	//============================Methods==========================

	// Paint the information of players
	public void drawInfo(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(260, 820, Game.player.getLife(), 10);
		g.setFont(font);
		g.setColor(Color.YELLOW);
		g.drawString("HP: " + String.format("%d", Game.player.getLife()), 170, 830); //player health
		String info = "SCORE: " + Game.score + "";
		g.drawString(info, 20, 830);

		String level = "LEVEL: " + Game.level + "";  //level info
		g.drawString(level, 20, 860);
		if ((100-Game.score)/10>0)
		{bossKill = (100-Game.score)/10;}
		else
		{bossKill = 0;}
		g.setColor(Color.RED);
		g.drawString(""+bossKill+"",240, 860);
		g.setColor(Color.YELLOW);
		String boss = "BOSS:       MORE KILLS"; //boss info
		g.drawString(boss, 170, 860);
		g.setColor(Color.GREEN);
		g.drawString("MOVE: ARROW KEYS         FIRE: F", 20, 40); //instructions
		for (int i = 0; i < Game.life; i++)
		{g.drawImage(playerImg, 20+60*i, 50, this.getWidth()/25, this.getHeight()/25, this);}

	}

	// Paint the information of boss
	public void drawBossInfo(Graphics g) {
		if (Game.boss != null) {
			g.setColor(Color.red);
			g.fillRect(Constant.FRAME_DIM.width - 30, 30, 10, Game.boss.getLife()/2);
			g.drawString(String.format("%d", Game.boss.getLife()),
					Constant.FRAME_DIM.width - 40, 20);
		}
	}

	// This method draws some text to the middle of the screen before/after a game

}