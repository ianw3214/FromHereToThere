package com.getfhtt.fhtt;

import android.os.AsyncTask;
import android.util.Log;

import com.getfhtt.fhtt.models.User;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Angelo on 2/4/2017.
 */

public class ElevationRoute {

    JSONObject data;
    JSONObject elevationData;
    DataLoadedListener listener;

    public ElevationRoute(JSONObject start, JSONObject end) {
        this.listener = null;
        String startLat = Double.toString((double)start.get("lat")) ;
        String startLng = Double.toString((double)start.get("lng"));
        String endLat = Double.toString((double)end.get("lat"));
        String endLng = Double.toString((double)end.get("lng"));
        String api = "https://maps.googleapis.com/maps/api/elevation/json?locations=";
        api += startLat + "," + startLng + "|";
        api += endLat + "," + endLng;
        api += "&key=AIzaSyA8HJbr8Yi5QITSEIFH6Y05iy4Bojyjn5o";
        new RetrieveDataTask().execute(api);
    }

    // Returns the elevation change between source and destination
    public Double getElevation(){
        JSONArray results = (JSONArray) data.get("results");
        JSONObject start = (JSONObject) results.get(0);
        JSONObject end = (JSONObject) results.get(1);
        if(start.get("elevation") instanceof  Long){
            Long startElevation = (Long) start.get("elevation");
            if(end.get("elevation") instanceof Long){
                Long endElevation = (Long) end.get("elevation");
                Long result = endElevation - startElevation;
                return result.doubleValue();
            }else{
                Double endElevation = (Double) end.get("elevation");
                Double startDouble = startElevation.doubleValue();
                return endElevation - startDouble;
            }
        }else {
            Double startElevation = (Double) start.get("elevation");
            if (end.get("elevation") instanceof Long) {
                Long endElevation = (Long) end.get("elevation");
                Double endDouble = endElevation.doubleValue();
                return endDouble - startElevation;
            } else {
                Double endElevation = (Double) end.get("elevation");
                return endElevation - startElevation;
            }
        }
    }

    public JSONObject getData(){
        return data;
    }

    //Checks if route data was successfully loaded
    public Boolean isLoaded() {
        return data != null;
    }

    private final String USER_AGENT = "Mozilla/5.0";

    public interface DataLoadedListener {
        // This interface is used to define the UserLoaded listener, which is used to delay activities until Firebase responds
        public void onDataLoaded();
    }

    public void setDataLoadedListener(DataLoadedListener listener) {
        this.listener = listener;
    }

    private String parseStringForLocation(String input) {
        return input.replaceAll(" ", "+");
    }

    class RetrieveDataTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            StringBuffer response = new StringBuffer();
            try {
                URL obj = new URL(urls[0]);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", USER_AGENT);
                int responseCode = con.getResponseCode();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } catch (MalformedURLException e) {
                Log.e("GET", "Malformed URL:" + e.getMessage());
            } catch (IOException e) {
                Log.e("GET", "IO Exception:" + e.getMessage());
            } catch (Exception e) {
                Log.e("GET", "cannot send get data:" + e.toString());
            }
            return null;
        }

        protected void onPostExecute(String returned) {
            JSONParser parser = new JSONParser();
            JSONObject result;
            try {
                Object obj = parser.parse(returned);
                result = (JSONObject) obj;
                data =  result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            listener.onDataLoaded();
        }
    }
}
