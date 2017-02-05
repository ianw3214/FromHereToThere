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
        getSupportActionBar().hide();
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
                finish();
            }
        });

        // display data differently depending on the users choice of travel
        switch(mode){
            case "walking":{
                tvTitle.setText("By walking, you...");
                String text = "Save " + emissons + " over driving\n";
                text += "Burn " + calories + " calories\n";
                text += "Save " + cost + " in gas money\n";
                text += "Travel " + distance + " km in " + duraction + "";
                tvText.setText(text);
            } break;
            case "bicycling": {
                tvTitle.setText("By biking, you...");
                String text = "Save " + emissons + " over driving\n";
                text += "Burn " + calories + " calories\n";
                text += "Save " + cost + " in gas money\n";
                text += "Travel " + distance + " km in " + duraction + "";
                tvText.setText(text);
            } break;
            case "transit": {
                tvTitle.setText("By taking public transit, you...");
                String text = "Burn less fossil fuel than if you drove a car (" + emissons + ")\n";
                text += "Burn " + calories + " calories\n";
                text += "Save a little less than " + cost + " in gas money\n";
                text += "Travel " + distance + " km in " + duraction + "";
                tvText.setText(text);
            } break;
            case "driving":
            default: {
                tvTitle.setText("By driving, you...");
                String text = "Produce " + emissons + "\n";
                text += "Burn " + calories + " calories\n";
                text += "Spend " + cost + " in gas money\n";
                text += "Travel " + distance + " km in " + duraction + "";
                tvText.setText(text);
            } break;
        }

    }
}
