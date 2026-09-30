package com.christfast.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

public class NotificationRestoreReceiver extends BroadcastReceiver {

    private static final String PREFS   = "FastingPrefs";
    private static final String KEY_END = "fast_end_time";

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs =
                context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        long end = prefs.getLong(KEY_END, 0);
        if (end <= System.currentTimeMillis()) return;

        Intent svc = new Intent(context, FastingService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(svc);
        } else {
            context.startService(svc);
        }
    }
}
