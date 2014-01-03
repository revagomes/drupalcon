package com.drupalcon.prague;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SessionDetail extends BaseActivity {

    public Session session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.session_detail);
        super.onCreate(savedInstanceState);

        // Get session.
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        int sessionId = extras.getInt("sessionId");
        DatabaseHandler db = new DatabaseHandler(this);
        session = db.getSession(sessionId);

        // Set header title.
        setTextViewString(R.id.header_title, R.string.menu_program);

        // Title.
        TextView st = (TextView) findViewById(R.id.session_title);
        st.setText(session.getTitle());

        // Time.
        int from = session.getStartDate();
        int to = session.getEndDate();
        DateFormat sdf = new SimpleDateFormat("kk:mm");
        Date startHour = new Date((long)from * 1000);
        Date endHour = new Date((long)to * 1000);
        TextView stime = (TextView) findViewById(R.id.session_time);
        String Date = getDateFromInteger(session.getDay(), false, this);
        stime.setText(Date + " | " + sdf.format(startHour) + " - " + sdf.format(endHour));

        // Session level icon.
        ImageView sl = (ImageView) findViewById(R.id.session_level);
        int level = session.getLevel();
        if (level > 0) {
            int LevelIconId = this.getResources().getIdentifier("level_" + session.getLevel(),"drawable", this.getPackageName());
            sl.setImageResource(LevelIconId);
        }
        else {
            sl.setVisibility(ImageView.GONE);
            TextView slt = (TextView) findViewById(R.id.session_level_text);
            slt.setVisibility(TextView.GONE);
        }

        // Room.
        TextView sr = (TextView) findViewById(R.id.session_room);
        if (session.getRoom().length() > 0) {
            sr.setText(session.getRoom());
        }
        else {
            sr.setVisibility(TextView.GONE);
        }

        // Track.
        TextView str = (TextView) findViewById(R.id.session_track);
        ImageView stri = (ImageView) findViewById(R.id.session_track_icon);
        if (session.getTrack().length() > 0) {
            String[] textIcon = session.getTrack().split("-");
            str.setText(textIcon[0]);
            int trackIcon = this.getResources().getIdentifier(textIcon[1],"drawable", this.getPackageName());
            stri.setImageResource(trackIcon);
        }
        else {
            str.setVisibility(TextView.GONE);
            stri.setVisibility(ImageView.GONE);
        }

        // Description.
        TextView sd = (TextView) findViewById(R.id.session_description);
        sd.setText(session.getDescription());

        // Set favorite button and attach listener.
        TextView favoriteText = (TextView) findViewById(R.id.session_favorite_action);
        ImageButton favoriteButton = (ImageButton) findViewById(R.id.session_favorite);
        favoriteButton.setOnClickListener(actionFavorite);
        favoriteText.setOnClickListener(actionFavorite);
        if (session.getFavorite() == 0) {
            favoriteButton.setImageResource(R.drawable.non_favorited_session);
            favoriteText.setText(getString(R.string.favorite_add));
        }
        else {
            favoriteButton.setImageResource(R.drawable.favorited_session);
            favoriteText.setText(getString(R.string.favorite_remove));
        }

        // Speakers.
        /*if (session.getSpeakers().size() > 0) {
            SpeakerListAdapter adapter = new SpeakerListAdapter(this, session.getSpeakers());
            int dp = (int) getResources().getDimension(R.dimen.global_padding);
            int dp_small = (int) getResources().getDimension(R.dimen.global_small_padding);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(dp, dp_small, dp, dp_small);

            LinearLayout speaker_list = (LinearLayout) findViewById(R.id.speaker_list);

            for (int i = 0; i < adapter.getCount(); i++) {
                View item = adapter.getView(i, null, null);
                item.setLayoutParams(layoutParams);
                speaker_list.addView(item);
            }
        }*/

        // Set fonts and colors.
        setFontToFuturaMedium(R.id.header_title);
        setFontToFuturaMedium(R.id.session_title);
        setFontToPTSansRegular(R.id.session_time);
        setFontToPTSansRegular(R.id.session_room);
        setFontToPTSansRegular(R.id.session_level_text);
        setFontToPTSansRegular(R.id.session_favorite_action);
        setFontToPTSansRegular(R.id.session_description);
        setFontToPTSansRegular(R.id.session_track);

    }

    /**
     * Favorite listener.
     */
    private final View.OnClickListener actionFavorite = new View.OnClickListener() {
        public void onClick(View v) {
            // Get favorite.
            int favorite = session.getFavorite();

            // Switch image and text.
            TextView favoriteText = (TextView) findViewById(R.id.session_favorite_action);
            ImageView i = (ImageView) findViewById(R.id.session_favorite);
            int setFavorite;
            if (favorite == 0) {
                setFavorite = 1;
                i.setImageResource(R.drawable.favorited_session);
                favoriteText.setText(getString(R.string.favorite_remove));
            } else {
                setFavorite = 0;
                i.setImageResource(R.drawable.non_favorited_session);
                favoriteText.setText(getString(R.string.favorite_add));
            }

            // Update in database.
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            db.saveFavorite(setFavorite, session.getId());

            // Update session in memory as well.
            session.setFavorite(setFavorite);
        }
    };
}
