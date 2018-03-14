package com.example.mrgarg.testyouriq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int q2_score = 0;
    int q3_score = 0;

    public void onRadioButtonClick (View view) {
        boolean q_checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_q2_op3:
                if (q_checked)
                    q2_score = 1;
                break;
            case R.id.radio_q2_op2:
                if (q_checked)
                    q2_score = 0;
                break;
            case R.id.radio_q2_op1:
                if (q_checked)
                    q2_score = 0;
                break;
            case R.id.radio_q3_op3:
                if (q_checked)
                    q3_score = 0;
                break;
            case R.id.radio_q3_op2:
                if (q_checked)
                    q3_score = 1;
                break;
            case R.id.radio_q3_op1:
                if (q_checked)
                    q3_score = 0;
                break;
        }
    }

    public void submitOrder(View view) {
        int scores = 0;

        EditText populatedEditText = (EditText) findViewById(R.id.populated_editText);
        String popu_answer = populatedEditText.getText().toString();
        popu_answer = popu_answer.trim();

        if (popu_answer.equalsIgnoreCase("China"))
            scores++;

        EditText osEditText = (EditText) findViewById(R.id.os_editText);
        String os_answer = osEditText.getText().toString();
        os_answer = os_answer.trim();

        if (os_answer.equalsIgnoreCase("Android"))
            scores++;

        CheckBox q4_op1CheckBox = (CheckBox) findViewById(R.id.q4_op1);
        boolean has_q4_op1 = q4_op1CheckBox.isChecked();

        CheckBox q4_op2CheckBox = (CheckBox) findViewById(R.id.q4_op2);
        boolean has_q4_op2 = q4_op2CheckBox.isChecked();

        CheckBox q4_op3CheckBox = (CheckBox) findViewById(R.id.q4_op3);
        boolean has_q4_op3 = q4_op3CheckBox.isChecked();

        if (has_q4_op1 && has_q4_op2 && !has_q4_op3)
            scores++;

//        RadioButton q2_op1_radioButton = (RadioButton) findViewById(R.id.radio_q2_op1);
//        boolean hasQ2Op1 = q2_op1_radioButton.isChecked();
//
//        RadioButton q2_op2_radioButton = (RadioButton) findViewById(R.id.radio_q2_op2);
//        boolean hasQ2Op2 = q2_op2_radioButton.isChecked();
//
//        RadioButton q2_op3_radioButton = (RadioButton) findViewById(R.id.radio_q2_op3);
//        boolean hasQ2Op3 = q2_op3_radioButton.isChecked();
//
//        if (has_q4_op3)
//            scores++;

        scores = q2_score + q3_score + scores;

        Toast.makeText(this, "Your score is " + scores + " out of 5", Toast.LENGTH_LONG).show();
    }
}
