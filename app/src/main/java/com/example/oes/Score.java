package com.example.oes;

public class Score {
    private String roll;
    private String marks;

    public Score(String roll, String marks) {
        this.roll = roll;
        this.marks = marks;
    }

    public Score() {
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}
