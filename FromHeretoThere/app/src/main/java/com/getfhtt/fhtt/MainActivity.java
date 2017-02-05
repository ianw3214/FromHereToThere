package com.getfhtt.fhtt;

import android.app.FragmentTransaction;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        SearchFragment mySearchFragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.flFragment, mySearchFragment).commit();
    }



    public void searchAndDisplay(String origin, String destination){
        getSupportActionBar().show();
        Bundle bundle = new Bundle();
        bundle.putString("origin", origin);
        bundle.putString("destination", destination);


        ResultsFragment rfResults = new ResultsFragment();
        rfResults.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.flFragment, rfResults).commit();
    }
}
