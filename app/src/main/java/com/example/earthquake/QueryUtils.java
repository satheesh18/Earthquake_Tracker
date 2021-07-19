package com.example.earthquake;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {
    private static final String LOG_TAG = "error";

    /** Sample JSON response for a USGS query */

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }
    public static List<earthquake_attr> fetchEarthquakeData(String requestUrl) {
        // Create URL object
        URL url = createurl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHTTPrequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<earthquake_attr> earthquakes = extractEarthquakes(jsonResponse);

        // Return the list of {@link Earthquake}s
        return earthquakes;
    }
    /**
     * Return a list of {@link earthquake_attr} objects that has been built up from
     * parsing a JSON response.
     * @param SAMPLE_JSON_RESPONSE
     */
    private static ArrayList<earthquake_attr> extractEarthquakes(String SAMPLE_JSON_RESPONSE) {


        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<earthquake_attr> earthquakes = new ArrayList<>();


        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject base=new JSONObject(SAMPLE_JSON_RESPONSE);
            JSONArray earray=base.getJSONArray("features");
            for(int i=0;i<earray.length();i++){
                JSONObject cearth=earray.getJSONObject(i);
                JSONObject properties=cearth.getJSONObject("properties");
                String magnitude=properties.getString("mag");
                String location=properties.getString("place");
                long time=properties.getLong("time");
                String url=properties.getString("url");
                earthquakes.add(new earthquake_attr(magnitude,location,time,url));
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }
    private static URL createurl(String website){
        URL url=null;
        try{
            url=new URL(website);
        }
        catch (MalformedURLException exception){
            //LOG statement
        }
        return url;
    }
    private static String makeHTTPrequest(URL url) throws IOException {
        String json="";
        if(url==null){
            return json;
        }
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        try{
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            inputStream=urlConnection.getInputStream();
            json=readfromstream(inputStream);
        }
        catch (IOException e){
            //LOG
        }
        finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return json;
    }
    private static String readfromstream(InputStream inputStream) throws IOException{
        StringBuilder output=new StringBuilder();
        if (inputStream!=null){
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader=new BufferedReader(inputStreamReader);
            String line=reader.readLine();
            while(line!=null){
                output.append(line);
                line=reader.readLine();
            }
        }
        return output.toString();
    }

}