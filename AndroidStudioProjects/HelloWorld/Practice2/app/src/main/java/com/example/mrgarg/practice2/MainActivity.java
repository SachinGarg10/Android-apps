package com.example.mrgarg.practice2;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private TextView tx1, tx2, tx3;
    private Button b1, b2, b3, b4;
    private ImageView iv;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;

    private double startTime = 0;
    private double finalTime = 0;

    private int forwardTime = 5000;
    private int backwardTime = 500;

    public static int oneTimeOnly = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);

        iv = (ImageView) findViewById(R.id.imageview);

        tx1 = (TextView) findViewById(R.id.textview2);
        tx2 = (TextView) findViewById(R.id.textview3);
        tx3 = (TextView) findViewById(R.id.textview4);

        tx3.setText("Jeena Jeena.mp3");

        mediaPlayer = MediaPlayer.create(this, R.raw.jeena);

        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setClickable(false);

        b2.setEnabled(false);

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Playing Sound", Toast.LENGTH_SHORT).show();
                mediaPlayer.start();

                finalTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();

                if (oneTimeOnly == 0) {
                    seekBar.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }

                tx2.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) finalTime), TimeUnit.MILLISECONDS.toSeconds((long) finalTime) - TimeUnit.MINUTES.toSeconds((TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))));

                tx1.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) startTime), TimeUnit.MILLISECONDS.toSeconds((long) startTime) - TimeUnit.MINUTES.toSeconds((TimeUnit.MILLISECONDS.toMinutes((long) startTime)))));

                seekBar.setProgress((int) startTime);

            }
        });

        // PASTE CODE YOU WANT TO TEST HERE

//        int raspberryPrice = 10;
//        display1("1 box: $" + raspberryPrice);
//        display2("2 boxes: $" + (raspberryPrice * 2));
//        display3("3 boxes: $" + (raspberryPrice * 3));

    }

//    /**
//     * Display methods that allow the text to appear on the screen. Don't worry if you don't know
//     * how these work yet. We'll be covering them in lesson 3.
//     */
//
//    public void display(String text) {
//        TextView t = (TextView) findViewById(R.id.display_text_view);
//        t.setText(text);
//    }
//
//    public void display(int text) {
//        TextView t = (TextView) findViewById(R.id.display_text_view);
//        t.setText(text + "");
//    }
//
//    public void display1(String text) {
//        display(text);
//    }
//
//    public void display2(String text) {
//        TextView t = (TextView) findViewById(R.id.display_text_view_2);
//        t.setText(text);
//    }
//
//    public void display3(String text) {
//        TextView t = (TextView) findViewById(R.id.display_text_view_3);
//        t.setText(text);
//    }
}