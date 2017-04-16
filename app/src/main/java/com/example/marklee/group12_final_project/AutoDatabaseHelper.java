/* File name: AutoDatabaseHelper.java
 * Author: Maga Lee, 040852763
 * Course: CST2335
 * Date: Apr 15, 2017
 * Professor: Eric Torunski
 * Purpose: This file is used to list items that are managed in this application.
 */

package com.example.marklee.group12_final_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class is the automobile main class which displays 4 items using ListView, including help dialog.
 * @author Maga Lee
 * @version 1.0
 */
public class AutoDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "AutomobileDB";
    private static final int VERSION_NUM = 1;

    public static final String tableName = "Automobile";
    public static final String KEY_ID = "_id";
    public static final String COL_TEMPERATURE = "TEMPERATURE";
    public static final String COL_RADIO_PRESET1 = "RADIO_PRESET1";
    public static final String COL_RADIO_PRESET2 = "RADIO_PRESET2";
    public static final String COL_RADIO_PRESET3 = "RADIO_PRESET3";
    public static final String COL_RADIO_PRESET4 = "RADIO_PRESET4";
    public static final String COL_RADIO_PRESET5 = "RADIO_PRESET5";
    public static final String COL_RADIO_PRESET6 = "RADIO_PRESET6";
    public static final String COL_RADIO_VOL = "RADIO_VOLUME";
    public static final String COL_RADIO_MUTE = "RADIO_MUTE";
    public static final String COL_NORMALLIGHT = "NORMALLIGHT";
    public static final String COL_HIGHBEAM = "COL_HIGHBEAM";
    public static final String COL_DIMMABLE_lIGHT = "DIMMABLE_LIGHT";

    public AutoDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        db.execSQL("CREATE TABLE " + tableName + "(" +
                    KEY_ID + " INTEGER PRIMARY KEY, " + COL_TEMPERATURE + " REAL, " +
                    COL_RADIO_PRESET1 + " REAL, " + COL_RADIO_PRESET2 + " REAL, " +
                    COL_RADIO_PRESET3 + " REAL, " + COL_RADIO_PRESET4 + " REAL, " +
                    COL_RADIO_PRESET5 + " REAL, " + COL_RADIO_PRESET6 + " REAL, " +
                    COL_RADIO_VOL + " INTEGER, " + COL_RADIO_MUTE + " TEXT, " +
                    COL_NORMALLIGHT + " TEXT, " + COL_HIGHBEAM + " TEXT, " + COL_DIMMABLE_lIGHT + " TEXT);" );
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion="+oldVersion + "newVersion="+newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }
}
