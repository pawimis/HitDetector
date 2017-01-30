package com.example.root.hitdetector;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
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
    int sound = R.raw.punch;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButtonStimulusSound = (ToggleButton) findViewById(R.id.mainactivity_tooglebutton_sound);
        toggleButtonStimulusSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stimulusSound = toggleButtonStimulusSound.isChecked();
                if(stimulusSound){
                    setupToogleUnlocked(true);
                    setupToggleUnchecked();
                    toggleButtonSoundGong.setChecked(true);
                    setSound(R.raw.punch);
                }else{
                    setupToggleUnchecked();
                    setupToogleUnlocked(false);
                    sound = 0;
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
                setSound(R.raw.punch);
            }
        });
        toggleButtonSoundShoot = (ToggleButton) findViewById(R.id.mainactivity_tooglebutton_sound_shoot);
        toggleButtonSoundShoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupToggleUnchecked();
                toggleButtonSoundShoot.setChecked(true);
                setSound(R.raw.punch);
            }
        });
        toggleButtonSoundBeep = (ToggleButton) findViewById(R.id.mainactivity_tooglebutton_sound_beep);
        toggleButtonSoundBeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupToggleUnchecked();
                toggleButtonSoundBeep.setChecked(true);
                setSound(R.raw.punch);
            }
        });
        toggleButtonSoundWhistle = (ToggleButton) findViewById(R.id.mainactivity_tooglebutton_sound_whistle);
        toggleButtonSoundWhistle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupToggleUnchecked();
                toggleButtonSoundWhistle.setChecked(true);
                setSound(R.raw.punch);
            }
        });
        toggleButtonSoundHajime = (ToggleButton) findViewById(R.id.mainactivity_tooglebutton_sound_hajime);
        toggleButtonSoundHajime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupToggleUnchecked();
                toggleButtonSoundHajime.setChecked(true);
                setSound(R.raw.punch);
            }
        });
        buttonPickSound = (Button) findViewById(R.id.mainactivity_button_pickSound);
        //TODO implement
        mSeekBarAmplitude = (SeekBar) findViewById(R.id.mainactivity_seekbar);
        mAmplitudeView = (TextView) findViewById(R.id.mainactivity_amplitude);
        mSeekBarAmplitude.setProgress(50);
        mSeekBarAmplitude.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 50;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i;
                float factor = 10000 * progress/100;
                amplitudeSet = 15000 + (long)factor;
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
                    textViewCounter.setText("90 sec");
                    trainingTimePunchCount = 90;
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
                    textViewCounter.setText("30");
                    trainingTimePunchCount = 30;
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
        mBestView.setText("BEST: "+ dbHelper.getBestScore()+" ms");
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

    }

    private void setSound(int sound){
        this.sound = sound;
    }

    private void setupToggleUnchecked() {
        toggleButtonSoundGong.setChecked(false);
        toggleButtonSoundShoot.setChecked(false);
        toggleButtonSoundBeep.setChecked(false);
        toggleButtonSoundWhistle.setChecked(false);
        toggleButtonSoundHajime.setChecked(false);
    }
    private void setupToogleUnlocked(boolean block){
        toggleButtonSoundGong.setEnabled(block);
        toggleButtonSoundShoot.setEnabled(block);
        toggleButtonSoundBeep.setEnabled(block);
        toggleButtonSoundWhistle.setEnabled(block);
        toggleButtonSoundHajime.setEnabled(block);
        buttonPickSound.setEnabled(block);
    }




}