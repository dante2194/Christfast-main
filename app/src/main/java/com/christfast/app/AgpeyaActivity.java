package com.christfast.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

/**
 * Coptic Orthodox Agpeya, seven canonical hours.
 *
 * Every Psalm and Gospel header is written as "Book chapter:verse"
 * so BibleLinkUtils can turn it into a gold clickable span that opens
 * the passage on Bible.com (NIV, version 111).
 *
 * Psalm numbers use MASORETIC (Hebrew/NIV) numbering so the link
 * resolves correctly; the traditional LXX number is shown in
 * parentheses for reference only.
 */
public class AgpeyaActivity extends AppCompatActivity {

    private TextView hourName, hourTime, openingText, psalmText, gospelText, closingText;
    private int currentHour = 0;

    private static final String[] NAMES = {
        "Prime (First Hour)", "Terce (Third Hour)", "Sext (Sixth Hour)",
        "None (Ninth Hour)", "Vespers (Eleventh Hour)",
        "Compline (Twelfth Hour)", "Midnight"
    };

    private static final String[] TIMES = {
        "~ 6 AM  \u00b7  Dawn  \u00b7  Resurrection",
        "~ 9 AM  \u00b7  Pentecost",
        "~ 12 PM  \u00b7  Noon  \u00b7  Crucifixion",
        "~ 3 PM  \u00b7  Death of Christ",
        "~ 6 PM  \u00b7  Sunset  \u00b7  Descent from the Cross",
        "~ 9 PM  \u00b7  Burial of Christ",
        "~ 12 AM  \u00b7  Second Coming"
    };

    private static final String INTRO =
        "In the name of the Father, and the Son, and the Holy Spirit, one God. Amen.\n\n" +
        "Kyrie eleison. Lord have mercy, Lord have mercy, Lord bless us. Amen.\n\n" +
        "Glory to the Father, and to the Son, and to the Holy Spirit, now and forever " +
        "and unto the ages of all ages. Amen.\n\n" +
        "THE LORD'S PRAYER\n" +
        "Our Father in heaven, hallowed be your name, your kingdom come, your will be " +
        "done, on earth as it is in heaven. Give us today our daily bread. And forgive " +
        "us our debts, as we also have forgiven our debtors. And lead us not into " +
        "temptation, but deliver us from the evil one. For yours is the kingdom and the " +
        "power and the glory forever. Amen.  (Matthew 6:9-13, NIV)\n\n" +
        "PRAYER OF THANKSGIVING\n" +
        "Let us give thanks to the beneficent and merciful God, the Father of our Lord, " +
        "God and Savior, Jesus Christ, for He has covered us, helped us, guarded us, " +
        "accepted us unto Him, spared us, supported us, and brought us to this hour. " +
        "Let us also ask Him, the Lord our God, the Almighty, to guard us in all peace " +
        "this holy day and all the days of our life. Amen.\n\n" +
        "Psalm 51:1-19 (NIV) \u00b7 LXX 50\n" +
        "Have mercy on me, O God, according to your unfailing love; according to your " +
        "great compassion blot out my transgressions. Wash away all my iniquity and " +
        "cleanse me from my sin. Create in me a pure heart, O God, and renew a " +
        "steadfast spirit within me. Do not cast me from your presence or take your " +
        "Holy Spirit from me. Restore to me the joy of your salvation. Amen.";

    private static final String[] PSALMS = {

        // Prime — First Hour
        "Psalm 19:1-14 (NIV) \u00b7 LXX 18\n" +
        "The heavens declare the glory of God; the skies proclaim the work of his " +
        "hands. The law of the LORD is perfect, refreshing the soul. May these words " +
        "of my mouth and this meditation of my heart be pleasing in your sight, LORD, " +
        "my Rock and my Redeemer.\n\n" +
        "Psalm 25:1-22 (NIV) \u00b7 LXX 24\n" +
        "In you, LORD, I put my trust. Show me your ways, LORD, teach me your paths. " +
        "Guide me in your truth and teach me, for you are God my Savior, and my hope " +
        "is in you all day long.\n\n" +
        "Psalm 27:1-14 (NIV) \u00b7 LXX 26\n" +
        "The LORD is my light and my salvation\u2014whom shall I fear? One thing I ask " +
        "from the LORD, this only do I seek: that I may dwell in the house of the LORD " +
        "all the days of my life.",

        // Terce — Third Hour
        "Psalm 20:1-9 (NIV) \u00b7 LXX 19\n" +
        "May the LORD answer you when you are in distress; may the name of the God of " +
        "Jacob protect you. May he give you the desire of your heart and make all your " +
        "plans succeed.\n\n" +
        "Psalm 23:1-6 (NIV) \u00b7 LXX 22\n" +
        "The LORD is my shepherd, I lack nothing. He makes me lie down in green " +
        "pastures, he leads me beside quiet waters, he refreshes my soul.\n\n" +
        "Psalm 24:1-10 (NIV) \u00b7 LXX 23\n" +
        "The earth is the LORD's, and everything in it. Who may ascend the mountain of " +
        "the LORD? The one who has clean hands and a pure heart.\n\n" +
        "Psalm 26:1-12 (NIV) \u00b7 LXX 25\n" +
        "Vindicate me, LORD, for I have led a blameless life; I have trusted in the " +
        "LORD and have not faltered.",

        // Sext — Sixth Hour
        "Psalm 54:1-7 (NIV) \u00b7 LXX 53\n" +
        "Save me, O God, by your name; vindicate me by your might. Surely God is my " +
        "help; the Lord is the one who sustains me.\n\n" +
        "Psalm 57:1-11 (NIV) \u00b7 LXX 56\n" +
        "Have mercy on me, my God, have mercy on me, for in you I take refuge. I will " +
        "take refuge in the shadow of your wings until the disaster has passed.\n\n" +
        "Psalm 61:1-8 (NIV) \u00b7 LXX 60\n" +
        "Hear my cry, O God; listen to my prayer. From the ends of the earth I call to " +
        "you, I call as my heart grows faint; lead me to the rock that is higher than I.",

        // None — Ninth Hour
        "Psalm 96:1-13 (NIV) \u00b7 LXX 95\n" +
        "Sing to the LORD a new song; sing to the LORD, all the earth. Declare his " +
        "glory among the nations, his marvelous deeds among all peoples.\n\n" +
        "Psalm 97:1-12 (NIV) \u00b7 LXX 96\n" +
        "The LORD reigns, let the earth be glad; let the distant shores rejoice. " +
        "Righteousness and justice are the foundation of his throne.\n\n" +
        "Psalm 98:1-9 (NIV) \u00b7 LXX 97\n" +
        "Sing to the LORD a new song, for he has done marvelous things; his right hand " +
        "and his holy arm have worked salvation for him.",

        // Vespers — Eleventh Hour
        "Psalm 117:1-2 (NIV) \u00b7 LXX 116\n" +
        "Praise the LORD, all you nations; extol him, all you peoples. For great is " +
        "his love toward us, and the faithfulness of the LORD endures forever.\n\n" +
        "Psalm 120:1-7 (NIV) \u00b7 LXX 119\n" +
        "I call on the LORD in my distress, and he answers me. Save me, LORD, from " +
        "lying lips and from deceitful tongues.\n\n" +
        "Psalm 121:1-8 (NIV) \u00b7 LXX 120\n" +
        "I lift up my eyes to the mountains\u2014where does my help come from? My help " +
        "comes from the LORD, the Maker of heaven and earth.",

        // Compline — Twelfth Hour
        "Psalm 4:1-8 (NIV)\n" +
        "Answer me when I call to you, my righteous God. In peace I will lie down and " +
        "sleep, for you alone, LORD, make me dwell in safety.\n\n" +
        "Psalm 6:1-10 (NIV)\n" +
        "LORD, do not rebuke me in your anger. Have mercy on me, LORD, for I am faint. " +
        "The LORD has heard my cry for mercy; the LORD accepts my prayer.\n\n" +
        "Psalm 13:1-6 (NIV)\n" +
        "How long, LORD? Will you forget me forever? But I trust in your unfailing " +
        "love; my heart rejoices in your salvation.\n\n" +
        "Psalm 16:1-11 (NIV)\n" +
        "Keep me safe, my God, for in you I take refuge. You make known to me the path " +
        "of life; you will fill me with joy in your presence.",

        // Midnight
        "Psalm 134:1-3 (NIV) \u00b7 LXX 133\n" +
        "Praise the LORD, all you servants of the LORD who minister by night in the " +
        "house of the LORD. Lift up your hands in the sanctuary and praise the LORD.\n\n" +
        "Psalm 119:62 (NIV)\n" +
        "At midnight I rise to give you thanks for your righteous laws."
    };

    private static final String[] GOSPELS = {
        "John 1:1-14 (NIV)\n" +
        "In the beginning was the Word, and the Word was with God, and the Word was " +
        "God. Through him all things were made. In him was life, and that life was " +
        "the light of all mankind. The light shines in the darkness, and the darkness " +
        "has not overcome it. The Word became flesh and made his dwelling among us. " +
        "We have seen his glory, the glory of the one and only Son, who came from the " +
        "Father, full of grace and truth.",

        "Acts 2:1-21 (NIV)\n" +
        "When the day of Pentecost came, they were all together in one place. " +
        "Suddenly a sound like the blowing of a violent wind came from heaven. All of " +
        "them were filled with the Holy Spirit and began to speak in other tongues as " +
        "the Spirit enabled them. Then Peter stood up with the Eleven: \u201CThese " +
        "people are not drunk, as you suppose. It's only nine in the morning! No, " +
        "this is what was spoken by the prophet Joel: In the last days, God says, I " +
        "will pour out my Spirit on all people.\u201D",

        "John 19:16-30 (NIV)\n" +
        "Finally Pilate handed him over to them to be crucified. Carrying his own " +
        "cross, he went out to the place of the Skull (which in Aramaic is called " +
        "Golgotha). There they crucified him, and with him two others. Near the cross " +
        "of Jesus stood his mother, and the disciple whom he loved. When Jesus saw " +
        "his mother there, he said to her, \u201CWoman, here is your son,\u201D and to " +
        "the disciple, \u201CHere is your mother.\u201D Later, knowing that everything " +
        "had now been finished, Jesus said, \u201CI am thirsty.\u201D When he had " +
        "received the drink, Jesus said, \u201CIt is finished.\u201D With that, he " +
        "bowed his head and gave up his spirit.",

        "Matthew 27:45-54 (NIV)\n" +
        "From noon until three in the afternoon darkness came over all the land. " +
        "About three in the afternoon Jesus cried out in a loud voice, \u201CEli, Eli, " +
        "lema sabachthani?\u201D (which means \u201CMy God, my God, why have you " +
        "forsaken me?\u201D). And when Jesus had cried out again in a loud voice, he " +
        "gave up his spirit. At that moment the curtain of the temple was torn in two " +
        "from top to bottom. When the centurion and those with him who were guarding " +
        "Jesus saw the earthquake and all that had happened, they were terrified, and " +
        "exclaimed, \u201CSurely he was the Son of God!\u201D",

        "Luke 15:11-32 (NIV)\n" +
        "Jesus continued: \u201CThere was a man who had two sons. The younger one " +
        "said to his father, \u2018Father, give me my share of the estate.\u2019 So he " +
        "divided his property between them. Not long after that, the younger son got " +
        "together all he had, set off for a distant country and there squandered his " +
        "wealth in wild living. When he came to his senses, he said, \u2018I will set " +
        "out and go back to my father.\u2019 But while he was still a long way off, " +
        "his father saw him and was filled with compassion for him; he ran to his " +
        "son, threw his arms around him and kissed him. For this son of mine was dead " +
        "and is alive again; he was lost and is found.\u201D",

        "Luke 2:25-35 (NIV)\n" +
        "Now there was a man in Jerusalem called Simeon, who was righteous and " +
        "devout. He was waiting for the consolation of Israel, and the Holy Spirit " +
        "was on him. Moved by the Spirit, he went into the temple courts. When the " +
        "parents brought in the child Jesus, Simeon took him in his arms and praised " +
        "God, saying: \u201CSovereign Lord, as you have promised, you may now dismiss " +
        "your servant in peace. For my eyes have seen your salvation, which you have " +
        "prepared in the sight of all nations: a light for revelation to the " +
        "Gentiles, and the glory of your people Israel.\u201D",

        "Matthew 25:1-13 (NIV)\n" +
        "\u201CAt that time the kingdom of heaven will be like ten virgins who took " +
        "their lamps and went out to meet the bridegroom. Five of them were foolish " +
        "and five were wise. The foolish ones took their lamps but did not take any " +
        "oil with them. The wise ones, however, took oil in jars along with their " +
        "lamps. The bridegroom was a long time in coming, and they all became drowsy " +
        "and fell asleep. At midnight the cry rang out: \u2018Here's the bridegroom! " +
        "Come out to meet him!\u2019 Therefore keep watch, because you do not know " +
        "the day or the hour.\u201D"
    };

    private static final String[] CLOSINGS = {
        "It is truly meet to bless you, O Theotokos, ever-blessed and most pure, and " +
        "the Mother of our God. More honorable than the Cherubim, and more glorious " +
        "beyond compare than the Seraphim, who without corruption gave birth to God " +
        "the Word, the true Theotokos, we magnify you.\n\n" +
        "Glory be to the Father, and to the Son, and to the Holy Spirit, now and " +
        "forever and unto the ages of ages. Amen.\n\n" +
        "Through the prayers of the Theotokos, O Savior, save us. Amen.",

        "O Heavenly King, the Comforter, the Spirit of truth, who art everywhere and " +
        "fillest all things, treasury of blessings and giver of life: come and abide " +
        "in us, cleanse us from every impurity, and save our souls, O Good One.\n\n" +
        "Glory be to the Father, and to the Son, and to the Holy Spirit, now and " +
        "forever and unto the ages of ages. Amen.",

        "O Christ our God, who at the sixth hour wast crucified upon the cross for our " +
        "sins: have mercy upon us and save us, for Thou art good and lovest mankind.\n\n" +
        "Glory be to the Father, and to the Son, and to the Holy Spirit, now and " +
        "forever and unto the ages of ages. Amen.",

        "O Christ our God, who at the ninth hour didst taste death in the flesh for " +
        "our sake: put to death our carnal mind, and save us, for Thou art good and " +
        "lovest mankind.\n\n" +
        "Glory be to the Father, and to the Son, and to the Holy Spirit, now and " +
        "forever and unto the ages of ages. Amen.",

        "O Christ our God, who at the eleventh hour didst ascend the cross and blot " +
        "out the handwriting of our sins: have mercy on us, O Word made flesh, and " +
        "save our souls.\n\n" +
        "Glory be to the Father, and to the Son, and to the Holy Spirit, now and " +
        "forever and unto the ages of ages. Amen.",

        "O Christ our God, who at the twelfth hour wast laid in the tomb for our sake: " +
        "grant us a peaceful night and a rest without sin, and save us, O our Savior.\n\n" +
        "Glory be to the Father, and to the Son, and to the Holy Spirit, now and " +
        "forever and unto the ages of ages. Amen.",

        "O Christ our God, who at midnight wast wrapped in swaddling cloths and laid " +
        "in the manger, and didst rise from the dead at dawn: enable us to watch with " +
        "Thee, and to praise Thee with joy, O our Savior.\n\n" +
        "Glory be to the Father, and to the Son, and to the Holy Spirit, now and " +
        "forever and unto the ages of ages. Amen."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agpeya);

        hourName    = findViewById(R.id.agpeyaHourName);
        hourTime    = findViewById(R.id.agpeyaHourTime);
        openingText = findViewById(R.id.agpeyaOpening);
        psalmText   = findViewById(R.id.agpeyaPsalms);
        gospelText  = findViewById(R.id.agpeyaGospel);
        closingText = findViewById(R.id.agpeyaClosing);

        Button prevBtn = findViewById(R.id.agpeyaPrevButton);
        Button nextBtn = findViewById(R.id.agpeyaNextButton);
        Button nowBtn  = findViewById(R.id.agpeyaNowButton);

        currentHour = currentCanonicalHour();
        render();

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (currentHour > 0) { currentHour--; render(); }
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (currentHour < NAMES.length - 1) { currentHour++; render(); }
            }
        });
        nowBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                currentHour = currentCanonicalHour();
                render();
            }
        });
    }

    private int currentCanonicalHour() {
        int h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (h >= 5  && h < 8)  return 0;
        if (h >= 8  && h < 11) return 1;
        if (h >= 11 && h < 14) return 2;
        if (h >= 14 && h < 17) return 3;
        if (h >= 17 && h < 20) return 4;
        if (h >= 20 && h < 23) return 5;
        return 6;
    }

    private void render() {
        hourName.setText(NAMES[currentHour]);
        hourTime.setText(TIMES[currentHour]);
        openingText.setText(INTRO);
        closingText.setText(CLOSINGS[currentHour]);
        BibleLinkUtils.linkify(this, psalmText, PSALMS[currentHour]);
        BibleLinkUtils.linkify(this, gospelText, GOSPELS[currentHour]);
    }
}
