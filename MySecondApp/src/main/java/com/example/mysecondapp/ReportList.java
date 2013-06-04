package com.example.mysecondapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by bmz4 on 5/17/13.
 */
public class ReportList extends ListActivity {
    private TaskKeeper myKeeper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_project);

        myKeeper = new TaskKeeper();

        ListView projectLV = (ListView) findViewById(android.R.id.list);

        ArrayAdapter myAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, myKeeper.getReportList());

        projectLV.setAdapter(myAdapter);
    }
}