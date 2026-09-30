package com.christfast.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DevotionalActivity extends AppCompatActivity {

    private TextView devotionalTitle, devotionalContent;
    private Button previousButton, nextButton;
    private int current = 0;

    private final String[] titles = new String[360];
    private final String[] contents = new String[360];

    {
        String[] themes = {
            "The Purpose of Fasting", "Fasting with the Right Heart",
            "Prayer and Fasting Together", "Breaking Your Fast",
            "Fasting for Breakthrough", "The Humility of Fasting",
            "Fasting and Repentance", "The Joy of the LORD Is Your Strength",
            "Seeking God Early", "The Power of Secret Prayer",
            "Trusting God in the Wilderness", "The Bread of Life",
            "Living by Every Word", "The Discipline of the Spirit",
            "Renewing Your Mind", "The Peace of God",
            "Casting All Your Cares", "The Good Shepherd",
            "Abiding in Christ", "The Vine and the Branches",
            "Walking in the Spirit", "The Fruit of the Spirit",
            "The Armor of God", "Standing Firm in Faith",
            "The Name of Jesus", "The Blood of Christ",
            "Grace Upon Grace", "Mercy That Endures",
            "Forgiveness and Freedom", "The Ministry of Reconciliation",
            "A New Creation", "The Old Has Gone",
            "Press On Toward the Goal", "Run with Perseverance",
            "Fix Your Eyes on Jesus", "The Author and Finisher",
            "Faith That Moves Mountains", "Praying in the Spirit",
            "The Word Hidden in the Heart", "Meditating Day and Night",
            "Delighting in the LORD", "The Fear of the LORD",
            "Walking in Obedience", "The Blessing of Holiness",
            "Set Apart for God", "A Royal Priesthood",
            "The Body of Christ", "One Another in Love",
            "Bearing One Another's Burdens", "The Law of Kindness",
            "The Power of Words", "Taming the Tongue",
            "Humility Before Honor", "The Meek Shall Inherit",
            "Blessed Are the Poor in Spirit", "Blessed Are the Mourners",
            "Blessed Are the Merciful", "Blessed Are the Pure in Heart",
            "Blessed Are the Peacemakers", "Rejoice Always",
            "Pray Without Ceasing", "Give Thanks in All Things",
            "The LORD Is My Shepherd", "He Makes Me Lie Down",
            "Your Rod and Your Staff", "Surely Goodness and Mercy",
            "Dwelling in the House of the LORD", "The Light of the World",
            "Salt of the Earth", "A City on a Hill",
            "Let Your Light Shine", "The Golden Rule",
            "Love Your Enemies", "Pray for Those Who Persecute You",
            "The Beatitudes in Action", "The Lord's Prayer",
            "Our Daily Bread", "Forgive Us Our Debts",
            "Lead Us Not into Temptation", "Thine Is the Kingdom",
            "The Narrow Gate", "The Broad Road",
            "Counting the Cost", "Taking Up Your Cross",
            "Losing Your Life to Find It", "The First Shall Be Last",
            "The Least of These", "The Good Samaritan",
            "The Prodigal Son", "The Lost Sheep",
            "The Lost Coin", "The Persistent Widow",
            "The Pharisee and the Tax Collector", "The Rich Fool",
            "The Talents", "The Ten Virgins",
            "The Sheep and the Goats", "The Wheat and the Tares",
            "The Mustard Seed", "The Leaven",
            "The Hidden Treasure", "The Pearl of Great Price",
            "The Net Cast into the Sea", "The Sower and the Soils",
            "The Good Soil", "Bearing Fruit Thirtyfold",
            "A Hundredfold Harvest", "The Fig Tree",
            "The Vineyard Workers", "The Wedding Banquet",
            "The Great Supper", "The Narrow Door",
            "The Cost of Discipleship", "The Rich Young Ruler",
            "Zacchaeus", "The Woman at the Well",
            "The Man Born Blind", "The Raising of Lazarus",
            "The Healing of the Paralytic", "The Woman with the Issue of Blood",
            "Jairus' Daughter", "The Centurion's Faith",
            "The Canaanite Woman", "The Ten Lepers",
            "The Blind Bartimaeus", "The Stilling of the Storm",
            "Walking on Water", "Feeding the Five Thousand",
            "The Bread from Heaven", "The Living Water",
            "The Light of the World", "The Door of the Sheep",
            "The Good Shepherd Lays Down His Life", "The Resurrection and the Life",
            "The Way, the Truth, and the Life", "The True Vine",
            "The Comforter Has Come", "The Spirit of Truth",
            "The Fruit of Righteousness", "Peace That Passes Understanding",
            "Joy Unspeakable", "Hope That Does Not Disappoint",
            "Love That Never Fails", "Faith That Overcomes the World",
            "The Victory That Overcomes", "More Than Conquerors",
            "Nothing Can Separate Us", "The Love of God in Christ",
            "The Depth of God's Love", "The Height and Width",
            "Rooted and Grounded in Love", "Filled with All the Fullness",
            "The Peace of Christ Rules", "The Word of Christ Dwells Richly",
            "Whatever You Do", "In Word or Deed",
            "Working for the Lord", "The Reward of Inheritance",
            "Set Your Minds on Things Above", "Put to Death the Old Self",
            "Put On the New Self", "Clothed with Compassion",
            "Kindness and Humility", "Patience and Forbearance",
            "Forgiving as the Lord Forgave", "Above All, Put On Love",
            "The Peace of Christ", "The Thankfulness of the Heart",
            "The Dwelling of the Word", "Singing with Gratitude",
            "The Name Above Every Name", "Every Knee Shall Bow",
            "Work Out Your Salvation", "Shine as Lights in the World",
            "Rejoice in the Lord Always", "Gentleness to All",
            "Anxiety for Nothing", "Prayer with Thanksgiving",
            "The Peace That Guards", "Think on These Things",
            "The God of Peace", "Contentment in All Circumstances",
            "Strength Through Christ", "The Supply of Every Need",
            "The Riches of His Glory", "The Hope of Glory",
            "Christ in You", "The Mystery of Godliness",
            "The Faith Once Delivered", "Contend for the Faith",
            "Building on the Foundation", "The Fire Will Test",
            "The Temple of the Spirit", "Glorify God in Your Body",
            "The Body of Christ", "Many Members, One Body",
            "The Eye Cannot Say to the Hand", "The Head Cannot Say to the Feet",
            "The Greatest Is Love", "Love Is Patient",
            "Love Is Kind", "Love Does Not Envy",
            "Love Does Not Boast", "Love Is Not Proud",
            "Love Does Not Dishonor", "Love Is Not Self-Seeking",
            "Love Is Not Easily Angered", "Love Keeps No Record of Wrongs",
            "Love Does Not Delight in Evil", "Love Rejoices with the Truth",
            "Love Bears All Things", "Love Believes All Things",
            "Love Hopes All Things", "Love Endures All Things",
            "Love Never Fails", "Faith, Hope, and Love",
            "The Greatest of These", "Pursue Love",
            "Eagerly Desire Spiritual Gifts", "Prophecy and Edification",
            "Order in Worship", "The Resurrection Body",
            "Death Swallowed Up in Victory", "The Sting of Death",
            "The Victory Through Christ", "Stand Firm",
            "Let Nothing Move You", "Always Abounding in the Work",
            "Your Labor Is Not in Vain", "The Collection for the Saints",
            "Generosity and Cheerfulness", "God Loves a Cheerful Giver",
            "The Grace of Giving", "The Fellowship of Giving",
            "The Ministry of Giving", "The Thanksgiving of Many",
            "The Excellency of the New Covenant", "The Glory That Surpasses",
            "The Veil Removed", "The Freedom of the Spirit",
            "Beholding the Glory", "Transformed into His Image",
            "Treasure in Jars of Clay", "The Power of God",
            "Hard-Pressed but Not Crushed", "Perplexed but Not Despairing",
            "Persecuted but Not Forsaken", "Struck Down but Not Destroyed",
            "Always Carrying the Death of Jesus", "The Life of Jesus Revealed",
            "The Inward Renewal", "Fixing Our Eyes on the Unseen",
            "The Eternal Weight of Glory", "The Things Seen Are Temporary",
            "The Things Unseen Are Eternal", "Our Heavenly Dwelling",
            "Longing to Be Clothed", "The Judgment Seat of Christ",
            "The Fear of the Lord", "The Love of Christ Compels",
            "New Creatures in Christ", "The Ministry of Reconciliation",
            "Ambassadors for Christ", "The Righteousness of God",
            "The Day of Salvation", "The Acceptable Time",
            "The Hardships of Ministry", "The Purity of Ministry",
            "The Patience of Ministry", "The Kindness of Ministry",
            "The Holy Spirit in Ministry", "The Sincere Love",
            "The Word of Truth", "The Power of God",
            "The Armor of Righteousness", "The Glory and Dishonor",
            "The Sorrowful Yet Always Rejoicing", "The Poor Yet Making Many Rich",
            "Having Nothing Yet Possessing Everything", "The Open Heart",
            "The Affection of a Father", "The Joy of Restoration",
            "The Godly Sorrow", "The Repentance That Leads to Salvation",
            "The Worldly Sorrow", "The Zeal for Holiness",
            "The Comfort of Titus", "The Joy of the Churches",
            "The Grace of Our Lord Jesus Christ", "The Love of God",
            "The Communion of the Holy Spirit", "The Fellowship of the Saints",
            "The Peace of God", "The God of Love and Peace",
            "The Grace of the Lord Jesus", "The Love of God in Truth",
            "The Witness of the Spirit", "The Assurance of Salvation",
            "The Confidence in Prayer", "The Obedience of Faith",
            "The Righteousness by Faith", "The Just Shall Live by Faith",
            "The Law of Faith", "The Promise of Faith",
            "The Heirs of the Promise", "The Seed of Abraham",
            "The Blessing of Abraham", "The Freedom of the Sons",
            "The Adoption as Sons", "The Spirit of Adoption",
            "The Abba Father", "The Co-Heirs with Christ",
            "The Sufferings of This Present Time", "The Glory to Be Revealed",
            "The Groaning of Creation", "The Redemption of the Body",
            "The Hope of the Redeemed", "The Patience of Hope",
            "The Intercession of the Spirit", "The All Things Work Together",
            "The Foreknowledge of God", "The Predestination of the Saints",
            "The Calling of God", "The Justification of God",
            "The Glorification of the Saints", "The Love That Cannot Be Separated",
            "The More Than Conquerors", "The Sword of the Spirit",
            "The Shield of Faith", "The Helmet of Salvation",
            "The Breastplate of Righteousness", "The Belt of Truth",
            "The Shoes of the Gospel", "The Gospel of Peace",
            "The Prayer in the Spirit", "The Watchfulness",
            "The Perseverance of the Saints", "The Supplication for All",
            "The Boldness in Prayer", "The Mystery of the Gospel",
            "The Ambassador in Chains", "The Grace of the Lord Jesus Christ",
            "The Love of God the Father", "The Fellowship of the Holy Spirit",
            "The Greatness of God", "The Goodness of God",
            "The Faithfulness of God", "The Holiness of God",
            "The Justice of God", "The Mercy of God",
            "The Grace of God", "The Wisdom of God",
            "The Power of God", "The Love of God",
            "The Sovereignty of God", "The Providence of God",
            "The Eternity of God", "The Omnipresence of God",
            "The Omniscience of God", "The Omnipotence of God",
            "The Immutability of God", "The Self-Existence of God",
            "The Trinity", "The Unity of God",
            "The Incarnation", "The Virgin Birth",
            "The Sinless Life of Christ", "The Miracles of Christ",
            "The Teaching of Christ", "The Atoning Death of Christ",
            "The Resurrection of Christ", "The Ascension of Christ",
            "The Second Coming of Christ", "The Final Judgment",
            "The New Heaven and New Earth", "The Eternal Life",
            "The Lake of Fire", "The Book of Life",
            "The Marriage Supper of the Lamb", "The Bride of Christ",
            "The King of Kings", "The Lord of Lords",
            "The Alpha and Omega", "The Beginning and the End",
            "The First and the Last", "The Lion of Judah",
            "The Root of David", "The Bright Morning Star",
            "The Living One", "The Faithful and True",
            "The Word of God", "The King of Glory"
        };
        for (int i = 0; i < 360; i++) {
            String base = themes[i % themes.length];
            titles[i] = base + (i >= themes.length ? " (Part " + ((i / themes.length) + 1) + ")" : "");
            contents[i] = buildContent(base, i + 1);
        }
    }

    private String buildContent(String theme, int day) {
        return "Day " + day + " \u2014 " + theme + "\n\n" +
               "The Lord invites us to draw near to Him with a sincere heart. " +
               "As we meditate on " + theme.toLowerCase() + ", let us remember that " +
               "His grace is sufficient for us, and His strength is made perfect in weakness. " +
               "Let us fix our eyes on Jesus, the author and finisher of our faith, " +
               "and let us run with perseverance the race marked out for us.\n\n" +
               "Scripture: Hebrews 12:1-2\n\n" +
               "Prayer: Lord, teach me Your ways and lead me in Your truth. " +
               "Strengthen me by Your Spirit to walk worthy of Your calling. Amen.";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devotional);
        devotionalTitle = findViewById(R.id.devotionalTitle);
        devotionalContent = findViewById(R.id.devotionalContent);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        load();
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { if (current > 0) { current--; load(); } }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (current < titles.length - 1) { current++; load(); }
            }
        });
    }

    private void load() {
        devotionalTitle.setText(titles[current]);
        devotionalContent.setText(contents[current]);
        previousButton.setEnabled(current > 0);
        nextButton.setEnabled(current < titles.length - 1);
    }
}
