package com.example.android.teachlesson.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.teachlesson.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private AdView mAdView;

    /// BUTTON FOR THE QUESTION
    private Button mathButton;
    private Button geoButton;
    private Button historyButton;
    private Button mSignOut;


    public static final String MATHEMATICS = "Mathematics";
    public static final String GEOGRAPH = "Geograph";
    public static final String HISTORY = "History";
    public static final String MATERIAL = "Material";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mathButton = (Button) findViewById(R.id.bt_math);
        geoButton = (Button) findViewById(R.id.bt_geograph);
        historyButton = (Button) findViewById(R.id.bt_history);
        mSignOut = (Button) findViewById(R.id.sign_out_button);

        clickListenerQuestion(MATHEMATICS, mathButton);
        clickListenerQuestion(GEOGRAPH, geoButton);
        clickListenerQuestion(HISTORY, historyButton);

        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), SignInActivity.class);
                in.putExtra("signOut", true);
                startActivity(in);

            }
            // ..
        });

    }

    public void clickListenerQuestion(String material, Button button) {

        switch (material) {
            case MATHEMATICS:
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString(MATERIAL, MATHEMATICS);
                        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                break;
            case GEOGRAPH:
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString(MATERIAL, GEOGRAPH);
                        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

                break;
            case HISTORY:
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString(MATERIAL, HISTORY);
                        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                break;
        }
    }
}
