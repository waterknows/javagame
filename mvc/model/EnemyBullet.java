package _08final.mvc.model;

import _08final.images.SpriteTexLoader;

import javax.swing.*;
import java.awt.*;

public class EnemyBullet extends Bullet {

    public EnemyBullet() {
        this.mTex = SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BULLET);
        ;
    }

    @Override
    public void move() {
        this.y = this.y + Constant.EBULLTE_SPEED;

    }

}