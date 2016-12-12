package _08final.mvc.model;

//This class defines ship,

abstract public class Ship extends Sprite {

    public int life;

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Ship() {
    }

    abstract void move(double deltaTimeInSecs);

    abstract Bullet[] shot();


}
