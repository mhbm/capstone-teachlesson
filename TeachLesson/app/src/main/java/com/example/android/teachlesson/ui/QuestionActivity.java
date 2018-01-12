package com.example.android.teachlesson.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.teachlesson.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.android.teachlesson.ui.MainActivity.MATERIAL;

/**
 * Created by lsitec101.macedo on 10/01/18.
 */

public class QuestionActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private AdView mAdView;

    private DatabaseReference mDatabase;
    private DatabaseReference mQuestionReference;

    private Button mBtnAnswer1;
    private Button mBtnAnswer2;
    private Button mBtnAnswer3;
    private Button mBtnAnswer4;

    private TextView mTvQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        mAdView = (AdView) findViewById(R.id.adView_question);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ///GET THE MATERIAL CHOOSE
        Bundle bundle = getIntent().getExtras();
        String material = bundle.getString(MATERIAL);

        mTvQuestion = (TextView) findViewById(R.id.tv_question);

        mBtnAnswer1 = (Button) findViewById(R.id.btn_answer1);
        mBtnAnswer2 = (Button) findViewById(R.id.btn_answer2);
        mBtnAnswer3 = (Button) findViewById(R.id.btn_answer3);
        mBtnAnswer4 = (Button) findViewById(R.id.btn_answer4);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        System.out.println(mDatabase.toString());

     


    }


}
