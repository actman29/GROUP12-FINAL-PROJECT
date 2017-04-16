package com.example.marklee.group12_final_project;

import android.content.Context;
import android.database.Cursor;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.marklee.group12_final_project.ScheduleDatabaseHelper.TABLE_NAME;

public class HouseSettings extends AppCompatActivity {

    public static final String ACTIVITY_VERSION = "1.0.0";

//    ArrayList<TempSchedule> listItems;
//    TestAdapter<TempSchedule> adapter;

    GarageFragment      frGarage;
    HouseTempFragment   frHouseTemperature;
    WeatherFragment     frWeather;
    ScheduleFragment    frSchedule;
    VersionFragment     frVersion;
    ConstraintSet csSetting = new ConstraintSet();
    ConstraintSet csGarage = new ConstraintSet();
    ConstraintSet csTempeture = new ConstraintSet();
    ConstraintSet csWeather = new ConstraintSet();
    ConstraintSet csSchedule = new ConstraintSet();
    ConstraintSet csVersion = new ConstraintSet();
    private ConstraintLayout mConstraintLayout;
    int setState = 0;

    public boolean onCreateOptionsMenu(Menu m)
    {
        getMenuInflater().inflate(R.menu.toolbar, m );
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem mi){
        int id = mi.getItemId();
        switch(id)
        {
            case R.id.version:
                TransitionManager.beginDelayedTransition(mConstraintLayout);
                    csVersion.applyTo(mConstraintLayout);
                    setState = 5;
                break;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        csSetting.clone(getApplicationContext(), R.layout.setting_default);
        csGarage.clone(getApplicationContext(), R.layout.setting_garage);
        csTempeture.clone(getApplicationContext(), R.layout.setting_tempeture);
        csWeather.clone(getApplicationContext(), R.layout.setting_weather);
        csSchedule.clone(getApplicationContext(), R.layout.setting_schedule);
        csVersion.clone(getApplicationContext(), R.layout.setting_version);
        setContentView(R.layout.activity_setting);
        mConstraintLayout = (ConstraintLayout)findViewById(R.id.activity_setting);
        TransitionManager.beginDelayedTransition(mConstraintLayout);
        csSetting.applyTo(mConstraintLayout);

        frGarage = new GarageFragment();
        getFragmentManager().beginTransaction().replace(R.id.frame_garage, frGarage).commit();
        frHouseTemperature = new HouseTempFragment();
        getFragmentManager().beginTransaction().replace(R.id.frame_house_temperature, frHouseTemperature).commit();
        frWeather = new WeatherFragment();
        getFragmentManager().beginTransaction().replace(R.id.frame_outside_weather, frWeather).commit();
        frSchedule = new ScheduleFragment();
        getFragmentManager().beginTransaction().replace(R.id.frame_schedule, frSchedule).commit();
        frVersion = new VersionFragment();
        getFragmentManager().beginTransaction().replace(R.id.frame_version, frVersion).commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        listItems = new ArrayList<>();
//        adapter = new TestAdapter<>(this);
//        ListView msgView = (ListView) findViewById(R.id.scheduleList);
//        msgView.setAdapter(adapter);

        Button garageButton = (Button) findViewById(R.id.bt_garage);
        garageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(mConstraintLayout);
                if(setState != 1){
                    csGarage.applyTo(mConstraintLayout);
                    setState = 1;
                }
                else{
                    csSetting.applyTo(mConstraintLayout);
                    setState = 0;
                }
            }
        });

        Button tempetureButton = (Button) findViewById(R.id.bt_house_temperature);
        tempetureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(mConstraintLayout);
                if(setState != 2){
                    csTempeture.applyTo(mConstraintLayout);
                    setState = 2;
                }
                else{
                    csSetting.applyTo(mConstraintLayout);
                    setState = 0;
                }
            }
        });
        Button weatherButton = (Button) findViewById(R.id.bt_outside_weather);
        weatherButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(mConstraintLayout);
                if(setState !=3){
                    csWeather.applyTo(mConstraintLayout);
                    setState = 3;
                }
                else{
                    csSetting.applyTo(mConstraintLayout);
                    setState = 0;
                }
            }
        });
        Button scheduleButton = (Button) findViewById(R.id.bt_schedule);
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(mConstraintLayout);
                if(setState !=4){
                    csSchedule.applyTo(mConstraintLayout);
                    setState = 4;
                }
                else{
                    csSetting.applyTo(mConstraintLayout);
                    setState = 0;
                }
            }
        });
    }
//    private class TestAdapter<TempSchedule> extends ArrayAdapter<TempSchedule> {
//        public TestAdapter(Context ctx) {
//            super(ctx, 0);
//        }
//
//        public long getItemId(int position){
////            ScheduleDatabaseHelper dbHelper = new ScheduleDatabaseHelper(this.getContext());
////            mydb = dbHelper.getReadableDatabase();
////            Cursor cursor = mydb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
////            cursor.moveToPosition(position);
////            return cursor.getLong(0);
//              return 3;
//        }
//
//        public int getCount(){
//            return listItems.size();
//        }
//
//        public TempSchedule getItem(int position){
//            return null;//(TempSchedule)listItems.get(position);
//        }
//
//        public View getView(int position, View convertView, ViewGroup parent){
//            LayoutInflater inflater = getLayoutInflater();
//            View result = inflater.inflate(R.layout.schedule_item, null);
//
//            TextView tempeture = (TextView)result.findViewById(R.id.temperature_text);
//            tempeture.setText(   getItem(position).toString()  ); // get the string at position
//            return result;
//        }
//    }
//    public class TempSchedule{
//        public String temperature;
//        public String time;
//        TempSchedule(String temp, String tm){
//            temperature = temp;
//            time = tm;
//        }
//    }
}

