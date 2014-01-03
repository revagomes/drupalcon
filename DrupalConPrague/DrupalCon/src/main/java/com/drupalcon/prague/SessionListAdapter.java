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
import android.widget.TextView;

import java.util.List;

/**
 * Session list adapter.
 */
public class SessionListAdapter extends BaseAdapter implements OnClickListener {
    private final Context context;
    private final List<Session> sessions;
    private LayoutInflater mInflater;
    private Typeface fontFace;

    private static final int NORMAL = 0;
    private static final int SPECIAL = 1;

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
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // Special item.
        if (sessions.get(position).getSpecial() == 1) {
            return SPECIAL;
        }
        // Normal row item.
        else {
            return NORMAL;
        }
    }

    static class ViewHolder {
        public int sessionId;
        public TextView title;
        public TextView speaker;
        public TextView track;
        public ImageView track_icon;
        public ImageView level;
        public LinearLayout session_item;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        int type = getItemViewType(position);
        Session session = sessions.get(position);

        if (convertView == null) {
            holder = new ViewHolder();

            switch (type) {
                case NORMAL:
                    convertView = mInflater.inflate(R.layout.session_normal_item, null);
                    holder.title = (TextView) convertView.findViewById(R.id.session_title);
                    //holder.speaker = (TextView) convertView.findViewById(R.id.session_speakers);
                    holder.track = (TextView) convertView.findViewById(R.id.session_track);
                    holder.track_icon = (ImageView) convertView.findViewById(R.id.session_track_icon);
                    holder.level = (ImageView) convertView.findViewById(R.id.session_level);
                    holder.session_item = (LinearLayout) convertView.findViewById(R.id.session_item);
                    break;
                case SPECIAL:
                    convertView = mInflater.inflate(R.layout.session_special_item, null);
                    holder.title = (TextView) convertView.findViewById(R.id.session_title);
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

            // We need to check for normal alone which get speakers, track and level.
           if (session.getSpecial() == 0) {

                /*String speakers = "";
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

                holder.speaker.setVisibility(View.VISIBLE);
                if (speakers.length() > 0) {
                    holder.speaker.setText(speakers);
                    holder.speaker.setTypeface(fontFace);
                }
                else {
                    holder.speaker.setVisibility(TextView.GONE);
                }*/

                // Track.
                holder.track.setVisibility(View.VISIBLE);
                holder.track_icon.setVisibility(View.VISIBLE);
                if (session.getTrack().length() > 0) {
                    String[] textIcon = session.getTrack().split("-");
                    holder.track.setText(textIcon[0]);
                    holder.track.setTypeface(fontFace);
                    int trackIcon = context.getResources().getIdentifier(textIcon[1].trim(),"drawable", context.getPackageName());
                    holder.track_icon.setImageResource(trackIcon);
                }
                else {
                    holder.track.setVisibility(TextView.GONE);
                    holder.track_icon.setVisibility(ImageView.GONE);
                }

                // Level icon.
                holder.level.setVisibility(View.VISIBLE);
                if (session.getLevel() > 0) {
                    int LevelIconId = context.getResources().getIdentifier("level_" + session.getLevel(), "drawable", context.getPackageName());
                    holder.level.setImageResource(LevelIconId);
                }
                else {
                    holder.level.setVisibility(ImageView.GONE);
                }

                // Set touch listener.
                convertView.setOnTouchListener(sessionTouch);
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
                    //holder.speaker.setTextColor(context.getResources().getColor(R.color.white));
                    holder.track.setTextColor(context.getResources().getColor(R.color.white));
                    holder.session_item.setBackgroundColor(context.getResources().getColor(R.color.press_row));
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    holder.title.setTextColor(context.getResources().getColor(R.color.dark_brown));
                    //holder.speaker.setTextColor(context.getResources().getColor(R.color.text_dark));
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