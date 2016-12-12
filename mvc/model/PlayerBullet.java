package _08final.mvc.model;

import _08final.images.SpriteTexLoader;

import javax.swing.*;
import java.awt.*;

public class PlayerBullet extends Bullet {

    public PlayerBullet() {
        this.mTex = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BULLET);
    }

    public PlayerBullet(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    @Override
    public void move() {
        this.y = this.y - Constant.PBULLTE_SPEED;
    }

}
