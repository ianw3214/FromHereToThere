package com.getfhtt.fhtt;

import android.app.FragmentTransaction;
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

    public void searchAndDisplay(String origin, String destination){
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
