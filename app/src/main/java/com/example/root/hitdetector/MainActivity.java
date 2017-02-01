package com.example.root.hitdetector;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    ToggleButton toggleButtonStimulusSound;
    ToggleButton toggleButtonStimulusVisual;

    ToggleButton toggleButtonSoundGong;
    ToggleButton toggleButtonSoundShoot;
    ToggleButton toggleButtonSoundBeep;
    ToggleButton toggleButtonSoundWhistle;
    ToggleButton toggleButtonSoundHajime;
    Button buttonPickSound;

    TextView mAmplitudeView;
    SeekBar mSeekBarAmplitude;


    ToggleButton toggleButtonTrainingTime;
    ToggleButton toggleButtonPunchCounter;

    TextView textViewCounter;
    SeekBar seekBarCounter;

    Button startButton;

    TextView mLastView;
    TextView mBestView;

    ToggleButton toggleButtonTrainingReaction;
    ToggleButton toggleButtonTrainingDecision;
    Button buttonResults;


    Boolean stimulusSound = false;
    Boolean stimulusVisual = false;
    int trainingTimePunchCount = 0;
    long amplitudeSet = 20000;
    Uri sound = Uri.parse("android.resource://com.example.root.hitdetector/R.raw.bell");

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButtonStimulusSound = (ToggleButton) findViewById(R.id.mainactivity_tooglebutton_sound);
        toggleButtonStimulusSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stimulusSound = toggleButtonStimulusSound.isChecked();
                if(stimulusSound){
                    setupToggleUnlocked(true);
                    setupToggleUnchecked();
                    toggleButtonSoundGong.setChecked(true);
                    setSound(R.raw.bell);
                }else{
                    setupToggleUnchecked();
                    setupToggleUnlocked(false);
                    sound = null;
                }
            }
        });
        toggleButtonStimulusVisual = (ToggleButton) findViewById(R.id.mainactivity_tooglebutton_visual);
        toggleButtonStimulusVisual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stimulusVisual = toggleButtonStimulusVisual.isChecked();
            }
        });

        toggleButtonSoundGong = (ToggleButton) findViewById(R.id.mainactivity_tooglebutton_sound_gong);
        toggleButtonSoundGong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupToggleUnchecked();
                toggleButtonSoundGong.setChecked(true);
                setSound(R.raw.bell);
            }
        });
        toggleButtonSoundShoot = (ToggleButton) findViewById(R.id.mainactivity_tooglebutton_sound_shoot);
        toggleButtonSoundShoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupToggleUnchecked();
                toggleButtonSoundShoot.setChecked(true);
                setSound(R.raw.shot);
            }
        });
        toggleButtonSoundBeep = (ToggleButton) findViewById(R.id.mainactivity_tooglebutton_sound_beep);
        toggleButtonSoundBeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupToggleUnchecked();
                toggleButtonSoundBeep.setChecked(true);
                setSound(R.raw.beep);
            }
        });
        toggleButtonSoundWhistle = (ToggleButton) findViewById(R.id.mainactivity_tooglebutton_sound_whistle);
        toggleButtonSoundWhistle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupToggleUnchecked();
                toggleButtonSoundWhistle.setChecked(true);
                setSound(R.raw.whistle);
            }
        });
        toggleButtonSoundHajime = (ToggleButton) findViewById(R.id.mainactivity_tooglebutton_sound_hajime);
        toggleButtonSoundHajime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupToggleUnchecked();
                toggleButtonSoundHajime.setChecked(true);
                setSound(R.raw.beep);
            }
        });
        buttonPickSound = (Button) findViewById(R.id.mainactivity_button_pickSound);
        buttonPickSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stimulusSound) {
                    Toast.makeText(getApplicationContext(), R.string.wait_prompt, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, TestActivity.class);
                    intent.putExtra("Sound", sound);
                    startActivityForResult(intent, 1);
                }
            }
        });
        mSeekBarAmplitude = (SeekBar) findViewById(R.id.mainactivity_seekbar);
        mAmplitudeView = (TextView) findViewById(R.id.mainactivity_amplitude);
        mSeekBarAmplitude.setProgress(50);
        Log.i("Progres", "progres");
        mSeekBarAmplitude.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 50;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i;
                float factor = 15000 * progress / 100;
                amplitudeSet = 10000 + (long) factor;
                mAmplitudeView.setText(String.valueOf(amplitudeSet));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        mAmplitudeView.setText(String.valueOf(amplitudeSet));

        toggleButtonTrainingTime = (ToggleButton) findViewById(R.id.mainactivity_tooglebutton_trening_time);
        toggleButtonTrainingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toggleButtonTrainingTime.isChecked()){
                    toggleButtonPunchCounter.setChecked(false);
                    trainingTimePunchCount = 90;
                    textViewCounter.setText("90 sec");
                }else
                    toggleButtonTrainingTime.setChecked(true);
            }
        });
        toggleButtonPunchCounter = (ToggleButton) findViewById(R.id.mainactivity_tooglebutton_punchCounter);
        toggleButtonPunchCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toggleButtonPunchCounter.isChecked()){
                    toggleButtonTrainingTime.setChecked(false);
                    trainingTimePunchCount = 30;
                    textViewCounter.setText(String.format("%d", trainingTimePunchCount));
                }else
                    toggleButtonPunchCounter.setChecked(true);
            }
        });

        seekBarCounter = (SeekBar) findViewById(R.id.mainactivity_seekbar_counter);
        textViewCounter = (TextView) findViewById(R.id.mainactivity_textview_counter);
        textViewCounter.setText("0");
        seekBarCounter.setProgress(50);
        seekBarCounter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 50;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i;
                float temp;
                if(toggleButtonPunchCounter.isChecked()){
                    temp = 60 *progress/100;
                    trainingTimePunchCount = (int)temp;
                    textViewCounter.setText(String.valueOf(trainingTimePunchCount));
                }else if(toggleButtonTrainingTime.isChecked()){
                    temp = 180 * progress/100;
                    trainingTimePunchCount = (int)temp;
                    textViewCounter.setText(String.valueOf(trainingTimePunchCount) + " sec");
                }else{
                    new AlertDialog.Builder(getApplicationContext())
                            .setMessage("Set punch counter or training time ")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        mBestView = (TextView) findViewById(R.id.mainactivity_text_best);
        mLastView = (TextView) findViewById(R.id.mainactivity_text_last);
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int highScore = sharedPreferences.getInt(getString(R.string.saved_best_score), 0);
        Log.i("HighScore", String.valueOf(highScore));
        mBestView.setText("BEST: " + String.format("%d", highScore) + " ms");
        mLastView.setText("LAST: "+dbHelper.getPreviousScore()+" ms");
        toggleButtonTrainingReaction = (ToggleButton) findViewById(R.id.mainactivity_tooglebutton_Training_reaction);
        toggleButtonTrainingReaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonTrainingDecision.setChecked(false);
                toggleButtonTrainingReaction.setChecked(true);
            }
        });
        toggleButtonTrainingDecision = (ToggleButton) findViewById(R.id.mainactivity_tooglebutton_Training_decision);
        toggleButtonTrainingDecision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonTrainingDecision.setChecked(true);
                toggleButtonTrainingReaction.setChecked(false);
            }
        });
        buttonResults = (Button) findViewById(R.id.mainactivity_button_Results);
        buttonResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ResultsActivity.class);
                startActivity(intent);
            }
        });
        toggleButtonTrainingReaction.setChecked(true);
        toggleButtonTrainingTime.setChecked(true);

        startButton = (Button) findViewById(R.id.mainactivity_button_start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ActionActivity.class);
                int trainingType = 1;
                intent.putExtra("Visual",stimulusVisual);
                intent.putExtra("Sound",stimulusSound);
                intent.putExtra("SoundFile",sound);
                intent.putExtra("Amplitude",amplitudeSet);
                intent.putExtra("TrainingTimeMode",toggleButtonTrainingTime.isChecked());
                intent.putExtra("TrainingPunchCounter",toggleButtonPunchCounter.isChecked());
                intent.putExtra("TrainingValue",trainingTimePunchCount);
                if(toggleButtonTrainingDecision.isChecked()) trainingType = 2;
                intent.putExtra("TrainingType",trainingType);

                MainActivity.this.startActivity(intent);
            }
        });
        trainingTimePunchCount = 90;
        textViewCounter.setText(String.valueOf(trainingTimePunchCount) + " sec");
        setupToggleUnlocked(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                amplitudeSet = data.getLongExtra("Amplitude", 20000);
                Log.i("Returned Amplitude", String.valueOf(amplitudeSet));
                amplitudeSet += 1000;
                Log.i("Returned Amplitude", String.valueOf(amplitudeSet));
                if (amplitudeSet < 10000) {
                    amplitudeSet = 10000;
                }
                Log.i("Returned Amplitude", String.valueOf(amplitudeSet));
                mAmplitudeView.setText(String.valueOf(amplitudeSet));
                double factor = ((double) amplitudeSet / (double) 25000);
                Log.i("Returned Amplitude", String.valueOf(factor));
                int value = (int) (100 * factor);
                Log.i("Returned Amplitude", String.valueOf(value));
                mSeekBarAmplitude.setProgress(value);
            }
        }
    }

    private void setSound(int sound){
        Uri uri = Uri.parse("android.resource://com.example.root.hitdetector/" + sound);
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), uri);
        mp.start();
        this.sound = uri;
    }

    private void setupToggleUnchecked() {
        toggleButtonSoundGong.setChecked(false);
        toggleButtonSoundShoot.setChecked(false);
        toggleButtonSoundBeep.setChecked(false);
        toggleButtonSoundWhistle.setChecked(false);
        toggleButtonSoundHajime.setChecked(false);
    }

    private void setupToggleUnlocked(boolean block) {
        toggleButtonSoundGong.setEnabled(block);
        toggleButtonSoundShoot.setEnabled(block);
        toggleButtonSoundBeep.setEnabled(block);
        toggleButtonSoundWhistle.setEnabled(block);
        toggleButtonSoundHajime.setEnabled(block);
        buttonPickSound.setEnabled(block);
    }




}