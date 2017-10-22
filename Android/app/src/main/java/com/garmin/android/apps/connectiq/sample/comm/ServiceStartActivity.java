package com.garmin.android.apps.connectiq.sample.comm;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class ServiceStartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_start);
    }

    // Method that executes when button is pressed. FOR TESTING PURPOSES.
    public void buttonClick(View view){
        // Initialize fake bundle and send it
        Bundle b = new Bundle();
//        b.putString(MyIntentService.TACH, "1548");
//        b.putString(MyIntentService.SPEED, "018");
//        b.putString(MyIntentService.THROTTLE, "40%");
//        b.putString(MyIntentService.OIL_TEMP, "168F");
//        b.putString(MyIntentService.FUEL_LEVEL, "82%");
//        b.putString(MyIntentService.FUEL_CONSUMPTION, "0.08 GPM");
        b.putDouble(MyIntentService.TACH, 2300);
        b.putDouble(MyIntentService.SPEED, 75);
        b.putDouble(MyIntentService.THROTTLE, 40);
        b.putDouble(MyIntentService.OIL_TEMP, 182);
        b.putDouble(MyIntentService.FUEL_LEVEL, 82);
        b.putDouble(MyIntentService.FUEL_CONSUMPTION, 0.1);

        sendMessageWithService(b);

//        Toast.makeText(this, "Beginning tach/speed cycle", Toast.LENGTH_SHORT).show();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        double tach, speed;

        for(int i = 0; i <= 7; i++){
            tach = i * 1000;
            speed = i * 10;
            b.clear();
            b.putDouble(MyIntentService.TACH, tach);
            b.putDouble(MyIntentService.SPEED, speed);
            b.putDouble(MyIntentService.THROTTLE, 40);
            b.putDouble(MyIntentService.OIL_TEMP, 182);
            b.putDouble(MyIntentService.FUEL_LEVEL, 82);
            b.putDouble(MyIntentService.FUEL_CONSUMPTION, 0.1);

            sendMessageWithService(b);
            Log.d(TAG, "Sent that message! i = " + i);

//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }


    }

    // Method to send one message to the watch from a bundle.
    public void sendMessageWithService(Bundle b){
        Intent i = new Intent(this, MyIntentService.class);
//        i.putBundleExtra("bundle");
        i.putExtra("bundle", b);
        startService(i);
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
    }
}
