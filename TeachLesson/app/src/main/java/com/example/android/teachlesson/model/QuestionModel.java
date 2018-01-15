package com.example.android.teachlesson.model;

import java.util.HashMap;

/**
 * Created by lsitec101.macedo on 10/01/18.
 */

public class QuestionModel {

    private int id;
    private String question;
    private HashMap<String,AnswerModel> answers;
    private String correct;
    private int pontuation;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public QuestionModel() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public HashMap<String,AnswerModel> getAnswers() {
        return answers;
    }

    public void setAnswers(HashMap<String,AnswerModel> answers) {
        this.answers = answers;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public int getPontuation() {
        return pontuation;
    }

    public void setPontuation(int pontuation) {
        this.pontuation = pontuation;
    }
}
