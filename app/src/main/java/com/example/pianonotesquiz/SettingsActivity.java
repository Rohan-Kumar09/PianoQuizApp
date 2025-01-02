package com.example.pianonotesquiz;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    EditText numberOfQuestions;
    Button saveButton;
    String sharedPrefFile = "com.example.android.sharedPrefFile";
    SharedPreferences quizPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        quizPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        numberOfQuestions = findViewById(R.id.questionCount);
        saveButton = findViewById(R.id.saveButton);


        saveButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = quizPreferences.edit();
            int numQuestions = Integer.parseInt(numberOfQuestions.getText().toString());
            if (numQuestions <= 0){ // no negative or 0 questions.
                numQuestions = 10;
            }
            editor.putInt("numberOfQuestions", numQuestions);
            editor.apply();
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // display the number of questions chosen in the settings last time.
        numberOfQuestions.setText(String.valueOf(quizPreferences.getInt("numberOfQuestions", 10)));
    }
}