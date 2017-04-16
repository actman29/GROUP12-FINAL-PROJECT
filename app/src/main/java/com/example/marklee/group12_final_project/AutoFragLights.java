/* File name: AutoFragLights.java
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
import android.widget.Switch;
import android.widget.Toast;

/**
 * This class is the automobile main class which displays 4 items using ListView, including help dialog.
 * @author Maga Lee
 * @version 1.0
 */
public class AutoFragLights extends Fragment {

    protected Context parent;
    protected View view;

    //Variables related to the database
    protected AutoDatabaseHelper autoDBHelper;
    protected SQLiteDatabase db;
    protected ContentValues newValues;
    protected int keyID;

    //Variables related to the lights
    protected Switch normalSwitch;
    protected Switch highSwitch;
    protected Switch dimmSwitch;
    protected Button addLight;
    protected String normalOnOff;
    protected String highOnOff;
    protected String dimmOnOff;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parent = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle bun) {

        bun = getArguments();
        normalOnOff = bun.getString("normalOnOff");
        highOnOff = bun.getString("highOnOff");
        dimmOnOff = bun.getString("dimmOnOff");
        keyID = bun.getInt("keyID");

        autoDBHelper = new AutoDatabaseHelper(parent);
        db = autoDBHelper.getWritableDatabase();

        view = inflater.inflate(R.layout.lights_layout, null);

        normalSwitch = (Switch) view.findViewById(R.id.normalSwitch);
        highSwitch = (Switch) view.findViewById(R.id.highSwitch);
        dimmSwitch = (Switch) view.findViewById(R.id.dimmSwitch);
        addLight = (Button) view.findViewById(R.id.addLight);

        if(normalOnOff.equals("ON")) normalSwitch.setChecked(true);
        if(highOnOff.equals("ON")) highSwitch.setChecked(true);
        if(dimmOnOff.equals("ON")) dimmSwitch.setChecked(true);

        addLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(parent);
                builder.setTitle(R.string.dialog_addLight);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        newValues = new ContentValues();
                        newValues.put(autoDBHelper.COL_NORMALLIGHT, normalSwitch.isChecked()?"ON":"OFF");
                        newValues.put(autoDBHelper.COL_HIGHBEAM, highSwitch.isChecked()?"ON":"OFF");
                        newValues.put(autoDBHelper.COL_DIMMABLE_lIGHT, dimmSwitch.isChecked()?"ON":"OFF");
                        db.update(autoDBHelper.tableName, newValues, autoDBHelper.KEY_ID + "=?", new String[] {String.valueOf(keyID)});
                        Toast.makeText(parent, getResources().getString(R.string.msg_light)+" "+
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