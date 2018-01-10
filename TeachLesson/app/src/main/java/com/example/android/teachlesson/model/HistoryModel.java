package com.example.android.teachlesson.model;

import java.util.ArrayList;

/**
 * Created by lsitec101.macedo on 10/01/18.
 */

public class HistoryModel {

    private int id;
    private String question;
    private ArrayList<String> answers;
    private String correct;
    private int pontuation;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public HistoryModel() {
    }

    public HistoryModel(int id, String question, ArrayList<String> answers, String correct, int pontuation) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.correct = correct;
        this.pontuation = pontuation;
    }
}
