package _08final.mvc.model;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import _08final.images.SpriteTexLoader;
import _08final.sounds.Sound;

//This class defines explode event

public class Explode extends Sprite {

    private int xpic;
    private boolean isExplode = false;

    public boolean isExplode() {
        return isExplode;
    }

    public void setExplode(boolean isExplode) {
        this.isExplode = isExplode;
    }

    public int getXpic() {
        return xpic;
    }

    public void setXpic(int xpic) {
        this.xpic = xpic;
    }

    private Timer timer;

    public Explode() {

        timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xpic += 66;
                if (xpic >= 528) {
                    timer.stop();
                    isExplode = true;
                }
            }
        });
        timer.start();
        //Sound.playSoundEffect(Sound.SoundEffect.SHIP_EXPLOSION);
    }

    private JPanel panel;
    private BufferedImage explode =  SpriteTexLoader.load(SpriteTexLoader.SpriteTex.BANG);
    ;

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(explode, x, y, x + 40, y + 40, xpic, 0, xpic + 66, 66, panel);
    }

}
