package uk.ac.southwales.beacontest;

import android.bluetooth.le.AdvertiseSettings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconData;
import org.altbeacon.beacon.BeaconDataNotifier;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;
import org.altbeacon.beacon.client.DataProviderException;
import org.altbeacon.beacon.simulator.BeaconSimulator;

import java.util.Arrays;
import java.util.List;


public class BeaconActivity extends ActionBarActivity {
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);

        Beacon beacon = new Beacon.Builder()
                .setId1("2f234454-cf6d-4a0f-adf2-f4911ba9ffa6")
                .setId2("1")
                .setId3("2")
                .setManufacturer(0x0118)
                .setTxPower(-59)
                .setDataFields(Arrays.asList(new Long[]{0l}))
                .build();
        BeaconParser beaconParser = new BeaconParser()
                .setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25");
        BeaconTransmitter beaconTransmitter = new BeaconTransmitter(getApplicationContext(), beaconParser);
        beaconTransmitter.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY);
        beaconTransmitter.setAdvertiseTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH);
        beaconTransmitter.startAdvertising(beacon);

        info = (TextView) findViewById(R.id.info);
        info.setText("Beacon Started.");
    }
}
