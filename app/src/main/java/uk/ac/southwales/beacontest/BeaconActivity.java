package uk.ac.southwales.beacontest;

import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;

import java.util.Arrays;

import uk.ac.southwales.beacontest.classes.Notification;


public class BeaconActivity extends BaseActivity {
    private TextView info;
    private String NOTIFICATION_ID = "BEACON_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        info = (TextView) findViewById(R.id.info);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

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
            beaconTransmitter.startAdvertising(beacon, new AdvertiseCallback() {
                @Override
                public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                    super.onStartSuccess(settingsInEffect);

                    info.setText("Beacon Started.");

                    new Notification(activity,
                            "Beacon Started",
                            "A beacon is broadcasting on this device, touch here for more options.",
                            true).makeNotification();
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
    }
}
