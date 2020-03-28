package com.example.oes;

import java.util.Arrays;

public class MCQ_CLASS {
    public MCQ_CLASS() {
    }

    private String questionTag,question,answerKey;
    private String option1,option2,option3,option4;
    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }


    public String getQuestionTag() {
        return questionTag;
    }

    public void setQuestionTag(String questionTag) {
        this.questionTag = questionTag;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerKey() {
        return answerKey;
    }

    public void setAnswerKey(String answerKey) {
        this.answerKey = answerKey;
    }


    public void printQuestion(){
        System.out.println("Printing Question"+getQuestionTag()+" \n"+getQuestion()+" \n"+getAnswerKey()+" \n");
    }







}
