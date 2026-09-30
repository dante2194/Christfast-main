package com.christfast.app;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.Locale;

public class FastingTrackerActivity extends AppCompatActivity {

    private static final String PREFS   = "FastingPrefs";
    private static final String KEY_END = "fast_end_time";
    private static final long HOUR_MS   = 60L * 60 * 1000;
    private static final int REQ_NOTIF  = 42;

    private TextView timerText, statusText;
    private Button startButton, stopButton;
    private RadioGroup durationGroup;
    private CountDownTimer countDownTimer;
    private SharedPreferences prefs;
    private long endTimeMs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasting_tracker);
        prefs = getSharedPreferences(PREFS, MODE_PRIVATE);

        timerText     = findViewById(R.id.timerText);
        statusText    = findViewById(R.id.statusText);
        startButton   = findViewById(R.id.startFastButton);
        stopButton    = findViewById(R.id.stopFastButton);
        durationGroup = findViewById(R.id.durationGroup);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ensureNotificationPermission();
                startFast(getSelectedDurationMs());
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { stopFast(); }
        });

        ensureNotificationPermission();
        restoreState();
    }

    @Override protected void onResume() { super.onResume(); restoreState(); }

    @Override protected void onPause() {
        super.onPause();
        if (countDownTimer != null) { countDownTimer.cancel(); countDownTimer = null; }
    }

    private long getSelectedDurationMs() {
        int id = durationGroup.getCheckedRadioButtonId();
        if (id == R.id.radio24) return 24L * HOUR_MS;
        if (id == R.id.radio18) return 18L * HOUR_MS;
        return 16L * HOUR_MS;
    }

    private void ensureNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{ Manifest.permission.POST_NOTIFICATIONS }, REQ_NOTIF);
            }
        }
    }

    private void restoreState() {
        endTimeMs = prefs.getLong(KEY_END, 0);
        long now = System.currentTimeMillis();

        if (endTimeMs > now) {
            statusText.setText("Fasting in progress...");
            setRunningUi(true);
            resumeCountdown(endTimeMs - now);
            startFastingService();
        } else {
            if (endTimeMs != 0) {
                prefs.edit().remove(KEY_END).commit();
                endTimeMs = 0;
                stopFastingService();
                statusText.setText("Fast completed! Praise God!");
            } else {
                statusText.setText("Ready to fast");
            }
            timerText.setText("00:00:00");
            setRunningUi(false);
        }
    }

    private void setRunningUi(boolean running) {
        startButton.setEnabled(!running);
        stopButton.setEnabled(running);
        durationGroup.setEnabled(!running);
        for (int i = 0; i < durationGroup.getChildCount(); i++)
            durationGroup.getChildAt(i).setEnabled(!running);
    }

    private void startFast(long durationMs) {
        endTimeMs = System.currentTimeMillis() + durationMs;
        prefs.edit().putLong(KEY_END, endTimeMs).commit();
        statusText.setText("Fasting in progress (" + (durationMs / HOUR_MS) + "h)...");
        setRunningUi(true);
        Toast.makeText(this, "Fast started. May God strengthen you!",
                Toast.LENGTH_SHORT).show();
        resumeCountdown(durationMs);
        startFastingService();
    }

    private void resumeCountdown(long remainingMs) {
        if (countDownTimer != null) countDownTimer.cancel();
        countDownTimer = new CountDownTimer(remainingMs, 1000) {
            @Override public void onTick(long ms) { updateDisplay(ms); }
            @Override public void onFinish() {
                updateDisplay(0);
                statusText.setText("Fast completed! Praise God!");
                prefs.edit().remove(KEY_END).commit();
                endTimeMs = 0;
                setRunningUi(false);
                stopFastingService();
            }
        }.start();
    }

    private void updateDisplay(long millis) {
        long h = millis / (60 * 60 * 1000);
        long m = (millis % (60 * 60 * 1000)) / (60 * 1000);
        long s = (millis % (60 * 1000)) / 1000;
        timerText.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", h, m, s));
    }

    private void stopFast() {
        if (countDownTimer != null) { countDownTimer.cancel(); countDownTimer = null; }
        prefs.edit().remove(KEY_END).commit();
        endTimeMs = 0;
        timerText.setText("00:00:00");
        statusText.setText("Fast ended");
        setRunningUi(false);
        stopFastingService();
    }

    private void startFastingService() {
        Intent i = new Intent(this, FastingService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) startForegroundService(i);
        else startService(i);
    }

    private void stopFastingService() {
        Intent i = new Intent(this, FastingService.class);
        i.setAction(FastingService.ACTION_STOP);
        startService(i);
    }
}
