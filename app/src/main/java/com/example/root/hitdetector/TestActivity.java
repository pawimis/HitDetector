package com.example.root.hitdetector;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

public class TestActivity extends AppCompatActivity {
    MediaPlayer mp;
    MediaRecorder mRecorder;
    Thread runner;
    long amplitude = 0;
    boolean recording = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Uri sound = intent.getParcelableExtra("Sound");
        if (sound == null) {
            cancelIntent();
        }
        mp = MediaPlayer.create(this, sound);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                startRecorder();
                startRunner();
                recording = true;
                mp.start();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mp.start();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopRunner();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Amplitude", amplitude);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    void cancelIntent() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

    public void startRecorder() {
        Log.d("Noise", "start runner()");
        if (mRecorder == null) {
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

    public void startRunner() {
        if (runner == null) {
            runner = new Thread() {
                public void run() {
                    while (runner != null) {
                        if (runner.isInterrupted()) {
                            runner = null;
                            break;
                        }
                        try {
                            Thread.sleep(5);
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

    public void stopRunner() {
        stopRecorder();
        recording = false;
        if (runner != null)
            runner.interrupt();
    }

    public void stopRecorder() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
        }
    }

    public void checkAmplitude() {
        if (recording) {
            long amp = mRecorder.getMaxAmplitude();
            if (amp > amplitude) {
                amplitude = amp;
                Log.i("adjusted amp", String.valueOf(amplitude));
            }
        }
    }
}
