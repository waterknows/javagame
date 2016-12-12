package _08final.mvc.model;

import java.awt.*;

//This class checks collision

public class CollisionCheck {

    public static boolean AC(PlayerBullet ball, Enemy enemy) {
        return CheckPoint(ball.getX(), ball.getY(), enemy);
    }

    public static boolean CheckPoint(int x, int y, Enemy enemy) {
        return x > enemy.getX() && x < enemy.getX() + enemy.getWidth()
                && y > enemy.getY() && y < enemy.getY() + enemy.getHeight();
    }

    /** Judge bullet and plane */
    public static boolean C1(Bullet ball, Ship enemy) {
        Rectangle a = new Rectangle(ball.getX(), ball.getY(), ball.getWidth(),
                ball.getHeight());
        Rectangle b = new Rectangle(enemy.getX(), enemy.getY(), enemy
                .getWidth(), enemy.getHeight());
        return a.intersects(b);
    }

    // Compare the two elements
    public static boolean Testing(Sprite ball, Sprite enemy) {
        Rectangle a = new Rectangle(ball.getX(), ball.getY(), ball.getWidth(),
                ball.getHeight());
        Rectangle b = new Rectangle(enemy.getX(), enemy.getY(), enemy
                .getWidth(), enemy.getHeight());
        return a.intersects(b);
    }

    public static boolean C3(int x, int y, int w, int h, Enemy enemy) {
        return C1(new PlayerBullet(x, y, w, h), enemy);
    }

}
