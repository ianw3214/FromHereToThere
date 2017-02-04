package com.getfhtt.fhtt;

import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by Angelo on 2/4/2017.
 */

public class Route {

    JSONObject data;

    public Route(String origin, String destination, String type) {
        data = new WebConnectHelper().getRouteResponse(origin, destination, type);
        Log.e("HELP", data.toJSONString() + "");
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

    public Long getDistance() {
        JSONArray routes = (JSONArray) data.get("routes");
        JSONObject route = (JSONObject) routes.get(0);
        JSONArray legs = (JSONArray) route.get("legs");
        JSONObject info = (JSONObject) legs.get(0);
        JSONObject distance = (JSONObject) info.get("distance");
        return (Long) distance.get("value");
    }

    public String getStartAddress() {
        JSONArray routes = (JSONArray) data.get("routes");
        JSONObject route = (JSONObject) routes.get(0);
        JSONArray legs = (JSONArray) route.get("legs");
        JSONObject info = (JSONObject) legs.get(0);
        return (String) info.get("start_address");
    }

    public String getEndAddress() {
        JSONArray routes = (JSONArray) data.get("routes");
        JSONObject route = (JSONObject) routes.get(0);
        JSONArray legs = (JSONArray) route.get("legs");
        JSONObject info = (JSONObject) legs.get(0);
        return (String) info.get("end_address");
    }

    public String getCopyright() {
        JSONArray routes = (JSONArray) data.get("routes");
        JSONObject route = (JSONObject) routes.get(0);
        return (String) route.get("copyrights");
    }

    //Checks if route data was successfully loaded
    public Boolean isLoaded() {
        return data != null;
    }
}
