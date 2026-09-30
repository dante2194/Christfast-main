package com.christfast.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Shared helper that scans text for Bible references and turns each one
 * into a gold-underlined clickable span opening Bible.com (NIV, version 111).
 */
public final class BibleLinkUtils {

    private static final String BIBLE_BASE = "https://www.bible.com/bible/111/";

    private static final String[][] BOOK_MAP = {
        { "Genesis",   "GEN" }, { "Exodus",   "EXO" }, { "Leviticus", "LEV" },
        { "Numbers",   "NUM" }, { "Deuteronomy", "DEU" }, { "Joshua", "JOS" },
        { "Judges",    "JDG" }, { "Ruth",     "RUT" }, { "1 Samuel",  "1SA" },
        { "2 Samuel",  "2SA" }, { "1 Kings",  "1KI" }, { "2 Kings",   "2KI" },
        { "1 Chronicles", "1CH" }, { "2 Chronicles", "2CH" }, { "Ezra", "EZR" },
        { "Nehemiah",  "NEH" }, { "Esther",   "EST" }, { "Job",       "JOB" },
        { "Psalm",     "PSA" }, { "Psalms",   "PSA" }, { "Proverbs",  "PRO" },
        { "Ecclesiastes", "ECC" }, { "Song of Songs", "SNG" }, { "Isaiah", "ISA" },
        { "Jeremiah",  "JER" }, { "Lamentations", "LAM" }, { "Ezekiel", "EZK" },
        { "Daniel",    "DAN" }, { "Hosea",    "HOS" }, { "Joel",      "JOL" },
        { "Amos",      "AMO" }, { "Obadiah",  "OBA" }, { "Jonah",     "JON" },
        { "Micah",     "MIC" }, { "Nahum",    "NAM" }, { "Habakkuk",  "HAB" },
        { "Zephaniah", "ZEP" }, { "Haggai",   "HAG" }, { "Zechariah", "ZEC" },
        { "Malachi",   "MAL" },
        { "Matthew",   "MAT" }, { "Mark",     "MRK" }, { "Luke",      "LUK" },
        { "John",      "JHN" }, { "Acts",     "ACT" }, { "Romans",    "ROM" },
        { "1 Corinthians", "1CO" }, { "2 Corinthians", "2CO" }, { "Galatians", "GAL" },
        { "Ephesians", "EPH" }, { "Philippians", "PHP" }, { "Colossians", "COL" },
        { "1 Thessalonians", "1TH" }, { "2 Thessalonians", "2TH" },
        { "1 Timothy", "1TI" }, { "2 Timothy", "2TI" }, { "Titus",     "TIT" },
        { "Philemon",  "PHM" }, { "Hebrews",  "HEB" }, { "James",     "JAS" },
        { "1 Peter",   "1PE" }, { "2 Peter",  "2PE" },
        { "1 John",    "1JN" }, { "2 John",   "2JN" }, { "3 John",    "3JN" },
        { "Jude",      "JUD" }, { "Revelation", "REV" }
    };

    // Match "Book chapter:verse" or "Book chapter:verse-verse"
    private static final Pattern REF_PATTERN = Pattern.compile(
            "([1-3]?\\s?[A-Z][a-zA-Z]+(?:\\s+of\\s+[A-Z][a-zA-Z]+)?)\\s+(\\d+):(\\d+)(?:-(\\d+))?");

    private BibleLinkUtils() {}

    /** Applies link styling + click handling to a TextView. */
    public static void linkify(final Context ctx, TextView tv, String text) {
        SpannableString ss = new SpannableString(text);
        Matcher m = REF_PATTERN.matcher(text);

        while (m.find()) {
            final String book = m.group(1).trim();
            final String chap = m.group(2);
            final String verse = m.group(3);
            final String osis = mapBook(book);
            if (osis == null) continue;

            ClickableSpan span = new ClickableSpan() {
                @Override public void onClick(@NonNull View w) {
                    String url = BIBLE_BASE + osis + "." + chap + "." + verse;
                    try {
                        ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    } catch (Exception e) {
                        Toast.makeText(ctx, "No browser available",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                @Override public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(0xFFD4AF37);
                    ds.setUnderlineText(true);
                }
            };
            ss.setSpan(span, m.start(), m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        tv.setText(ss);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setHighlightColor(0x00000000);
    }

    private static String mapBook(String name) {
        for (String[] p : BOOK_MAP) {
            if (p[0].equalsIgnoreCase(name)) return p[1];
        }
        return null;
    }
}
