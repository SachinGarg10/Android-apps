package com.example.mrgarg.testdesign2;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;

public class MainActivity extends AppCompatActivity {

    boolean colorChange = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Submit(View view) {
        int finalRadius = (int) Math.hypot(view.getWidth()/2, view.getHeight()/2);
        if (colorChange) {
            view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            colorChange = false;
        } else {
            Animator anim = ViewAnimationUtils.createCircularReveal(view,
                    (int) view.getWidth()/2,
                    (int) view.getHeight()/2,
                    0,
                    finalRadius);
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            anim.start();
            colorChange = true;
        }
    }
}
