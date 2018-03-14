package com.example.mrgarg.happybirthday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("this", "onCreate()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("this", "onStop()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("this", "onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("this", "onStart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("this", "onDestroy()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("this", "onPause()");
    }
}
