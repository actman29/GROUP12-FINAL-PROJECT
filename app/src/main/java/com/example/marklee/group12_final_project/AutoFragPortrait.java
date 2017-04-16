/* File name: AutoFragPortrait.java
 * Author: Maga Lee, 040852763
 * Course: CST2335
 * Date: Apr 15, 2017
 * Professor: Eric Torunski
 * Purpose: This file is used to list items that are managed in this application.
 */

package com.example.marklee.group12_final_project;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * This class is the automobile main class which displays 4 items using ListView, including help dialog.
 * @author Maga Lee
 * @version 1.0
 */
public class AutoFragPortrait extends AppCompatActivity {
    protected int itemNum;

    //Variables related to the database
    protected AutoDatabaseHelper autoDBHelper;
    protected SQLiteDatabase db;
    protected Cursor results;
    protected ContentValues newValues;
    protected int keyID;

    protected double curTemp;
    protected double preset1Freg;
    protected double preset2Freg;
    protected double preset3Freg;
    protected double preset4Freg;
    protected double preset5Freg;
    protected double preset6Freg;
    protected String normalOnOff;
    protected String highOnOff;
    protected String dimmOnOff;

    protected AutoFragTemp fragTemp;
    protected AutoFragRadio fragRadio;
    protected AutoFragGPS fragGPS;
    protected AutoFragLights fragLights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_portrait);

        openDatabase thread = new openDatabase();
        thread.execute();
    }

    private class openDatabase extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... args)

        {
            String in = "";
            try {
                autoDBHelper = new AutoDatabaseHelper(AutoFragPortrait.this);
                db = autoDBHelper.getWritableDatabase();

                results = db.query(false, autoDBHelper.tableName, new String[] {autoDBHelper.KEY_ID, autoDBHelper.COL_TEMPERATURE,
                        autoDBHelper.COL_RADIO_PRESET1, autoDBHelper.COL_RADIO_PRESET2, autoDBHelper.COL_RADIO_PRESET3,
                        autoDBHelper.COL_RADIO_PRESET4, autoDBHelper.COL_RADIO_PRESET5, autoDBHelper.COL_RADIO_PRESET6,
                        autoDBHelper.COL_RADIO_VOL, autoDBHelper.COL_RADIO_MUTE, autoDBHelper.COL_NORMALLIGHT, autoDBHelper.COL_HIGHBEAM,
                        autoDBHelper.COL_DIMMABLE_lIGHT }, null, null, null, null, null, null);
                results.moveToFirst();

                if(results.getCount() == 0) {
                    newValues = new ContentValues();
                    newValues.put(autoDBHelper.COL_RADIO_MUTE, "OFF");
                    newValues.put(autoDBHelper.COL_NORMALLIGHT, "OFF");
                    newValues.put(autoDBHelper.COL_HIGHBEAM, "OFF");
                    newValues.put(autoDBHelper.COL_DIMMABLE_lIGHT, "OFF");
                    db.insert(autoDBHelper.tableName, "", newValues);
                }

                while(!results.isAfterLast()) {
                    keyID = results.getInt(results.getColumnIndex(autoDBHelper.KEY_ID));
                    curTemp = results.getDouble(results.getColumnIndex(autoDBHelper.COL_TEMPERATURE));
                    preset1Freg = results.getDouble(results.getColumnIndex(autoDBHelper.COL_RADIO_PRESET1));
                    preset2Freg = results.getDouble(results.getColumnIndex(autoDBHelper.COL_RADIO_PRESET2));
                    preset3Freg = results.getDouble(results.getColumnIndex(autoDBHelper.COL_RADIO_PRESET3));
                    preset4Freg = results.getDouble(results.getColumnIndex(autoDBHelper.COL_RADIO_PRESET4));
                    preset5Freg = results.getDouble(results.getColumnIndex(autoDBHelper.COL_RADIO_PRESET5));
                    preset6Freg = results.getDouble(results.getColumnIndex(autoDBHelper.COL_RADIO_PRESET6));
                    normalOnOff = results.getString(results.getColumnIndex(autoDBHelper.COL_NORMALLIGHT));
                    highOnOff = results.getString(results.getColumnIndex(autoDBHelper.COL_HIGHBEAM));
                    dimmOnOff = results.getString(results.getColumnIndex(autoDBHelper.COL_DIMMABLE_lIGHT));
                    results.moveToNext();
                }

            } catch (Exception me) {  }
            return in;
        }

        public void onProgressUpdate(Integer... value) {

        }

        public void onPostExecute(String work) {
            Bundle bun = getIntent().getExtras();
            itemNum = bun.getInt("itemNum");
            Bundle arg = new Bundle();

            if(itemNum == 0) {
                fragTemp = new AutoFragTemp();
                arg.putDouble("curTemp", curTemp);
                arg.putInt("keyID", keyID);
                fragTemp.setArguments(arg);
                getFragmentManager().beginTransaction().add(R.id.autoFrameLayout, fragTemp).commit();
            } else if(itemNum == 1) {
                fragRadio = new AutoFragRadio();
                arg.putDouble("preset1Freg", preset1Freg);
                arg.putDouble("preset2Freg", preset2Freg);
                arg.putDouble("preset3Freg", preset3Freg);
                arg.putDouble("preset4Freg", preset4Freg);
                arg.putDouble("preset5Freg", preset5Freg);
                arg.putDouble("preset6Freg", preset6Freg);
                arg.putInt("keyID", keyID);
                fragRadio.setArguments(arg);
                getFragmentManager().beginTransaction().add(R.id.autoFrameLayout, fragRadio).commit();
            } else if(itemNum == 2) {
                fragGPS = new AutoFragGPS();
                getFragmentManager().beginTransaction().add(R.id.autoFrameLayout, fragGPS).commit();
            } else if(itemNum == 3) {
                fragLights = new AutoFragLights();
                arg.putString("normalOnOff", normalOnOff);
                arg.putString("highOnOff", highOnOff);
                arg.putString("dimmOnOff", dimmOnOff);
                arg.putInt("keyID", keyID);
                fragLights.setArguments(arg);
                getFragmentManager().beginTransaction().add(R.id.autoFrameLayout, fragLights).commit();
            }
        }
    }
}
