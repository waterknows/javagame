package _08final.mvc.model;

import _08final.images.SpriteTexLoader;

import javax.swing.*;
import java.awt.*;

//This class defines player ship

public class Player extends Ship {

    public static boolean up, down, left, right; // The player's direction

    public void setLife(int life) {
        if (life <= 0)
            life = 0;
        if (life >= 100)
            life = 100;
        this.life = life;
    }

    public Player() {
        this.mTex = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.PLAYER);
        life = 100;
    }

    @Override
    public void move(double deltaTimeInSecs){} //not using dealta time for player, make control unsmooth

    public void control() {
        if (Player.up) {
            if (y - Constant.PLAYER_SPEED >= 0) {
                y -= Constant.PLAYER_SPEED;
            }
        }
        if (Player.down) {
            if (y + Constant.PLAYER_SPEED <= Constant.FRAME_DIM.height - 100) {
                y += Constant.PLAYER_SPEED;
            }
        }
        if (Player.right) {
            if (x + Constant.PLAYER_SPEED <= Constant.FRAME_DIM.width - 80)
                x += Constant.PLAYER_SPEED;
        }
        if (Player.left) {
            if (x - Constant.PLAYER_SPEED >= 0)
                x -= Constant.PLAYER_SPEED;
        }
    }

    @Override
    public Bullet[] shot() {

        Bullet bullet[] = new PlayerBullet[Constant.PBULLTE_NUMBER];
        for (int i = 0; i < Constant.PBULLTE_NUMBER; i++) {
            bullet[i] = new PlayerBullet();

            if(Constant.PBULLTE_NUMBER==1){
                bullet[i].setX(x+ this.width/2-4);
            }
            else{
                bullet[i].setX(x + this.width / (Constant.PBULLTE_NUMBER == 1 ? 2
                        : Constant.PBULLTE_NUMBER - 1) * i-4);
            }
            bullet[i].setY(y);
            bullet[i].setWidth(8);
            bullet[i].setHeight(15);
            bullet[i].setHurt(Constant.PBULLTE_HURT);
        }
        return bullet;
    }

}
