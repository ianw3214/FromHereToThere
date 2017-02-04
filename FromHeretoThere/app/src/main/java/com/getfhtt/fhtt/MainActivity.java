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
}
