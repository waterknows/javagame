package _08final.mvc.model;

import _08final.images.SpriteTexLoader;
import _08final.mvc.controller.Game;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.sin;

//This class defines enemy ship

public class Enemy extends Ship {
    private int xspeed = 100;
    private int yspeed = 120;
    private int xspeed2 = -150;
    private int yspeed2 = 50;


    public Enemy() {
        this.mTex = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.ENEMY);
        life = 1;
    }

    @Override
    public void move(double deltaTimeInSecs) //time independent movement
    {
        if (y<200){
            y = y + (int) (yspeed * deltaTimeInSecs);
        }

        if (y>=200)
        {
            if (this.x > Constant.FRAME_DIM.width - this.width || this.x < 0) {
                xspeed = -xspeed;
            }
            if (this.y > 500 || this.y < 200) {
                yspeed2 = - yspeed2;
            }
            x = x + (int) (xspeed * deltaTimeInSecs);
            y = y + (int) (yspeed2 * deltaTimeInSecs);
        }

    }

    public void leave(double deltaTimeInSecs) //describe how certain ships leave the screen
    {
        if (y>=200) {
            if (this.x > Constant.FRAME_DIM.width - this.width || this.x < 0) {
                xspeed2 = -xspeed2;
            }
            x = x + (int) (xspeed2 * deltaTimeInSecs);
            y = y + (int) (yspeed * deltaTimeInSecs);
        }
    }


    @Override
    public Bullet[] shot() {
        Bullet bullet[] = new EnemyBullet[Constant.EBULLTE_NUMBER];
        for (int i = 0; i < Constant.EBULLTE_NUMBER; i++) {
            bullet[i] = new EnemyBullet();
            bullet[i].setX(x
                    + this.width
                    / (Constant.EBULLTE_NUMBER == 1 ? 2
                    : Constant.EBULLTE_NUMBER - 1) * i);
            if (Constant.EBULLTE_NUMBER == 1) {
                bullet[i].setX(x
                        + this.width / 2 - 2);
            }
            bullet[i].setY(y + height);
            bullet[i].setWidth(5);
            bullet[i].setHeight(10);
            bullet[i].setHurt(Constant.EBULLTE_HURT);
        }
        return bullet;
    }

    public Explode getBang() {
        Explode explode = new Explode();
        explode.setX(x);
        explode.setY(y);
        explode.setWidth(width);
        explode.setHeight(height);
        return explode;
    }
}