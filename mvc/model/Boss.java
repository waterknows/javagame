package _08final.mvc.model;

import _08final.images.SpriteTexLoader;
import _08final.mvc.controller.Game;
import _08final.sounds.Sound;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

//This class defines boss ship

public class Boss extends Enemy {

    public Random random = new Random();

    public Boss() {
        this.mTex = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BOSS);
        life = 500;
    }


    private int xspeed = 6;
    private int yspeed = 3;

    @Override
    public void move(double deltaTimeInSecs) {} //more advanced movement


    public void autoMove() { //auto movement for boss
        if (this.x > Constant.FRAME_DIM.width - this.width || this.x < 0) {
            xspeed = 0 - xspeed;
        }
        if (this.y > 200 || this.y < 0) {
            yspeed = 0 - yspeed;
        }
        x = x + xspeed;
        y = y + yspeed;
    }

    @Override
    public Bullet[] shot() {

        Bullet bullet[] = new EnemyBullet[Constant.BOSS_NUMBER];
        for (int i = 0; i < Constant.BOSS_NUMBER; i++) {
            bullet[i] = new EnemyBullet();
            bullet[i].setX(x
                    + this.width
                    / (Constant.BOSS_NUMBER == 1 ? 2
                    : Constant.BOSS_NUMBER - 1) * i);
            bullet[i].setY(y + this.height);
            bullet[i].setWidth(5);
            bullet[i].setHeight(8);
            bullet[i].setHurt(Constant.EBULLTE_HURT);
        }
        return bullet;
    }

}
