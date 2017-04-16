/* File name: AutoFragGPS.java
 * Author: Maga Lee, 040852763
 * Course: CST2335
 * Date: Apr 15, 2017
 * Professor: Eric Torunski
 * Purpose: This file is used to list items that are managed in this application.
 */

package com.example.marklee.group12_final_project;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * This class is the automobile main class which displays 4 items using ListView, including help dialog.
 * @author Maga Lee
 * @version 1.0
 */
public class AutoFragGPS extends Fragment {

    protected Context parent;
    protected View view;

    //Variables related to the navigation
    protected EditText destText;
    protected ImageView gpsStart;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle bun) {

        view = inflater.inflate(R.layout.gps_layout, null);
        destText = (EditText) view.findViewById(R.id.destText);
        gpsStart = (ImageView) view.findViewById(R.id.gpsStart);

        gpsStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!destText.getText().toString().equals("")) {
                    // Create a Uri from an intent string. Use the result to create an Intent.
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + destText.getText().toString());
                    // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    // Make the Intent explicit by setting the Google Maps package
                    mapIntent.setPackage("com.google.android.apps.maps");
                    // Attempt to start an activity that can handle the Intent
                    startActivity(mapIntent);
                    destText.setText("");
                }
            }
        });

        return view;
    }
}