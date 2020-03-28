package com.example.oes;

public class BLANKS_CLASS {
    private String questionTag,question,answerKey;
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
        System.out.println("Printing Question \n"+getQuestionTag()+"\n"+getQuestion()+"\n"+getAnswerKey()+"\n");
    }
}
