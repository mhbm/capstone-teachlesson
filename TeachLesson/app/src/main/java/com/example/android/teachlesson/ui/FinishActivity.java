package com.example.android.teachlesson.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.teachlesson.R;
import com.example.android.teachlesson.widget.UpdateServiceWidget;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.android.teachlesson.ui.MainActivity.GEOGRAPH;
import static com.example.android.teachlesson.ui.MainActivity.HISTORY;
import static com.example.android.teachlesson.ui.MainActivity.MATERIAL;
import static com.example.android.teachlesson.ui.MainActivity.MATHEMATICS;
import static com.example.android.teachlesson.ui.MainActivity.PONTUATION_GEOGRAPH;
import static com.example.android.teachlesson.ui.MainActivity.PONTUATION_HISTORY;
import static com.example.android.teachlesson.ui.MainActivity.PONTUATION_MATHEMATICS;
import static com.example.android.teachlesson.ui.QuestionActivity.USER_PONTUATION;

/**
 * Created by lsitec101.macedo on 15/01/18.
 */

public class FinishActivity extends AppCompatActivity {


    int userPontuation = 0;

    private TextView mTvPontuationText;
    private TextView mTvUserPontuation;
    private ImageView mImagePhotoUser;

    private FirebaseUser userFirebase;

    private DatabaseReference mDatabase;

    private Uri photoUriUser;
    private String uidUser;
    private String userName;

    private String material;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        userPontuation = getIntent().getIntExtra(USER_PONTUATION, 0);
        material = getIntent().getStringExtra(MATERIAL);

        mTvPontuationText = (TextView) findViewById(R.id.tv_pontuation);
        mTvUserPontuation = (TextView) findViewById(R.id.tv_userPontuation);
        mImagePhotoUser = (ImageView) findViewById(R.id.iv_photoUser);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        userFirebase = FirebaseAuth.getInstance().getCurrentUser();

        if (userFirebase != null) {

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            uidUser = userFirebase.getUid();
            photoUriUser = userFirebase.getPhotoUrl();
            userName = userFirebase.getDisplayName();
        }

        updateUI();
    }


    public void updateUI() {
        mTvPontuationText.setText(R.string.finisH_pontuation);
        mTvUserPontuation.setText(String.valueOf(userPontuation));
        Picasso.with(getBaseContext()).load(photoUriUser.toString()).into(mImagePhotoUser);
    }


    /**
     * This method is called when the Share Text Content button is clicked. It will simply share
     * the text contained within the String textThatYouWantToShare.
     *
     * @param v Button that was clicked.
     */
    public void onClickShareTextButton(View v) {

        String pontuation =
                mTvPontuationText.getText() + " " + userPontuation;

        /* Send that text to our method that will share it. */
        shareText(pontuation);
    }


    private void shareText(String textToShare) {

        String mimeType = "text/plain";

        String title = "Pontuation of TeachLesson®";

        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(title)
                .setText(textToShare)
                .startChooser();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        super.onBackPressed();
    }


    /**
     * This method is called when the Save button is clicked. It will simply save
     * the pontuation into Firebase
     *
     * @param v Button that was clicked.
     */
    public void onClickSavePontuation(View v) {

        mDatabase.child("users").child(uidUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    switch (material) {
                        case MATHEMATICS:
                            if (Integer.parseInt(String.valueOf(dataSnapshot.child(PONTUATION_MATHEMATICS).getValue())) < userPontuation)
                                mDatabase.child("users").child(uidUser).child(PONTUATION_MATHEMATICS).setValue(String.valueOf(userPontuation));
                            putPontuationIntoWidget(String.valueOf(userPontuation), String.valueOf(dataSnapshot.child(PONTUATION_HISTORY).getValue()), String.valueOf(dataSnapshot.child(PONTUATION_GEOGRAPH).getValue()), userName);
                            break;
                        case HISTORY:
                            if (Integer.parseInt(String.valueOf(dataSnapshot.child(PONTUATION_HISTORY).getValue())) < userPontuation)
                                mDatabase.child("users").child(uidUser).child(PONTUATION_HISTORY).setValue(String.valueOf(userPontuation));
                            putPontuationIntoWidget(String.valueOf(dataSnapshot.child(PONTUATION_MATHEMATICS).getValue()), String.valueOf(userPontuation), String.valueOf(dataSnapshot.child(PONTUATION_GEOGRAPH).getValue()), userName);
                            break;
                        case GEOGRAPH:
                            if (Integer.parseInt(String.valueOf(dataSnapshot.child(PONTUATION_GEOGRAPH).getValue())) < userPontuation)
                                mDatabase.child("users").child(uidUser).child(PONTUATION_GEOGRAPH).setValue(String.valueOf(userPontuation));
                            putPontuationIntoWidget(String.valueOf(dataSnapshot.child(PONTUATION_MATHEMATICS).getValue()), String.valueOf(dataSnapshot.child(PONTUATION_HISTORY).getValue()), String.valueOf(userPontuation), userName);
                            break;
                    }
//                    putPontuationIntoWidget(String.valueOf(dataSnapshot.child(PONTUATION_MATHEMATICS).getValue()), String.valueOf(dataSnapshot.child(PONTUATION_HISTORY).getValue()), String.valueOf(dataSnapshot.child(PONTUATION_GEOGRAPH).getValue()), userName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void putPontuationIntoWidget(String mathPontuation, String historyPontuation, String geographPontuation, String userName) {

        ArrayList<String> pontuationListString = new ArrayList<>();

        pontuationListString.add("YOUR PONTUATION \n\n" + userName + "\n\n");


        pontuationListString.add(PONTUATION_MATHEMATICS + " " + mathPontuation + "\n");
        pontuationListString.add(PONTUATION_HISTORY + " " + historyPontuation + "\n");
        pontuationListString.add(PONTUATION_GEOGRAPH + " " + geographPontuation + "\n");

        UpdateServiceWidget.startWidgetService(pontuationListString, getBaseContext());
    }


}
