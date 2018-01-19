package com.example.android.teachlesson.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lsitec101.macedo on 15/01/18.
 */

public class AnswerModel implements Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.answer);
    }

    protected AnswerModel(Parcel in) {
        this.answer = in.readString();
    }

    public static final Parcelable.Creator<AnswerModel> CREATOR = new Parcelable.Creator<AnswerModel>() {
        @Override
        public AnswerModel createFromParcel(Parcel source) {
            return new AnswerModel(source);
        }

        @Override
        public AnswerModel[] newArray(int size) {
            return new AnswerModel[size];
        }
    };
}
