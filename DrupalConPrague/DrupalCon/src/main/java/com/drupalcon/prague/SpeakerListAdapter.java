package com.drupalcon.prague;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class SpeakerListAdapter extends BaseAdapter implements View.OnClickListener {

    private final Context context;
    private final List<Speaker> speakers;
    private LayoutInflater mInflater;
    private Typeface fontFace;

    public SpeakerListAdapter(Context context, List<Speaker> speakers) {
        this.context = context;
        this.speakers = speakers;
        this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.fontFace = Typeface.createFromAsset(context.getAssets(), "PTC55F.ttf");
    }

    public int getCount() {
        return speakers.size();
    }

    public Speaker getItem(int position) {
        return speakers.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public void onClick(View view) {

    }

    public static class ViewHolder {
        public int speakerId;
        public TextView speaker;
        public LinearLayout speaker_item;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Speaker speaker = speakers.get(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.speaker_item, null);
            holder = new ViewHolder();
            holder.speaker = (TextView) convertView.findViewById(R.id.speaker_name);
            holder.speaker_item = (LinearLayout) convertView.findViewById(R.id.speaker_item);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (speaker != null) {

            // Id.
            holder.speakerId = speaker.getId();

            // Name.
            if (speaker.getFirstName().length() > 0) {
                holder.speaker.setText(speaker.getFirstName() + ' ' + speaker.getLastName());
            }
            else {
                holder.speaker.setText(speaker.getUsername());
            }
            holder.speaker.setTypeface(fontFace);

            // Set touch listener.
            convertView.setOnTouchListener(speakerTouch);
        }

        return convertView;
    }

    /**
     * onTouchListener for speaker.
     */
    View.OnTouchListener speakerTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent motionEvent) {
            ViewHolder holder = (ViewHolder)v.getTag();
            int action = motionEvent.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    holder.speaker.setTextColor(context.getResources().getColor(R.color.white));
                    holder.speaker_item.setBackgroundColor(context.getResources().getColor(R.color.press_row));
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    holder.speaker.setTextColor(context.getResources().getColor(R.color.text_dark));
                    holder.speaker_item.setBackgroundColor(context.getResources().getColor(R.color.light_brown));
                    if (action == MotionEvent.ACTION_UP) {
                        int sessionId = holder.speakerId;
                        Intent intent = new Intent(context, SpeakerDetail.class);
                        intent.putExtra("speakerId", sessionId);
                        context.startActivity(intent);
                    }
                    break;
            }
            return true;
        }
    };
}
