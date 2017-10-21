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

    public void buttonClick(View view){

        Intent i = new Intent(this, MyIntentService.class);
        i.putExtra(MyIntentService.PARAM_IN_MSG, "message here");
        startService(i);

        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
    }
}
