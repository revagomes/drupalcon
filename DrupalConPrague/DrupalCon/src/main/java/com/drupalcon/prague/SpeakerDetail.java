package com.drupalcon.prague;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class SpeakerDetail extends BaseActivity {

    public Speaker speaker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.speaker_detail);
        super.onCreate(savedInstanceState);

        // Get speaker.
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        int speakerId = extras.getInt("speakerId");
        DatabaseHandler db = new DatabaseHandler(this);
        speaker = db.getSpeaker(speakerId);

        // Set header title.
        setTextViewString(R.id.header_title, R.string.menu_speakers);

        // Speaker name.
        TextView st = (TextView) findViewById(R.id.speaker_name);
        st.setText(speaker.getFirstName() + ' ' + speaker.getLastName());

        // Sessions of this speaker.
        List<Session> sessions = db.getSpeakerSessions(speaker.getId());
        SessionListSmallAdapter adapter = new SessionListSmallAdapter(this, sessions);
        int dp = (int) getResources().getDimension(R.dimen.global_padding);
        int dp_small = (int) getResources().getDimension(R.dimen.global_small_padding);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dp, dp_small, dp, dp_small);

        LinearLayout session_list = (LinearLayout) findViewById(R.id.session_list);

        for (int i = 0; i < adapter.getCount(); i++) {
            View item = adapter.getView(i, null, null);
            item.setLayoutParams(layoutParams);
            session_list.addView(item);
        }

        // Set fonts and colors.
        setFontToFuturaMedium(R.id.header_title);
        setFontToFuturaMedium(R.id.speaker_name);
    }

}
