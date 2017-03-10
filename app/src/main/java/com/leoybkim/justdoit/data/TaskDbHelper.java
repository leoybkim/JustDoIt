package com.leoybkim.justdoit.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by leo on 03/03/17.
 */

public class TaskDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 2;

    public TaskDbHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TASKS_TABLE =  "CREATE TABLE " + TaskContract.TaskEntry.TABLE_NAME + " ("
                + TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TaskContract.TaskEntry.COLUMN_TASK_DESCRIPTION + " TEXT, "
                + TaskContract.TaskEntry.COLUMN_TASK_DUE_DATE + " TEXT, "
                + TaskContract.TaskEntry.COLUMN_TASK_PRIORITY + " TEXT);";
        db.execSQL(SQL_CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
