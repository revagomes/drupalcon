package com.drupalcon.prague;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class Favorites extends BaseActivity {

    public List<Session> sessions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Do not show on favorites button.
        showFavoritesButton = false;

        setContentView(R.layout.favorites);
        super.onCreate(savedInstanceState);

        DatabaseHandler db = new DatabaseHandler(this);
        sessions = db.getFavorites();

        SessionListSmallAdapter adapter = new SessionListSmallAdapter(this, sessions);
        ListView favorites_list = (ListView) findViewById(R.id.favorites_list);
        favorites_list.setAdapter(adapter);

        // Set header title.
        setTextViewString(R.id.header_title, R.string.menu_favorites);

        // Set fonts and colors.
        setFontToFuturaMedium(R.id.header_title);
    }
}
