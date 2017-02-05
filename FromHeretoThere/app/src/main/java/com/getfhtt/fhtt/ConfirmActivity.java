package com.getfhtt.fhtt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        // get the data from the previous state to display on screen
        String mode = savedInstanceState.getString("mode");
        String emissons = savedInstanceState.getString("carEmissions");
        int calories = savedInstanceState.getInt("calories");
        String cost = savedInstanceState.getString("carCost");
        String distance = savedInstanceState.getString("distance");
        String duraction = savedInstanceState.getString("duration");
        String url = savedInstanceState.getString("url");

        // display data differently depending on the users choice of travel
        switch(mode){
            case "walking":{

            } break;
            case "bicycling": {

            } break;
            case "transit": {

            } break;
            case "driving":
            default: {

            } break;
        }
    }
}
