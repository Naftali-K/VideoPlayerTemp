package com.example.videoplayertemp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
https://drive.google.com/file/d/1unhPd84cGJB4ymcKV-jS5mjNzzAEZbq7/view?usp=sharing

https://docs.google.com/uc?export=download&id=1unhPd84cGJB4ymcKV-jS5mjNzzAEZbq7

 */
public class MainActivity extends AppCompatActivity {

    private Button localVideoPlayerBtn, customControlsBtn, exoPlayerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setReferences();

        localVideoPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(new Intent(getBaseContext(), LocalVideoPlayerActivity.class));
            }
        });

        customControlsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(new Intent(getBaseContext(), VideoViewCustomControlsActivity.class));
            }
        });

        exoPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(new Intent(getBaseContext(), ExoPlayerActivity.class));
            }
        });
    }

    private void setReferences(){
        localVideoPlayerBtn = findViewById(R.id.local_video_player_btn);
        customControlsBtn = findViewById(R.id.custom_controls_btn);
        exoPlayerBtn = findViewById(R.id.exo_player_btn);
    }

    private void openActivity(Intent intent){
        startActivity(intent);
    }
}