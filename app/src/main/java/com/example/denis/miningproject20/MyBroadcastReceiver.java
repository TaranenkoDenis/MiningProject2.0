package com.example.denis.miningproject20;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.denis.miningproject20.service.MyService;

/**
 * Created by denis on 14.07.17.
 */

public class MyBroadcastReceiver extends BroadcastReceiver{

    private static final String LOG_TAG = "MY_LOG: " + MyBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "onReceive " + intent.getAction());
        context.startService(new Intent(context, MyService.class));
    }
}
