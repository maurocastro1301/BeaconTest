package uk.ac.southwales.beacontest;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;

import java.util.Arrays;


public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button buttonBeacon = (Button) findViewById(R.id.buttonBeacon);
        buttonBeacon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, BeaconActivity.class));
            }
        });

        Button buttonRanging = (Button) findViewById(R.id.buttonRanging);
        buttonRanging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, RangingActivity.class));
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }
}
