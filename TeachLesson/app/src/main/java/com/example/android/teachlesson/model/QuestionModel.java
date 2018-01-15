package com.example.android.teachlesson.model;

import java.util.ArrayList;

/**
 * Created by lsitec101.macedo on 10/01/18.
 */

public class QuestionModel {

    private int id;
    private String question;
    private ArrayList<AnswerModel> answers;
    private String correct;
    private String pontuation;

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

    public ArrayList<AnswerModel> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<AnswerModel> answers) {
        this.answers = answers;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getPontuation() {
        return pontuation;
    }

    public void setPontuation(String pontuation) {
        this.pontuation = pontuation;
    }
}
