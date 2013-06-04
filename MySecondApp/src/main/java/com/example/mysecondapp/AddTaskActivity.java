package com.example.mysecondapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by bmz4 on 5/20/13.
 */
public class AddTaskActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        EditText et = (EditText) findViewById(R.id.editNumber);
        et.setRawInputType(InputType.TYPE_CLASS_NUMBER);
    }

    public void onButtonClick(View button) {
        TextView v = (TextView) findViewById(R.id.editName);
        String name = String.valueOf(v.getText());

        v = (TextView) findViewById(R.id.editNumber);
        String number = String.valueOf(v.getText());

        TaskKeeper myKeeper = new TaskKeeper();
        myKeeper.addNewTask(name, number);

        NavUtils.navigateUpFromSameTask(this);
    }
}
