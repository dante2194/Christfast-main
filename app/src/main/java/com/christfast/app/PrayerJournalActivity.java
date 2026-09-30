package com.christfast.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PrayerJournalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_journal);
        final EditText journalEntry = findViewById(R.id.journalEntry);
        Button saveButton = findViewById(R.id.saveButton);
        final SharedPreferences prefs = getSharedPreferences("PrayerJournal", MODE_PRIVATE);
        final String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        journalEntry.setText(prefs.getString(today, ""));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                prefs.edit().putString(today, journalEntry.getText().toString()).commit();
                Toast.makeText(PrayerJournalActivity.this,
                        "Prayer journal saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
