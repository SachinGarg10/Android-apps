package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthQuakeAdapter extends ArrayAdapter<Earthquake>{

    private static final String LOCATION_SEPARATOR = " of ";

    public EarthQuakeAdapter(Activity context, ArrayList<Earthquake> earthQuakes){
        super(context, 0, earthQuakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earth_quake_list_item, parent, false);
        }

        Earthquake currentEarthQuake = getItem(position);

        TextView magTextView = (TextView) listItemView.findViewById(R.id.mag);

        // Set the proper background color on the magnitude circle.
        // Fetch the TextView, which is a GradientDrawable.
        GradientDrawable magCircle = (GradientDrawable) magTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthQuake.getMagnitude());

        // Set the color on the magnitude circle.
        magCircle.setColor(magnitudeColor);

        DecimalFormat formatMagnitude = new DecimalFormat("0.0");
        double magnitude = currentEarthQuake.getMagnitude();
        String formattedMagnitude = formatMagnitude.format(magnitude);
        magTextView.setText(formattedMagnitude);

        String originalLocation = currentEarthQuake.getPlace();
        String offsetLocation;
        String primaryLocation;

        if (originalLocation.contains(LOCATION_SEPARATOR)){
            //String[] parts = originalLocation.split("(?<=of)");
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);

            offsetLocation = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            offsetLocation = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        TextView primaryPlaceTextView = (TextView) listItemView.findViewById(R.id.primary_place);
        primaryPlaceTextView.setText(primaryLocation);

        TextView offsetPlaceTextView = (TextView) listItemView.findViewById(R.id.offset_place);
        offsetPlaceTextView.setText(offsetLocation);

        Date dateObject = new Date(currentEarthQuake.getTimeInMiliseconds());

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        String formattedDate = formatDate(dateObject);
        dateTextView.setText(formattedDate);

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        String formattedTime = formatTime(dateObject);
        timeTextView.setText(formattedTime);

        return listItemView;
    }

    private String formatDate(Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject){
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        return timeFormat.format(dateObject);
    }

    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor){
            case 0:
            case 1: magnitudeColorResourceId = R.color.magnitude1;
                break;

            case 2: magnitudeColorResourceId = R.color.magnitude2;
                break;

            case 3: magnitudeColorResourceId = R.color.magnitude3;
                break;

            case 4: magnitudeColorResourceId = R.color.magnitude4;
                break;

            case 5: magnitudeColorResourceId = R.color.magnitude5;
                break;

            case 6: magnitudeColorResourceId = R.color.magnitude6;
                break;

            case 7: magnitudeColorResourceId = R.color.magnitude7;
                break;

            case 8: magnitudeColorResourceId = R.color.magnitude8;
                break;

            case 9: magnitudeColorResourceId = R.color.magnitude9;
                break;

            default: magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
