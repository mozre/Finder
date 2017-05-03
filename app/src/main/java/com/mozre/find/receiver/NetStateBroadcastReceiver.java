package com.mozre.find.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by MOZRE on 2016/6/17.
 */
public class NetStateBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = NetStateBroadcastReceiver.class.getName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: ");
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo WiFiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mobileInfo.isConnected() && WiFiInfo.isConnected()) {
            Log.d(TAG, "onReceive: 网络可用");
        }

    }
}
