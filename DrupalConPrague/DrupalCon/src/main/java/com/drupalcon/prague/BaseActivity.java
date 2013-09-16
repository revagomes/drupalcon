package com.drupalcon.prague;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseActivity extends Activity {

    public boolean clickRefreshButton = false;
    public boolean showFavoritesButton = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add listener on menu buttons.
        ImageView go_to_program = (ImageView) findViewById(R.id.menu_program);
        go_to_program.setId(1);
        go_to_program.setOnTouchListener(menuBar);
        ImageView go_to_speakers = (ImageView) findViewById(R.id.menu_speakers);
        go_to_speakers.setId(2);
        go_to_speakers.setOnTouchListener(menuBar);
        ImageView go_to_location = (ImageView) findViewById(R.id.menu_location);
        go_to_location.setId(3);
        go_to_location.setOnTouchListener(menuBar);
        ImageView go_to_information = (ImageView) findViewById(R.id.menu_information);
        go_to_information.setId(4);
        go_to_information.setOnTouchListener(menuBar);

        // Favorites listener or hider.
        ImageButton go_to_favorites = (ImageButton) findViewById(R.id.header_go_to_favorites);
        if (showFavoritesButton) {
            go_to_favorites.setId(5);
            go_to_favorites.setOnTouchListener(menuBar);
        }
        else {
            go_to_favorites.setVisibility(ImageButton.GONE);
        }
    }

    /**
     * MenuBar button listener.
     */
    private final View.OnTouchListener menuBar = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent motionEvent) {
            switch (v.getId()) {
                case 1:
                    Intent program = new Intent(getBaseContext(), SessionList.class);
                    setBackGroundColor(v, motionEvent, program);
                    break;
                case 2:
                    Intent speakers = new Intent(getBaseContext(), SpeakerList.class);
                    setBackGroundColor(v, motionEvent, speakers);
                    break;
                case 3:
                    Intent location = new Intent(getBaseContext(), Location.class);
                    setBackGroundColor(v, motionEvent, location);
                    break;
                case 4:
                    Intent information = new Intent(getBaseContext(), Information.class);
                    setBackGroundColor(v, motionEvent, information);
                    break;
                case 5:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        Intent favorites = new Intent(getBaseContext(), Favorites.class);
                        startActivity(favorites);
                    }
                    break;
            }

            return true;
        }
    };

    /**
     * Set backgroundColor based on action.
     */
    public void setBackGroundColor(View v, MotionEvent e, Intent activity) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setBackgroundColor(getResources().getColor(R.color.press_menu));
                startActivity(activity);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                v.setBackgroundColor(Color.TRANSPARENT);
                break;
        }
    }

    /**
     * Set text title.
     *
     * @param textViewId
     *   The textView id.
     * @param stringId
     *   The string resource id.
     */
    public void setTextViewString(Integer textViewId, Integer stringId) {
        TextView tv = (TextView) findViewById(textViewId);
        tv.setText(stringId);
    }

    /**
     * Set Font to Futura medium.
     *
     * @param textViewId
     *   The textView id.
     */
    public void setFontToFuturaMedium(Integer textViewId) {
        TextView tv = (TextView) findViewById(textViewId);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Futura Medium.ttf");
        tv.setTypeface(tf);
    }

    /**
     * Set Font to PT Sans regular.
     *
     * @param textViewId
     *   The textView id.
     */
    public void setFontToPTSansRegular(Integer textViewId) {
        TextView tv = (TextView) findViewById(textViewId);
        Typeface tf = Typeface.createFromAsset(getAssets(), "PTC55F.ttf");
        tv.setTypeface(tf);
    }

    /**
     * Returns the text string for a date based on a day.
     */
    public static int checkDay(int day, boolean returnFirstIfNotFound, Context context) {

        int day_integer = 0;
        int index = -1;
        int[] date_int_resources = context.getResources().getIntArray(R.array.dates_int);

        for (int i = 0; i < date_int_resources.length; i++) {
            if (date_int_resources[i] == day) {
                index = i;
            }
        }

        if (index != -1) {
            day_integer = day;
        }
        else if (returnFirstIfNotFound) {
            day_integer = date_int_resources[0];
        }

        return day_integer;
    }

    /**
     * Returns the text string for a date based on a day.
     */
    public static String getDateFromTimestamp(int day, boolean returnFirstIfNotFound, Context context) {

        String date_text = "";
        int index = -1;
        int[] date_int_resources = context.getResources().getIntArray(R.array.dates_int);
        String[] date_string_resources = context.getResources().getStringArray(R.array.dates_full);

        for (int i = 0; i < date_int_resources.length; i++) {
            if (date_int_resources[i] == day) {
                index = i;
            }
        }

        if (index != -1) {
            date_text = date_string_resources[index];
        }
        else if (returnFirstIfNotFound) {
            date_text = date_string_resources[0];
        }

        return date_text;
    }
}
