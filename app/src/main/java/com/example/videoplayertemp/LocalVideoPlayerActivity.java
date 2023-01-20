package com.example.videoplayertemp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

/*
For save local videos, need add "Android Resource directory"
 */

public class LocalVideoPlayerActivity extends AppCompatActivity {

    private VideoView videoPlayerVv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_video_player);
        setReferences();

//        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.naftali_masumi_kyoto_japan;
        String videoPath = "https://docs.google.com/uc?export=download&id=1unhPd84cGJB4ymcKV-jS5mjNzzAEZbq7";
        Uri uri = Uri.parse(videoPath);
        videoPlayerVv.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        videoPlayerVv.setMediaController(mediaController);
        mediaController.setAnchorView(videoPlayerVv);
    }

    private void setReferences(){
        videoPlayerVv = findViewById(R.id.video_player_vv);
    }
}