package com.example.pianonotesquiz;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
//import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

public class KeyboardActivity extends AppCompatActivity {

    SoundPool soundPool;

    int[] soundIds = new int[88]; // array of loaded sounds
    int[] rawIds = new int[88]; // array of raw sound files
    int[] buttonIds = new int[88]; // array of button ids
    Button[] pianoKeys = new Button[88]; // array of buttons

    ImageButton backButton;

    String[] notes;
    TextView notePlayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);

        // Get data from intent
        notes = getIntent().getStringArrayExtra("notes");
        rawIds = getIntent().getIntArrayExtra("rawIds");
        buttonIds = getIntent().getIntArrayExtra("buttonIds");

        // Initialize all components
        backButton = findViewById(R.id.backButton2);
        notePlayed = findViewById(R.id.notePlayed);

        backButton.setOnClickListener(view -> finish());

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(audioAttributes)
                .build();

        // Load all the piano sounds
        for (int i = 0; i < 88; i++) {
            soundIds[i] = soundPool.load(this, rawIds[i], 1);
            pianoKeys[i] = findViewById(buttonIds[i]);
            int finalI = i;
            pianoKeys[i].setOnClickListener(v -> {
                soundPool.play(soundIds[finalI], 1.0f, 1.0f, 0, 0, 1);
                notePlayed.setText(notes[finalI]);
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }
}

// previous way using media player... Inefficient and would stop after 50 keys.
// reason being multiple were created without being destroyed.
//    MediaPlayer mp;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_keyboard);
//
//        backButton = findViewById(R.id.backButton2);
//        notes = getIntent().getStringArrayExtra("notes");
//        notePlayed = findViewById(R.id.notePlayed);
//
//        backButton.setOnClickListener(view -> {
//            finish();
//        });
//
//        for (int i = 0; i < 88; i++) {
//            pianoKeys[i] = findViewById(getResources().getIdentifier(notes[i], "id", getPackageName()));
//            int finalI = i;
//            pianoKeys[i].setOnClickListener(view -> {
//                if (mp != null) {
//                    mp.release();
//                }
//                mp = MediaPlayer.create(this, getResources().getIdentifier(notes[finalI], "raw", getPackageName()));
//                notePlayed.setText(notes[finalI]);
//                mp.setLooping(false);
//                mp.start();
//            });
//        }