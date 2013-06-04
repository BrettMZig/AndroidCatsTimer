package com.example.mysecondapp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends Activity {

    private Chronometer myChron;
    private TaskKeeper myKeeper;
    private TextView currentTaskName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myChron = (Chronometer) findViewById(R.id.chronometer);

        myKeeper = new TaskKeeper("StoredData.csv");

        currentTaskName = (TextView) findViewById(R.id.currentText);
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentTaskName.setText( myKeeper.getCurrentName() );
        myChron.setBase(myKeeper.getStartTime());
        myChron.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        try {
            myKeeper.storeData();
            Log.d("DataStorage_Auto", "Storage should have happened!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onStartBtnClick(View v) {
        Intent intent = new Intent(this, ProjectList.class);
        startActivity(intent);
    }

    public void onStopBtnClick(View v) {
        Intent intent = new Intent(this, ReportList.class);
        startActivity(intent);
    }

    public void onPauseBtnClick(View v) {
        myKeeper.reset();
        this.onResume();
    }

    public boolean onMenuAddClick(MenuItem item) {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
        return true;
    }

    public boolean onMenuSaveClick(MenuItem item) {

        try {
            myKeeper.storeData();
            Log.d("DataStorage_Manual", "Storage should have happened!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
