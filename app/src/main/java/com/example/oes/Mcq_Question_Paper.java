package com.example.oes;

import java.util.ArrayList;

public class Mcq_Question_Paper {
    private ArrayList<MCQ_CLASS> questionArray;
    private int examType;

    public Mcq_Question_Paper() {
    }

    public Mcq_Question_Paper(ArrayList<MCQ_CLASS> questionArray, int examType) {
        this.questionArray = questionArray;
        this.examType = examType;
    }

    public ArrayList<MCQ_CLASS> getQuestionArray() {
        return questionArray;
    }

    public void setQuestionArray(ArrayList<MCQ_CLASS> questionArray) {
        this.questionArray = questionArray;
    }

    public int getExamType() {
        return examType;
    }

    public void setExamType(int examType) {
        this.examType = examType;
    }

}
