package uk.ac.southwales.beacontest;

import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public class RangingActivity extends BaseActivity implements BeaconConsumer {
    protected static final String TAG = "RangingActivity";
    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
    private BaseActivity activity;

    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this;

        beaconManager.bind(this);
        beaconManager.debug = true;

        info = (TextView) findViewById(R.id.info);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_ranging;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(true);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(false);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    Beacon firstBeacon = beacons.iterator().next();
                    logToDisplay("The first beacon "+firstBeacon.toString()+" is about "+firstBeacon.getDistance()+" meters away.");            }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }
    }
    private void logToDisplay(final String line) {
        runOnUiThread(new Runnable() {
            public void run() {
                info.append(line+"\n");
            }
        });
    }
}

