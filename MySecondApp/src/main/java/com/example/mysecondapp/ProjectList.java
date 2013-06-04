package com.example.mysecondapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static android.support.v4.app.NavUtils.*;

/**
 * Created by bmz4 on 5/17/13.
 */
public class ProjectList extends ListActivity {
    private TaskKeeper myKeeper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_project);

        myKeeper = new TaskKeeper();

        ListView projectLV = (ListView) findViewById(android.R.id.list);

        ArrayAdapter myAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, myKeeper.getTaskList());

        projectLV.setAdapter(myAdapter);
    }

    @Override
    protected void onListItemClick (ListView l, View v, int position, long id){
        myKeeper.setCurrent(position);
        NavUtils.navigateUpFromSameTask(this);
    }

}