package com.example.android.teachlesson.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.android.teachlesson.R;

import static com.example.android.teachlesson.ui.QuestionActivity.USER_PONTUATION;

/**
 * Created by lsitec101.macedo on 15/01/18.
 */

public class FinishActivity extends AppCompatActivity {


    int userPontuation = 0;

    private TextView mTvPontuationText;
    private TextView mTvUserPontuation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        userPontuation = getIntent().getIntExtra(USER_PONTUATION, 0);

        mTvPontuationText = (TextView) findViewById(R.id.tv_pontuation);
        mTvUserPontuation = (TextView) findViewById(R.id.tv_userPontuation);

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
}
