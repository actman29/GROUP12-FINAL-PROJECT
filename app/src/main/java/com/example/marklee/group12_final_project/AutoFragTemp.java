/* File name: AutoFragTemp.java
 * Author: Maga Lee, 040852763
 * Course: CST2335
 * Date: Apr 15, 2017
 * Professor: Eric Torunski
 * Purpose: This file is used to list items that are managed in this application.
 */

package com.example.marklee.group12_final_project;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class is the automobile main class which displays 4 items using ListView, including help dialog.
 * @author Maga Lee
 * @version 1.0
 */
public class AutoFragTemp extends Fragment {

    protected Context parent;
    protected View view;

    //Variables related to the database
    protected AutoDatabaseHelper autoDBHelper;
    protected SQLiteDatabase db;
    protected ContentValues newValues;
    protected int keyID;

    //Variables related to the temperature
    protected final double MAX_TEMP = 28.0;
    protected final double MIN_TEMP = 16.0;
    protected TextView tempValue;
    protected double curTemp;
    protected ImageButton tempUp;
    protected ImageButton tempDown;
    protected Button addTemp;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parent = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle bun) {

        bun = getArguments();
        curTemp = bun.getDouble("curTemp");
        keyID = bun.getInt("keyID");

        autoDBHelper = new AutoDatabaseHelper(parent);
        db = autoDBHelper.getWritableDatabase();

        view = inflater.inflate(R.layout.temperature_layout, null);

        tempValue = (TextView) view.findViewById(R.id.tempValue);
        tempUp = (ImageButton) view.findViewById(R.id.tempUp);
        tempDown = (ImageButton) view.findViewById(R.id.tempDown);
        curTemp = curTemp == 0 ? Double.parseDouble(tempValue.getText().toString()) : curTemp;
        tempValue.setText(Double.toString(curTemp));
        addTemp = (Button) view.findViewById(R.id.addTemperature);

        tempUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (curTemp < MAX_TEMP) {
                curTemp += 0.5;
                tempValue.setText(curTemp + "");
            }
            }
        });

        tempDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (curTemp > MIN_TEMP) {
                curTemp -= 0.5;
                tempValue.setText(curTemp + "");
            }
            }
        });

        addTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(parent);
                builder.setTitle(R.string.dialog_addTemp);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        newValues = new ContentValues();
                        newValues.put(autoDBHelper.COL_TEMPERATURE, Double.parseDouble(tempValue.getText().toString()));
                        db.update(autoDBHelper.tableName, newValues, autoDBHelper.KEY_ID + "=?", new String[]{String.valueOf(keyID)});
                        Toast.makeText(parent, getResources().getString(R.string.msg_temp)+" "+
                                               getResources().getString(R.string.msg_setConfirm), Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                builder.create().show();
            }
        });

        return view;
    }
}
