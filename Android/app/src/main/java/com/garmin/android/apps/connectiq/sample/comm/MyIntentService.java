package com.garmin.android.apps.connectiq.sample.comm;

import android.app.AlertDialog;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.garmin.android.apps.connectiq.sample.comm.adapter.IQDeviceAdapter;
import com.garmin.android.connectiq.ConnectIQ;
import com.garmin.android.connectiq.IQApp;
import com.garmin.android.connectiq.IQDevice;
import com.garmin.android.connectiq.exception.InvalidStateException;
import com.garmin.android.connectiq.exception.ServiceUnavailableException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by Cole on 10/21/2017.
 */

public class MyIntentService extends IntentService {

    public static final String PARAM_IN_MSG = "imsg";
    public static final String PARAM_OUT_MSG = "omsg";

    public static final String TACH = "tach";
    public static final String SPEED = "speed";
    public static final String THROTTLE = "throttle";
    public static final String OIL_TEMP = "oil_temp";
    public static final String FUEL_LEVEL = "fuel_level";
    public static final String FUEL_CONSUMPTION = "fuel_consumption";
    public static final String ALERT = "alert";

    private ConnectIQ mConnectIQ;
    private IQDeviceAdapter mAdapter;
    List<IQDevice> devices;
    private IQApp mMyApp;
    private boolean mAppIsOpen;
    public static final String MY_APP = "7074f62305c345b3828e4324f340ab82";

    private boolean initialized = false;
    IQDevice mDevice;


    public MyIntentService(){
        super("MyIntentService");

        mConnectIQ = ConnectIQ.getInstance(this, ConnectIQ.IQConnectType.WIRELESS);
//        mConnectIQ.initialize(getApplicationContext(), true, mListener);
//        loadDevices();


    }

    @Override
    protected void onHandleIntent(Intent intent){

        if(!initialized){
            mConnectIQ.initialize(getApplicationContext(), true, mListener);
            loadDevices();

            mDevice = devices.get(0);

            // Ask the watch to open our application
            mMyApp = new IQApp(MY_APP);
            try {
                mConnectIQ.openApplication(mDevice, mMyApp, mOpenAppListener);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // Begin transmission sequence if device is present.
        if (mDevice != null) {
            // Get our instance of ConnectIQ (reference the one created)
//            mConnectIQ = ConnectIQ.getInstance();

            if(!initialized){
                try {
                    mConnectIQ.registerForDeviceEvents(mDevice, new ConnectIQ.IQDeviceEventListener() {
                        @Override
                        public void onDeviceStatusChanged(IQDevice device, IQDevice.IQDeviceStatus status) {
                            // Since we will only get updates for this device, just display the status
                            Log.d(TAG, "onDeviceStatusChanged: " + status.name());
                        }
                    });
                } catch (InvalidStateException e) {
                    Log.wtf(TAG, "InvalidStateException:  We should not be here!");
                }
            }

            // Let's check the status of our application on the device.
            try {
                mConnectIQ.getApplicationInfo(MY_APP, mDevice, new ConnectIQ.IQApplicationInfoListener() {
                    @Override
                    public void onApplicationInfoReceived(IQApp app) {
                        // This is a good thing. Now we can show our list of message options.
                        // Send a message to open the app
                        try {
                            Toast.makeText(getApplicationContext(), "Opening app...", Toast.LENGTH_SHORT).show();
//                            mConnectIQ.openApplication(mDevice, app, mOpenAppListener);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    @Override
                    public void onApplicationNotInstalled(String applicationId) {
                        // The Comm widget is not installed on the device.
                        String e = "Error: Install the App on the watch first!";
                        Toast.makeText(MyIntentService.this, e, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onApplicationNotInstalled: " + e);
                    }
                });
            } catch (InvalidStateException e1) {
            } catch (ServiceUnavailableException e1) {
            }
        }

        // Sends the message
        try {
            // Constructs the Map to send from the bundle
            Bundle b = intent.getBundleExtra("bundle");
//            Map<String, Object> dictionary = new HashMap<String, Object>();
//            dictionary.put(TACH, b.getDouble(TACH));
//            dictionary.put(SPEED, b.getDouble(SPEED));
//            dictionary.put(THROTTLE, b.getDouble(THROTTLE));
//            dictionary.put(OIL_TEMP, b.getDouble(OIL_TEMP));
//            dictionary.put(FUEL_LEVEL, b.getDouble(FUEL_LEVEL));
//            dictionary.put(FUEL_CONSUMPTION, b.getDouble(FUEL_CONSUMPTION));

            List<Object> list = new ArrayList<Object>();
            list.add(b.getDouble(SPEED));
            list.add(b.getDouble(TACH));
            list.add(b.getDouble(THROTTLE));
            list.add(b.getDouble(OIL_TEMP));
            list.add(b.getDouble(FUEL_LEVEL));
            list.add(b.getDouble(FUEL_CONSUMPTION));
            //list.add(b.getDouble(ALERT));

            // Sends the message
            mConnectIQ.sendMessage(mDevice, mMyApp, list, new ConnectIQ.IQSendMessageListener() {

                @Override
                public void onMessageStatus(IQDevice device, IQApp app, ConnectIQ.IQMessageStatus status) {
                    Log.d(TAG, "onMessageStatus: " + status.name());
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            double tach, speed;
//
//            for(int i = 0; i <= 7; i++){
//                tach = i * 1000;
//                speed = i * 10;
////                dictionary.put(SPEED, speed);
//                list.set(0, speed);
//                list.set(1, tach);
//
//                Log.d(TAG, "Sent that message! i = " + i);
//
//                // Sends the message
//                mConnectIQ.sendMessage(mDevice, mMyApp, list, new ConnectIQ.IQSendMessageListener() {
//
//                    @Override
//                    public void onMessageStatus(IQDevice device, IQApp app, ConnectIQ.IQMessageStatus status) {
//                        Log.d(TAG, "onMessageStatus: " + status.name());
//                    }
//                });
//
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }

        } catch (InvalidStateException e) {
            Toast.makeText(this, "ConnectIQ is not in a valid state", Toast.LENGTH_SHORT).show();
        } catch (ServiceUnavailableException e) {
            Toast.makeText(this, "ConnectIQ service is unavailable.   Is Garmin Connect Mobile installed and running?", Toast.LENGTH_LONG).show();
        }




        if(!initialized){
            // Let's register to receive messages from our application on the device.
            try {
                mConnectIQ.registerForAppEvents(mDevice, mMyApp, new ConnectIQ.IQApplicationEventListener() {

                    @Override
                    public void onMessageReceived(IQDevice device, IQApp app, List<Object> message, ConnectIQ.IQMessageStatus status) {

                        // We know from our Comm sample widget that it will only ever send us strings, but in case
                        // we get something else, we are simply going to do a toString() on each object in the
                        // message list.
                        StringBuilder builder = new StringBuilder();

                        if (message.size() > 0) {
                            for (Object o : message) {
                                builder.append(o.toString());
                                builder.append("\r\n");
                            }
                        } else {
                            builder.append("Received an empty message from the application");
                        }

                        AlertDialog.Builder dialog = new AlertDialog.Builder(MyIntentService.this);
                        dialog.setTitle(R.string.received_message);
                        dialog.setMessage(builder.toString());
                        dialog.setPositiveButton(android.R.string.ok, null);
                        dialog.create().show();
                    }

                });
            } catch (InvalidStateException e) {
                Toast.makeText(this, "ConnectIQ is not in a valid state", Toast.LENGTH_SHORT).show();
            }
        }
        initialized = true;
    }

    // Listener to get statuses.
    private ConnectIQ.ConnectIQListener mListener = new ConnectIQ.ConnectIQListener() {
            @Override
            public void onInitializeError(ConnectIQ.IQSdkErrorStatus errStatus) {
                Log.d(TAG, "onInitializeError: ");
            }

            @Override
            public void onSdkReady() {
                Log.d(TAG, "onSdkReady: ");
            }

            @Override
            public void onSdkShutDown() {
                Log.d(TAG, "onSdkShutDown: ");
        }
    };

    // Gets devices from the internal list of known devices.
    public void loadDevices() {
        // Retrieve the list of known devices
        try {
            devices = mConnectIQ.getKnownDevices();
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

    // Catches status of the application.
    private ConnectIQ.IQOpenApplicationListener mOpenAppListener = new ConnectIQ.IQOpenApplicationListener() {
        @Override
        public void onOpenApplicationResponse(IQDevice device, IQApp app, ConnectIQ.IQOpenApplicationStatus status) {
            Toast.makeText(getApplicationContext(), "App Status: " + status.name(), Toast.LENGTH_SHORT).show();

            if (status == ConnectIQ.IQOpenApplicationStatus.APP_IS_ALREADY_RUNNING) {
                mAppIsOpen = true;
//                mOpenAppButton.setText(R.string.open_app_already_open);
            } else {
                mAppIsOpen = false;
//                mOpenAppButton.setText(R.string.open_app_open);
            }
        }
    };

}
