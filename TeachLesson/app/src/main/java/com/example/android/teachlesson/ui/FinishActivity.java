package com.example.android.teachlesson.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.android.teachlesson.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.android.teachlesson.ui.QuestionActivity.USER_PONTUATION;

/**
 * Created by lsitec101.macedo on 15/01/18.
 */

public class FinishActivity extends AppCompatActivity {


    int userPontuation = 0;

    private TextView mTvPontuationText;
    private TextView mTvUserPontuation;

    private FirebaseUser userFirebase;

    private DatabaseReference mDatabase;

    private String nameUser;
    private String emailUser;
    private Uri photoUriUser;
    private String uidUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        userPontuation = getIntent().getIntExtra(USER_PONTUATION, 0);

        mTvPontuationText = (TextView) findViewById(R.id.tv_pontuation);
        mTvUserPontuation = (TextView) findViewById(R.id.tv_userPontuation);

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

            System.out.println("PARABENS USUARIO :");
            System.out.println(nameUser);
            System.out.println(emailUser);
            System.out.println(uidUser);


        }

        updateUI();
    }


    public void updateUI() {
        mTvPontuationText.setText(R.string.finisH_pontuation);
        mTvUserPontuation.setText(String.valueOf(userPontuation));
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

        String pontuation =
                mTvPontuationText.getText() + " " + userPontuation;

        mDatabase.child("users").child(uidUser).child("name").setValue(nameUser);
        mDatabase.child("users").child(uidUser).child("email").setValue(emailUser);
        mDatabase.child("users").child(uidUser).child("photo").setValue(photoUriUser.toString());
        mDatabase.child("users").child(uidUser).child("pontuation").setValue(String.valueOf(userPontuation));

        System.out.println("alll");
    }
}
