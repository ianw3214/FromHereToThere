package com.getfhtt.fhtt.models;

import android.os.AsyncTask;
import android.util.Log;

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
    JSONObject elevationData;
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
        return  (long) (duration.get("value"))/60 + "";
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

    // Returns the walking distance of the route
    public Long getWalkingTime(){
        Long result = 0L;
        JSONArray routes = (JSONArray) data.get("routes");
        JSONObject route = (JSONObject) routes.get(0);
        JSONArray legs = (JSONArray) route.get("legs");
        JSONObject leg = (JSONObject) legs.get(0);
        JSONArray steps = (JSONArray) leg.get("steps");
        for(int i = 0; i < steps.size(); i++){
            JSONObject current = (JSONObject) steps.get(i);
            String key = (String) current.get("travel_mode");
            if(key.equals("WALKING")){
                JSONObject distance = (JSONObject) current.get("duration");
                Long addition = (Long) distance.get("value");
                result += addition;
            }
        }
        return result/60;
    }

    // Returns the fare fee if found
    public long getFare() {
        JSONArray routes = (JSONArray) data.get("routes");
        JSONObject route = (JSONObject) routes.get(0);
        JSONObject legs = (JSONObject) route.get("fare");
        if (legs != null) {
            long amount = (long) legs.get("value");
            return amount;
        }
        return -1;
    }

    public String getCurrency(){
        JSONArray routes = (JSONArray) data.get("routes");
        JSONObject route = (JSONObject) routes.get(0);
        JSONObject legs = (JSONObject) route.get("fare");
        if (legs != null) {
            String curr= (String) legs.get("currency");
            return curr;
        }
        return "";
    }

    //Checks if route data was successfully loaded
    public Boolean isLoaded() {
        return data != null;
    }

    private final String USER_AGENT = "Mozilla/5.0";

    public JSONObject getStart(){
        JSONArray routes = (JSONArray) data.get("routes");
        JSONObject route = (JSONObject) routes.get(0);
        JSONArray legs = (JSONArray) route.get("legs");
        JSONObject leg = (JSONObject) legs.get(0);
        return (JSONObject) leg.get("start_location");
    }

    public JSONObject getEnd(){
        JSONArray routes = (JSONArray) data.get("routes");
        JSONObject route = (JSONObject) routes.get(0);
        JSONArray legs = (JSONArray) route.get("legs");
        JSONObject leg = (JSONObject) legs.get(0);
        return (JSONObject) leg.get("end_location");
    }

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

    // check to make sure that the map can find goecodes for locations
    public boolean checkGeoCoder(){
        JSONArray arr = (JSONArray) data.get("geocoded_waypoints");
        JSONObject status = (JSONObject) arr.get(0);
        String key = (String) status.get("geocoder_status");
        if(key == "NOT_FOUND"){return false;}
        return true;
    }

    // check to make sure routes exist for the card
    public boolean checkRoutes(){
        JSONArray arr = (JSONArray) data.get("routes");
        if(arr.size() == 0){ return false; }
        return true;
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
