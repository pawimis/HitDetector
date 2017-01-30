package com.example.root.hitdetector;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class ActionActivity extends AppCompatActivity{

    RelativeLayout relativeLayout;

    TextView textScore1;
    TextView textScore2;
    TextView textScore3;
    TextView textScore4;
    TextView textScore5;

    TextView textScoreBest;
    TextView textScoreRemaining;

    TextView textScoreCurrent;

    Button buttonAllResults;
    Button buttonAgain;

    TextView textScoreBestEver;
    TextView textScoreLastScores;
    boolean visualStimulus;
    boolean soundStimulus;
    int sound;
    int amplitude;
    boolean timeSetTraining;
    boolean punchSetTrainig;
    boolean recording;
    boolean timeTrainingRun = false;
    int trainingValueCounter;
    int savedTraningValues;
    int trainingType;
    int best;
    Handler timerHandler;
    ArrayList<String> resultListLast5;
    MediaPlayer mediaPlayer;
    MediaRecorder mRecorder;
    Thread runner;
    ArrayList<Long> resultsList;
    long startTime = 0;
    long randomTime = 3500 + (long)(Math.random()*6500);
    DBHelper dbHelper;
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            seconds = seconds % 60;
            if(trainingValueCounter - seconds > 0 && timeTrainingRun) {
                Log.i("Timer","Timer");
                textScoreRemaining.setText(String.valueOf(trainingValueCounter - seconds )+ " sec");
                timerHandler.postDelayed(this, 500);
            }
            else{
                timeTrainingRun = false;
                textScoreRemaining.setText("0");
                textScoreCurrent.setText("END");
                buttonAgain.setVisibility(View.VISIBLE);
                buttonAgain.setEnabled(true);
                buttonAllResults.setVisibility(View.VISIBLE);
                buttonAgain.setEnabled(true);
                timerHandler.removeCallbacks(timerRunnable);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        Intent intent = getIntent();
        visualStimulus = intent.getBooleanExtra("Visual",false);
        soundStimulus = intent.getBooleanExtra("Sound",false);
        sound = intent.getIntExtra("SoundFile",0);
        amplitude = intent.getIntExtra("Amplitude",20000);
        timeSetTraining = intent.getBooleanExtra("TrainingTimeMode",true);
        punchSetTrainig = intent.getBooleanExtra("TrainingPunchCounter",false);
        trainingValueCounter = intent.getIntExtra("TrainingValue",60);
        savedTraningValues = trainingValueCounter;
        trainingType = intent.getIntExtra("TrainingType",1);
        resultsList = new ArrayList<Long>();
        Log.i("Training type",String.valueOf(trainingType));
        if(sound != 0 && soundStimulus){
            mediaPlayer = new MediaPlayer();
            Log.i("Sound",String.valueOf(sound));
            //mediaPlayer.selectTrack(sound);
        }
        relativeLayout = (RelativeLayout) findViewById(R.id.actionActivity_RelativeLayout_main);
        textScore1 = (TextView) findViewById(R.id.actionActivity_TextScores_1);
        textScore2 = (TextView) findViewById(R.id.actionActivity_TextScores_2);
        textScore3 = (TextView) findViewById(R.id.actionActivity_TextScores_3);
        textScore4 = (TextView) findViewById(R.id.actionActivity_TextScores_4);
        textScore5 = (TextView) findViewById(R.id.actionActivity_TextScores_5);

        textScoreBest = (TextView) findViewById(R.id.actionActivity_TextScores_Best);
        textScoreRemaining = (TextView) findViewById(R.id.actionActivity_TextScores_Remaning);

        textScoreCurrent = (TextView) findViewById(R.id.actionActivity_text_current_result);

        buttonAgain = (Button) findViewById(R.id.actionActivity_Button_again);
        buttonAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAgain.setVisibility(View.INVISIBLE);
                buttonAgain.setEnabled(false);
                buttonAllResults.setVisibility(View.INVISIBLE);
                buttonAgain.setEnabled(false);
                trainingValueCounter = savedTraningValues;
                startTraining();
            }
        });
        buttonAllResults = (Button) findViewById(R.id.actionActivity_Button_allResults);
        buttonAllResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActionActivity.this,ResultsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        textScoreBestEver = (TextView) findViewById(R.id.actionActivity_text_best);
        textScoreLastScores = (TextView) findViewById(R.id.actionActivity_text_last);
        resultListLast5 = new ArrayList<>();
        while(resultListLast5.size() !=5){
            resultListLast5.add("x");
        }
        textScore1.setText(resultListLast5.get(0));
        textScore2.setText(resultListLast5.get(1));
        textScore3.setText(resultListLast5.get(2));
        textScore4.setText(resultListLast5.get(3));
        textScore5.setText(resultListLast5.get(4));

        dbHelper = new DBHelper(getApplicationContext());
        textScoreBestEver.setText("BEST: " + dbHelper.getBestScore() + " ms");
        textScoreLastScores.setText("LAST: " + dbHelper.getPreviousScore() + " ms");
        startTraining();
    }
    private void startTraining(){
        if(punchSetTrainig){
            textScoreRemaining.setText(String.valueOf(trainingValueCounter));
        }
        if(timeSetTraining){
            timerHandler = new Handler();
            textScoreRemaining.setText(String.valueOf(trainingValueCounter) + "sec");
            startTime = System.currentTimeMillis();
            timerHandler.post(timerRunnable);
            timeTrainingRun = true;
        }
        start();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        timeTrainingRun = false;
        stopRunner();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void start(){
        recording = true;
        Log.i("test1","test2");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i("testMiddle","test2");
                textScoreCurrent.setText("Prepare");
            }
        });
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override public void run() {
                Log.i("test2","test2");
                if(trainingType == 1) {
                    if (soundStimulus)
                        Log.i("test3","test2");
                    startRunner();
                    textScoreCurrent.setText("HIT!");
                    if (visualStimulus)
                        relativeLayout.setBackgroundColor(Color.RED);
                    if (soundStimulus)
                        mediaPlayer.start();
                }else if(trainingType == 2){
                    if(Math.random() < 0.33){
                        textScoreCurrent.setText("NOT!");
                        if (visualStimulus)
                            relativeLayout.setBackgroundColor(Color.GREEN);
                        if (soundStimulus)
                            mediaPlayer.start();
                        start();
                        h.removeCallbacks(this);
                    }else{
                        startRunner();
                        textScoreCurrent.setText("HIT!");
                        if (visualStimulus)
                            relativeLayout.setBackgroundColor(Color.RED);
                        if (soundStimulus)
                            mediaPlayer.start();
                    }
                }
            }
        },randomTime);
    }
    public void startRecorder(){
        Log.d("Noise", "start runner()");
        if (mRecorder == null)
        {
            mRecorder = new MediaRecorder();
            mRecorder.reset();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile("/dev/null");

            try {
                mRecorder.prepare();
                mRecorder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void startRunner(){
        if (runner == null)
        {
            runner = new Thread(){
                public void run()
                {
                    while (runner != null)
                    {
                        if(runner.isInterrupted()) {
                            runner = null;
                            break;
                        }
                        try
                        {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        checkAmplitude();
                    }
                }
            };
            runner.start();
            startRecorder();
            Log.d("Noise", "start runner()");
        }
    }
    public void stopRunner(){
        stopRecorder();
        recording = false;
        if(runner != null)
            runner.interrupt();
    }
    public void checkAmplitude(){
        if(recording) {
            long amp = mRecorder.getMaxAmplitude();
            //Log.i("Activity", String.valueOf(amp));
            resultsList.add(amp);

            if (amp > amplitude) {

                ListIterator<Long> listIterator = resultsList.listIterator();
                while(listIterator.next() == 0){
                    listIterator.remove();
                }
                int hitTime = resultsList.size() * 10;
                stopRunner();
                serviceUI(hitTime);
                resultsList.clear();
            }
        }
    }
    private void serviceUI(final int result){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                relativeLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                if(best == 0 || result < best ){
                    best = result;
                    textScoreBest.setText(String.valueOf(result) + "ms");
                }
                resultListLast5.add(String.valueOf(result));
                if(resultListLast5.size() > 4 && !resultListLast5.get(5).matches("x")){
                    resultListLast5.remove(0);
                }
                for(String res : resultListLast5){
                    Log.i("results",res);
                }
                textScore1.setText(resultListLast5.get(0)+ "ms");
                textScore2.setText(resultListLast5.get(1)+ "ms");
                textScore3.setText(resultListLast5.get(2)+ "ms");
                textScore4.setText(resultListLast5.get(3)+ "ms");
                textScore5.setText(resultListLast5.get(4)+ "ms");
                dbHelper.insertRecord(String.valueOf(result));
                if(punchSetTrainig){
                    trainingValueCounter--;
                    textScoreRemaining.setText(String.valueOf(trainingValueCounter));
                    if (trainingValueCounter>0) {
                        start();
                    }else{
                        textScoreRemaining.setText("0");
                        textScoreCurrent.setText("END");
                        buttonAgain.setVisibility(View.VISIBLE);
                        buttonAgain.setEnabled(true);
                        buttonAllResults.setVisibility(View.VISIBLE);
                        buttonAllResults.setEnabled(true);
                    }
                }else if(timeSetTraining){
                    if(timeTrainingRun)
                        start();
                    else{
                        textScoreRemaining.setText("0");
                        textScoreCurrent.setText(String.valueOf(result));
                    }
                }
            }
        });

    }
    public void stopRecorder() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
        }
    }
}
