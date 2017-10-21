package com.garmin.android.apps.connectiq.sample.comm;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
        b.putString(MyIntentService.TACH, "1548");
        b.putString(MyIntentService.SPEED, "018");
        b.putString(MyIntentService.THROTTLE, "40%");
        b.putString(MyIntentService.OIL_TEMP, "168F");
        b.putString(MyIntentService.FUEL_LEVEL, "82%");
        b.putString(MyIntentService.FUEL_CONSUMPTION, "0.08 GPM");

        sendMessageWithService(b);
    }

    // Method to send one message to the watch from a bundle.
    public void sendMessageWithService(Bundle b){
        Intent i = new Intent(this, MyIntentService.class);
        i.putExtras(b);
        startService(i);
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
    }
}
