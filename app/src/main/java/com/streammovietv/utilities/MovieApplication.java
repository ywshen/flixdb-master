package com.streammovietv.utilities;

import android.app.Application;

import com.streammovietv.R;
import com.streammovietv.networking.ConnectivityReceiver;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MovieApplication extends Application {

    private static MovieApplication mInstance;

    public static synchronized MovieApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ubuntu_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        mInstance = this;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}