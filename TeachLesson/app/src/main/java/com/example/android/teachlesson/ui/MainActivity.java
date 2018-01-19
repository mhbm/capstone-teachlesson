package com.example.android.teachlesson.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.teachlesson.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private AdView mAdView;

    /// BUTTON FOR THE QUESTION
    @BindView(R.id.bt_math) Button mathButton;
    @BindView(R.id.bt_geograph) Button geoButton;
    @BindView(R.id.bt_history) Button historyButton;
    @BindView(R.id.sign_out_button) Button mSignOut;




    public static final String MATHEMATICS = "mathematics";
    public static final String GEOGRAPH = "geograph";
    public static final String HISTORY = "history";
    public static final String MATERIAL = "Material";

    public static final String PONTUATION_MATHEMATICS = "pontuationMathematics";
    public static final String PONTUATION_HISTORY = "pontuationHistory";
    public static final String PONTUATION_GEOGRAPH = "pontuationGeograph";


    private FirebaseUser userFirebase;

    private DatabaseReference mDatabase;

    private String nameUser;
    private String emailUser;
    private Uri photoUriUser;
    private String uidUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ButterKnife.bind(this);

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

        Timber.plant(new Timber.DebugTree());

        mDatabase = FirebaseDatabase.getInstance().getReference();

        userFirebase = FirebaseAuth.getInstance().getCurrentUser();

        if (userFirebase != null) {
            nameUser = userFirebase.getDisplayName();
            emailUser = userFirebase.getEmail();
            photoUriUser = userFirebase.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            uidUser = userFirebase.getUid();

            mDatabase.child("users").child(uidUser).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        mDatabase.child("users").child(uidUser).child("name").setValue(nameUser);
                        mDatabase.child("users").child(uidUser).child("email").setValue(emailUser);
                        mDatabase.child("users").child(uidUser).child("photo").setValue(photoUriUser.toString());
                        mDatabase.child("users").child(uidUser).child(PONTUATION_MATHEMATICS).setValue(String.valueOf(0));
                        mDatabase.child("users").child(uidUser).child(PONTUATION_HISTORY).setValue(String.valueOf(0));
                        mDatabase.child("users").child(uidUser).child(PONTUATION_GEOGRAPH).setValue(String.valueOf(0));
                        Timber.d("Create a new user in firebase");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Timber.d("Problem with the firebase");
                }
            });


        }

    }

    public void clickListenerQuestion(String material, Button button) {

        switch (material) {
            case MATHEMATICS:
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Timber.d("Mathematics choose");
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
                        Timber.d("Geograph choose");
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
                        Timber.d("History choose");
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
