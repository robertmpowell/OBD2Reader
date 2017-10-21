package com.garmin.android.apps.connectiq.sample.comm;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.garmin.android.apps.connectiq.sample.comm.adapter.IQDeviceAdapter;
import com.garmin.android.connectiq.ConnectIQ;
import com.garmin.android.connectiq.IQDevice;
import com.garmin.android.connectiq.exception.InvalidStateException;
import com.garmin.android.connectiq.exception.ServiceUnavailableException;

import java.util.List;

/**
 * Created by Cole on 10/21/2017.
 */

public class MyIntentService extends IntentService {

    public static final String PARAM_IN_MSG = "imsg";
    public static final String PARAM_OUT_MSG = "omsg";


    private ConnectIQ mConnectIQ;
    private IQDeviceAdapter mAdapter;
    List<IQDevice> devices;

    public MyIntentService(){
        super("MyIntentService");

        mConnectIQ = ConnectIQ.getInstance(this, ConnectIQ.IQConnectType.WIRELESS);
        mConnectIQ.initialize(getBaseContext(), true, mListener);
        loadDevices();


    }

    @Override
    protected void onHandleIntent(Intent intent){

//        String s = intent.getStringExtra(PARAM_IN_MSG);

        IQDevice device = devices.get(0);
        System.out.println("");
    }

    private ConnectIQ.ConnectIQListener mListener = new ConnectIQ.ConnectIQListener() {
            @Override
            public void onInitializeError(ConnectIQ.IQSdkErrorStatus errStatus) {
                Log.d("error","error");
            }

            @Override
            public void onSdkReady() {
                Log.d("error","error");
            }

            @Override
            public void onSdkShutDown() {
            Log.d("error","error");
        }
    };

    public void loadDevices() {
        // Retrieve the list of known devices
        try {
            devices = mConnectIQ.getKnownDevices();

//            if (devices != null) {
//                mAdapter.setDevices(devices);
//
//                // Let's register for device status updates.  By doing so we will
//                // automatically get a status update for each device so we do not
//                // need to call getStatus()
//                for (IQDevice device : devices) {
//                    mConnectIQ.registerForDeviceEvents(device, mDeviceEventListener);
//                }
//            }

        } catch (InvalidStateException e) {
            // This generally means you forgot to call initialize(), but since
            // we are in the callback for initialize(), this should never happen
        } catch (ServiceUnavailableException e) {
            // This will happen if for some reason your app was not able to connect
            // to the ConnectIQ service running within Garmin Connect Mobile.  This
            // could be because Garmin Connect Mobile is not installed or needs to
            // be upgraded.
            Log.d("error", "loadDevices: Error");
        }
    }


}
