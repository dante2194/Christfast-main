package com.christfast.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import java.util.Locale;

public class FastingService extends Service {

    public static final String CHANNEL_ID      = "christfast_fasting";
    public static final int    NOTIFICATION_ID = 1001;
    public static final String ACTION_STOP     = "com.christfast.app.STOP_FAST_SERVICE";

    private static final String PREFS   = "FastingPrefs";
    private static final String KEY_END = "fast_end_time";

    private Handler handler;
    private Runnable ticker;
    private NotificationManager nm;

    @Override public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());
        nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && ACTION_STOP.equals(intent.getAction())) {
            stopForeground(true);
            stopSelf();
            return START_NOT_STICKY;
        }
        long end = getSharedPreferences(PREFS, MODE_PRIVATE).getLong(KEY_END, 0);
        if (end <= System.currentTimeMillis()) {
            stopForeground(true);
            stopSelf();
            return START_NOT_STICKY;
        }
        startForeground(NOTIFICATION_ID, buildNotification());
        if (handler != null && ticker != null) handler.removeCallbacks(ticker);
        ticker = new Runnable() {
            @Override public void run() {
                long e = getSharedPreferences(PREFS, MODE_PRIVATE).getLong(KEY_END, 0);
                if (e == 0 || e <= System.currentTimeMillis()) {
                    stopForeground(true);
                    stopSelf();
                    return;
                }
                nm.notify(NOTIFICATION_ID, buildNotification());
                handler.postDelayed(this, 60_000);
            }
        };
        handler.postDelayed(ticker, 60_000);
        return START_STICKY;
    }

    private Notification buildNotification() {
        long end = getSharedPreferences(PREFS, MODE_PRIVATE).getLong(KEY_END, 0);
        long remaining = Math.max(0, end - System.currentTimeMillis());
        long h = remaining / (60 * 60 * 1000);
        long m = (remaining % (60 * 60 * 1000)) / (60 * 1000);
        long s = (remaining % (60 * 1000)) / 1000;
        String timeLeft = String.format(Locale.getDefault(),
                "%d h %02d m %02d s remaining", h, m, s);

        PendingIntent tapIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP),
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        PendingIntent deleteIntent = PendingIntent.getBroadcast(this, 1,
                new Intent(this, NotificationRestoreReceiver.class),
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_fast)
                .setContentTitle("Fast in progress")
                .setContentText(timeLeft)
                .setContentIntent(tapIntent)
                .setDeleteIntent(deleteIntent)
                .setAutoCancel(false)
                .setOngoing(true)
                .setSilent(true)
                .setOnlyAlertOnce(true)
                .setShowWhen(false)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_PROGRESS)
                .build();
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel ch = new NotificationChannel(CHANNEL_ID,
                    "Fasting Timer", NotificationManager.IMPORTANCE_LOW);
            ch.setDescription("Shows remaining fasting time");
            ch.setShowBadge(false);
            ch.enableVibration(false);
            ch.setSound(null, null);
            nm.createNotificationChannel(ch);
        }
    }

    @Override public void onDestroy() {
        if (handler != null && ticker != null) handler.removeCallbacks(ticker);
        super.onDestroy();
    }
    @Nullable @Override public IBinder onBind(Intent intent) { return null; }
}
