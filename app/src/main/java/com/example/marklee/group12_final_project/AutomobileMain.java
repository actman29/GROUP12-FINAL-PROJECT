/* File name: AutomobileMain.java
 * Author: Maga Lee, 040852763
 * Course: CST2335
 * Date: Apr 15, 2017
 * Professor: Eric Torunski
 * Purpose: This file is used to list items that are managed in this application.
 */

package com.example.marklee.group12_final_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * This class is the automobile main class which displays 4 items using ListView, including help dialog.
 * @author Maga Lee
 * @version 1.0
 */
public class AutomobileMain extends AppCompatActivity {

    protected ListView autoItemList;
    protected ArrayAdapter adapter;
    protected String[] dataItems;
    protected ImageView helpInfo;
    protected AlertDialog.Builder helpDialog;
    protected LayoutInflater helpInflater;
    protected View helpView;
    protected ProgressBar startProgress;
    protected TextView initText;
    protected TextView instTemp;
    protected TextView instRadio;
    protected TextView instGPS;
    protected TextView instLight;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobile_main);

        helpInfo = (ImageView) findViewById(R.id.helpInfo);
        startProgress = (ProgressBar) findViewById(R.id.startprogress);
        initText = (TextView) findViewById(R.id.initText);

        dataItems = new String[4];
        dataItems[0] = getString(R.string.menu_temp);
        dataItems[1] = getString(R.string.menu_radio);
        dataItems[2] = getString(R.string.menu_GPS);
        dataItems[3] = getString(R.string.menu_light);

        autoItemList = (ListView) findViewById(R.id.autoItem);

        initProgram thread = new initProgram();
        thread.execute();
    }

    private class initProgram extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... args)

        {
            String in = "";
            try {

                publishProgress(25);
                Thread.sleep(1000);
                publishProgress(50);
                Thread.sleep(1000);
                publishProgress(75);
                Thread.sleep(1000);
                publishProgress(100);
                Thread.sleep(1000);

            } catch (Exception me) {  }
            return in;
        }

        public void onProgressUpdate(Integer... value) {
            startProgress.setProgress(value[0]);
        }

        public void onPostExecute(String work) {

            startProgress.setVisibility(View.INVISIBLE);
            initText.setVisibility(View.INVISIBLE);
            helpInfo.setVisibility(View.VISIBLE);

            adapter = new ArrayAdapter(AutomobileMain.this, R.layout.auto_row_layout, dataItems);
            autoItemList.setAdapter(adapter);

            autoItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent intent = new Intent(AutomobileMain.this, AutoFragPortrait.class);
                    intent.putExtra("itemNum", i);
                    startActivity(intent);

                }
            });

            helpInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    helpDialog = new AlertDialog.Builder(AutomobileMain.this);
                    helpInflater = LayoutInflater.from(AutomobileMain.this);
                    helpView = helpInflater.inflate(R.layout.dialog_autohelp, null);

                    instTemp = (TextView) helpView.findViewById(R.id.instTemp);
                    instTemp.setText(R.string.instTemp);

                    instRadio = (TextView) helpView.findViewById(R.id.instRadio);
                    instRadio.setText(R.string.instRadio);

                    instGPS = (TextView) helpView.findViewById(R.id.instGPS);
                    instGPS.setText(R.string.instGPS);

                    instLight = (TextView) helpView.findViewById(R.id.instLight);
                    instLight.setText(R.string.instLight);

                    helpDialog.setView(helpView)
                              .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialog, int id) {
                                  }
                              });
                    helpDialog.create().show();
                }
            });
        }
    }
}
