package _08final.mvc.model;

//This class defines bullet

public abstract class Bullet extends Sprite {

    public int speed;
    public int hurt;

    public boolean isUsed = false;

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public Bullet() {
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHurt() {
        return hurt;
    }

    public void setHurt(int hurt) {
        this.hurt = hurt;
    }

    abstract void move();
}
