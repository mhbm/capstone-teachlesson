package com.example.android.teachlesson.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.teachlesson.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static com.example.android.teachlesson.ui.MainActivity.MATERIAL;

/**
 * Created by lsitec101.macedo on 10/01/18.
 */

public class QuestionActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private AdView mAdView;

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


    }
}
