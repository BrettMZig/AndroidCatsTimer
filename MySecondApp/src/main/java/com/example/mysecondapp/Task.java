package com.example.mysecondapp;

import android.os.SystemClock;
import android.util.Log;

/**
 * Created by bmz4 on 5/19/13.
 */
public class Task {
    private String name;
    private String number;
    private long time;
    private boolean current;

    private String formatString = "%s \n%s  %s";
    private String storageFormat = "%s , %s , %s , %s \n";

    public Task(String name, String number) {
        this.number = number;
        this.name = name;
        this.current = false;
        this.time = 0l;
    }

    public Task(String csvString) {
        String[] temp = csvString.split(",");
        this.name = temp[0].trim();
        this.number = temp[1].trim();
        this.time = Long.decode(temp[2].trim());
        this.current = Boolean.parseBoolean(temp[3].trim());
        Log.d("myTask:" + this.name, temp[3].trim() + ":"  + String.valueOf(this.current));
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return this.time;
    }

    public String toString() {
        return this.name;
    }

    public String reportString() {
//        return this.name + " - " + this.time + " - " + this.number;
        return String.format(formatString, this.name, this.time, this.number);
    }

    public void setCurrent(boolean flag) {
        this.current = flag;
    }

    public boolean getCurrent() {
        return this.current;
    }

    public void reset() {
        this.time = 0l;
        this.current = false;
    }

    public String getCsvData() {
        return String.format(storageFormat, this.name, this.number, this.time, this.current);
    }
}
