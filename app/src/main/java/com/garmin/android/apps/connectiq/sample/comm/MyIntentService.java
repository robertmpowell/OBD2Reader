package com.garmin.android.apps.connectiq.sample.comm;

import android.app.AlertDialog;
import android.app.IntentService;
import android.content.Intent;
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


    private ConnectIQ mConnectIQ;
    private IQDeviceAdapter mAdapter;
    List<IQDevice> devices;
    private IQApp mMyApp;
    private boolean mAppIsOpen;
    public static final String MY_APP = "7074f62305c345b3828e4324f340ab82";


    public MyIntentService(){
        super("MyIntentService");

        mConnectIQ = ConnectIQ.getInstance(this, ConnectIQ.IQConnectType.WIRELESS);
//        mConnectIQ.initialize(getApplicationContext(), true, mListener);
//        loadDevices();


    }

    @Override
    protected void onHandleIntent(Intent intent){

//        String s = intent.getStringExtra(PARAM_IN_MSG);

        mConnectIQ.initialize(getApplicationContext(), true, mListener);
        loadDevices();

        IQDevice mDevice = devices.get(0);

//        Intent i = new Intent(this, DeviceActivity.class);
//        i.putExtra(DeviceActivity.IQDEVICE, device);
//        startActivity(i);

        mMyApp = new IQApp(MY_APP);

        try {
            mConnectIQ.openApplication(mDevice, mMyApp, mOpenAppListener);
        } catch (Exception ex) {
        }



        if (mDevice != null) {
//            mDeviceName.setText(mDevice.getFriendlyName());
//            mDeviceStatus.setText(mDevice.getStatus().name());
//
//            mOpenAppButton.setOnClickListener(this);

            // Get our instance of ConnectIQ.  Since we initialized it
            // in our MainActivity, there is no need to do so here, we
            // can just get a reference to the one and only instance.
            mConnectIQ = ConnectIQ.getInstance();
            try {
                mConnectIQ.registerForDeviceEvents(mDevice, new ConnectIQ.IQDeviceEventListener() {

                    @Override
                    public void onDeviceStatusChanged(IQDevice device, IQDevice.IQDeviceStatus status) {
                        // Since we will only get updates for this device, just display the status
//                        mDeviceStatus.setText(status.name());
                    }

                });
            } catch (InvalidStateException e) {
                Log.wtf(TAG, "InvalidStateException:  We should not be here!");
            }

            // Let's check the status of our application on the device.
            try {
                mConnectIQ.getApplicationInfo(MY_APP, mDevice, new ConnectIQ.IQApplicationInfoListener() {

                    @Override
                    public void onApplicationInfoReceived(IQApp app) {
                        // This is a good thing. Now we can show our list of message options.
//                        String[] options = getResources().getStringArray(R.array.send_message_display);
//
//                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeviceActivity.this, android.R.layout.simple_list_item_1, options);
//                        setListAdapter(adapter);

                        // Send a message to open the app
                        try {
                            Toast.makeText(getApplicationContext(), "Opening app...", Toast.LENGTH_SHORT).show();
//                            mConnectIQ.openApplication(mDevice, app, mOpenAppListener);
                        } catch (Exception ex) {
                        }
                    }

                    @Override
                    public void onApplicationNotInstalled(String applicationId) {
                        // The Comm widget is not installed on the device so we have
                        // to let the user know to install it.
//                        AlertDialog.Builder dialog = new AlertDialog.Builder(DeviceActivity.this);
//                        dialog.setTitle(R.string.missing_widget);
//                        dialog.setMessage(R.string.missing_widget_message);
//                        dialog.setPositiveButton(android.R.string.ok, null);
//                        dialog.create().show();
                    }

                });
            } catch (InvalidStateException e1) {
            } catch (ServiceUnavailableException e1) {
            }

//            // Let's register to receive messages from our application on the device.
//            try {
//                mConnectIQ.registerForAppEvents(mDevice, mMyApp, new ConnectIQ.IQApplicationEventListener() {
//
//                    @Override
//                    public void onMessageReceived(IQDevice device, IQApp app, List<Object> message, ConnectIQ.IQMessageStatus status) {
//
//                        // We know from our Comm sample widget that it will only ever send us strings, but in case
//                        // we get something else, we are simply going to do a toString() on each object in the
//                        // message list.
//                        StringBuilder builder = new StringBuilder();
//
//                        if (message.size() > 0) {
//                            for (Object o : message) {
//                                builder.append(o.toString());
//                                builder.append("\r\n");
//                            }
//                        } else {
//                            builder.append("Received an empty message from the application");
//                        }
//
////                        AlertDialog.Builder dialog = new AlertDialog.Builder(DeviceActivity.this);
////                        dialog.setTitle(R.string.received_message);
////                        dialog.setMessage(builder.toString());
////                        dialog.setPositiveButton(android.R.string.ok, null);
////                        dialog.create().show();
//                    }
//
//                });
//            } catch (InvalidStateException e) {
//                Toast.makeText(this, "ConnectIQ is not in a valid state", Toast.LENGTH_SHORT).show();
//            }
        }


        Object message = MessageFactory.getMessage(this, 0);
        try {

            Map<String, Object> dictionary = new HashMap<String, Object>();
            dictionary.put("tach", "0748");
            dictionary.put("speed", "001");
            dictionary.put("throttle", "05%");
            dictionary.put("oil_temp", "180F");
            dictionary.put("fuel_level", "05%");
            dictionary.put("fuel_consumption", "0.04 gpm");
            
            mConnectIQ.sendMessage(mDevice, mMyApp, dictionary, new ConnectIQ.IQSendMessageListener() {

                @Override
                public void onMessageStatus(IQDevice device, IQApp app, ConnectIQ.IQMessageStatus status) {
                    Toast.makeText(MyIntentService.this, status.name(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext().this, status.name(), Toast.LENGTH_SHORT).show();
                }

            });
        } catch (InvalidStateException e) {
            Toast.makeText(this, "ConnectIQ is not in a valid state", Toast.LENGTH_SHORT).show();
        } catch (ServiceUnavailableException e) {
            Toast.makeText(this, "ConnectIQ service is unavailable.   Is Garmin Connect Mobile installed and running?", Toast.LENGTH_LONG).show();
        }

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
