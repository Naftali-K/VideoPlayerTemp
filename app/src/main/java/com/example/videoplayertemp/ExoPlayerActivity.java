package com.example.videoplayertemp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

/*
https://youtu.be/iUGQxGrOs5I - video lesson
https://exoplayer.dev/ - original web site of implementation
    * ExoPlayer NOT support h.265.
*/

public class ExoPlayerActivity extends AppCompatActivity {

    private PlayerView playerView;
    private ProgressBar progressBar;
    private ImageView fullscreenBtn, exoLock;
    private boolean isFullScreen = false;
    private boolean isLock = false;

    private SimpleExoPlayer simpleExoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);
        setReferences();

        simpleExoPlayer = new SimpleExoPlayer.Builder(this)
                .setSeekBackIncrementMs(5000) //settings for replay. 5sec
                .setSeekForwardIncrementMs(5000) //setting for forward. 5sec
                .build();

        playerView.setPlayer(simpleExoPlayer);
        playerView.setKeepScreenOn(true);

        simpleExoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//                Player.Listener.super.onPlayerStateChanged(playWhenReady, playbackState);
                if (playbackState == Player.STATE_BUFFERING){
                    progressBar.setVisibility(View.VISIBLE);
                }else if (playbackState == Player.STATE_READY){
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

//        Uri videoSource = Uri.parse("https://docs.google.com/uc?export=download&id=1unhPd84cGJB4ymcKV-jS5mjNzzAEZbq7");
//        Uri videoSource = Uri.parse(DataManager.MEDIA_PATH1);
        Uri videoSource = Uri.parse(DataManager.MEDIA_PATH_H_264);
//        Uri videoSource = Uri.parse(DataManager.MEDIA_PATH_H_265); //not working
        MediaItem mediaItem = MediaItem.fromUri(videoSource);
        simpleExoPlayer.setMediaItem(mediaItem);
        simpleExoPlayer.prepare();
        simpleExoPlayer.play();

        fullscreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFullScreen){
                    fullscreenBtn.setImageDrawable(getDrawable(R.drawable.ic_fullscreen_exit));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }else{
                    fullscreenBtn.setImageDrawable(getDrawable(R.drawable.ic_fullscreen));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }

                isFullScreen = !isFullScreen;
            }
        });

        exoLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLock){
                    exoLock.setImageDrawable(getDrawable(R.drawable.ic_lock));
                }else{
                    exoLock.setImageDrawable(getDrawable(R.drawable.ic_lock_open));
                }

                isLock = !isLock;
                lockScreen(isLock);
            }
        });
    }

    private void setReferences(){
        playerView = findViewById(R.id.player);
        progressBar = findViewById(R.id.progress_bar);
        fullscreenBtn = findViewById(R.id.fullscreen_btn);
        exoLock = findViewById(R.id.exo_lock);
    }

    private void lockScreen(boolean lock){
        LinearLayout secControlvid1 = findViewById(R.id.sec_controlvid1);
        LinearLayout secControlvid2 = findViewById(R.id.sec_controlvid2);

        if (lock){
            secControlvid1.setVisibility(View.INVISIBLE);
            secControlvid2.setVisibility(View.INVISIBLE);
        }else{
            secControlvid1.setVisibility(View.VISIBLE);
            secControlvid2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (isLock) return;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            fullscreenBtn.performClick();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        simpleExoPlayer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        simpleExoPlayer.pause();
    }
}