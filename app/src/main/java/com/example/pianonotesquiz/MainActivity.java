package com.example.pianonotesquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


/**
 * This is a musical notes quiz game..
 */

public class MainActivity extends AppCompatActivity {

    Button playButton;
    Button quitButton;
    Button keyboardButton;
    Button settingsButton;

    // Array of audio file names.
    String[] notes = {"a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7", "as0", "as1", "as2", "as3", "as4", "as5", "as6", "as7",
            "b0", "b1", "b2", "b3", "b4", "b5", "b6", "b7",
            "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "cs1", "cs2", "cs3", "cs4", "cs5", "cs6", "cs7",
            "d1", "d2", "d3", "d4", "d5", "d6", "d7", "ds1", "ds2", "ds3", "ds4", "ds5", "ds6", "ds7",
            "e1", "e2", "e3", "e4", "e5", "e6", "e7",
            "f1", "f2", "f3", "f4", "f5", "f6", "f7", "fs1", "fs2", "fs3", "fs4", "fs5", "fs6", "fs7",
            "g1", "g2", "g3", "g4", "g5", "g6", "g7", "gs1", "gs2", "gs3", "gs4", "gs5", "gs6", "gs7"
    };
    int[] rawIds = { // for loading files
            R.raw.a0, R.raw.a1, R.raw.a2, R.raw.a3, R.raw.a4, R.raw.a5, R.raw.a6, R.raw.a7,
            R.raw.as0, R.raw.as1, R.raw.as2, R.raw.as3, R.raw.as4, R.raw.as5, R.raw.as6, R.raw.as7,
            R.raw.b0, R.raw.b1, R.raw.b2, R.raw.b3, R.raw.b4, R.raw.b5, R.raw.b6, R.raw.b7,
            R.raw.c1, R.raw.c2, R.raw.c3, R.raw.c4, R.raw.c5, R.raw.c6, R.raw.c7, R.raw.c8,
            R.raw.cs1, R.raw.cs2, R.raw.cs3, R.raw.cs4, R.raw.cs5, R.raw.cs6, R.raw.cs7,
            R.raw.d1, R.raw.d2, R.raw.d3, R.raw.d4, R.raw.d5, R.raw.d6, R.raw.d7,
            R.raw.ds1, R.raw.ds2, R.raw.ds3, R.raw.ds4, R.raw.ds5, R.raw.ds6, R.raw.ds7,
            R.raw.e1, R.raw.e2, R.raw.e3, R.raw.e4, R.raw.e5, R.raw.e6, R.raw.e7,
            R.raw.f1, R.raw.f2, R.raw.f3, R.raw.f4, R.raw.f5, R.raw.f6, R.raw.f7,
            R.raw.fs1, R.raw.fs2, R.raw.fs3, R.raw.fs4, R.raw.fs5, R.raw.fs6, R.raw.fs7,
            R.raw.g1, R.raw.g2, R.raw.g3, R.raw.g4, R.raw.g5, R.raw.g6, R.raw.g7,
            R.raw.gs1, R.raw.gs2, R.raw.gs3, R.raw.gs4, R.raw.gs5, R.raw.gs6, R.raw.gs7
    };
    int[] buttonIds = { // for assigning buttons
            R.id.a0, R.id.a1, R.id.a2, R.id.a3, R.id.a4, R.id.a5, R.id.a6, R.id.a7,
            R.id.as0, R.id.as1, R.id.as2, R.id.as3, R.id.as4, R.id.as5, R.id.as6, R.id.as7,
            R.id.b0, R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7,
            R.id.c1, R.id.c2, R.id.c3, R.id.c4, R.id.c5, R.id.c6, R.id.c7, R.id.c8,
            R.id.cs1, R.id.cs2, R.id.cs3, R.id.cs4, R.id.cs5, R.id.cs6, R.id.cs7,
            R.id.d1, R.id.d2, R.id.d3, R.id.d4, R.id.d5, R.id.d6, R.id.d7,
            R.id.ds1, R.id.ds2, R.id.ds3, R.id.ds4, R.id.ds5, R.id.ds6, R.id.ds7,
            R.id.e1, R.id.e2, R.id.e3, R.id.e4, R.id.e5, R.id.e6, R.id.e7,
            R.id.f1, R.id.f2, R.id.f3, R.id.f4, R.id.f5, R.id.f6, R.id.f7,
            R.id.fs1, R.id.fs2, R.id.fs3, R.id.fs4, R.id.fs5, R.id.fs6, R.id.fs7,
            R.id.g1, R.id.g2, R.id.g3, R.id.g4, R.id.g5, R.id.g6, R.id.g7,
            R.id.gs1, R.id.gs2, R.id.gs3, R.id.gs4, R.id.gs5, R.id.gs6, R.id.gs7
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String sharedPrefFile = "com.example.android.sharedPrefFile";
        SharedPreferences quizPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        SharedPreferences.Editor editor = quizPreferences.edit();
        editor.putInt("numberOfQuestions", 10); // 10 is the default number of questions
        editor.apply();

        playButton = findViewById(R.id.playButton);
        quitButton = findViewById(R.id.quitButton);
        keyboardButton = findViewById(R.id.keyboardButton);
        settingsButton = findViewById(R.id.settingsButton);

        playButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
            intent.putExtra("notes", notes); // pass array of notes to play activity.
            intent.putExtra("rawIds", rawIds); // pass array of raw files to play activity.
            intent.putExtra("buttonIds", buttonIds); // pass array of button ids to play activity.
            startActivity(intent);
        });

        keyboardButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), KeyboardActivity.class);
            intent.putExtra("notes", notes); // pass array of notes to play activity.
            intent.putExtra("rawIds", rawIds); // pass array of raw files to play activity.
            intent.putExtra("buttonIds", buttonIds); // pass array of button ids to play activity.
            startActivity(intent);
        });

        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        });

        quitButton.setOnClickListener(v ->
            System.exit(0)
        );
    }
}