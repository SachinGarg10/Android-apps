package com.example.mrgarg.mymusicplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView play = (TextView) findViewById(R.id.play_button);
        if (play != null) {
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.horse_ride);
                    mediaPlayer.start();
                }
            });
        }

        TextView pause = (TextView) findViewById(R.id.pause_button);
        if (pause != null) {
            pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.horse_ride);
                    mediaPlayer.start();
                }
            });
        }

        TextView volume = (TextView) findViewById(R.id.volume_button);
        if (volume != null) {
            volume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.jeena);
                    mediaPlayer.start();
                }
            });
        }
    }
}
