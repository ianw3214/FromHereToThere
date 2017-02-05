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

public class Route {

    JSONObject data;
    DataLoadedListener listener;


    public Route(String origin, String destination, String type) {
        this.listener = null;
        String apiLink = "https://maps.googleapis.com/maps/api/directions/json?mode=";
        apiLink += type + "&origin=";
        apiLink += this.parseStringForLocation(origin) + "&destination=";
        apiLink += this.parseStringForLocation(destination);
        apiLink += "&key=AIzaSyA8HJbr8Yi5QITSEIFH6Y05iy4Bojyjn5o";
        new RetrieveDataTask().execute(apiLink);
    }

    public JSONObject getData(){
        return data;
    }

    /**
     * Returns the time needed to travel between two points
     *
     * @return : 			A string representation of time.
     */
    public String getTravelTime() {
        JSONArray routes = (JSONArray) data.get("routes");
        JSONObject route = (JSONObject) routes.get(0);
        JSONArray legs = (JSONArray) route.get("legs");
        JSONObject info = (JSONObject) legs.get(0);
        JSONObject duration = (JSONObject) info.get("duration");
        return (String) duration.get("text");
    }

    public String getTravelTimeMin() {
        JSONArray routes = (JSONArray) data.get("routes");
        JSONObject route = (JSONObject) routes.get(0);
        JSONArray legs = (JSONArray) route.get("legs");
        JSONObject info = (JSONObject) legs.get(0);
        JSONObject duration = (JSONObject) info.get("duration");
        return (String) duration.get("value");
    }

    // Returns the distance between two points based on travel mode
    public Long getDistance() {
        JSONArray routes = (JSONArray) data.get("routes");
        JSONObject route = (JSONObject) routes.get(0);
        JSONArray legs = (JSONArray) route.get("legs");
        JSONObject info = (JSONObject) legs.get(0);
        JSONObject distance = (JSONObject) info.get("distance");
        return (Long) distance.get("value");
    }

    // Returns the geocodded start address
    public String getStartAddress() {
        JSONArray routes = (JSONArray) data.get("routes");
        JSONObject route = (JSONObject) routes.get(0);
        JSONArray legs = (JSONArray) route.get("legs");
        JSONObject info = (JSONObject) legs.get(0);
        return (String) info.get("start_address");
    }

    // Returns the geocodded end address
    public String getEndAddress() {
        JSONArray routes = (JSONArray) data.get("routes");
        JSONObject route = (JSONObject) routes.get(0);
        JSONArray legs = (JSONArray) route.get("legs");
        JSONObject info = (JSONObject) legs.get(0);
        return (String) info.get("end_address");
    }

    // Returns the mandatory copyright string
    public String getCopyright() {
        JSONArray routes = (JSONArray) data.get("routes");
        JSONObject route = (JSONObject) routes.get(0);
        return (String) route.get("copyrights");
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
