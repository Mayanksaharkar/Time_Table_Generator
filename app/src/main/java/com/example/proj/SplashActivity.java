package com.example.proj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.VideoView;

public class SplashActivity extends AppCompatActivity {

    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mVideoView = findViewById(R.id.videoView);
        mVideoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.splash_screen);
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                startNextActivity(); // start the next activity when the video finishes
            }
        });
        mVideoView.setMediaController(null);
        mVideoView.start();
    }
    private void startNextActivity() {
        Intent intent = new Intent(this, create_Activity.class);
        startActivity(intent);
        finish();
    }
}
