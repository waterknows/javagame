package _08final.mvc.model;

//this class defines score

import java.io.Serializable;

public class Score  implements Serializable  {
    private int score;

    //constructor

    public Score(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }


}


