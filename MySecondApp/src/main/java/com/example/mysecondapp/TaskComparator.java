package com.example.mysecondapp;

import java.util.Comparator;

/**
 * Created by bmz4 on 5/19/13.
 */
public class TaskComparator implements Comparator<Task> {
    public int compare(Task o1, Task o2) {
        long dif = o1.getTime() - o2.getTime();
        return (int) dif;
    }
}
