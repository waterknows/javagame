package _08final.mvc.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class ScoreManager
{
    // An arraylist of the type "score"
    private ArrayList<Score> scores;

    //name of output file
    private static final String scoreFile = "scores.dat";

    //Initialise an in and outputStream for working with the file
    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;

    public ScoreManager() {
        //initialize the scores-arraylist
        scores = new ArrayList<Score>();
    }

    //=============================methods==============================
    public ArrayList<Score> getScores() {
        loadScoreFile();
        sort();
        return scores;
    }
    private void sort() {
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(scores, comparator);
    }
    public void addScore(int score) {
        loadScoreFile();
        scores.add(new Score(score));
        updateScoreFile();
    }

    public void loadScoreFile() {
        try {
            inputStream = new ObjectInputStream(new FileInputStream(scoreFile));
            scores = (ArrayList<Score>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("[Load] FNF Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[Load] IO Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("[Load] CNF Error: " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Load] IO Error: " + e.getMessage());
            }
        }
    }

    public void updateScoreFile() {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(scoreFile));
            outputStream.writeObject(scores);
        } catch (FileNotFoundException e) {
            System.out.println("[Update] FNF Error: " + e.getMessage() + ",data will be saved in a new file");
        } catch (IOException e) {
            System.out.println("[Update] IO Error: " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Update] Error: " + e.getMessage());
            }
        }
    }

}
