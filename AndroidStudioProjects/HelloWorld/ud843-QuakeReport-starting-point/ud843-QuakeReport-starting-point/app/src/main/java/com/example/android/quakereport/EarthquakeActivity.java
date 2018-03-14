/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//import android.app.LoaderManager;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

//    private static final String USGS_REQUEST_URL =
//            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query";

    private ListView earthquakeListView;

    private TextView mEmptyStateTextView;

    private ProgressBar mProgressBar;

    private EarthQuakeAdapter mAdapter;

    private static final int EARTHQUAKE_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

//        // Create a fake list of earthquake locations.
//        ArrayList<Earthquake> earthquakes = new ArrayList<>();
//        earthquakes.add(new Earthquake("7.2", "San Francisco", "Feb 2, 2016"));
//        earthquakes.add(new Earthquake("6.1", "London", "July 20, 2015"));
//        earthquakes.add(new Earthquake("3.9", "Tokyo", "Nov 10, 2014"));
//        earthquakes.add(new Earthquake("5.4", "Mexico City", "May 3, 2014"));
//        earthquakes.add(new Earthquake("2.8", "Moscow", "Jan 31, 2013"));
//        earthquakes.add(new Earthquake("4.9", "Rio de Janeiro", "Aug 19, 2012"));
//        earthquakes.add(new Earthquake("1.6", "Paris", "Oct 30, 2011"));
//
//        EarthQuakeAdapter adapter = new EarthQuakeAdapter(this, earthquakes);
//
//        // Find a reference to the {@link ListView} in the layout
//        ListView earthquakeListView = (ListView) findViewById(R.id.list);
//
////        // Create a new {@link ArrayAdapter} of earthquakes
////        ArrayAdapter<Earthquake> adapter = new ArrayAdapter<String>(
////                this, R.layout.earth_quake_list_item, earthquakes);
//
//        // Set the adapter on the {@link ListView}
//        // so the list can be populated in the user interface
//        if (earthquakeListView != null) {
//            earthquakeListView.setAdapter(adapter);
//        }

        earthquakeListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        // mEmptyStateTextView.setText(R.string.loading);

        earthquakeListView.setEmptyView(mEmptyStateTextView);

        mProgressBar = (ProgressBar) findViewById(R.id.loading_spinner);

        //ArrayList<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(USGS_REQUEST_URL);
        mAdapter = new EarthQuakeAdapter(this, new ArrayList<Earthquake>());

        if (earthquakeListView != null) {
            earthquakeListView.setAdapter(mAdapter);

            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Earthquake currentEarthquake = mAdapter.getItem(position);

                    Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            });
        }

//        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
//        task.execute(USGS_REQUEST_URL);

        // Get a reference  to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active data network
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
            // Get a reference in the LoaderManager, in order to intreact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader, Pass in the int ID constant defined above and pass in
            // null for the bundle. Pass in this activity for the LoaderCallbacks parameter
            // (which is valid because this activity implements the LoaderCallbacks interface).
            Log.d(LOG_TAG, "initLoader: Initialising loader");
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            mProgressBar.setVisibility(View.GONE);

            // Update empty state with no internet connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle bundle) {
        Log.d(LOG_TAG, "onCreateLoader: creating");
        // Create a new loader for the given URL

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
//        uriBuilder.appendQueryParameter("orderby", "time");
        uriBuilder.appendQueryParameter("orderby", orderBy);

//        return new EarthquakeLoader(this, USGS_REQUEST_URL);
        return new EarthquakeLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        // Hide the loading indicator
        mProgressBar.setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_earthquakes);


        Log.d(LOG_TAG, "onLoadFinished: finishing");
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // if there is a valid list of {@link Earthquake}s, data set.
        // This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()){
            mAdapter.addAll(earthquakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        Log.d(LOG_TAG, "onLoaderReset: resetting");
        // Loader reset, so we can clear out our existing data
        mAdapter.clear();
    }

//    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>>{
//        @Override
//        protected List<Earthquake> doInBackground(String... urls) {
//            if (urls.length < 1 || urls[0] == null){
//                return null;
//            }
//            List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(urls[0]);
//            return earthquakes;
//        }
//
//        @Override
//        protected void onPostExecute(List<Earthquake> data) {
//            // Clear the adapter of previous earthquake data
//            mAdapter.clear();
//
//            // if there is a valid list of {@link Earthquake}s, data set.
//            // This will trigger the ListView to update.
//            if (data != null && !data.isEmpty()){
//                mAdapter.addAll(data);
//            }
//        }
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
