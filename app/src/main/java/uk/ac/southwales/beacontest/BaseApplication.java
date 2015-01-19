package uk.ac.southwales.beacontest;

import android.app.Application;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconTransmitter;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;

/**
 * Created by nwillia2 on 16/01/2015.
 */
public class BaseApplication extends Application {
    private BackgroundPowerSaver backgroundPowerSaver;
    private BeaconTransmitter beaconTransmitter;

    @Override
    public void onCreate() {
        super.onCreate();
        // Simply constructing this class and holding a reference to it in your custom Application class
        // enables auto battery saving of about 60%
        backgroundPowerSaver = new BackgroundPowerSaver(this);
    }

    public void setApplicationBeaconTransmitter(BeaconTransmitter bt){
        this.beaconTransmitter = bt;
    }

    public BeaconTransmitter getApplicationBeaconTransmitter(){
        return this.beaconTransmitter;
    }
}
