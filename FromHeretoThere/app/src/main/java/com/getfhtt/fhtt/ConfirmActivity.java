package com.getfhtt.fhtt;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        Bundle extras = getIntent().getExtras();
        // get the data from the previous state to display on screen
        String mode = extras.getString("mode");
        String emissons = extras.getString("carEmissions");
        int calories = extras.getInt("calories");
        String cost = extras.getString("carCost");
        String distance = extras.getString("distance");
        String duraction = extras.getString("duration");
        final String url = extras.getString("url");

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvText = (TextView) findViewById(R.id.tvText);
        Button bContinue = (Button) findViewById(R.id.bContinue);
        bContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });

        // display data differently depending on the users choice of travel
        switch(mode){
            case "walking":{
                tvTitle.setText("By walking...");
                String text = "You save " + emissons + ",\n";
                text += "You burn " + calories + ",\n";
                text += "You save " + cost + " in gas money,\n";
                text += "You travel " + distance + ",\n";
                text += "And You travel for " + duraction + ",\n";
                tvText.setText(text);
            } break;
            case "bicycling": {
                tvTitle.setText("By biking...");
                String text = "You save " + emissons + ",\n";
                text += "You burn " + calories + ",\n";
                text += "You save " + cost + " in gas money,\n";
                text += "You travel " + distance + ",\n";
                text += "And You travel for " + duraction + ",\n";
                tvText.setText(text);
            } break;
            case "transit": {
                tvTitle.setText("By taking public transit...");
                String text = "You will burn less fossil fuel than if you drove a car (" + emissons + "),\n";
                text += "You burn " + calories + ",\n";
                text += "You save a little less than " + cost + " in gas money,\n";
                text += "You travel " + distance + ",\n";
                text += "And You travel for " + duraction + ",\n";
                tvText.setText(text);
            } break;
            case "driving":
            default: {
                tvTitle.setText("By driving...");
                String text = "You use " + emissons + ",\n";
                text += "You burn " + calories + ",\n";
                text += "You spend " + cost + " in gas money,\n";
                text += "You travel " + distance + ",\n";
                text += "And You travel for " + duraction + ",\n";
                tvText.setText(text);
            } break;
        }

    }
}
