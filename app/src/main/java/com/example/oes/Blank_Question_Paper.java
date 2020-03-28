package com.example.oes;

import java.util.ArrayList;

public class Blank_Question_Paper {
    private ArrayList<BLANKS_CLASS> questionArray;
    private int examType;

    public Blank_Question_Paper() {
    }

    public Blank_Question_Paper(ArrayList<BLANKS_CLASS> questionArray, int examType) {
        this.questionArray = questionArray;
        this.examType = examType;
    }

    public ArrayList<BLANKS_CLASS> getQuestionArray() {
        return questionArray;
    }

    public void setQuestionArray(ArrayList<BLANKS_CLASS> questionArray) {
        this.questionArray = questionArray;
    }

    public int getExamType() {
        return examType;
    }

    public void setExamType(int examType) {
        this.examType = examType;
    }
}

