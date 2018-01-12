package com.example.android.teachlesson.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.android.teachlesson.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private AdView mAdView;

    /// BUTTON FOR THE QUESTION
    private Button mathButton;
    private Button geoButton;
    private Button historyButton;


    public static final String MATHEMATICS = "Mathematics";
    public static final String GEOGRAPH = "Geograph";
    public static final String HISTORY = "History";
    public static final String MATERIAL = "Material";

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private StorageReference mStorageRef;

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

        clickListenerQuestion(MATHEMATICS, mathButton);
        clickListenerQuestion(GEOGRAPH, geoButton);
        clickListenerQuestion(HISTORY, historyButton);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        mStorageRef = FirebaseStorage.getInstance().getReference();

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
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
