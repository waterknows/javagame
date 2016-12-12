package _08final.mvc.controller;
import _08final.mvc.model.*;
import _08final.mvc.view.GameFrame;
import _08final.sounds.Sound;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.sql.Time;
import java.util.Random;
import java.util.Vector;


public class Game extends Component implements Runnable, KeyListener, MouseListener {

	//Represents the ship in the game
	public static Player player;
	// List of Sprites that need to be rendered
	public static Vector <Enemy> enemies = new Vector<Enemy>();
	public static Vector<PlayerBullet> pbullets = new Vector<PlayerBullet>();
	public static Vector<EnemyBullet> ebullets = new Vector<EnemyBullet>();
	public static Vector<Explode> explodes = new Vector<Explode>();
	public static Boss boss = null;
	public static Random random = new Random();
	public static int score;
	public static int life=3; //3 chances
	//The mode of the game
	public static int currentMode=0;
	public static byte flag = 1; //variable for blinking effect
	public static int level = 1; //initial level
	public static int totScores = 0;

	// Represents the Animation delay between frames
	private int DRAW_DELAY = 40;
	private boolean isFire = false;
	private boolean isStop = false;
	// Represents the JFrame for the game
	private GameFrame mGameFrame;
	// The thread that handles the render loop for the game
	private Thread mRenderThread;
	private Timer timer;
	private Timer timer2;
	private byte toggle = 1;
	private int xLeave;

	public Game() {
		this.mGameFrame = new GameFrame(this);
		//Initiate Player
		player = new Player();
		player.setWidth(80);
		player.setHeight(80);
		player.setX(Constant.FRAME_DIM.width / 2 - player.getWidth() / 2);
		player.setY(Constant.FRAME_DIM.height - 2 * player.getHeight());
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() { // uses the Event dispatch thread from Java 5 (refactored)
			public void run() {
				try {
					Game game = new Game(); // construct itself
					game.startRenderLoopThread();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Starts the thread that will handle the render loop for the game
	 */
	private void startRenderLoopThread() {
		//Check to make sure the render loop thread has not begun
		if (this.mRenderThread == null) {
			//All threads that are created in java need to be passed a Runnable object.
			//In this case we are making the "Runnable Object" the actual game instance.
			this.mRenderThread = new Thread(this);
			//Start the thread
			this.mRenderThread.start();
		}
	}

	public void run() {

		//Make this thread a low priority such that the main thread of the Event Dispatch is always
		//running first.
		this.mRenderThread.setPriority(Thread.MIN_PRIORITY);
		Sound.playSoundEffect(Sound.SoundEffect.THEME);

		while(currentMode == 0){
			this.mGameFrame.draw();
			//toggle the flag to make the text blinking
			flag = (byte)(flag ^ toggle);
			try{
				Thread.sleep(200);
			} catch (Exception e){
			}
		}

		while (currentMode==1){
			// and get the current time
			//Get the current time of rendering this frame
			long elapsedTime = System.currentTimeMillis();

			long currentTime;
			long lastTime = 0;
			long deltaTime;
			double deltaTimeInSecs;
			double timeInSecs = 0;

			Sound.playSoundEffect(Sound.SoundEffect.START);

			// Define Timer, let the enemies shoot at certain speed
			timer = new Timer(5000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (enemies.size()>0){
					enemyFire();}
				}
			});
			timer.start();


			timer2 = new Timer(2000, new ActionListener() { //boss fires faster
				@Override
				public void actionPerformed(ActionEvent g) {
					bossFire();
				}
			});
			timer2.start();
			//
			// this thread animates the scene
			int n = 0;
			while (currentMode==1 & Thread.currentThread() == this.mRenderThread) {
				n=n+1; //loop number
				currentTime = System.currentTimeMillis();

				if(lastTime == 0){
					lastTime = currentTime;
					deltaTime = 0;
				}else {
				deltaTime = currentTime - lastTime;
				lastTime = currentTime;
				}
				final double MILS_TO_SECS = 0.001f;
				deltaTimeInSecs = deltaTime * MILS_TO_SECS; //use delta time to smooth animation
				timeInSecs += deltaTimeInSecs; //accumulate delta time

				// add Enemy
				addEnemy();
				// player move
				player.control();
				// player fire
				if (isFire) {
					Bullet[] bs = player.shot();
					for (int i = 0; i < bs.length; i++) {
						pbullets.add((PlayerBullet) bs[i]);
						//Sound.playSoundEffect(Sound.SoundEffect.FIRE_BULLET);
						Sound.playSoundEffect(Sound.SoundEffect.SHOT);
					}
				}
				if (enemies.size()>0) {
					enemyMove(deltaTimeInSecs);
					enemies.firstElement().leave(deltaTimeInSecs);
					collision();
					enemyGetShot();
					enemyDown();
					removeEnemy();
				}
				playerGetShot();
				playerDown();

				if (boss != null) {
					boss.autoMove();
					collisionBoss();
					bossGetShot();
				}
				// remove player's bullet
				removePBullet();
				//remove enemies' bullet
				removeEBullet();
				showBoss();

				//paint
				this.mGameFrame.draw();

				try {
					/** We want to ensure that the drawing time is at least the DRAW_DELAY we specified. */
					elapsedTime += DRAW_DELAY;
					Thread.sleep(Math.max(0,elapsedTime - currentTime));
				} catch (InterruptedException e) {
					continue;
				}
			} // end while
		}
		while(currentMode==2)
		{
			this.mGameFrame.draw();
			//toggle the flag to make the text blinking
			flag = (byte)(flag ^ toggle);
			try{
				Thread.sleep(150);
			} catch (Exception e){

			}

		}
		while(currentMode==3)
		{
			this.mGameFrame.draw();
			//toggle the flag to make the text blinking
			flag = (byte)(flag ^ toggle);
			try{
				Thread.sleep(200);
			} catch (Exception e){
			}
		}
	} // end run

	//=========================key listeners & mouth listeners=========================
	@Override
	public void keyPressed(KeyEvent e) {
		switch(currentMode){
			case 0: //main menu
				if(e.getKeyCode() == KeyEvent.VK_SPACE){
					currentMode = 1;
				}
				if(e.getKeyCode() == KeyEvent.VK_S){
					currentMode = 3;
				}
				if(e.getKeyCode() == KeyEvent.VK_X){
					System.exit(0);
				}
				break;
			case 1: //game
				int keycode = e.getKeyCode();
				switch (keycode) {
					case KeyEvent.VK_UP:
						Player.up = true;
						break;
					case KeyEvent.VK_LEFT:
						Player.left = true;
						break;
					case KeyEvent.VK_RIGHT:
						Player.right = true;
						break;
					case KeyEvent.VK_DOWN:
						Player.down = true;
						break;
					case KeyEvent.VK_F:
						isFire = true;
						break;
					default:
						break;
				}
				break;
			case 2: //restart
				if(e.getKeyCode() == KeyEvent.VK_SPACE){
					currentMode = 0;
					reset(); //reset the game
					totScores = 0;	//reset total scores
					life = 3;  //reset life
					//restart the game completely
					try {
						Game game = new Game();
						game.startRenderLoopThread();
					} catch (Exception x) {
						x.printStackTrace();
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_X){
					System.exit(0);
				}
				break;
			case 3: //score history
				if(e.getKeyCode() == KeyEvent.VK_SPACE){
					currentMode = 0;
					try {
						Game game = new Game();
						game.startRenderLoopThread();
					} catch (Exception x) {
						x.printStackTrace();
					}
				}
				break;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		int keycode = e.getKeyCode();
		switch (keycode) {
			case KeyEvent.VK_UP:
				Player.up = false;
				break;
			case KeyEvent.VK_LEFT:
				Player.left = false;
				break;
			case KeyEvent.VK_RIGHT:
				Player.right = false;
				break;
			case KeyEvent.VK_DOWN:
				Player.down = false;
				break;
			case KeyEvent.VK_F:
				isFire = false;
				break;
			default:
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		isStop = true;
		JOptionPane.showConfirmDialog(this, "Use arrow keys to move, Use F to fire!", "Instruction",
				JOptionPane.YES_OPTION);
		isStop = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	//=======================Methods============================

	// Add Enemies, when boss appear, no enemy

	public void addEnemy() {
		if (enemies.size()==0 && boss==null) {
			for (int i = 0; i <= 1; i++) {
				for (int j = 0; j <= 3+score/50; j++) { //more enemies if score>50n
					Enemy e01 = new Enemy();
					e01.setX(250-(score/50)*100 + j * 200);
					e01.setY(i * 100);
					e01.setHeight(30);
					e01.setWidth(30);
					enemies.add(e01);
				}
			}
		}
	}


	// if enemy crash on player, player minus 50 hp
	public void enemyMove(double deltaTimeInSecs) {
		//int m=random.nextInt(enemies.size());
		//enemies.elementAt(m).move(deltaTimeInSecs);
		for (int i = 0; i < enemies.size(); i++) {
			enemies.elementAt(i).move(deltaTimeInSecs);
		}
	}

	public void collision(){
		for (int i = 0; i < enemies.size(); i++) {
			if (CollisionCheck.C3(player.x, player.y + 20, 80, 60, enemies.elementAt(i))) {
				Explode a = new Explode();
				Sound.playSoundEffect(Sound.SoundEffect.BANG);
				a.setX(enemies.elementAt(i).getX());
				a.setY(enemies.elementAt(i).getY());
				explodes.add(a);
				player.setLife(player.getLife() - 50);
				enemies.remove(i);
			}
		}
	}


	public void collisionBoss(){
		if (CollisionCheck.C3(player.x, player.y + 20, 80, 60, boss)) {
			Explode a = new Explode();
			Sound.playSoundEffect(Sound.SoundEffect.BANG);
			a.setX(boss.getX());
			a.setY(boss.getY());
			explodes.add(a);
			player.setLife(player.getLife() - 50);
		}
	}

	//player loss health points if get shot
	public void playerGetShot() {
		for (int i = 0; i < ebullets.size(); i++) {
			ebullets.elementAt(i).move();
			if (CollisionCheck.C1(ebullets.elementAt(i), player)
					&& (!ebullets.elementAt(i).isUsed())) {
				Explode b = new Explode();
				Sound.playSoundEffect(Sound.SoundEffect.BANG);
				b.setX(ebullets.elementAt(i).getX());
				b.setY(ebullets.elementAt(i).getY());
				explodes.add(b);

				player.setLife(player.getLife()
						- ebullets.elementAt(i).getHurt());
				ebullets.elementAt(i).setUsed(true);
			}

		}
	}
	
	// judge if the player's bullets hit the enemies. Enemies lost health points if get shot,
	// Explode if health = 0, and the player will get 10 points */
	public void enemyGetShot() {
		for (int i = 0; i < pbullets.size(); i++) {
			for (int j = 0; j < enemies.size(); j++) {
				if (CollisionCheck.C1(pbullets.elementAt(i), enemies.elementAt(j))
						&& !pbullets.elementAt(i).isUsed()) {
					enemies.elementAt(j).setLife(enemies.elementAt(j).getLife()
									- pbullets.elementAt(i).getHurt()); 
					pbullets.elementAt(i).setUsed(true); //remove bullets
					if (enemies.elementAt(j).getLife() <= 0) {
						//
						explodes.add(enemies.elementAt(j).getBang()); //add explosion
						Sound.playSoundEffect(Sound.SoundEffect.KILL);
						enemies.remove(j); // delete enemies
						score += 10;
					}
				}
			}
		}
	}

	public void bossGetShot()
	{
		for (int i = 0; i < pbullets.size(); i++) {
			// if the boss appear, judge if the player hit the boss
			if (boss != null) {
				if (CollisionCheck.Testing(pbullets.elementAt(i), boss)
						&& !pbullets.elementAt(i).isUsed()) {
					boss.setLife(boss.getLife()
							- pbullets.elementAt(i).getHurt());
					pbullets.elementAt(i).setUsed(true);
					if (boss.getLife() <= 0) {
						boss.setLife(0);
						score += 100; //boss worth 100 points
						ScoreManager scoreManager = new ScoreManager(); //record score
						scoreManager.addScore(Game.score);
						currentMode = 2;

					}
				}
			}
		}
	}

	// delete dead
	public void enemyDown() {
		for (int i = 0; i < explodes.size(); i++) {
			if (explodes.elementAt(i).isExplode()) { // Delete after explosion
				explodes.remove(i);
			}
		}
	}
	// judge outcome
	public void playerDown() {
		if (player.getLife() <= 0) {
			ScoreManager scoreManager = new ScoreManager(); //record score
			scoreManager.addScore(Game.score);
			totScores=+Game.score;
			if (life>1)
			{
				life=life-1;
				reset();
			}
			else {
				currentMode = 2;
			}
		}
	}
	public void removeEnemy(){
		for(int j = 0;j<enemies.size();j++){
			if (enemies.elementAt(j).getY() >= Constant.FRAME_DIM.height-30) {//
				enemies.remove(j);
			}
		}
	}

	// remove the bullet out of frame
	public void removeEBullet() {
		for (int i = 0; i < ebullets.size(); i++) {
			if (ebullets.elementAt(i).getY() > Constant.FRAME_DIM.height
					|| ebullets.elementAt(i).isUsed) {
				ebullets.remove(i);
			}
		}
	}
	// Player bullet move
	public void removePBullet() {
		for (int i = 0; i < pbullets.size(); i++) {
			pbullets.elementAt(i).move();
			if (pbullets.elementAt(i).getY() <= 0) { // Delete if out of frame
				pbullets.remove(i);
			}
		}
	}

	// boss appears if score>50
	public void showBoss() {
		if (score>=100 && boss == null) {
			boss = new Boss();
			Sound.playSoundEffect(Sound.SoundEffect.WARN);
			boss.setX(200);
			boss.setY(20);
			boss.setWidth(100);
			boss.setHeight(100);
			level=2; //set level to 2 when boss appears
		}
	}
	// boss shot
	public void bossFire() {
		if (boss != null) {
			EnemyBullet[] bs = (EnemyBullet[]) boss.shot();
			for (int i = 0; i < bs.length; i++) {
				ebullets.add(bs[i]);
			}
		}
	}

	// enemy fire
	public void enemyFire() {
		for (int i = 0; i < enemies.size(); i++) {
			EnemyBullet[] es = (EnemyBullet[]) enemies.elementAt(i).shot();
			for (int j = 0; j < es.length; j++) {
				ebullets.add(es[j]);
			}
		}
	}


	// reset game
	public void reset() {
		ebullets.clear();
		pbullets.clear();
		enemies.clear();
		player.setX(Constant.FRAME_DIM.width / 2 - player.getWidth() / 2);
		player.setY(Constant.FRAME_DIM.height - 2 * player.getHeight());
		player.setLife(100);
		score = 0;
		level = 1;
		Player.up = false;
		Player.down = false;
		Player.left = false;
		Player.right = false;
		isFire = false;
		boss = null;
	}

}