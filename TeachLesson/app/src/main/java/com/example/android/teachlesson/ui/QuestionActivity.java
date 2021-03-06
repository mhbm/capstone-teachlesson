package com.example.android.teachlesson.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.teachlesson.R;
import com.example.android.teachlesson.model.QuestionModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.teachlesson.ui.MainActivity.MATERIAL;

/**
 * Created by lsitec101.macedo on 10/01/18.
 */

public class QuestionActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    public static final String USER_PONTUATION = String.valueOf(R.string.userPontuation);;
    private static final String NUMBER_QUESTION = String.valueOf(R.string.numberQuestion);
    private static final String QUESTION_QUIZ = String.valueOf(R.string.question_quiz);


    private AdView mAdView;

    private DatabaseReference mDatabase;

    @BindView(R.id.btn_answer1) Button mBtnAnswer1;
    @BindView(R.id.btn_answer2) Button mBtnAnswer2;
    @BindView(R.id.btn_answer3) Button mBtnAnswer3;
    @BindView(R.id.btn_answer4) Button mBtnAnswer4;

    @BindView(R.id.tv_question) TextView mTvQuestion;
    private ArrayList<QuestionModel> questionQuiz;

    private int userPontuation;
    private int numberQuestion;

    private String material;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        mAdView = (AdView) findViewById(R.id.adView_question);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ///GET THE MATERIAL CHOOSE
        Bundle bundle = getIntent().getExtras();
        material = bundle.getString(MATERIAL);

        ButterKnife.bind(this);

        mBtnAnswer1.setVisibility(View.INVISIBLE);
        mBtnAnswer2.setVisibility(View.INVISIBLE);
        mBtnAnswer3.setVisibility(View.INVISIBLE);
        mBtnAnswer4.setVisibility(View.INVISIBLE);

        makeFunctionButton(mBtnAnswer1);
        makeFunctionButton(mBtnAnswer2);
        makeFunctionButton(mBtnAnswer3);
        makeFunctionButton(mBtnAnswer4);

        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.keepSynced(true);

        questionQuiz = new ArrayList<>();

        getInformationFirebase(material);


        if (savedInstanceState != null) {
           userPontuation = savedInstanceState.getInt(USER_PONTUATION);
            numberQuestion = savedInstanceState.getInt(NUMBER_QUESTION);
            questionQuiz = savedInstanceState.getParcelableArrayList(QUESTION_QUIZ);
            updateUI();
        } else {
            //// initialze variable aux
            userPontuation = 0;
            numberQuestion = 0;
        }
    }


    public void getInformationFirebase(String material) {

        switch (material) {
            case "geograph":
                mDatabase.child("/0/" + material).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot questionDataSnapshot : dataSnapshot.getChildren()) {
                            long value = questionDataSnapshot.getChildrenCount();
                            Log.d(TAG, String.valueOf(R.string.nChildren + value));
                            QuestionModel questionModel = questionDataSnapshot.getValue(QuestionModel.class);
                            questionQuiz.add(questionModel);
                        }
                        updateUI();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
            case "mathematics":
                mDatabase.child("/1/" + material).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot questionDataSnapshot : dataSnapshot.getChildren()) {
                            long value = questionDataSnapshot.getChildrenCount();
                            Log.d(TAG, String.valueOf(R.string.nChildren + value));
                            QuestionModel questionModel = questionDataSnapshot.getValue(QuestionModel.class);
                            questionQuiz.add(questionModel);
                        }
                        updateUI();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                break;
            default:
                mDatabase.child("/2/" + material).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int i = 0;
                        for (DataSnapshot questionDataSnapshot : dataSnapshot.getChildren()) {
                            long value = questionDataSnapshot.getChildrenCount();
                            Log.d(TAG, String.valueOf(R.string.nChildren + value));
                            QuestionModel questionModel = questionDataSnapshot.getValue(QuestionModel.class);
                            questionQuiz.add(questionModel);

                        }
                        updateUI();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                break;
        }
    }


    public void updateUI() {
        mTvQuestion.setText(questionQuiz.get(numberQuestion).getQuestion());

        mBtnAnswer1.setText(questionQuiz.get(numberQuestion).getAnswers().get(0).getAnswer());
        mBtnAnswer2.setText(questionQuiz.get(numberQuestion).getAnswers().get(1).getAnswer());
        mBtnAnswer3.setText(questionQuiz.get(numberQuestion).getAnswers().get(2).getAnswer());
        mBtnAnswer4.setText(questionQuiz.get(numberQuestion).getAnswers().get(3).getAnswer());

        mBtnAnswer1.setVisibility(View.VISIBLE);
        mBtnAnswer2.setVisibility(View.VISIBLE);
        mBtnAnswer3.setVisibility(View.VISIBLE);
        mBtnAnswer4.setVisibility(View.VISIBLE);

    }

    public void makeFunctionButton(final Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerTheQuestion(button);
            }
        });
    }

    public void answerTheQuestion(Button buttonClicked) {
        if (buttonClicked.getText().equals(questionQuiz.get(numberQuestion).getCorrect())) {
            userPontuation += Integer.valueOf(questionQuiz.get(numberQuestion).getPontuation());
            if (numberQuestion == questionQuiz.size() -1) {
                endGame();
            } else {
                numberQuestion++;
                updateUI();
            }
        } else {
            endGame();
        }
    }

    public void endGame() {
        Intent intent = new Intent(this, FinishActivity.class);
        intent.putExtra(USER_PONTUATION, userPontuation);
        intent.putExtra(MATERIAL, material);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(USER_PONTUATION, userPontuation);
        outState.putInt(NUMBER_QUESTION, numberQuestion);
        outState.putParcelableArrayList(QUESTION_QUIZ, questionQuiz);
    }
}
