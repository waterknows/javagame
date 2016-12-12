package _08final.mvc.model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public abstract class Sprite {
	/** The position of the sprite */
	public int x;
	public int y;
	/** The dimensions of the sprite */
	public int width;
	public int height;
	/** The texture for the sprite */
	public BufferedImage mTex;
	public JPanel panel;

	public Sprite() {}

	public Sprite(int x, int y, int width, int height, BufferedImage texture) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.mTex = texture;
	}

	public void draw(Graphics g) {
		g.drawImage(mTex, x, y, width, height, panel);
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Image getTex() {
		return mTex;
	}

	public void setImg(BufferedImage texture) {
		this.mTex = texture;
	}

}
