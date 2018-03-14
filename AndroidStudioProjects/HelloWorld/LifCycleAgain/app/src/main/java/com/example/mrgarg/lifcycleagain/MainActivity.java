package com.example.mrgarg.lifcycleagain;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int i;

    private MediaPlayer mediaPlayer;

    private AudioManager am;

    AudioManager.OnAudioFocusChangeListener afcl = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                release();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK) {
                mediaPlayer.pause();
                //mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we ave regained focus and can resume playback
                mediaPlayer.start();
            }
        }
    };

    private MediaPlayer.OnCompletionListener onComp = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            release();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        TextView tv = (TextView) findViewById(R.id.tv);
        TextView pause = (TextView) findViewById(R.id.pause);
        TextView forward = (TextView) findViewById(R.id.forward);
        TextView back = (TextView) findViewById(R.id.back);
        TextView play = (TextView) findViewById(R.id.play);
        TextView loop = (TextView) findViewById(R.id.loop);

        if (tv != null) {
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    release();

                    int result = am.requestAudioFocus(afcl, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.jeena);
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(onComp);
                    }
                }
            });
        }

        if (pause != null) {
            pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Pause", Toast.LENGTH_SHORT).show();
                    mediaPlayer.pause();
                }
            });
        }

        if (forward != null) {
            forward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i = mediaPlayer.getCurrentPosition();
                    i = i + 10000;
                    mediaPlayer.seekTo(i);
                    int j = mediaPlayer.getDuration();
                    Toast.makeText(MainActivity.this, "10 sec forward "+ i + " in duration of " + j, Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (back != null) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i = mediaPlayer.getCurrentPosition();
                    i = i - 10000;
                    mediaPlayer.seekTo(i);
                    i = i/1000;
                    int j = mediaPlayer.getDuration()/1000;
                    Toast.makeText(MainActivity.this, "10 sec backward "+ i + " in in duration of " + j, Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (play != null) {
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "play", Toast.LENGTH_SHORT).show();
                    mediaPlayer.start();
                }
            });
        }

        if (loop != null) {
            loop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer.isLooping()) {
                        mediaPlayer.setLooping(false);
                        Toast.makeText(MainActivity.this, "loop false", Toast.LENGTH_SHORT).show();
                    } else {
                        mediaPlayer.setLooping(true);
                        Toast.makeText(MainActivity.this, "loop true", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //release();
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            am.abandonAudioFocus(afcl);
        }
    }
}
