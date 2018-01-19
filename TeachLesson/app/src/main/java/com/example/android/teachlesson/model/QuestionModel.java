package com.example.android.teachlesson.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by lsitec101.macedo on 10/01/18.
 */

public class QuestionModel implements Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.question);
        dest.writeList(this.answers);
        dest.writeString(this.correct);
        dest.writeString(this.pontuation);
    }

    protected QuestionModel(Parcel in) {
        this.id = in.readInt();
        this.question = in.readString();
        this.answers = new ArrayList<AnswerModel>();
        in.readList(this.answers, AnswerModel.class.getClassLoader());
        this.correct = in.readString();
        this.pontuation = in.readString();
    }

    public static final Parcelable.Creator<QuestionModel> CREATOR = new Parcelable.Creator<QuestionModel>() {
        @Override
        public QuestionModel createFromParcel(Parcel source) {
            return new QuestionModel(source);
        }

        @Override
        public QuestionModel[] newArray(int size) {
            return new QuestionModel[size];
        }
    };
}
