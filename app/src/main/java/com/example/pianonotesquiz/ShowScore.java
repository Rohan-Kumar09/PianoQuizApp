package com.example.pianonotesquiz;

import android.widget.TextView;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class ShowScore extends AppCompatActivity {

    TextView scoreView;
    TextView emoji;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_score);

        scoreView = findViewById(R.id.scoreView);
        backButton = findViewById(R.id.backButton);
        emoji = findViewById(R.id.emoji);

        String score = getIntent().getStringExtra("score");
        String emojiString = getIntent().getStringExtra("emoji");

        scoreView.setText(score); // show the score.
        emoji.setText(emojiString); // set emoji based on the score.

        backButton.setOnClickListener(v ->
            finish() // return to main activity.
        );
    }
}