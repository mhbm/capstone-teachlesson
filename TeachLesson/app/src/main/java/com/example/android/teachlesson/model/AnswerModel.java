package com.example.android.teachlesson.model;

/**
 * Created by lsitec101.macedo on 15/01/18.
 */

public class AnswerModel {

    private String answer;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public AnswerModel() {
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
