package com.example.mrgarg.sachinbasketcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int score_A = 0;
    int score_B = 0;

    public void three_points_A(View view) {
        score_A = score_A + 3;
        displayForTeamA(score_A);
    }

    public void two_points_A(View view) {
        score_A = score_A + 2;
        displayForTeamA(score_A);
    }

    public void free_throw_A(View view) {
        score_A = score_A + 1;
        displayForTeamA(score_A);
    }

    public void three_points_B(View view) {
        score_B = score_B + 3;
        displayForTeamB(score_B);
    }

    public void two_points_B(View view) {
        score_B = score_B + 2;
        displayForTeamB(score_B);
    }

    public void free_throw_B(View view) {
        score_B = score_B + 1;
        displayForTeamB(score_B);
    }

    public void reset(View view) {
        score_A = 0;
        score_B = 0;
        displayForTeamA(score_A);
        displayForTeamB(score_B);
    }

    public void displayForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.scorecardA);
        scoreView.setText(String.valueOf(score));
    }

    public void displayForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.scorecardB);
        scoreView.setText(String.valueOf(score));
    }
}
