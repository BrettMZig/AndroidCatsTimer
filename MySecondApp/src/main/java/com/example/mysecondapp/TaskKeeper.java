package com.example.mysecondapp;

import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by bmz4 on 5/19/13.
 */
public class TaskKeeper {
    private String defaultTaskName = "Down Time";

    private static File storageFile;
    private static long startTime;
    private static ArrayList<Task> taskList;
    private static String currentName;


    static {
        TaskKeeper.taskList = new ArrayList<Task>();
        TaskKeeper.startTime = SystemClock.elapsedRealtime();
    }

    public TaskKeeper() {
        if( TaskKeeper.taskList.size() < 1 ) {
            this.addNewTask(this.defaultTaskName, "No Code");
            this.setCurrent(0);
        }
    }

    public TaskKeeper(String storageFileName) {
        if( TaskKeeper.taskList.size() < 1 ) {
            storageFile = new File(Environment.getExternalStoragePublicDirectory("CatsStorage"), storageFileName );
            try {
                this.restoreData();
            } catch (IOException e) {
                if( TaskKeeper.taskList.size() < 1 ) {
                    this.addNewTask(this.defaultTaskName, "No Code");
                    this.setCurrent(0);
                }
            }
        }
    }

    public void addNewTask(String name, String number) {
        TaskKeeper.taskList.add(new Task(name, number));
    }

    public void addNewTask(String csvLine) {
        TaskKeeper.taskList.add(new Task(csvLine));
    }

    public ArrayList<Task> getTaskList() {
        return TaskKeeper.taskList;
    }

    public void setCurrent(int index) {
        Task myTask;

        for( int i = 0; i < TaskKeeper.taskList.size(); i++ ) {
            myTask = TaskKeeper.taskList.get(i);
            if(myTask.getCurrent()) {
                long temp = myTask.getTime();
                long delta = SystemClock.elapsedRealtime() - TaskKeeper.startTime;
                myTask.setTime( temp + delta );
                myTask.setCurrent(false);
            }

            if( i == index ) {
                myTask.setCurrent(true);
                TaskKeeper.currentName = myTask.toString();
            }

            TaskKeeper.taskList.set(i, myTask);
        }

            TaskKeeper.startTime = SystemClock.elapsedRealtime();
    }


    public String getCurrentName() {
        return TaskKeeper.currentName;
    }

    public long getStartTime() {
        return TaskKeeper.startTime;
    }

    public void reset() {
        int myIndex = 0;
        Task myTask;

        TaskKeeper.startTime = SystemClock.elapsedRealtime();

        for( int i = 0; i < TaskKeeper.taskList.size(); i++ ) {
            myTask = TaskKeeper.taskList.get( i );
            myTask.reset();
            if(myTask.toString().equals(this.defaultTaskName)) {
               myIndex = i;
            }
        }

        this.setCurrent( myIndex );
    }

    public ArrayList<String> getReportList() {
        Task mTask;

        this.update();
        ArrayList<Task> tempList = new ArrayList<Task>();
        ArrayList<String> reportList = new ArrayList<String>();

        for( int i = 0; i < TaskKeeper.taskList.size(); i++ ) {
            mTask = TaskKeeper.taskList.get(i);
            if(mTask.getTime() > 0) {
                tempList.add(mTask);
            }
        }

        Collections.sort(tempList, new TaskComparator());

        for( Task myTask : tempList ) {
            reportList.add( String.format( myTask.reportString() ) );
        }

        return reportList;
    }

    public void update() {
        for( Task myTask : TaskKeeper.taskList ) {
            if(myTask.getCurrent()) {
                long temp = myTask.getTime();
                long delta = SystemClock.elapsedRealtime() - TaskKeeper.startTime;
                myTask.setTime( temp + delta );
                TaskKeeper.startTime = SystemClock.elapsedRealtime();
                TaskKeeper.currentName = myTask.toString();
            }
        }
    }

    public void storeData() throws IOException {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if(!this.storageFile.isFile()) {
                this.storageFile.getParentFile().mkdirs();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(this.storageFile));
            writer.write(Long.toString(TaskKeeper.startTime) + "\n");

            for(Task mTask : TaskKeeper.taskList) {
                writer.write(mTask.getCsvData());
            }

            writer.close();

            Log.d("Storage_Inside", "Success!");
        } else {
            Log.d("Storage_Inside", "Failure!");
        }
    }

    private void restoreData() throws IOException {
        String state = Environment.getExternalStorageState();
        String curLine;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if(this.storageFile.isFile()) {
                BufferedReader reader = new BufferedReader(new FileReader(this.storageFile));

                curLine = reader.readLine();
                TaskKeeper.startTime = Long.decode(curLine.trim());

                while( (curLine = reader.readLine()) != null  ) {
                    this.addNewTask(curLine);
                }

                reader.close();
//                this.update();
                Log.d("Storage_Inside", "I can read!");
            }
        } else if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            if(this.storageFile.isFile()) {
                BufferedReader reader = new BufferedReader(new FileReader(this.storageFile));

                curLine = reader.readLine();
                Log.d("StartTime", curLine);
                TaskKeeper.startTime = Long.getLong(curLine);

                while( (curLine = reader.readLine()) != null  ) {
                    this.addNewTask(curLine);
                }

                reader.close();
//                this.update();
                Log.d("Storage_Inside", "I can read!");
            }
        } else {
            Log.d("Storage_Inside", "Illiterate!");
            throw new IOException("You should create default task");
        }

        for( Task myTask : TaskKeeper.taskList ) {
            if(myTask.getCurrent()) {
                TaskKeeper.currentName = myTask.toString();
            }
        }
    }
}