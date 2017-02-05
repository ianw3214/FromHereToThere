package com.getfhtt.fhtt;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide();
        getSupportActionBar().setTitle("");
        SearchFragment mySearchFragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.flFragment, mySearchFragment).commit();
    }

    public void searchAndDisplay(String origin, String destination) {
        //getSupportActionBar().show();
        getSupportActionBar().setTitle("Trip Details");
        Bundle bundle = new Bundle();
        bundle.putString("origin", origin);
        bundle.putString("destination", destination);


        ResultsFragment rfResults = new ResultsFragment();
        rfResults.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.flFragment, rfResults).commit();
    }

    public void showConfirm(Bundle data) {
        Intent in = new Intent(MainActivity.this, ConfirmActivity.class);
        in.putExtras(data);
        startActivity(in);
    }

    public void goBack() {
        //getSupportActionBar().hide();
        getSupportActionBar().setTitle("");
        getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public void onBackPressed() {
        getSupportActionBar().setTitle("");
        //getSupportActionBar().hide();
        super.onBackPressed();
    }
}
