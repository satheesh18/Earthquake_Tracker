package com.example.earthquake;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;


public class earthquake_adapter extends ArrayAdapter<earthquake_attr> {
    private String part1, part2;

    public earthquake_adapter(Activity context, ArrayList<earthquake_attr> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        earthquake_attr y = getItem(position);
        View listitemview = convertView;
        if (listitemview == null) {
            listitemview = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView mag = (TextView) listitemview.findViewById(R.id.mag);
        mag.setText(y.getmagnitude());
        String loc = y.getlocation();
        if (loc.contains("of")) {
            String[] parts = loc.split("of");
            part1 = parts[0] + "of";
            part2 = parts[1];
        } else {
            part1 = "Near the";
            part2 = loc;
        }

        TextView loca = (TextView) listitemview.findViewById(R.id.location1);
        loca.setText(part1);
        TextView locat = (TextView) listitemview.findViewById(R.id.location2);
        locat.setText(part2);
        TextView date = (TextView) listitemview.findViewById(R.id.date);
        date.setText(y.getdate());
        TextView time = (TextView) listitemview.findViewById(R.id.time);
        time.setText(y.gettime());
        GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        // int magnitudeColor = R.color.magnitude1;
        double d = Double.parseDouble(y.getmagnitude());
        int magnitudeColor = getMagnitudeColor(d);

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        return listitemview;

    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

}

