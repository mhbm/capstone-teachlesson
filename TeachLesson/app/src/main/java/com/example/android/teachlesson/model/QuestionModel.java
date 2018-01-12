package com.example.android.teachlesson.model;

import java.util.ArrayList;

/**
 * Created by lsitec101.macedo on 10/01/18.
 */

public class QuestionModel {

    private int id;
    private String question;
    private ArrayList<String> answers;
    private String correct;
    private int pontuation;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public QuestionModel() {
    }

    public QuestionModel(int id, String question, ArrayList<String> answers, String correct, int pontuation) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.correct = correct;
        this.pontuation = pontuation;
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

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
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
