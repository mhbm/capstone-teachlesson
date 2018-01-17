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

import static com.example.android.teachlesson.ui.MainActivity.MATERIAL;

/**
 * Created by lsitec101.macedo on 10/01/18.
 */

public class QuestionActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    public static final String USER_PONTUATION = "USER_PONTUATION";


    private AdView mAdView;

    private DatabaseReference mDatabase;
    private DatabaseReference mQuestionReference;

    private Button mBtnAnswer1;
    private Button mBtnAnswer2;
    private Button mBtnAnswer3;
    private Button mBtnAnswer4;

    private TextView mTvQuestion;
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



        mTvQuestion = (TextView) findViewById(R.id.tv_question);

        mBtnAnswer1 = (Button) findViewById(R.id.btn_answer1);
        mBtnAnswer2 = (Button) findViewById(R.id.btn_answer2);
        mBtnAnswer3 = (Button) findViewById(R.id.btn_answer3);
        mBtnAnswer4 = (Button) findViewById(R.id.btn_answer4);

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

        System.out.println(mDatabase.toString());

        questionQuiz = new ArrayList<>();

        getInformationFirebase(material);

        //// initialze variable aux
        userPontuation =  0;
        numberQuestion = 0;
    }


    public void getInformationFirebase(String material) {

        switch (material) {
            case "geograph":
                mDatabase.child("/0/" + material).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot questionDataSnapshot : dataSnapshot.getChildren()) {
                            long value = questionDataSnapshot.getChildrenCount();
                            Log.d(TAG, "no of children: " + value);
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
                            Log.d(TAG, "no of children: " + value);
                            QuestionModel questionModel = questionDataSnapshot.getValue(QuestionModel.class);
//                            System.out.println(questionModel.getQuestion());
                            questionQuiz.add(questionModel);
                            System.out.println(questionQuiz.get(0).getPontuation());
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
                            Log.d(TAG, "no of children: " + value);
                            QuestionModel questionModel = questionDataSnapshot.getValue(QuestionModel.class);
                            questionQuiz.add(questionModel);

//                            System.out.println(questionQuiz.get(i).getQuestion());
//                            i++;
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
            System.out.println("pontuaaaaaaaaaaation  " + userPontuation  + "         " +questionQuiz.size());
            if (numberQuestion == questionQuiz.size() -1) {
//                System.out.println("FINISH your pontuation " + userPontuation);
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
        System.out.println("END GAME = PONTUATION " + userPontuation);
        startActivity(intent);
    }

}
