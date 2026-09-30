package com.christfast.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView welcomeText = findViewById(R.id.welcomeText);
        TextView verseText   = findViewById(R.id.verseText);
        TextView agpeyaHint  = findViewById(R.id.agpeyaHint);

        Button fastingButton    = findViewById(R.id.fastingButton);
        Button bibleButton      = findViewById(R.id.bibleButton);
        Button agpeyaButton     = findViewById(R.id.agpeyaButton);
        Button prayerButton     = findViewById(R.id.prayerButton);
        Button devotionalButton = findViewById(R.id.devotionalButton);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault());
        welcomeText.setText("Welcome to Christfast\n" + sdf.format(new Date()));

        verseText.setText("\"But when you fast, put oil on your head and wash your face, " +
                "so that it will not be obvious to others that you are fasting, " +
                "but only to your Father, who is unseen.\"\n\n\u2014 Matthew 6:17-18 (NIV)");

        agpeyaHint.setText("Now praying: " + currentHourName());

        fastingButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FastingTrackerActivity.class));
            }
        });
        bibleButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BibleReadingActivity.class));
            }
        });
        agpeyaButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AgpeyaActivity.class));
            }
        });
        prayerButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PrayerJournalActivity.class));
            }
        });
        devotionalButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DevotionalActivity.class));
            }
        });
    }

    private String currentHourName() {
        int h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (h >= 5  && h < 8)  return "Prime (First Hour)";
        if (h >= 8  && h < 11) return "Terce (Third Hour)";
        if (h >= 11 && h < 14) return "Sext (Sixth Hour)";
        if (h >= 14 && h < 17) return "None (Ninth Hour)";
        if (h >= 17 && h < 20) return "Vespers (Eleventh Hour)";
        if (h >= 20 && h < 23) return "Compline (Twelfth Hour)";
        return "Midnight";
    }
}
