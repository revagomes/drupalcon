package com.drupalcon.prague;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Location extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.location);
        super.onCreate(savedInstanceState);

        // Set header title.
        setTextViewString(R.id.header_title, R.string.menu_location);

        // Add listener on map link.
        TextView mapLink = (TextView) findViewById(R.id.location_map_link);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((cm.getActiveNetworkInfo() != null) && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
            mapLink.setOnClickListener(actionMap);
            setFontToPTSansRegular(R.id.location_map_link);
        }
        else {
            mapLink.setVisibility(TextView.GONE);
        }

        // Set fonts and colors.
        setFontToFuturaMedium(R.id.header_title);
        setFontToPTSansRegular(R.id.location_info);
    }

    /**
     * Map listener.
     */
    private final View.OnClickListener actionMap = new View.OnClickListener() {
        public void onClick(View v) {
            String mapUrl = "http://maps.google.com/maps?q=" + getString(R.string.location_geo_lat) + "," + getString(R.string.location_geo_long);
            Uri uri = Uri.parse(mapUrl);
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    };
}