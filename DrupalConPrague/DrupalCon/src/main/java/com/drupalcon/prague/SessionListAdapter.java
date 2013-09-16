package com.drupalcon.prague;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Session list adapter.
 */
public class SessionListAdapter extends BaseAdapter implements OnClickListener {
    private final Context context;
    private final List<Session> sessions;
    private LayoutInflater mInflater;
    private Typeface fontFace;

    private int from;
    private int to;
    private Date startHour;
    private Date endHour;
    private DateFormat sdf;

    private static final int NORMAL = 0;
    private static final int SPECIAL = 1;
    private static final int SLOTTIME = 2;

    public SessionListAdapter(Context context, List<Session> sessions) {
        this.context = context;
        this.sessions = sessions;
        this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.fontFace = Typeface.createFromAsset(context.getAssets(), "PTC55F.ttf");
    }

    public int getCount() {
        return sessions.size();
    }

    public Session getItem(int position) {
        return sessions.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public void onClick(View view) {
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        int special = sessions.get(position).getSpecial();

        // Slot item.
        if (special == 2) {
            return SLOTTIME;
        }
        // Special item (e.g. lunch)
        else if (special == 1) {
            return SPECIAL;
        }
        // Normal row item.
        else {
            return NORMAL;
        }
    }

    public static class ViewHolder {
        public int sessionId;
        public TextView title;
        public TextView speaker;
        public TextView time;
        public TextView track;
        public ImageView track_icon;
        public ImageView level;
        public LinearLayout session_item;
        public RelativeLayout session_meta_info;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        int type = getItemViewType(position);
        Session session = sessions.get(position);

        if (convertView == null) {
            holder = new ViewHolder();

            switch (type) {
                case NORMAL:
                    convertView = mInflater.inflate(R.layout.session_normal_item, null);
                    holder.title = (TextView) convertView.findViewById(R.id.session_title);
                    holder.speaker = (TextView) convertView.findViewById(R.id.session_speakers);
                    holder.track = (TextView) convertView.findViewById(R.id.session_track);
                    holder.track_icon = (ImageView) convertView.findViewById(R.id.session_track_icon);
                    holder.level = (ImageView) convertView.findViewById(R.id.session_level);
                    holder.session_item = (LinearLayout) convertView.findViewById(R.id.session_item);
                    holder.session_meta_info = (RelativeLayout) convertView.findViewById(R.id.session_meta_info);
                    break;
                case SLOTTIME:
                    convertView = mInflater.inflate(R.layout.session_slot_item, null);
                    holder.time = (TextView) convertView.findViewById(R.id.session_title_time);
                    break;
                case SPECIAL:
                    convertView = mInflater.inflate(R.layout.session_special_item, null);
                    holder.title = (TextView) convertView.findViewById(R.id.session_title);
                    holder.time = (TextView) convertView.findViewById(R.id.session_time);
                    break;
            }
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (session != null) {

            // Id.
            holder.sessionId = session.getId();

            // Every item has a title.
            holder.title.setText(session.getTitle());
            holder.title.setTypeface(fontFace);

            // We switch on normal and special. Slot time
            // does not need extra handling.
            switch (type) {

                case NORMAL:

                    // Normal sessions also get speakers, hour and favorite button.
                    String speakers = "";
                    List<Speaker> speakerList = session.getSpeakers();
                    for (int i = 0; i < speakerList.size(); i++) {
                        Speaker speakerItem = speakerList.get(i);
                        if (i > 0) {
                            speakers += " - ";
                        }
                        if (speakerItem.getFirstName().length() > 0) {
                            speakers += speakerItem.getFirstName() + ' ' + speakerItem.getLastName();
                        }
                        else {
                            speakers += speakerItem.getUsername();
                        }
                    }

                    if (speakers.length() > 0) {
                        holder.speaker.setText(speakers);
                        holder.speaker.setTypeface(fontFace);
                    }
                    else {
                        holder.speaker.setVisibility(TextView.GONE);
                    }

                    // Track.
                    if (session.getTrack().length() > 0) {
                        holder.track.setText(session.getTrack());
                        holder.track.setTypeface(fontFace);
                        // @todo when the service has been updated.
                        holder.track_icon.setImageResource(R.drawable.business);
                    }
                    else {
                        holder.track.setVisibility(TextView.GONE);
                        holder.track_icon.setVisibility(ImageView.GONE);
                    }

                    // Level icon.
                    int level = session.getLevel();
                    if (level > 0) {
                        int LevelIconId = context.getResources().getIdentifier("level_" + session.getLevel(),"drawable", context.getPackageName());
                        holder.level.setImageResource(LevelIconId);
                    }
                    else {
                        holder.level.setVisibility(ImageView.GONE);
                    }

                    // Set touch listener.
                    convertView.setOnTouchListener(sessionTouch);
                    break;

                case SPECIAL:
                    from = session.getStartDate();
                    to = session.getEndDate();
                    sdf = new SimpleDateFormat("kk:mm");
                    startHour = new Date((long)from * 1000);
                    endHour = new Date((long)to * 1000);
                    holder.time.setText(sdf.format(startHour) + " - " + sdf.format(endHour));
                    holder.time.setTypeface(fontFace);
                    break;
            }
        }

        return convertView;
    }

    /**
     * onTouchListener for session.
     */
    View.OnTouchListener sessionTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent motionEvent) {
            ViewHolder holder = (ViewHolder)v.getTag();
            int action = motionEvent.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    holder.title.setTextColor(context.getResources().getColor(R.color.white));
                    holder.speaker.setTextColor(context.getResources().getColor(R.color.white));
                    holder.track.setTextColor(context.getResources().getColor(R.color.white));
                    holder.session_item.setBackgroundColor(context.getResources().getColor(R.color.press_row));
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    holder.title.setTextColor(context.getResources().getColor(R.color.dark_brown));
                    holder.speaker.setTextColor(context.getResources().getColor(R.color.text_dark));
                    holder.track.setTextColor(context.getResources().getColor(R.color.text_dark));
                    holder.session_item.setBackgroundColor(context.getResources().getColor(R.color.light_brown));
                    if (action == MotionEvent.ACTION_UP) {
                        int sessionId = holder.sessionId;
                        Intent intent = new Intent(context, SessionDetail.class);
                        intent.putExtra("sessionId", sessionId);
                        context.startActivity(intent);
                    }
                    break;
            }
            return true;
        }
    };

}