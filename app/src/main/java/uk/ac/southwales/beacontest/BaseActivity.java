package uk.ac.southwales.beacontest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by nwillia2 on 16/01/2015.
 */
public abstract class BaseActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        if (toolbar != null) {
//            setSupportActionBar(toolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
    }

    protected abstract int getLayoutResource();
}