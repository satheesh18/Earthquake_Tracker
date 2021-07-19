package com.example.earthquake;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class earthquakeloader extends AsyncTaskLoader<List<earthquake_attr>> {
    private String murl;
    public earthquakeloader(@NonNull Context context, String url) {
        super(context);
        murl=url;
    }

    protected void onStartLoading(){
        forceLoad();
    }
    @Nullable
    @Override
    public List<earthquake_attr> loadInBackground() {
        if(murl==null){
            return null;
        }
        List<earthquake_attr> earthquakes=QueryUtils.fetchEarthquakeData(murl);
        return earthquakes;
    }
}
