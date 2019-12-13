package com.example.thesoundbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;




public class MainActivity extends AppCompatActivity {

    private MediaRecorder myAudioRecorder;

    private boolean permissionToRecordAccepted = false;
    private boolean permissionToWriteAccepted = false;
    private String [] permissions = {"android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE"};






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int requestCode = 200;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }

        Button airplane = (Button) this.findViewById(R.id.airplane);
        final MediaPlayer airsound = MediaPlayer.create(this, R.raw.airplane);
        airplane.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                airsound.start();
            }
        });

        Button bell = (Button) this.findViewById(R.id.bell);
        final MediaPlayer bellsound = MediaPlayer.create(this, R.raw.bell);
        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bellsound.start();
            }
        });

        Button firetruck = (Button) this.findViewById(R.id.firetruck);
        final MediaPlayer firesound = MediaPlayer.create(this, R.raw.firetruck);
        firetruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firesound.start();
            }
        });

        Button gong = (Button) this.findViewById(R.id.gong);
        final MediaPlayer gongsound = MediaPlayer.create(this, R.raw.gong);
        gong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gongsound.start();
            }
        });

        Button sos = (Button) this.findViewById(R.id.sos);
        final MediaPlayer sossound = MediaPlayer.create(this, R.raw.sos);
        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sossound.start();
            }
        });

        Button train = (Button) this.findViewById(R.id.train);
        final MediaPlayer trainsound = MediaPlayer.create(this, R.raw.train);
        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trainsound.start();
            }
        });

        final Button play = (Button) findViewById(R.id.play);
        final Button stop = (Button) findViewById(R.id.stop);
        final Button record = (Button) findViewById(R.id.record);
        stop.setEnabled(false);
        play.setEnabled(false);
        final String outputFile = (String) Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                } catch (IllegalStateException ise) {
                    // make something ...
                } catch (IOException ioe) {
                    // make something
                }
                record.setEnabled(false);
                stop.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myAudioRecorder.stop();
                    myAudioRecorder.release();
                    myAudioRecorder = null;
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
                record.setEnabled(true);
                stop.setEnabled(false);
                play.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Audio Recorded", Toast.LENGTH_LONG).show();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(outputFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(getApplicationContext(), "Playing Audio", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // make something
                }
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 200:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                permissionToWriteAccepted  = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) MainActivity.super.finish();
        if (!permissionToWriteAccepted ) MainActivity.super.finish();

    }
}

