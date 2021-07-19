package com.example.earthquake;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<List<earthquake_attr>> {
    private static final String LOG_TAG="Main Activity";
    TextView emptystate;
    ProgressBar progress;
    private earthquake_adapter adapter;
    private static final String website="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=5&limit=100";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        adapter=new earthquake_adapter(this,new ArrayList<earthquake_attr>());
        emptystate=(TextView)findViewById(R.id.empty_view);
        progress=(ProgressBar)findViewById(R.id.progressBar);
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            android.app.LoaderManager loaderManager =getLoaderManager();
            getSupportLoaderManager().initLoader(0, null, this);
        } else {
            progress.setVisibility(View.GONE);
            emptystate.setText("No internet bruh");
        }
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                earthquake_attr temp=adapter.getItem(position);
                Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse(temp.geturl()));
                startActivity(implicit);
            }
        });

    }

    @Override
    public Loader<List<earthquake_attr>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG,"oncreateloader");
        return new earthquakeloader(this,website);
    }

    @Override
    public void onLoadFinished(@NonNull androidx.loader.content.Loader<List<earthquake_attr>> loader, List<earthquake_attr> data) {
        Log.i(LOG_TAG,"onloadfinished");
        adapter.clear();
        progress.setVisibility(View.GONE);
        if (data != null && !data.isEmpty()){
            adapter.addAll(data);
        }
        else {
            emptystate.setText("no_data_found");
        }
    }

    @Override
    public void onLoaderReset(@NonNull androidx.loader.content.Loader<List<earthquake_attr>> loader) {
        Log.i(LOG_TAG,"onloadreset");
        adapter.clear();
    }




}
