package uk.ac.southwales.beacontest;

import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconData;
import org.altbeacon.beacon.BeaconDataNotifier;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;
import org.altbeacon.beacon.client.DataProviderException;

import java.util.Arrays;

import uk.ac.southwales.beacontest.classes.Notification;


public class BeaconActivity extends BaseActivity {
    private TextView info;
    private Button startBroadcast;
    private Button stopBroadcast;
    private Notification notification;
    private BeaconTransmitter beaconTransmitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        info = (TextView) findViewById(R.id.info);



        startBroadcast = (Button) findViewById(R.id.startBroadcast);
        stopBroadcast = (Button) findViewById(R.id.stopBroadcast);

        // we may already have one
        beaconTransmitter = ((BaseApplication) getApplication()).getApplicationBeaconTransmitter();

        startBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // ID 1 is generated once
                    // ID 2 is generated when the beacon is created
                    // ID 3 is not used
                    Beacon beacon = new Beacon.Builder()
                            .setId1("F0C75D89-D12A-41DE-9097-45DF1A2604CE")
                            .setId2("1")
                            .setId3("1")
                            .setTxPower(-59)
                            .setDataFields(Arrays.asList(new Long[]{0l}))
                            .build();
                    BeaconParser beaconParser = new BeaconParser()
                            //.setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25");
                            // use iBeacon spec instead
                            .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24");
                    beaconTransmitter = new BeaconTransmitter(getApplicationContext(), beaconParser);
                    beaconTransmitter.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY);
                    beaconTransmitter.setAdvertiseTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH);

                    beaconTransmitter.startAdvertising(beacon, new AdvertiseCallback() {
                        @Override
                        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                            super.onStartSuccess(settingsInEffect);

                            info.setText("Beacon Started.");

                            notification = new Notification(activity,
                                    "Beacon Started",
                                    "A beacon is broadcasting on this device, touch here for more options.",
                                    true);
                            notification.makeNotification();

                            startBroadcast.setVisibility(View.GONE);
                            stopBroadcast.setVisibility(View.VISIBLE);

                            ((BaseApplication) getApplication()).setApplicationBeaconTransmitter(beaconTransmitter);
                        }

                        @Override
                        public void onStartFailure(int errorCode) {
                            super.onStartFailure(errorCode);

                            info.setText("Beacon not Started. Error Code:" + String.valueOf(errorCode));
                        }
                    });
                } else {
                    info.setText("Beacon peripheral mode is not available on this device.");
                }
            }
        });

        stopBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (beaconTransmitter != null) {
                    beaconTransmitter.stopAdvertising();
                    beaconTransmitter.setBeacon(null);
                    beaconTransmitter = null;
                    ((BaseApplication) getApplication()).setApplicationBeaconTransmitter(null);
                }

                info.setText("Beacon Stopped.");

                if (notification != null)
                    notification.removeNotification();

                startBroadcast.setVisibility(View.VISIBLE);
                stopBroadcast.setVisibility(View.GONE);
            }
        });

        if (beaconTransmitter == null) {
            startBroadcast.setVisibility(View.VISIBLE);
            stopBroadcast.setVisibility(View.GONE);
        } else {
            startBroadcast.setVisibility(View.GONE);
            stopBroadcast.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_beacon;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (beaconManager != null)
            beaconManager.setBackgroundMode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (beaconManager != null)
            beaconManager.setBackgroundMode(false);

        beaconTransmitter = ((BaseApplication) getApplication()).getApplicationBeaconTransmitter();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        beaconTransmitter = ((BaseApplication) getApplication()).getApplicationBeaconTransmitter();
    }
}
