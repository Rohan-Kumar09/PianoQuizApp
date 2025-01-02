package com.example.pianonotesquiz;

import android.content.Intent;
//import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import android.content.SharedPreferences;
import java.util.concurrent.atomic.AtomicBoolean;
import android.media.AudioAttributes;
import android.media.SoundPool;

public class PlayActivity extends AppCompatActivity {

    Random rand = new Random(); // Random number generator.

    String[] notes = new String[88]; // Array of notes to play.
    int[] rawIds = new int[88]; // Array of raw sound files.
    int[] buttonIds = new int[88]; // Array of button ids.
    int soundId;

    static int questionInteger = 1; // Question number.
    static int score = 0;

    ImageButton backButton;
    ImageButton forwardButton;
    ImageButton audioButton;

    SoundPool soundPool;

    Button[] choiceButtons = new Button[4];

    TextView scoreInt;
    TextView messageBox; // Message box text view.
    TextView correctAnswerDisplayPrompt; // Correct answer text view.
    TextView correctAnswerDisplay; // Correct answer output view.
    TextView questionNumber; // Question number output view.

    int numberOfQuestions;
    private String sharedPrefFile = "com.example.android.sharedPrefFile";
    SharedPreferences quizPreferences;

    String[] emojis = {"🤮", "🤮", "🤮", "😭", "😐", "👌", "😯", "😍", "🥳", "😎", "🤯"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Initialize SharedPreferences.
        quizPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        numberOfQuestions = quizPreferences.getInt("numberOfQuestions", 10); // 10 is the default number of questions

        // Initialize SoundPool
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        // Get data from intent
        notes = getIntent().getStringArrayExtra("notes"); // Array of notes to play.
        rawIds = getIntent().getIntArrayExtra("rawIds"); // Array of raw sound files.
        buttonIds = getIntent().getIntArrayExtra("buttonIds"); // Array of button ids.

        // Initialize all components.
        choiceButtons[0] = findViewById(R.id.choiceButton1);
        choiceButtons[1] = findViewById(R.id.choiceButton2);
        choiceButtons[2] = findViewById(R.id.choiceButton3);
        choiceButtons[3] = findViewById(R.id.choiceButton4);
        backButton = findViewById(R.id.backButton);
        audioButton = findViewById(R.id.audioButton);
        scoreInt = findViewById(R.id.scoreView);
        messageBox = findViewById(R.id.messageBox);
        correctAnswerDisplay = findViewById(R.id.correctAnswerDisplay);
        correctAnswerDisplayPrompt = findViewById(R.id.correctAnswerDisplayPrompt);
        forwardButton = findViewById(R.id.forwardButton);
        questionNumber = findViewById(R.id.questionNumber);

        scoreInt.setText(String.format(Locale.US, "%d", score)); // show initial score (for recreate())
        questionNumber.setText(String.format(Locale.US, "%d", questionInteger)); // to show question number.

        int correctChoiceIndex = rand.nextInt(notes.length); // The index of note to play.

        // REMOVE THIS LINE IT'S FOR TESTING ONLY, this line shows the answer regardless
//        correctAnswerDisplay.setText(notes[correctChoiceIndex]);
        // REMOVE THIS LINE IT'S FOR TESTING ONLY

        ArrayList<Integer> choices = new ArrayList<>();
        choices = getRandomChoices(choices, correctChoiceIndex, notes.length);

        int correctChoiceButton = rand.nextInt(4); // 0, 1, 2, or 3 for choice buttons.

        setChoiceButtonText(correctChoiceButton, choices, notes);

        backButton.setOnClickListener(v -> {
            // return to main activity, quit the quiz.
            finish();
            score = 0; // reset score
            questionInteger = 1; // reset question number.
        });

        forwardButton.setOnClickListener(v -> {
            // only recreate if the current question has been answered.
            if (questionInteger == numberOfQuestions){
                Intent intent = new Intent(this, ShowScore.class);
                intent.putExtra("score", String.format(Locale.US, "%d / %d", score, numberOfQuestions * 10));
                intent.putExtra("emoji", emojis[score / numberOfQuestions]);
                startActivity(intent);
                score = 0;
                questionInteger = 1;
                finish(); // go back to main activity after showing score.
            }
            else{
                // only recreate if the current question has been answered.
                if (correctAnswerDisplay.getText().equals(notes[correctChoiceIndex])){
                    questionInteger++;
                    recreate();
                }
            }
        });

        soundId = soundPool.load(this, rawIds[correctChoiceIndex], 1); // load the sound int sound ID
        audioButton.setOnClickListener(v ->
            soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1) // play the soundId sound
        );
        setChoiceButtonListeners(correctChoiceIndex, messageBox, correctAnswerDisplay, correctAnswerDisplayPrompt, notes);
    }

    /*
    * Sets listeners for choice buttons.
    * */
    protected void setChoiceButtonListeners(int correctChoiceIndex, TextView messageBox, TextView correctAnswerDisplay, TextView correctAnswerDisplayPrompt, String[] notes){
        AtomicBoolean graded = new AtomicBoolean(false);
        AtomicBoolean clicked = new AtomicBoolean(false);
        for (Button choiceButton : choiceButtons){
            choiceButton.setOnClickListener(v -> {
                if (!clicked.get() && !graded.get() && choiceButton.getText().equals(notes[correctChoiceIndex])){
                    score += 10;
                    graded.set(true);
                    scoreInt.setText(String.format(Locale.US, "%d", score)); // show updated score
                    messageBox.setText(R.string.right);
                }
                clicked.set(true);
                if (!graded.get())
                    messageBox.setText(R.string.wrong);
                correctAnswerDisplayPrompt.setVisibility(View.VISIBLE);
                correctAnswerDisplay.setText(notes[correctChoiceIndex]);
            });
        }
    }

    protected void setChoiceButtonText(int correctChoiceButton, ArrayList<Integer> choices, String[] notes) {
        // set all buttons with wrong choices
        choiceButtons[0].setText(notes[choices.get(1)]);
        choiceButtons[1].setText(notes[choices.get(2)]);
        choiceButtons[2].setText(notes[choices.get(3)]);
        choiceButtons[3].setText(notes[choices.get(4)]);

        switch(correctChoiceButton){
            // change one button to have the right choice
            case 0:
                choiceButtons[0].setText(notes[choices.get(0)]);
                break;
            case 1:
                choiceButtons[1].setText(notes[choices.get(0)]);
                break;
            case 2:
                choiceButtons[2].setText(notes[choices.get(0)]);
                break;
            case 3:
                choiceButtons[3].setText(notes[choices.get(0)]);
                break;
        }
    }

    /*
    * Returns ArrayList with 0th index being the correct choice, and the rest being
    * random wrong choices from index 1 to 5 inclusive.
    * */
    protected ArrayList<Integer> getRandomChoices(ArrayList<Integer> choices, int correctChoiceIndex , int length){
        choices.add(correctChoiceIndex);
        int randomPlaceInt = rand.nextInt(length);
        for (int i = 1; i < 5; i++){
            while(choices.contains(randomPlaceInt)){ // while duplicate random number.
                randomPlaceInt = rand.nextInt(length); // keep generating new random number.
            }
            choices.add(randomPlaceInt);
        }
        return choices;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }
}