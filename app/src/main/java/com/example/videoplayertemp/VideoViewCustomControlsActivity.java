package com.example.videoplayertemp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

/*
https://youtu.be/ZJlUfhJQYiE

    * Video player - NOT support h.265
 */

public class VideoViewCustomControlsActivity extends AppCompatActivity {

    private static final String TAG = "Test_Code";
    private String MEDIA_PATH;

    private VideoView videoPlayerVv;
    private ProgressBar progressBar;
    private ImageView replay30Iv, replay10Iv, replay5Iv, restoreIv, playIv, pauseIv, stopIv, forward5Iv, forward10Iv, forward30Iv;
    private SeekBar currentTimeSeekBar;
    private TextView currentTimeTv, totalTimeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view_custom_controls);
        setReferences();

//        MEDIA_PATH = DataManager.MEDIA_PATH1;
        MEDIA_PATH = DataManager.MEDIA_PATH2;
//        MEDIA_PATH = DataManager.MEDIA_PATH_H_264;
//        MEDIA_PATH = DataManager.MEDIA_PATH_H_265;

//        videoPlayerVv.setVideoPath("https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_1mb.mp4");
//        videoPlayerVv.setVideoPath("https://docs.google.com/uc?export=download&id=1unhPd84cGJB4ymcKV-jS5mjNzzAEZbq7");
//        videoPlayerVv.setVideoPath(DataManager.MEDIA_PATH1);
//        videoPlayerVv.setVideoPath(DataManager.MEDIA_PATH_H_264);
//        videoPlayerVv.setVideoPath(DataManager.MEDIA_PATH_H_265); //not working.
        videoPlayerVv.setVideoPath(MEDIA_PATH);
        videoPlayerVv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Toast.makeText(getBaseContext(), "Video is prepared and ready for play.", Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.INVISIBLE);
                totalTimeTv.setText("" + videoPlayerVv.getDuration());
                currentTimeTv.setText("0");

                currentTimeSeekBar.setMax(videoPlayerVv.getDuration());

                mediaPlayer.start();
            }
        });

        videoPlayerVv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(getBaseContext(), "Video Finished.", Toast.LENGTH_SHORT).show();
            }
        });
//        videoPlayerVv.start();

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoPlayerVv);

        videoPlayerVv.setMediaController(mediaController);


        restoreIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                videoPlayerVv.resume();
                videoPlayerVv.stopPlayback();
                videoPlayerVv.setVideoPath(MEDIA_PATH);
                videoPlayerVv.start();
            }
        });

        playIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (videoPlayerVv.isPlaying()){
                    videoPlayerVv.resume();
                }else{
                    videoPlayerVv.start();
                }


                new CountDownTimer(videoPlayerVv.getDuration(), 1) {
                    @Override
                    public void onTick(long l) {
//                        Log.d(TAG, "onTick: Update progress bar: " + videoPlayerVv.getCurrentPosition());
                        currentTimeSeekBar.setProgress(videoPlayerVv.getCurrentPosition(), true);
                        currentTimeTv.setText("" + videoPlayerVv.getCurrentPosition());
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
            }
        });

        pauseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoPlayerVv.pause();
            }
        });

        stopIv.setVisibility(View.GONE);
        stopIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoPlayerVv.stopPlayback();
//                videoPlayerVv.setVideoPath(MEDIA_PATH);
            }
        });

        replay30Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replayPosition(videoPlayerVv.getCurrentPosition(), 30000);
            }
        });

        replay10Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replayPosition(videoPlayerVv.getCurrentPosition(), 10000);
            }
        });

        replay5Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replayPosition(videoPlayerVv.getCurrentPosition(), 5000);
            }
        });

        forward5Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forwardPosition(videoPlayerVv.getCurrentPosition(), 5000);
            }
        });

        forward10Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forwardPosition(videoPlayerVv.getCurrentPosition(), 10000);
            }
        });

        forward30Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forwardPosition(videoPlayerVv.getCurrentPosition(), 30000);
            }
        });

        currentTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStopTrackingTouch: Change current time to: " + seekBar.getProgress());
                videoPlayerVv.seekTo(seekBar.getProgress());
                Log.d(TAG, "onStopTrackingTouch: Current time: " + videoPlayerVv.getCurrentPosition());
            }
        });
    }

    private void setReferences(){
        videoPlayerVv = findViewById(R.id.video_player_vv);
        progressBar = findViewById(R.id.progressBar);
        replay30Iv = findViewById(R.id.replay30_iv);
        replay10Iv = findViewById(R.id.replay10_iv);
        replay5Iv = findViewById(R.id.replay5_iv);
        restoreIv = findViewById(R.id.restore_iv);
        playIv = findViewById(R.id.play_iv);
        pauseIv = findViewById(R.id.pause_iv);
        stopIv = findViewById(R.id.stop_iv);
        forward5Iv = findViewById(R.id.forward5_iv);
        forward10Iv = findViewById(R.id.forward10_iv);
        forward30Iv = findViewById(R.id.forward30_iv);
        currentTimeSeekBar = findViewById(R.id.current_time_seekBar);
        currentTimeTv = findViewById(R.id.current_time_tv);
        totalTimeTv = findViewById(R.id.total_time_tv);
    }

    private void replayPosition(int currentPosition, int replayMillisecond){

        Log.d(TAG, "replayPosition: Video current position: " + currentPosition);
        currentPosition -= replayMillisecond;

        if (currentPosition < 0){
            currentPosition = 0;
        }

        Log.d(TAG, "replayPosition: Video new position " + currentPosition);

        videoPlayerVv.seekTo(currentPosition);
    }

    private void forwardPosition(int currentPosition, int forwardMillisecond){

        Log.d(TAG, "forwardPosition: Video current position: " + currentPosition);

        currentPosition += forwardMillisecond;

        if (currentPosition >= videoPlayerVv.getDuration()){
            return;
        }

        videoPlayerVv.seekTo(currentPosition);

    }
}