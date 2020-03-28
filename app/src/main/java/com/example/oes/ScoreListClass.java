package com.example.oes;

import java.util.ArrayList;

public class ScoreListClass {
    public ScoreListClass(ArrayList<Score> scoreList) {
        this.scoreList = scoreList;
    }

    private ArrayList<Score> scoreList;

    public ScoreListClass() {

    }

    public ArrayList<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(ArrayList<Score> scoreList) {
        this.scoreList = scoreList;
    }
}
