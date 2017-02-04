package com.getfhtt.fhtt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchFragment mySearchFragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.flFragment, mySearchFragment).commit();
    }

    public void userStats(String distance) {
        int metres = Integer.parseInt(distance);

        //Constants for Calculations
        float costPerKm = 0.106;
        int emissionsPerKm = 255;
        int caloriesPerKm = 112;

        //Calculation for money saved
        float moneySaved = costPerKm * metres;

        //Calculation for emissions saved
        int emissionsSaved = emissionsPerKm * metres;

        //Calculation for caloriesBurned
        int caloriesSaved = caloriesPerKm * metres;

    }
}
