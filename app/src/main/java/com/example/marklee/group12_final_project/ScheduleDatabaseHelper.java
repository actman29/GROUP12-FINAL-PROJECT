package com.example.marklee.group12_final_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Andrew on 2017-04-10.
 */

public class ScheduleDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbLab5.db";
    private static final int DATABASE_VERSION = 2;
    final static String TABLE_NAME = "house_schedule_t";
    final static String ACTIVITY_NAME = "HouseSettings";
    static String KEY_ID = "id";
    static String TEMPETURE = "temperature";
    static String SCHEDULE_TIME = "schedule_time";


    public ScheduleDatabaseHelper(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE house_schedule_t(" +
                KEY_ID + " INTEGER primary key, " +
                SCHEDULE_TIME + " TEXT, " +
                TEMPETURE + " TEXT);");
        Log.i("ScheduleDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("ScheduleDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + "newVersion=" + newVersion);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("ScheduleDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + "newVersion=" + newVersion);
    }
}
