package com.example.marklee.group12_final_project;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class SetTime extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);


//        mPicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @Override
//            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//
//            }
//        });
        Button setTime = (Button) findViewById(R.id.setTime);
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent(  );
                TimePicker mPicker = (TimePicker)findViewById(R.id.timePicker);
                resultIntent.putExtra("resultStr", ""+mPicker.getHour() +":" + mPicker.getMinute());
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

            }
        });
    }


}
